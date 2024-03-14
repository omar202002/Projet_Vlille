package projet.vlille.meansOfTransport.bike;

import static org.junit.Assert.*;
import org.junit.Test;

import projet.vlille.exception.OutOfServiceException;

public class BikeTest {

    private Bike bike = new Bike();

    @Test
    public void testGetNbLocations() {
        assertEquals(0, bike.getNbLocations());
    }

    @Test
    public void testReset() throws OutOfServiceException {
        bike.use();
        bike.reset();
        assertEquals(0, bike.getNbLocations());
        assertTrue(bike.isAvailable());
    }

    @Test
    public void testIsOutOfService() {
        assertFalse(bike.isOutOfService());
        for (int i = 0; i < Bike.MAX_USE; i++) {
            try {
                bike.use();
            } catch (OutOfServiceException e) {
                fail("Unexpected OutOfServiceException");
            }
        }
        assertTrue(bike.isOutOfService());
    }

    @Test
    public void testIsAvailable() {
        assertTrue(bike.isAvailable());
        for (int i=0; i<20; i++) {
        try {
            bike.use();
        } catch (OutOfServiceException e) {
            fail("Unexpected OutOfServiceException");
        }
        }
        assertFalse(bike.isAvailable());
    }

    @Test
    public void testUse() {
        try {
            bike.use();
            assertEquals(1, bike.getNbLocations());
            assertTrue(bike.isAvailable());
        } catch (OutOfServiceException e) {
            fail("Unexpected OutOfServiceException");
        }
    }

    @Test(expected = OutOfServiceException.class)
    public void testUseWhenOutOfService() throws OutOfServiceException {
        for (int i = 0; i < Bike.MAX_USE; i++) {
            bike.use();
        }
        bike.use();
    }
}