package quarkus;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.List;
import java.util.NoSuchElementException;

@Path("/temperatures")
public class TemperaturesResource {

    private TemperaturesService temperatures;

    @Inject
    public  TemperaturesResource(TemperaturesService temperatures) {
        this.temperatures = temperatures;
    }

    @POST
    public Temperature newOne(Temperature temp){
        temperatures.addTemperature(temp);
        return(temp);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Temperature> list() {
        return temperatures.getTemperatures();
    }

    @GET
    @Path("/maximum")
    @Produces(MediaType.TEXT_PLAIN)
    public Response maximum() {
        if (temperatures.isEmpty()){
            return Response.status(404).entity("There's no temperatures").build();
        }else{
            int temperatureMaximum = temperatures.maximum();
            return Response.ok(Integer.toString(temperatureMaximum))
                    .header("X-Hello", "Good morning")
                    .build();
        }
    }

    @GET
    @Path("{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public Temperature takeOut(@PathParam("city") String city){
        return temperatures.takeOutTemperature(city)
                .orElseThrow(() ->
                        new NoSuchElementException("There's no any register for the city " + city));
    }
}
