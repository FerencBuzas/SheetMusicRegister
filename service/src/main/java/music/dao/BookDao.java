package music.dao;

import music.common.Book;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Give one or more Book objects.
 */
@ComponentScan
@Repository
public interface BookDao {

    List<Book> getBooks();

    void storeBook(Book book);

    void deleteBook(long id);
}
