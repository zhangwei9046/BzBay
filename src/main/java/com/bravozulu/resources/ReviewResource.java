package com.bravozulu.resources;

import com.bravozulu.core.Review;
import com.bravozulu.core.User;
import com.bravozulu.db.ReviewDAO;
import com.bravozulu.db.UserDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by ying on 7/5/16.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ReviewResource {
    private final ReviewDAO reviewDAO;
    private final UserDAO userDAO;

    public ReviewResource(ReviewDAO reviewsDAO, UserDAO userDAO) {
        this.reviewDAO = reviewsDAO;
        this.userDAO = userDAO;
    }

    @GET
    @Path("review")
    @UnitOfWork
    public List<Review> findAllReviews() {
        System.out.println("hello zoey");
        return reviewDAO.findAll();
    }

    @POST
    @Path("review")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Review createReview(@Auth User user, Review review) {
        return reviewDAO.create(review);
    }

    @PUT
    @Path("review/{reviewId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Review updateReview(@Auth User user, @PathParam("reviewId") LongParam reviewId, Review review) {
        return reviewDAO.update(reviewId.get(), review);
    }

    @DELETE
    @Path("review/{reviewId}")
    @UnitOfWork
    public void deleteReview(@Auth User user, @PathParam("reviewId") LongParam reviewId) {
        reviewDAO.delete(reviewId.get());
    }

    @GET
    @Path("user/sendername={username}/review")
    @UnitOfWork
    public List<Review> findReviewsForSender(@Auth User user, @PathParam("username") String username) {
        User userObj = userDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("No such user."));
        return reviewDAO.findBySenderId(userObj.getUserId());
    }

    @GET
    @Path("user/receivername={username}/review")
    @UnitOfWork
    public List<Review> findReviewsForReceiver(@Auth User user, @PathParam("username") String username) {
        User userObj = userDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("No such user."));
        return reviewDAO.findByReceiverId(userObj.getUserId());
    }
}
