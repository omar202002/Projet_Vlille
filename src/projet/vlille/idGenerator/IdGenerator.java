package projet.vlille.idGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * IdGenerator is a class that provides unique identifiers for stations, MoTs, staffs and users
 */
public class IdGenerator {

	/** the users id database */
	private ArrayList<Integer> usedUsersIds;

	/** the database of the station's ids */
	private ArrayList<Integer> usedStationsIds;

	/** the database of the staff's ids */
	private ArrayList<Integer> usedStaffIds;

	/** the database of the MoT's ids */
	private ArrayList<Integer> usedMoTsIds;
	
	/**
	 * IdGenerator's constructor
	 */
	public IdGenerator() {
		this.usedUsersIds = new ArrayList<Integer>();
		this.usedStationsIds = new ArrayList<Integer>();
		this.usedStaffIds = new ArrayList<Integer>();
		this.usedMoTsIds = new ArrayList<Integer>();
	}
	
	/**
	 * provide an unique identifier
	 * @param idRange starting value of identifiers
	 * @param idsBase identifiers database
	 * @return the generated id
	 */
	private int generateIdByCategory(int idRange, ArrayList<Integer> idsBase) {
		Random random = new Random();
		int id = idRange+random.nextInt(999);
		while(idsBase.contains(id)) {
			id = idRange+random.nextInt(999);
		}
		idsBase.add(id);
		return id;
	}
	/**
	 * provide an unique id for a station
	 * @return the generated id
	 */
	public int generateStationId() {
		return generateIdByCategory(1000, usedStationsIds);
	}
	
	/**
	 * provide an unique id for a MoT
	 * @return the generated id
	 */
	public int generateMoTId() {
		return generateIdByCategory(2000, usedMoTsIds);
	}
	
	/**
	 * provide an unique id for a staff
	 * @return the generated id
	 */
	public int generateStaffId() {
		return generateIdByCategory(3000, usedStaffIds);
	}
	
	/**
	 * provide an unique id for an user
	 * @return the generated id
	 */
	public int generateUserId() {
		return generateIdByCategory(4000, usedUsersIds);
	}
	
	
}
