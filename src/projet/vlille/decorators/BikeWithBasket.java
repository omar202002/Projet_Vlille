package projet.vlille.decorators;
import projet.vlille.meansOfTransport.bike.*;

/**
 * BikeDecorator is the abstract class that represents a bike decorator
 */
public class BikeWithBasket extends BikeDecorator{
    

	/**
     * BikeWithBasket's constructor
     * @param bike the bike to be decorated
     */
    public BikeWithBasket(Bike bike) {
		super(bike);
	}

    /** provides this bike's name followed by its id
	 * @return this bike's name followed by its id
	 */
	public String toString() {
        return "Bike" + this.id + "equipped with a basket";
    }

}
