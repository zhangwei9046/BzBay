package com.bravozulu.resources;

import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
//@Api(value = "/user", description = "This is user.")
public class UserResource {
    private final UserDAO userDAO;

    private static Logger logger = LoggerFactory.getLogger(ReviewResource.class);

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @RolesAllowed("Admin")
    @ApiOperation(value = "Find all Users",
            response = User.class,
            responseContainer = "List")
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed("Admin")
    @ApiOperation(value = "Create a user")
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
    @ApiOperation(value = "get user by userId",
            response = User.class)
    public User getUserById(@PathParam("userId") LongParam userId) {
        return userDAO.findById(userId.get()).orElseThrow(() -> new NotFoundException("No such user."));
    }

    @GET
    @Timed
    @Path("/username={username}")
    @UnitOfWork
    @RolesAllowed("Admin")
    @ApiOperation(value = "get user by username",
            response = User.class)
    public User getUserByUsername(@PathParam("username") String username) {
        return userDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("No such user."));
    }

    //This method works
    @PUT
    @Timed
    @UnitOfWork
    @ApiOperation(value = "update user information")
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
    @RolesAllowed("Admin")
    @ApiOperation(value = "Delete a user")
    public void deleteUser(@PathParam("userId") LongParam userId) {
        userDAO.delete(userId.get());
    }


    @POST
    @Timed
    @Path("/login")
    @UnitOfWork
    @ApiOperation(value = "user login",
            authorizations = {@Authorization(value="UserBasicAuth")},
            notes = "Returns a user if login successfully")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Sign in fail",
                    response = String.class),
            @ApiResponse(code = 200, message = "Login Successfully! Welcome <user>", response = String.class)
    })
    public User login(@Auth User user) {
        return user;
    }

    @POST
    @Timed
    @Path("/register")
    @UnitOfWork
    @ApiOperation(value = "user register")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message="Successfully created User.", response = String.class),
            @ApiResponse(code = 405, message="Failure : user name already exists.", response = String.class)
    })
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
