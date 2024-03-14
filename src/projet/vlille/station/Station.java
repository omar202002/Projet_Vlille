package projet.vlille.station;

import java.util.HashMap;
import projet.vlille.controlCenter.ControlCenter;
import projet.vlille.exception.OutOfServiceException;
import projet.vlille.exception.StationEmptyException;
import projet.vlille.exception.StationFullException;
import projet.vlille.exception.UnregistredUserException;
import projet.vlille.meansOfTransport.MeanOfTransport;
import projet.vlille.user.User;

/**
 * In this class code, we'll use "MoT" as reference to mean of transport.
 */
public class Station  {
    
	/** Maximum station's capacity*/
	public static final int MAX_NB_BIKE = 20;

	/** Minimum station's capacity*/
	public static final int MIN_NB_BIKE = 10;

	/** Station' identifier */
	private final int id;

	/** Station's capacity of holding bikes */
	private int capacity;

	/** it represents bike places in the station */
	private HashMap<Integer,MeanOfTransport> places;

	/**
	 *Build a station with a specified id
	 */
	public Station() {
		this.id = ControlCenter.CENTER.getIds_Generator().generateStationId();
		this.capacity = (int)Math.floor(Math.random()*(MAX_NB_BIKE - MIN_NB_BIKE + 1) + MIN_NB_BIKE);
		this.initPLaces();
	}

	/**
	 * Initiate station's mean of transport spaces 
	 */
	private void initPLaces() {
		this.places = new HashMap<Integer, MeanOfTransport>();
		for(int i = 1; i<=this.capacity; i++) {
			this.places.put(i, null);
		}
	}

	/**
	 * get the station's places
	 * @return the station's places
	 */
	public HashMap<Integer, MeanOfTransport> getPlaces() {
		return this.places;
	}
    
	/**
	 * set the station's places
	 * @param places the station's places
	 */
	public void setPlaces(HashMap<Integer, MeanOfTransport> places) {
		this.places = places;
	}
	
	
	/**
	 * get the station's identifier
	 * @return station's id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * get the station's capacity
	 * @return station's capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * provide the first available place in this station
	 * @throws StationFullException when this station is full
	 * @return the first available place in this station
	 */
	private int firstAvailablePlace() throws StationFullException{
		if (this.isFull()) {
			throw new StationFullException();
		}
		else if (this.isEmpty()) {
			return 1;
		}
		int res = 1;
		for (Integer place : places.keySet()) {
			if(places.get(place) == null) {
				res = place;
				break;
			}
		}
		return res;
	}
	
	/**
	 * provide the place of the first available mean of transport in this station
	 * @throws StationEmptyException when this station is empty
	 * @return the place of the first available mean of transport in this station
	 */
	public int placeOfTheFirstAvailableMoT() throws StationEmptyException{
		if(this.isEmpty()) {
			throw new StationEmptyException();
		}
		else if(this.isFull()) {
			return 1;
		}
		int motPlace = 0;
		for(int place : this.places.keySet()) {
			if (this.places.get(place) != null && !(this.places.get(place).isOutOfService())){
				motPlace = place;
				break;
			}
		}
		return motPlace;
	}
	
	/**
	 * add a meanOfTransport to the station's places
	 * @param meanOfTransport the mean of transport to be added
	 * @throws StationFullException 
	 */
	public void addMeanOfTransport(MeanOfTransport meanOfTransport) throws StationFullException {
		places.put(firstAvailablePlace(), meanOfTransport);
	}
	
	/**
	 * provide the place of the corresponding mean of transport in this station
	 * @param mot the means of transport whose place is returned
	 * @return the place of the given mean of transport in this station
	 */
	private int getPlaceNumberByMoT(MeanOfTransport mot) {
		int res=0;
		for(Integer place : places.keySet()) {
			if(places.get(place)!=null && places.get(place).equals(mot)) {
				res = place;
				break;
			}
		}
		return res;
	}
	
	/**
	 * remove a meanOfTransport from the station's places
	 * @param mot the mean of transport to be removed
	 */
	public void removeMeanOfTransport(MeanOfTransport mot) {
		this.places.replace(getPlaceNumberByMoT(mot), null);
	}
	
	/**
	 * check if this station is full
	 * @return boolean: false if this station isn't full, and true if this station is full
	 */
	public boolean isFull() {
		boolean res = true;
		for(MeanOfTransport m: places.values()) {
			if(m == null) {
				res = false;
				break;
			}
		}
		return res;
	}
	
	/**
	 * check if this station is empty
	 * @return boolean: false if this station isn't empty, and true if this station is empty
	 */
	public boolean isEmpty() {
		boolean res = true;
		for(MeanOfTransport m: places.values()) {
			if( m != null) {
				res = false;
				break;
			}
		}
		return res;
	}

	/**
	 * this method allows a user to take a MoT from this station
	 * @param user: the user who's going to take a mean of transport from this station
	 * @throws StationEmptyException when this station is empty
	 * @throws OutOfServiceException when user tries to take an out of service MoT
	 */
	public void userTakeMoT(User user) throws StationEmptyException, OutOfServiceException{
		if (this.isEmpty()) {
			throw new StationEmptyException();
		}
		int place = placeOfTheFirstAvailableMoT();
		MeanOfTransport mot = places.get(place);
		mot.use();
		ControlCenter.CENTER.addMoTinUse(user, mot);
		this.places.replace(place, null);
		System.out.println(toString()+ ": " + mot.toString() + " has just been taken by " + user.toString() + " ." );
	}

	/**
	 * this method allows a registered user to drop a MoT at this station
	 * @param user: the user who's going to drop off a mean of transport at this station
	 * @throws StationFullException when this station is full
	 * @throws UnregistredUserException when the user is unregistered in the control center database
	 */
	public void userDropMot(User user) throws StationFullException, UnregistredUserException{
		if (!ControlCenter.CENTER.getMoTsInUse().containsKey(user)) {
			throw new UnregistredUserException();
		}
		if (this.isFull()){
			throw new StationFullException();
		}
		MeanOfTransport mot = ControlCenter.CENTER.getMoT_ByUser(user);
		this.places.put(this.firstAvailablePlace(),mot);
		ControlCenter.CENTER.removeMoTinUse(user);
		System.out.println(toString()+ ": " + mot.toString() + " has just been dropped off by " + user.toString()+" ." );
	}
	
	/** provides this station's name followed by its id
	 * @return this station's name followed by its id
	 */
	@Override
	public String toString() {
		return "Station" + this.getId();
	}

	/**
	 * provides the number of available MoTs in this station
	 * @return the number of available MoTs in this station
	 */
    public int getNbAvailableMeans() {
		int res = 0;
		for(MeanOfTransport m: this.places.values()) {
			if(m != null) {
				res++;
			}
		}
		return res;
    }
	
}
