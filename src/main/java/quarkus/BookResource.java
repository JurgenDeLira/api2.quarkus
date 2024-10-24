package quarkus;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/books")
@Transactional
public class BookResource {

    @GET
    public List<Book> index(){
        return Book.listAll();
    }

    @POST
    public Book insert(Book insertedBook) {
        insertedBook.persist();
        return insertedBook;
    }
}
