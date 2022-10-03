package org.wildfly.wuar.demo;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.wildfly.wuar.demo.service.DemoService;

@Path("/")
public class DemoEndpoint {

    private static final int N = 2;

    @Inject
    private DemoService service;

    @GET
    @Produces("text/plain")
    public Response doGet(@QueryParam("value") long value) {
        long response = service.powerOf(value, N);
        return Response.ok(response).build();
    }
}
