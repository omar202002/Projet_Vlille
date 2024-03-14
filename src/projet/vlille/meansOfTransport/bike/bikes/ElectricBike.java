package projet.vlille.meansOfTransport.bike.bikes;

import projet.vlille.meansOfTransport.bike.Bike;

/**
 * ElectricBike is the class that represents an electric bike
 */
public class ElectricBike extends Bike{
	/**
	 * ElectricBike's constructor
	 */
	public ElectricBike() {
	    super();
	  }
	
	/** provides this bike's name followed by its id
	 * @return this bike's name followed by its id
	 */
	public String toString() {
		return "ElectricBike" + this.id;
  } 
}
