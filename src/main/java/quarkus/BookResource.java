package quarkus;

import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.NoSuchElementException;

@Path("/books")
@Transactional
public class BookResource {

    @Inject
    private BooksRepository booksRepository;

    @GET
    public List<Book> index(@QueryParam("q") String query) {
        if (query == null) {
            return booksRepository.listAll(Sort.by("pubDate", Sort.Direction.Descending));
        } else {
            String filter = "%"+ query + "%";
            Sort crit = Sort.by("pubDate", Sort.Direction.Ascending);
            return booksRepository.list("title ILIKE ?1 OR description ILIKE ?2", crit, filter, filter);
        }
    }

    @POST
    public Book insert(Book book) {
        booksRepository.persist(book);
        return book;
    }

    @GET
    @Path("{id}")
    public Book retrieve(@PathParam("id") Long id) {
        var book = booksRepository.findById(id);
        if (book != null) {
            return book;
        }
        throw new NoSuchElementException("No hay libro con el ID " + id + ".");
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        booksRepository.deleteById(id);
    }

    @PUT
    @Path("{id}")
    public Book update(@PathParam("id") Long id, Book book) {
        var updatedBook = booksRepository.findById(id);
        if (updatedBook != null) {
            updatedBook.setTitle(book.getTitle());
            updatedBook.setPubDate(book.getPubDate());
            updatedBook.setNumPages(book.getNumPages());
            updatedBook.setDescription(book.getDescription());
            booksRepository.persist(updatedBook);
            return updatedBook;
        }
        throw new NoSuchElementException("No hay libro con el ID " + id + ".");
    }
}