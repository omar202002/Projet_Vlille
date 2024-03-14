package projet.vlille.staff;

import projet.vlille.controlCenter.ControlCenter;
import projet.vlille.meansOfTransport.MeanOfTransport;

/**
 * Staff is the class that represents a staff member
 */
public abstract class Staff {

	/** the id associated to each Staff instance */
	protected int id;
	
	/**
	 * Staff's constructor
	 */
	public Staff() {
		this.id = ControlCenter.CENTER.getIds_Generator().generateStaffId();
	}
	
	/**
	 * run the operation process
	 * @param mot the mean of transport to be operated
	 */
	protected abstract void operate(MeanOfTransport mot);
}
