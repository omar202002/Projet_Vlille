package projet.vlille.controlCenter;

import java.util.ArrayList;
import java.util.HashMap;

import projet.vlille.exception.StationEmptyException;
import projet.vlille.exception.StationFullException;
import projet.vlille.idGenerator.IdGenerator;
import projet.vlille.meansOfTransport.MeanOfTransport;
import projet.vlille.meansOfTransport.bike.Bike;
import projet.vlille.staff.staffs.Repairer;
import projet.vlille.station.Station;
import projet.vlille.user.User;

/**
 * This class represents the control center of the vlille project
 */
public class ControlCenter {

	/** the list of meansOfTransport Objects */
	private ArrayList<MeanOfTransport> meansOfTransport = new ArrayList<MeanOfTransport>();

	/** the list of stations of the Control Center */
	private ArrayList<Station> stations = new ArrayList<Station>();

	/** the list of empty stations of the Control Center */
	private ArrayList<Station> emptyStations = new ArrayList<Station>();

	/** the list of full stations of the Control Center */
	private ArrayList<Station> fullStations = new ArrayList<Station>();

	/** the hashmap that's representing each mean of transport and the station it's in */
	private HashMap<MeanOfTransport, Station> means = new HashMap<MeanOfTransport, Station>();

	/** the Id generator of the Control Center */
	private IdGenerator ids_Generator = new IdGenerator();

	/** the hashmap that's representing each user and the mot he's using */
	private HashMap<User, MeanOfTransport> meansInUse = new HashMap<User,MeanOfTransport>();

	/** the list of means of transport that are out of service */
	private ArrayList<MeanOfTransport> outOfServiceMeans = new ArrayList<MeanOfTransport>();

	/** the list of means of transport that are under repair */
	private ArrayList<MeanOfTransport> meansUnderRepair = new ArrayList<MeanOfTransport>();

	/** the unique instance of this class */
	public static final ControlCenter CENTER = new ControlCenter();


	/**
	 * initiate the ControlCenter's stations
	 * @param nb the number of stations to be created 
	 */
	public void initStations(int nb) {
		for (int i = 0 ; i<nb; i++) {
			createNewStation();
		}
	}
	
	/**
	 * create a new station and add it to this center's stations database
	 */
	public void createNewStation() {
		Station s = new Station();
		this.stations.add(s);
	}
	
	/**
	 * perform the distribution of mots in this center's stations
	 * @throws StationFullException
	 */
	public void distribute() throws StationFullException {
		for(Station station : this.stations){
			while (!station.isFull()){
				MeanOfTransport bike = new Bike();
				station.addMeanOfTransport(bike);
				means.put(bike, station);
				System.out.printf("The %s has been filled whith %s\n", station.toString(), bike.toString());
			}
		}
	}

