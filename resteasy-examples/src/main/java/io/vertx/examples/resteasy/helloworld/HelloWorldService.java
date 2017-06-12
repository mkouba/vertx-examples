package io.vertx.examples.resteasy.helloworld;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@ApplicationScoped // Specify the scope explicitly, otherwise @RequestScoped is used and the invocation fails
@Path("/")
public class HelloWorldService {

    @Inject
    Uppercaser uppercaser;

    @GET
    @Path("/{name:.*}")
    public Response doGet(@PathParam("name") String name) {
        if (name == null || name.isEmpty()) {
            name = "World";
        }
        return Response.status(200).entity("Hello " + uppercaser.apply(name)).build();
    }
}