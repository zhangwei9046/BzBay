package com.bravozulu.resources;

import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //This method works
    @GET
    @UnitOfWork
    @RolesAllowed("Admin")
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    //This method works
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public User createUser(User user) {
        return userDAO.create(user);
    }

    //This method works
    @GET
    @Path("/{userId}")
    @UnitOfWork
    @RolesAllowed("Admin")
    public User getUserById(@PathParam("userId") LongParam userId) {
        return userDAO.findById(userId.get()).orElseThrow(() -> new NotFoundException("No such user."));
    }

    @GET
    @Path("/username={username}")
    @UnitOfWork
    @RolesAllowed("Admin")
    public User getUserByUsername(@PathParam("username") String username) {
        return userDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("No such user."));
    }

    //This method works
    @PUT
    @Path("/{userId}")
    @UnitOfWork
    public User updateUser(@PathParam("userId") LongParam userId, @Auth User user) {
        return userDAO.update(userId.get(), user);
    }

    //This method works
    @DELETE
    @Path("/{userId}")
    @UnitOfWork
    //Need to check admin for all methods.
    @RolesAllowed("Admin")
    public void deleteUser(@PathParam("userId") LongParam userId) {
        userDAO.delete(userId.get());
    }

}
