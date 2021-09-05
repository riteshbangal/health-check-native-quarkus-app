package com.rcb.quarkus;

import com.rcb.quarkus.health.HealthCheck;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/health-check")
public class HealthCheckApp {

    private Logger LOGGER = Logger.getLogger(HealthCheckApp.class.getName());

    Boolean IS_HEALTH_CHECKING = Boolean.FALSE;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        LOGGER.info("Calling healthCheck()");
        if (IS_HEALTH_CHECKING) {
            return "Already Checking Health!";
        }
        IS_HEALTH_CHECKING = Boolean.TRUE;

        return new HealthCheck().checkHealth();
    }

}
