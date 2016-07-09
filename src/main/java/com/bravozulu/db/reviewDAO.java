package com.bravozulu.db;

import com.bravozulu.core.Review;
import com.google.common.base.Preconditions;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Created by ying on 7/8/16.
 */
public class ReviewDAO extends AbstractDAO<Review> {
    public ReviewDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Review> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Review create(Review review) { return persist(review); }

    public List<Review> findAll() {
        return list(namedQuery("com.bravozulu.core.Review.findAll"));
    }

    public void delete(Long reviewId) {
        Review reviewObj = findById(reviewId).orElseThrow(() -> new NotFoundException("No such review."));
        currentSession().delete(reviewObj);
    }

    public Review update(Long reviewId, Review review) {
        review.setReviewId(reviewId);
        return persist(review);
    }

    public List<Review> findBySenderId(Long senderId) {
        Query query = Preconditions.checkNotNull(namedQuery("com.bravozulu.core.User.findBySenderId"));
        query.setParameter("senderId", senderId);
        return list(query);
    }

    public List<Review> findByReceiverId(Long receiverId) {
        Query query = Preconditions.checkNotNull(namedQuery("com.bravozulu.core.User.findByReceiverId"));
        query.setParameter("receiverId", receiverId);
        return list(query);
    }
}
