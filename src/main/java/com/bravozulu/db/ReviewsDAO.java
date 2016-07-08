package com.bravozulu.db;


import com.bravozulu.core.Review;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ying on 6/27/16.
 */
public class ReviewsDAO extends AbstractDAO<Review> {
    public ReviewsDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Review> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Review create(Review review) { return persist(review); }

    public List<Review> findAll() {
        return list(namedQuery("com.bravozulu.core.Review.findAll"));
    }

    public void delete(long id) {}

    public List<Review> findReviewsBySenderId(long senderId) {
        return new ArrayList<Review>();
    }

    public List<Review> findReviewsByReceiverId(long receiverId) {
        return new ArrayList<Review>();
    }

}
