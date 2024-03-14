package projet.vlille.staff.staffs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import projet.vlille.controlCenter.ControlCenter;
import projet.vlille.exception.StationFullException;
import projet.vlille.meansOfTransport.MeanOfTransport;
import projet.vlille.meansOfTransport.bike.Bike;
import projet.vlille.staff.Staff;

/**
 * Repairer is the class that represents a repairer
 */
public class Repairer extends Staff {

	/** The duration it takes to the repairer to repair a MoT*/
	private final int PROCESS_DURATION_MS = 5000;

	/** the scheduler that will run the repair operation after the process duration ends */
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
	
	/**
	 * Repairer's constructor
	 */
	public Repairer() {
		super();
	}
	
	/**
	 * repair the mot by reseting its nb of location
	 * @param mot the mean of transport to be repaired
	 */
	private void repair(MeanOfTransport mot) {
		mot.reset();
		
	}
	@Override
	/**
	 * run the repair process 
	 * @param mot the mean of transport to be repaired
	 */
	public void operate(MeanOfTransport mot) {
		scheduler.schedule(() -> {
			repair(mot);
			System.out.println( toString() + ": "+ mot.toString() + " has just been repaired!");
			try {
				ControlCenter.CENTER.distributeRepairedMoT((Bike) mot);
			} catch (StationFullException e) {
				e.printStackTrace();
			}
			scheduler.shutdown();
		}, PROCESS_DURATION_MS, TimeUnit.MILLISECONDS);
		
	}
	
	/** provides this repairer's name followed by his id
	 * @return this repairer's name followed by his id
	 */
	@Override 
	public String toString() {
		return "Repairer" + this.id;
	}
}
