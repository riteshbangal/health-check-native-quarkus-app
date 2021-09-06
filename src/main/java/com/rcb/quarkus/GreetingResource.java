package com.rcb.quarkus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/hello")
public class GreetingResource {

    Logger logger = Logger.getLogger(GreetingResource.class.getName());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        logger.info("Calling hello()");
        return "Hello RESTEasy";
    }

    @GET
    @Path("/v1")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello_v1() {
        logger.info("Calling hello v1()");
        return "V1: Hello RESTEasy";
    }

    @GET
    @Path("/v2")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello_v2() {
        logger.info("Calling hello v2()");
        return "V2: Hello RESTEasy";
    }
}