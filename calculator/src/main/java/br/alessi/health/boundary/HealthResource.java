package br.alessi.health.boundary;

import br.alessi.MyReactiveMessagingApplication;
import br.alessi.health.control.HealthService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/health")
public class HealthResource {

    @Inject
    HealthService healthService;

    @Inject
    MyReactiveMessagingApplication emmiter;

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response healthStatus(@QueryParam("message") String message) {
        emmiter.emit(message);
        return Response.ok(healthService.getHealthStatus()).build();
    }

}
