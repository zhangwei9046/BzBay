package com.bravozulu.resources;

import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //This method works
    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed("Admin")
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    //This method works
    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed("Admin")
    public User createUser(User user) {
        Optional<User> op = userDAO.findByUsername(user.getUsername());
        if (op.isPresent()) {
            throw new NotAcceptableException("User already existed!");
        }
        return userDAO.create(user);
    }

    //This method works
    @GET
    @Timed
    @Path("/{userId}")
    @UnitOfWork
    @RolesAllowed("Admin")
    public User getUserById(@PathParam("userId") LongParam userId) {
        return userDAO.findById(userId.get()).orElseThrow(() -> new NotFoundException("No such user."));
    }

    @GET
    @Timed
    @Path("/username={username}")
    @UnitOfWork
    @RolesAllowed("Admin")
    public User getUserByUsername(@PathParam("username") String username) {
        return userDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("No such user."));
    }

    //This method works
    @PUT
    @Timed
//    @Path("/{userId}")
    @UnitOfWork
    public User updateUser(@Auth User u, User userObj) {
//        user.setUsername(userObj.getUsername());
        User user = userDAO.findByUsername(u.getUsername()).get();
        user.setFirstName(userObj.getFirstName());
        user.setLastName(userObj.getLastName());
        user.setPassword(userObj.getPassword());
        user.setEmail(userObj.getEmail());
        user.setCity(userObj.getCity());
        user.setState(userObj.getState());
        user.setAddress(userObj.getAddress());

        return userDAO.update(user);
    }

    //This method works
    @DELETE
    @Timed
    @Path("/{userId}")
    @UnitOfWork
    //Need to check admin for all methods.
    @RolesAllowed("Admin")
    public void deleteUser(@PathParam("userId") LongParam userId) {
        userDAO.delete(userId.get());
    }


    @POST
    @Timed
    @Path("/login")
    @UnitOfWork
    public User login(@Auth User user) {
        return user;
    }

    @POST
    @Timed
    @Path("/register")
    @UnitOfWork
    public User register(User user) {
        Optional<User> op = userDAO.findByUsername(user.getUsername());
        if (!op.isPresent()) {
            user.setAdmin(false);
            userDAO.create(user);
            login(user);
            return user;
        } else {
            throw new NotAcceptableException("User already existed!");
        }
    }
}
