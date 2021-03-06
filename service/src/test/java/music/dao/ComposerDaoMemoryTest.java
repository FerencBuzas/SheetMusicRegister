package music.dao;

import music.common.Composer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComposerDaoMemoryTest {
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    private DataCreator dc;
    private ComposerDaoMemory cdm;

    @Before
    public void setUp() {

        dc = new DataCreator(entityManagerFactory);
        dc.createData(2, false);
        cdm = new ComposerDaoMemory(dc);
    }

    @Test
    public void testGetComposersByName() {

        List<Composer> coLi = cdm.getComposersByName("Bach");
        assertEquals(1, coLi.size());
        assertTrue(coLi.get(0).getName().contains("Bach"));
        
        List<Composer> coLi0 = cdm.getComposersByName("NoSuch");
        assertEquals(0, coLi0.size());
    }
}
