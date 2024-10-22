package quarkus;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Path("/temperatures")
public class TemperaturesResource {

    private List<Temperature> values = new ArrayList<>();

    @POST
    public Temperature newOne(Temperature temp){
        values.add(temp);
        return(temp);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Temperature> list() {
        return Collections.unmodifiableList(values);
    }

}
