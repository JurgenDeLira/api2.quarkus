package quarkus;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.Optional;

@Path("/greet")
public class EcoResource {

    @GET
    public String greet(@QueryParam("message")String message){
        return Optional.ofNullable(message)
                .map(n -> "> " + n)
                .orElse("Well, IDK what to say...");
    }

    @GET
    @Path("/{name}")
    public String greeting(@PathParam("name") String name) {
        return "Hello, " + name;
    }

    @GET
    @Path("/{name}/UpperCase")
    public String yell(@PathParam("name") String name){
        return "HELLO, " + name.toUpperCase();
    }



    /*@GET
    public String greet(@QueryParam("message") String message){
        if (message == null){
            return "Well, IDK what to say...";
        }else{
            return "> " + message;
        }
    }

    @GET
    @Path("/morning")
    public String morning(){
        return "Hello, good morning";
    }

    @GET
    @Path("/evening")
    public String evening(){
        return "Hi, good evening";
    }

    @GET
    @Path("/night")
    public String night() {
        return "Good night tiny boy";
    }*/
}