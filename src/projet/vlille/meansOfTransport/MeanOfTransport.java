package projet.vlille.meansOfTransport;

import projet.vlille.controlCenter.ControlCenter;
import projet.vlille.exception.OutOfServiceException;

/**
* A MeansOfTransport that refers to a certain type of transport with different informations
*/
public abstract class MeanOfTransport {

  protected boolean available;  // it tells us if this MoT is available or not
  protected int nbLocations;    // the number of times that the MoT has been taken
  public static final int MAX_USE = 20;		// the limit of nbLocations
  protected int id;				// the identifier

  /**
  * build a MeanOfTransport
  */
  public MeanOfTransport() {
	this.available = true;
	this.nbLocations=0;
	this.id = ControlCenter.CENTER.getIds_Generator().generateMoTId();
  }

  /**
  * get the number of times the type of transport has been taken
  * @return the number of times the type of transport has been taken
  */
  public int getNbLocations() {
    return this.nbLocations;
  }
  /**
  * reset the state of the type of transport
  */
  public void reset() {
    this.available = true;
    this.nbLocations = 0;
  }
  
  /**
   * check if this MoT is out of service
   * @return true if this MoT's nbLocations equals the MAX_USE, and false if itsn't
   */
  public boolean isOutOfService() {
	  return this.nbLocations == MAX_USE;
  }

  /**
   * check if this MoT is available
   * @return true if this MoT is available, and false if itsn't
   */
  public boolean isAvailable() {
    return this.available;
  }
  
  /**
   * use this MoT and increment its nbLocation 
   * @throws OutOfServiceException when this MoT is out of service
   */
  public void use() throws OutOfServiceException {
	  if (!this.isAvailable()) {
		  throw new OutOfServiceException();
	  }
	  if (this.nbLocations == MAX_USE - 1) {
		  this.available=false;
	  }
	  this.nbLocations++;
  }
  
  /**
   * reset this MoT's state
   */
  public abstract String toString() ;
}
