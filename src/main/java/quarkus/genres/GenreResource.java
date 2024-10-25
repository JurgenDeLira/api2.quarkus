package quarkus.genres;


import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import quarkus.PaginatedResponse;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Path("/genres")
public class GenreResource {

    @Inject
    private GenreRepository genres;

    @GET
    public PaginatedResponse<Genre> list(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("q") String q
    ) {

        var query = genres.findPage(page);
        if (q != null){
            var nameLike = "%" + q + "%";
            query.filter("name.like:", Parameters.with("name", nameLike));
        }
        return new PaginatedResponse<>(query);
    }

    @POST
    @Transactional
    public Response create(Genre genre) {
        genres.persist(genre);
        return Response.created(URI.create("/genres/" + genre.getId())).entity(genre).build();
    }

    @GET
    @Path("{id}")
    public Genre get(@PathParam("id") Long id){
        return genres
                .findByIdOptional(id)
                .orElseThrow(() -> new NoSuchElementException("Genre " + id + " not found"));
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Genre update(@PathParam("id") Long id, Genre inbox) {
        Genre found = genres
                .findByIdOptional(id)
                .orElseThrow(() -> new NoSuchElementException("Genre " + id + " not found"));
        found.setName(inbox.getName());
        genres.persist(found);
        return found;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id")long id) {
        genres.deleteById(id);

    }
}
