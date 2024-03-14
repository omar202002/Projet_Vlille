package projet.vlille.idGenerator;

import static org.junit.Assert.*;
import org.junit.Test;

public class IdGeneratorTest {

    private IdGenerator idGenerator = new IdGenerator();

    @Test
    public void testGenerateId() {
        int id = idGenerator.generateMoTId();
        int idd = idGenerator.generateMoTId();
        assertNotNull(id);
        assertNotEquals(idd, id);
    }

}