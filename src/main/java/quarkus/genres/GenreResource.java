package quarkus.genres;


import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import quarkus.PaginatedResponse;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Path("/genres")
public class GenreResource {

    private GenreRepository genres;

    private GenreMapper mapper;

    private GenreValidator validator;

    @Inject
    public GenreResource(GenreRepository genres, GenreMapper mapper) {
        this.genres = genres;
        this.mapper = mapper;
    }

    @GET
    public PaginatedResponse<GenreResponseDto> list(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("q") String q
    ) {
        PanacheQuery<Genre> query = genres.findPage(page);
        PanacheQuery<GenreResponseDto> present = query.project(GenreResponseDto.class);
        if (q != null){
            var nameLike = "%" + q + "%";
            present.filter("name.like:", Parameters.with("name", nameLike));
        }
        return new PaginatedResponse<>(present);
    }

    @POST
    @Transactional
    public Response create(CreateGenreDto genre) {
        var error = this.validator.validateGenre(genre);
        if (error.isPresent()){
            var msg = error.get();
            return Response.status(400).entity(msg).build();
        }
        var entity = mapper.fromCreate(genre);
        genres.persist(entity);
        var representation = mapper.present(entity);
        return Response.created(URI.create("/genres/" + entity.getId())).entity(representation).build();
    }

    @GET
    @Path("{id}")
    public GenreResponseDto get(@PathParam("id") Long id){
        return genres
                .findByIdOptional(id)
                .map(mapper::present)
                .orElseThrow(() -> new NoSuchElementException("Genre " + id + " not found"));
    }

    @PUT
    @Path("{id}")
    @Transactional
    public GenreResponseDto update(@PathParam("id") Long id, @Valid UpdateGenreDto inbox) {
        Genre found = genres
                .findByIdOptional(id)
                .orElseThrow(() -> new NoSuchElementException("Genre " + id + " not found"));
        mapper.update(inbox, found);
        genres.persist(found);
        return mapper.present(found);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id")long id) {
        genres.deleteById(id);

    }
}
