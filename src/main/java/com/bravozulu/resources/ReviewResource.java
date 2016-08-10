package com.bravozulu.resources;

import com.bravozulu.core.Review;
import com.bravozulu.core.User;
import com.bravozulu.db.ReviewDAO;
import com.bravozulu.db.UserDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by ying on 7/5/16.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
//@Api(value = "/review", description = "This is review.")
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
    @RolesAllowed("Admin")
    public List<Review> findAllReviews() {
        return reviewDAO.findAll();
    }

    @POST
    @Path("review")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Review leaveAReview(Review review, @Auth User user) {
        User cur_user = userDAO.findByUsername(user.getUsername()).orElseThrow(() -> new NotFoundException("Please log in first."));
        if (cur_user.getUserId() != review.getSenderId()) {
            throw new NotAcceptableException("Please login first.");
        }
        return reviewDAO.create(review);
    }

    //Deprecated
//    @PUT
//    @Path("review/{reviewId}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @UnitOfWork
//    public Review updateReview(@Auth User user, @PathParam("reviewId") LongParam reviewId, Review review) {
//        return reviewDAO.update(reviewId.get(), review);
//    }

    @DELETE
    @Path("review/{reviewId}")
    @UnitOfWork
    public void deleteReview(@Auth User user, @PathParam("reviewId") LongParam reviewId) {
        Review review = reviewDAO.findById(reviewId.get()).orElseThrow(() -> new NotFoundException("No such review."));
        User cur_user = userDAO.findByUsername(user.getUsername()).orElseThrow(() -> new NotFoundException("Please log in first."));
        if (cur_user.getUserId() != review.getSenderId()) {
            throw new NotAcceptableException("Please login first.");
        }
        reviewDAO.delete(reviewId.get());
    }

    @GET
    @Path("user/sendername={username}/review")
    @UnitOfWork
    public List<Review> findReviewsForSender(@Auth User user, @PathParam("username") String username) {
        User userObj = userDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("No such user."));
        if (!user.getUsername().equals(username)) {
            throw new NotAcceptableException("Please login first.");
        }
        return reviewDAO.findBySenderId(userObj.getUserId());
    }

    @GET
    @Path("user/receivername={username}/review")
    @UnitOfWork
    public List<Review> findReviewsForReceiver(@Auth User user, @PathParam("username") String username) {
        User userObj = userDAO.findByUsername(username).orElseThrow(() -> new NotFoundException("No such user."));
        if (!userObj.getUsername().equals(username)) {
            System.out.println("ddd");
            throw new NotAcceptableException("Please login first.");
        }
        return reviewDAO.findByReceiverId(userObj.getUserId());
    }
}
