package projet.vlille.program;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import projet.vlille.controlCenter.ControlCenter;
import projet.vlille.exception.OutOfServiceException;
import projet.vlille.exception.StationEmptyException;
import projet.vlille.exception.StationFullException;
import projet.vlille.exception.UnregistredUserException;
import projet.vlille.meansOfTransport.bike.bikes.ElectricBike;
import projet.vlille.station.Station;
import projet.vlille.user.User;
import projet.vlille.steal.*;

/**
 * This class represents the program of the vlille project
 */
public class Program {
	
	/** The timer of the program */
	private Timer timer;

	/** The duration of the program */
	private int duration;

	/** The number of stations in the program */
	private int nbOfStations;

	/** The interval of the program */
	private final int interval ;

	/** A random generator */
	private Random random = new Random();

	/** The stealer of the program */
	protected Stealer stealer;
	
	
	/**
	 * Create a new program
	 * @param duration: the duration of the program
	 * @param nbOfStations: the number of stations in the program
	 */
	public Program(int duration, int nbOfStations) {
		this.duration = duration;
		this.nbOfStations = nbOfStations;
		this.interval = duration/3;
		this.timer = new Timer();
		this.stealer = new Stealer();
	}
	
	/**
	 * run the program's simulation
	 * @throws StationFullException
	 */
	public void Run() throws StationFullException {
		System.out.println("___________________Start Of Simulation__________________");
		System.out.println("");
		ControlCenter.CENTER.initStations(nbOfStations);
		ControlCenter.CENTER.distribute();
		timer.scheduleAtFixedRate(new TimerTask() {
			int cons = duration*1/3;
			@Override
			public void run() {
				if (duration > interval) {
					try {
						randomTakeAndDrop(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					duration-=cons;
				}
				else if	(duration==interval) {
					ControlCenter.CENTER.redistribute();
					stealer.steal();
					duration-=cons;
					
				}
				else if (duration==0) {
					System.out.println("");
					System.out.println("____________________End Of Simulation___________________");
					timer.cancel();
				}
				 
			}
			
		}, 0, 1111);
	}
	
	/**
	 * perform take and drop actions by different users in different stations randomly 
	 * @param min_range: the minimum number of times take and drop actions will be performed
	 * @throws InterruptedException 
	 */
	public void randomTakeAndDrop(int min_range) throws InterruptedException {
		for (int i=0; i< min_range + random.nextInt(10); i++) {
			Station station = getRandomStation();
			try {
				station.userTakeMoT(new User());
				Thread.sleep(999);
			} catch (StationEmptyException | OutOfServiceException e) {
				if(e instanceof OutOfServiceException) {
					ControlCenter.CENTER.checkForOutOfSericeMoTs();
				}
				else {
					System.out.printf("You can't take an MoT from here! the %s is Empty!\n", station.toString());					
				}
			}
		}
		for (int i=0; i< min_range + random.nextInt(10); i++) {
			Station station = getRandomStation();
			try {
				station.userDropMot(getRandomUser());
				Thread.sleep(999);
			} catch (StationFullException | UnregistredUserException e) {
				// TODO Auto-generated catch block
				if(e instanceof StationFullException) {
					System.out.printf("You can't drop your MoT here! the %s is Full!\n", station.toString());
				}
			}
		}
	}
	
	/**
	 * provide a random station from the control center stations database
	 * @return Station: a random station from the control center stations database
	 */
	private Station getRandomStation() {
		int nb = random.nextInt(nbOfStations-1);
		return ControlCenter.CENTER.getStations().get(nb);
	}
	
	/**
	 * provide a random user from the control center users database
	 * @return User: a random user from the control center users database
	 */
	private User getRandomUser() {
		int nb_of_users = ControlCenter.CENTER.getMoTsInUse().size();
		int nb = random.nextInt(nb_of_users-1);
		ArrayList<User> users = new ArrayList<User>(ControlCenter.CENTER.getMoTsInUse().keySet());
		return users.get(nb);
	}
}
