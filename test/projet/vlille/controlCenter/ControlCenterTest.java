package projet.vlille.controlCenter;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;


import projet.vlille.exception.OutOfServiceException;
import projet.vlille.exception.StationEmptyException;
import projet.vlille.exception.StationFullException;
import projet.vlille.idGenerator.IdGenerator;
import projet.vlille.meansOfTransport.MeanOfTransport;
import projet.vlille.meansOfTransport.bike.Bike;
import projet.vlille.station.Station;
import projet.vlille.user.User;

public class ControlCenterTest {

	@BeforeEach
	public void init() {
    	ControlCenter.CENTER.initStations(4);
    	try {
			ControlCenter.CENTER.distribute();
		} catch (StationFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @Test
    public void testDistribute() throws StationFullException {
    	ControlCenter.CENTER.distribute();
		for (Station station : ControlCenter.CENTER.getStations()) {
		    assertFalse(station.isEmpty());
		}
		
    }

    @Test
    public void testInitStations() {
    	int size = ControlCenter.CENTER.getStations().size();
    	ControlCenter.CENTER.initStations(2);
        assertEquals(size+2,ControlCenter.CENTER.getStations().size() );
    }
    
    @Test
    public void testAddStation() {
        Station station = new Station();
        ControlCenter.CENTER.addStation(station);
        ArrayList<Station> stations = ControlCenter.CENTER.getStations();
        assertTrue(stations.contains(station));
    }

    @Test
    public void testCreateNewStation() {
    	int size = ControlCenter.CENTER.getStations().size();
    	ControlCenter.CENTER.createNewStation();
        assertEquals(size+1, ControlCenter.CENTER.getStations().size());
    }

    @Test
    public void testRedistributeForEmptyStations() throws StationFullException {
    	ControlCenter.CENTER.distribute();
    	ArrayList<Station> emptyStations = ControlCenter.CENTER.getEmptyStations();
        ControlCenter.CENTER.redistributeForEmptyStations();
        for (Station emptyStation : emptyStations) {
            assertFalse(emptyStation.isEmpty());
        }
    }

    @Test
    public void testRedistributeForFullStations() throws StationFullException, StationEmptyException, OutOfServiceException {
        ArrayList<Station> fullStations = ControlCenter.CENTER.getFullStations();
        for(int i=0; i<ControlCenter.CENTER.getStations().size(); i++) {
        	if(!ControlCenter.CENTER.getStations().get(i).isEmpty()) {
        	ControlCenter.CENTER.getStations().get(i).userTakeMoT(new User());
        	ControlCenter.CENTER.getStations().get(i).userTakeMoT(new User());
        	ControlCenter.CENTER.getStations().get(i).userTakeMoT(new User());
        
        	}	}
        ControlCenter.CENTER.redistributeForFullStations();
        for (Station fullStation : fullStations) {
            assertFalse(fullStation.isFull());
        }
    }

    @Test
    public void testRedistribute() throws StationFullException, StationEmptyException, OutOfServiceException {
        ControlCenter.CENTER.distribute();
        ControlCenter.CENTER.redistribute();
        ArrayList<Station> emptyStations = ControlCenter.CENTER.getEmptyStations();
        ArrayList<Station> fullStations = ControlCenter.CENTER.getFullStations();
        for(int i=0; i<ControlCenter.CENTER.getStations().size(); i++) {
        	ControlCenter.CENTER.getStations().get(i).userTakeMoT(new User());
        	ControlCenter.CENTER.getStations().get(i).userTakeMoT(new User());
        	ControlCenter.CENTER.getStations().get(i).userTakeMoT(new User());
        }
        for (Station emptyStation : emptyStations) {
            assertFalse(emptyStation.isEmpty());
        }
        for (Station fullStation : fullStations) {
            assertFalse(fullStation.isFull());
        }
    }

    @Test
    public void testIsTheMoToutOfService() {
        MeanOfTransport mot = new Bike();
        assertFalse(ControlCenter.CENTER.isTheMoToutOfService(mot));
        for (int i=0; i<20; i++) {
            try {
                mot.use();
            } catch (OutOfServiceException e) {
                fail("Unexpected OutOfServiceException");
            }
            }
        assertTrue(ControlCenter.CENTER.isTheMoToutOfService(mot));
    }

    
    @Test
    public void testUpdateFullAndEmptyStations() throws StationFullException {
        ControlCenter.CENTER.updateFullAndEmptyStations();
        ArrayList<Station> emptyStations = ControlCenter.CENTER.getEmptyStations();
        ArrayList<Station> fullStations = ControlCenter.CENTER.getFullStations();
        for (Station emptyStation : emptyStations) {
            assertTrue(emptyStation.isEmpty());
        }
        for (Station fullStation : fullStations) {
            assertTrue(fullStation.isFull());
        }
    }

    @Test
    public void testCheckForOutOfSericeMoTs() throws StationFullException {
        ArrayList<MeanOfTransport> outOfServiceMoTs = ControlCenter.CENTER.getOutOfServiceMoTs();
        ControlCenter.CENTER.checkForOutOfSericeMoTs();
        for (MeanOfTransport mot : outOfServiceMoTs) {
            assertFalse(mot.isOutOfService());
        }
    }

    @Test
    public void testGetMoTsInUse() {
        User user = new User();
        MeanOfTransport mot = new Bike();
        ControlCenter.CENTER.addMoTinUse(user, mot);
        HashMap<User, MeanOfTransport> meansInUse = ControlCenter.CENTER.getMoTsInUse();
        assertTrue(meansInUse.containsKey(user));
        assertEquals(mot, meansInUse.get(user));
    }

    @Test
    public void testGetMoT_ByUser() {
        User user = new User();
        MeanOfTransport mot = new Bike();
        ControlCenter.CENTER.addMoTinUse(user, mot);
        assertEquals(mot, ControlCenter.CENTER.getMoT_ByUser(user));
    }
    @Test
    public void testDistributeRepairedMoT() throws StationFullException {
    	ControlCenter.CENTER.distribute();
        MeanOfTransport mot = ControlCenter.CENTER.getStations().get(0).getPlaces().get(1);
        ControlCenter.CENTER.getStations().get(0).removeMeanOfTransport(mot);
        for (int i = 0; i < 5; i++) {
            try {
                mot.use();
            } catch (OutOfServiceException e) {
                fail("Unexpected OutOfServiceException");
            }
        }
        try {
            ControlCenter.CENTER.distributeRepairedMoT(mot);
            ArrayList<Station> stations = ControlCenter.CENTER.getStations();
            boolean found = false;
            for (Station station : stations) {
                if (station.getPlaces().containsValue(mot)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        } catch (StationFullException e) {
            fail("Exception should not be thrown");
        }
    }
    @Test
    public void testGetIds_Generator() {
        IdGenerator idsGenerator = ControlCenter.CENTER.getIds_Generator();
        assertNotNull(idsGenerator);
    }

}
