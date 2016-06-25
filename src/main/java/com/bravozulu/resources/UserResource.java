package com.bravozulu.resources;

import com.bravozulu.core.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @GET
    public User showUsers() {
        User user = new User("dd", "David", "Dan");
        return user;
    }
}
