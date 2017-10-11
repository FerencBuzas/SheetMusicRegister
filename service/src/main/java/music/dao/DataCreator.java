package music.dao;

import music.common.Book;
import music.common.Composer;
import music.common.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used only with JPA: creates and persists all the data.
 */
@Service
@ComponentScan(basePackages = "music.common, music.dao")
public class DataCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCreator.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private List<Book> books;
    private List<Composer> composers;
    private List<Publisher> publishers;
    
    public void createData(int nComposers) {
        LOGGER.info("DataCreator.createData() nComposers={}", nComposers);

        // Create lists in memory
        createComposerList(nComposers);
        createPublisherList();

        // Store the lists with JPA.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (Composer composer: composers) {
            entityManager.persist(composer);
        }
        for (Publisher publisher: publishers) {
            entityManager.persist(publisher);
        }

        // Create and store dependent Books after Composers and Publishers are stored.
        createBookList();
        for (Book book: books) {
            entityManager.persist(book);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private void createComposerList(int nComposers) {

        List<Composer> list = new ArrayList<>();

        list.add(new Composer("Bach", 1685));
        list.add(new Composer("Haydn", 1732));
        if (nComposers > 2) {
            list.add(new Composer("Mozart", 1756));
            list.add(new Composer("Beethoven", 1770));
            list.add(new Composer("Schubert", 1797));
            list.add(new Composer("Schumann", 1810));
            //        list.add(new Composer("Chopin", 1810));
            list.add(new Composer("Liszt", 1811));
            //        list.add(new Composer("Brahms", 1833));
            list.add(new Composer("Muszorgszkij", 1839));
            //        list.add(new Composer("Tschaikowskij", 1840));
            //        list.add(new Composer("Debussy", 1862));
            //        list.add(new Composer("Rachmaninov", 1873));
            list.add(new Composer("Bartók", 1881));
        }
            
        composers = list;
    }

    private void createPublisherList() {

        List<Publisher> list = new ArrayList<>();

        list.add(new Publisher("Breitkopf"));
        list.add(new Publisher("Editio Musica Budapest"));
        list.add(new Publisher("Peters"));

        publishers = list;
    }

    Publisher findPublisherByName(String name) {
        for (Publisher publisher: publishers) {
            if (publisher.getName().startsWith(name)) {
                return publisher;
            }
        }
        return null;
    }

    Composer findComposerByName(String name) {
        for (Composer composer: composers) {
            if (composer.getName().startsWith(name)) {
                return composer;
            }
        }
        return null;
    }

    private void createBookList() {

        List<Book> list = new ArrayList<>();

        Composer bach = findComposerByName("Bach");
        Composer haydn = findComposerByName("Haydn");
        Composer beethoven = findComposerByName("Beethoven");
        Composer liszt = findComposerByName("Liszt");

        Publisher peters = findPublisherByName("Peters");
        Publisher emb = findPublisherByName("Editio");

        list.add(new Book("Wohltemperiertes Klavier I", bach, peters, 1998));
        list.add(new Book("Wohltemperiertes Klavier II", bach, peters, 1998));
        list.add(new Book("Piano sonatas", haydn, peters, 1988));

        if (beethoven != null) {  // sorry, in-memory version adds only Bach and Haydn
            list.add(new Book("Zongoraszonáták I", beethoven, emb, 1992));
            list.add(new Book("Zongoraszonáták II", beethoven, emb, 1992));
            list.add(new Book("Zongoraszonáták III", beethoven, emb, 1992));
            list.add(new Book("Sonate h-moll", liszt, emb, 1988));
        }

        books =  list;
    }

    // === NOTE: this is far from ideal OOP
    
    public List<Book> getBooks() {
        return books;
    }
    public List<Composer> getComposers() {
        return composers;
    }
    public List<Publisher> getPublishers() {
        return publishers;
    }
}
