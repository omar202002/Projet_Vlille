package projet.vlille.decorators;
import projet.vlille.meansOfTransport.bike.*;

/**
 * BikeDecorator is the abstract class that represents a bike decorator
 */
public abstract class BikeDecorator extends Bike{
    protected Bike bike;

    /**
     * BikeDecorator's constructor
     * @param bike the bike to be decorated
     */
	public BikeDecorator(Bike bike) {
		this.bike = bike;
	}

	/** provides this bike's name followed by its id
	 * @return this bike's name followed by its id
	 */
    public abstract String toString();

  

}
