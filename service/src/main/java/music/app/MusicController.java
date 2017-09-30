package music.app;

import music.common.Book;
import music.common.Composer;
import music.common.Publisher;
import music.dao.BookDao;
import music.dao.ComposerDao;
import music.dao.PublisherDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@CrossOrigin     // Adds Allow Cross Origin to header; TODO: restrict to address ...
@ComponentScan(basePackages = "music.common, music.dao")
public class MusicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicController.class);

    private static final String URL_BASE = "localhost:8080/music";

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ComposerDao composerDao;

    @Autowired
    private PublisherDao publisherDao;

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/music")
    public String music() {
        return link("book") + link("composer") + link("publisher");
    }

    private static String link(String lastWord) {
        return String.format("<a href=\"%s/%s\">%s</a><br>", URL_BASE, lastWord, lastWord);
    }
    
    // ======== Book ================================
    
    @RequestMapping(method = RequestMethod.GET, value="/music/book")
    public Collection<Book> books() {
        return bookDao.getBooks();
    }

    // TODO: use RequestMethod.DELETE (and in all similar places)
    @RequestMapping(value="/music/book/delete")
    public String removeBook(@RequestParam(value="id") String id) {
        LOGGER.debug("removeBook id={} ##", id);
        bookDao.deleteBook(Long.parseLong(id));
        return "{ \"status\": \"OK\" }";
    }

    @RequestMapping(value="/music/book/add")
    public String addBook(@RequestParam(value="title") String title,
                          @RequestParam(value="composer") String composerName,
                          @RequestParam(value="publisher") String publisherName,
                          @RequestParam(value="pubYear") int pubYear) {
        LOGGER.debug("addBook title={} ##", title);
        
        List<Composer> composers = composerDao.getComposersByName(composerName);
        int nCo = composers.size();  
        if (nCo != 1) {
            return "{ \"status\": \"Failure (number of composers: "+nCo+")\"";
        }
        
        List<Publisher> publishers = publisherDao.getPublishersByName(publisherName);
        int nPu = publishers.size();
        if (nPu != 1) {
            return "{ \"status\": \"Failure (number of publishers: "+nPu+")\"";
        }
        
        bookDao.addBook(new Book(title, composers.get(0), publishers.get(0), pubYear));
        return "{ \"status\": \"OK\" }";
    }

    // ======== Composer ================================

    @RequestMapping(value="/music/composer")
    public Collection<Composer> composers(@RequestParam(value="name", defaultValue="") String name) {
        if (name.isEmpty()) {
            return composerDao.getComposers();
        }
        else {
            return composerDao.getComposersByName(name);
        }
    }

    @RequestMapping(value="/music/composer/add")
    public String addComposer(@RequestParam(value="name") String name,
            @RequestParam(value="birthYear") int birthYear) {
        LOGGER.debug("addComposer name={} ##", name);

        composerDao.addComposer(new Composer(name, birthYear));
        return "{ \"status\": \"OK\" }";
    }

    @RequestMapping(value="/music/composer/delete")
    public String removeComposer(@RequestParam(value="id") String id) {
        LOGGER.debug("removeComposer id={} ##", id);
        composerDao.deleteComposer(Long.parseLong(id));
        return "{ \"status\": \"OK\" }";
    }

    // ======== Publisher ================================

    @RequestMapping(value="/music/publisher")
    public Collection<Publisher> publishers(@RequestParam(value="name", defaultValue="") String name) {
        Collection<Publisher> result = publisherDao.getPublishers();
        LOGGER.info("publishers() returning {} elements", result.size());
        return result;
    }
    
    @RequestMapping(value="/music/publisher/add")
    public String addPublisher(@RequestParam(value="name") String name) {
        LOGGER.debug("addPublisher name={} ##", name);

        publisherDao.addPublisher(new Publisher(name));
        return "{ \"status\": \"OK\" }";
    }

    @RequestMapping(value="/music/publisher/delete")
    public String removePublisher(@RequestParam(value="id") String id) {
        LOGGER.debug("removePublisher id={} ##", id);
        publisherDao.deletePublisher(Long.parseLong(id));
        return "{ \"status\": \"OK\" }";
    }
}
