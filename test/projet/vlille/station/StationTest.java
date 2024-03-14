package projet.vlille.station;

import static org.junit.Assert.*;
import org.junit.Test;

import projet.vlille.controlCenter.ControlCenter;
import projet.vlille.exception.OutOfServiceException;
import projet.vlille.exception.StationEmptyException;
import projet.vlille.exception.StationFullException;
import projet.vlille.exception.UnregistredUserException;
import projet.vlille.meansOfTransport.MeanOfTransport;
import projet.vlille.meansOfTransport.bike.Bike;
import projet.vlille.user.User;

public class StationTest {

    private Station station = new Station();

    @Test
    public void testGetPlaces() {
        assertNotNull(station.getPlaces());
    }

    @Test
    public void testGetId() {
        assertTrue(station.getId() > 0);
    }

    @Test
    public void testGetCapacity() {
        assertTrue(station.getCapacity() >= Station.MIN_NB_BIKE && station.getCapacity() <= Station.MAX_NB_BIKE);
    }

    @Test
    public void testAddMeanOfTransport() throws StationFullException {
        MeanOfTransport mot = new Bike();
        station.addMeanOfTransport(mot);
        assertTrue(station.getPlaces().containsValue(mot));
    }

    @Test
    public void testRemoveMeanOfTransport() throws StationFullException {
        MeanOfTransport mot = new Bike();
        station.addMeanOfTransport(mot);
        station.removeMeanOfTransport(mot);
        assertFalse(station.getPlaces().containsValue(mot));
    }

    @Test
    public void testIsFull() throws StationFullException {
        assertFalse(station.isFull());
        for (int i = 0; i < station.getCapacity(); i++) {
            station.addMeanOfTransport(new Bike());
        }
        assertTrue(station.isFull());
    }

    @Test
    public void testIsEmpty() throws StationFullException {
        assertTrue(station.isEmpty());
        station.addMeanOfTransport(new Bike());
        assertFalse(station.isEmpty());
    }

    @Test
    public void testUserTakeMoT() throws StationEmptyException, OutOfServiceException, StationFullException {
        User user = new User();
        MeanOfTransport mot = new Bike();
        station.addMeanOfTransport(mot);
        station.userTakeMoT(user);
        assertFalse(station.getPlaces().containsValue(mot));
    }

    @Test
    public void testUserDropMot() throws StationFullException, UnregistredUserException {
        User user = new User();
        MeanOfTransport mot = new Bike();
        ControlCenter.CENTER.addMoTinUse(user, mot);
        station.userDropMot(user);
        assertTrue(station.getPlaces().containsValue(mot));
    }

    @Test
    public void testGetNbAvailableMeans() throws StationFullException {
        assertEquals(0, station.getNbAvailableMeans());
        MeanOfTransport mot1 = new Bike();
        MeanOfTransport mot2 = new Bike();
        station.addMeanOfTransport(mot1);
        station.addMeanOfTransport(mot2);
        assertEquals(2, station.getNbAvailableMeans());
    }
}