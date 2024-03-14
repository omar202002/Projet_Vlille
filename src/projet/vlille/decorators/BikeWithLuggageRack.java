package projet.vlille.decorators;
import projet.vlille.meansOfTransport.bike.*;

/**
 * BikeDecorator is the abstract class that represents a bike decorator
 */
public class BikeWithLuggageRack extends BikeDecorator{

	
	/**
     * BikeDWithLuggage's constructor
     * @param b the bike to be decorated 
     */
    public BikeWithLuggageRack (Bike b) {
        super(b);
    }

    /** provides this bike's name followed by its id
	 * @return this bike's name followed by its id
	 */
    public String toString() {
        return "Bike" + this.id + "equipped with a luggage rack";
    }
    
}