    /**
	 * perform the redistribution of mots in case of empty stations
	 */
	public void redistributeForEmptyStations() {
		for (Station emptyStation : this.getEmptyStations()){
			System.out.printf("-----------------%s------------------\n", emptyStation.toString()+" is empty!");
			System.out.println("");
			
				for (Station station : this.stations){

					if (!station.isEmpty() && !station.isFull() && !(station.equals(emptyStation))) {

						try {
							MeanOfTransport mot_to_be_moved = station.getPlaces().get(station.placeOfTheFirstAvailableMoT());
							station.removeMeanOfTransport(mot_to_be_moved);
							emptyStation.addMeanOfTransport(mot_to_be_moved);
							System.out.println(mot_to_be_moved.toString() + " of the " + station.toString() + " was moved in the " + emptyStation.toString());
							System.out.println("");

							try {                           
								Thread.sleep(600);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
						} catch (StationEmptyException | StationFullException e) {
							e.printStackTrace();
						}

					}
				}
			
		}
	}
    
	/**
	 * perform the redistribution of mots in case of full stations
	 */
	public void redistributeForFullStations() {
		updateFullAndEmptyStations();
		for (Station fullStation : fullStations){
			
			System.out.printf("------------------%s------------------\n", fullStation.toString()+" is full!");
			System.out.println("");
			
			for (Station station : this.stations){
				if (fullStation.getNbAvailableMeans()<=fullStation.getCapacity()/2) {
					break;
				}
				if (!station.isFull() && !(station.equals(fullStation))) {
						
					try {
						MeanOfTransport mot_to_be_moved = fullStation.getPlaces().get(fullStation.placeOfTheFirstAvailableMoT());
						fullStation.removeMeanOfTransport(mot_to_be_moved);
						station.addMeanOfTransport(mot_to_be_moved);
						System.out.println(mot_to_be_moved.toString() + " of the " + fullStation.toString() + " was moved in the " + station.toString());
						System.out.println("");
	
						try {                                   
							Thread.sleep(600);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	
					} catch (StationEmptyException | StationFullException e) {
						e.printStackTrace();
					};
				}
			} 
	
		}
		
		}	
    
	/** *
	 * perform the redistribution of mots in this center's stations
	 */
	public void redistribute(){
		System.out.println("");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<Redistribution>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("");
		
		this.redistributeForEmptyStations();
		this.redistributeForFullStations();
	}

	/**
	 * update the lists of full and empty stations
	 */
	public void updateFullAndEmptyStations(){
		this.emptyStations = new ArrayList<Station>();
		this.fullStations = new ArrayList<Station>();
		for (Station station : this.stations){
			if (station.isFull()){
				this.fullStations.add(station);
			}
			else if(station.isEmpty()){
				this.emptyStations.add(station);
			}
		}
	}
    
	/**
	 * check if some mots are out of service and send them back for repair
	 */
	public void checkForOutOfSericeMoTs(){
		this.outOfServiceMeans = new ArrayList<MeanOfTransport>();
		for( MeanOfTransport mot: means.keySet()){
			if (mot.isOutOfService()){
				outOfServiceMeans.add(mot);
				sendMoTtoReparation(mot);
			}
		}
		repairMoT();
	}
    
	/**
	 * send the given mot back for repair
	 * @param mot the mean of transport to be sent back for repair
	 */
	private void sendMoTtoReparation(MeanOfTransport mot) {
		Station station = means.get(mot);
		station.removeMeanOfTransport(mot);
		meansUnderRepair.add(mot);
		System.out.println("CENTER: "+ mot.toString() + " has just been sent back for repair!");
	}
	
	/**
	 * repair the mots that are under repair
	 */
	private void repairMoT() {
		Repairer repairer = new Repairer();
		for(MeanOfTransport mot : meansUnderRepair) {
			repairer.operate(mot);
			outOfServiceMeans.remove(mot);
		}
		meansUnderRepair = new ArrayList<MeanOfTransport>();
	}
	
	/**
	 * distribute the given mot in the first available station
	 * @param mot the mean of transport to be distributed
	 * @throws StationFullException if the station is full
	 */
	public void distributeRepairedMoT(MeanOfTransport mot) throws StationFullException {
		for(Station station: stations) {
			if(!station.isFull()){
				station.addMeanOfTransport(mot);
				means.put(mot, station); 
				System.out.println(station.toString()+": "+ mot.toString()+ " has just been dropped off after its repair!");
				break;
			}
		}
	}
    
	/**
	 * add the given station to this control center's stations database
	 * @param station the station to be added
	 */
	public void addStation(Station station) {
		this.stations.add(station);
	}
	
	/**
	 * @return the list of means of transport that are out of service
	 */
	public ArrayList<MeanOfTransport> getOutOfServiceMoTs() {
		return outOfServiceMeans;
	}
	
	
	
	/**
	 * check if the given MoT is out of service
	 * @param mot the mean of transport
	 * @return true if the given mot is Out of service and false if itsn't
	 */
	public boolean isTheMoToutOfService(MeanOfTransport mot) {
		return mot.isOutOfService();
	}
	
	/**
	 * provide this center's list of stations
	 * @return this center's list of stations
	 */
	public ArrayList<Station> getStations() {
		return stations;
	}

	/**
	 *  add a pair which contains user and the mean of transport he's using to this center's users database
	 * @param user the user to be added
	 * @param mot the mean of transport to be added
	 */
	public void addMoTinUse(User user, MeanOfTransport mot){
		this.meansInUse.put(user,mot);
	}
	
	/**
	 *  remove the given user from the users database
	 * @param user the user to be removed from the users database
	 */
	public void removeMoTinUse(User user){
		this.meansInUse.remove(user);
	}
	
	/**
	 * provide the hashmap that's representing each user and the mot he's using 
	 * @return the hashmap that's representing each user and the mot he's using 
	 */
	public HashMap<User,MeanOfTransport> getMoTsInUse(){
		return this.meansInUse;
	}

	/**
	 * provide the list of all means of transport 
	 * @return the list of all means of transport
	 */
	public ArrayList<MeanOfTransport> getMoTsOfTransport() {
		return meansOfTransport;
	}
	
	/**
	 * provide the mot which is associated to the given user
	 * @param user the user who's is using an mot
	 * @return the associated mot to user 
	 */
	public MeanOfTransport getMoT_ByUser(User user) {
		return this.meansInUse.get(user);
	}
	
	/**
	 * provide the updated list of full stations
	 * @return the updated list of full stations
	 */
	public ArrayList<Station> getFullStations(){
		updateFullAndEmptyStations();
		return this.fullStations;
	}
	
	/**
	 * provide the updated list of empty stations
	 * @return the updated list of empty stations
	 */
	public ArrayList<Station> getEmptyStations(){
		updateFullAndEmptyStations();
		return this.emptyStations;
	}
	
	/**
	 * provide the center's Id generator
	 * @return this center's Id generator
	 */
	public IdGenerator getIds_Generator() {
		return ids_Generator;
	}
	
	
}
