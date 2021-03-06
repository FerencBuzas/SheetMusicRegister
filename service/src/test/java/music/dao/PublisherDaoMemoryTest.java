package music.dao;

import music.common.Publisher;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PublisherDaoMemoryTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    private DataCreator pdc;
    private PublisherDaoMemory pdm;

    @Before
    public void setUp() {

        pdc = new DataCreator(entityManagerFactory);
        pdc.createData(2, false);
        pdm = new PublisherDaoMemory(pdc);
    }

    @Test
    public void testGetPublishersByName() {

        List<Publisher> puLi = pdm.getPublishersByName("Peters");

        assertEquals(1, puLi.size());
        assertTrue(puLi.get(0).getName().contains("Peters"));
    }

}
