package com.bravozulu.resources;

import com.bravozulu.core.Review;
import com.bravozulu.db.ReviewsDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by ying on 7/5/16.
 */
@Path("/reviews")
@Produces(MediaType.APPLICATION_JSON)
public class ReviewResource {
    private final ReviewsDAO reviewsDAO;

    public ReviewResource(ReviewsDAO reviewsDAO) {
        this.reviewsDAO = reviewsDAO;
    }

    @GET
    @UnitOfWork
    public List<Review> findAllUsers() {
        return reviewsDAO.findAll();
    }
}
