package org.wildfly.wuar.demo;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.wildfly.wuar.demo.service.DemoService;

@Path("/")
public class DemoEndpoint {

    private static final int N = 2;

    @Inject
    private DemoService service;

    @Inject
    @ConfigProperty(name="demo.default.text", defaultValue = "Hello, World!")
    String defaultText;

    @GET
    @Path("/reverse")
    @Produces("text/plain")
    public Response reverse(@QueryParam("text") String text) {
        if (text == null) {
            text = defaultText;
        }
        String response = service.reverse(text);
        return Response.ok(response).build();
    }
}
