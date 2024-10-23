package quarkus;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/books")
@Transactional
public class BookResource {

    @Inject
    private BookRepository repo;

    @GET
    public List<Book> index(){
        return repo.listAll();
    }

    @POST
    public Book insert(Book insertedBook) {
        repo.persist(insertedBook);
        return insertedBook;
    }
}
