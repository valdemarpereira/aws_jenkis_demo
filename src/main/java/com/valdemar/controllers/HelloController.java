package com.valdemar.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("hello")
public class HelloController {

    @GET
    public String helloJohnDoe() {
        return "Hello John Doe!";
    }

    @GET
    @Path("{username}")
    public String hello(@PathParam("username") String username) {
        return String.format("Hello %s!", username);
    }

}
