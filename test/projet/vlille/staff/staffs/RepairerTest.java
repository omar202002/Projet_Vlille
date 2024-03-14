package projet.vlille.staff.staffs;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import projet.vlille.exception.OutOfServiceException;
import projet.vlille.meansOfTransport.MeanOfTransport;
import projet.vlille.meansOfTransport.bike.Bike;

public class RepairerTest {

    private Repairer repairer = new Repairer();
    private MeanOfTransport mot = new Bike();

    @Test
    public void testOperate() throws InterruptedException {
    	for (int i=0; i<20; i++) {
            try {
                mot.use();
            } catch (OutOfServiceException e) {
                fail("Unexpected OutOfServiceException");
            }
            }
    	assertThrows(OutOfServiceException.class, ()->{mot.use();} );
        repairer.operate(mot);
        assertThrows(OutOfServiceException.class, ()->{mot.use();} );
        assertFalse(mot.isAvailable());
        Thread.sleep(5000);
        assertTrue(mot.isAvailable());
        
        
    }
}