package projet.vlille.user;

import projet.vlille.controlCenter.ControlCenter;

/**
 * User is the class that represents a user
 */
public class User {

	/** the id associated to each User instance */
	private int id;
	
	/**
	 * Create a new user
	 */
	public User() {
		this.id = (ControlCenter.CENTER.getIds_Generator().generateUserId());
	}

	/**
	 * provide this user's id
	 * @return this user's id
	 */
	public int getId() {
		return id;
	}
	
	@Override
	/** provide this user's name followed by his id
	 * @return this user's name followed by his id
	 */
	public String toString() {
		return "User" + getId();	}
}
