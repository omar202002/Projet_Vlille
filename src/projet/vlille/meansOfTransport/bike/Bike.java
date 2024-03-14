package projet.vlille.meansOfTransport.bike;

import projet.vlille.meansOfTransport.MeanOfTransport;

/**
 * Bike is the class that represents a bike
 */
public class Bike extends MeanOfTransport {
	
	/**
	 * Bike's constructor
	 */
	public Bike() {
		super();
	}

	@Override
	public boolean isOutOfService() {
		return this.nbLocations == MAX_USE;
  }
	/** provides this bike's name followed by its id
	 * @return this bike's name followed by its id
	 */
	public String toString() {
		return "Bike" + this.id;
  } 
}
