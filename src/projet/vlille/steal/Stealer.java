package projet.vlille.steal;

import projet.vlille.controlCenter.ControlCenter;
import projet.vlille.exception.StationEmptyException;
import projet.vlille.meansOfTransport.MeanOfTransport;
import projet.vlille.station.Station;

/**
 * Stealer is the class that represents a stealer
 */
public class Stealer {

	/*
	 * Steal a MoT from a station
	 */
    public void steal() {
		for (Station station : ControlCenter.CENTER.getStations()) {
			if (station.getNbAvailableMeans() == 1) {
				int prob = (int) (Math.random() * 2);
				if (prob == 1) {
					try {
						MeanOfTransport mot = station.getPlaces().get(station.placeOfTheFirstAvailableMoT());
						ControlCenter.CENTER.getMoTsOfTransport().remove(mot);
						station.getPlaces().put(station.placeOfTheFirstAvailableMoT(), null) ;

						System.out.printf("%s had just been stolen!\n", mot.toString());
					} catch (StationEmptyException e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Looks Like Someone tried to steal an MoT in the "+station.toString());
				}
			}
		}
	}
}