package com.bravozulu.db;

import com.bravozulu.core.Review;
import com.bravozulu.core.User;
import org.hibernate.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ying on 7/22/16.
 */
public class ReviewDAOTest extends DAOTests {
    private ReviewDAO dao;
    private UserDAO userDao;

    private User user1;
    private User user2;
    private User u1;
    private User u2;

    @Before
    public void setup() {
        dao = new ReviewDAO(sessionFactory);
        userDao = new UserDAO(sessionFactory);
        getSession().beginTransaction();
        Query q = getSession().createQuery("delete from Review");
        q.executeUpdate();
        user1 = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
        user2 = new User("alice", "Alice", "Wonderland", "alice", "alice@wonderland.com","Seattle", "WA", "401 Terry Ave N", true);
        u1 = userDao.create(user1);
        u2 = userDao.create(user2);
        getSession().getTransaction().commit();
    }

    @After
    public void tearDown() {
        getSession().beginTransaction();
        Query q = getSession().createQuery("delete from Review");
        q.executeUpdate();
        getSession().getTransaction().commit();
    }

    @Test
    public void findById() {
        getSession().beginTransaction();

        Review review = new Review(u1.getUserId(), u2.getUserId(), "Bad!", 4.5);
        Date date = new Date();
        review.setDate(date);

        Review r = dao.create(review);
        assertEquals(dao.findById(r.getReviewId()).get(), review);
        getSession().getTransaction().commit();
    }

    @Test
    public void create() {
        getSession().beginTransaction();
        Review review = new Review(u1.getUserId(), u2.getUserId(), "Good!", 4.5);
        Date date = new Date();
        review.setDate(date);

        Review r = dao.create(review);
        assertEquals(dao.findById(r.getReviewId()).get(), review);
        getSession().getTransaction().commit();
    }

    @Test
    public void findAll() {
        getSession().beginTransaction();

        for (int i = 0; i < 9; i++) {
            Review review = new Review(u1.getUserId(), u2.getUserId(), "Good!", 4.5);
            Date date = new Date();
            review.setDate(date);
            dao.create(review);
        }

        assertEquals(dao.findAll().size(), 9);
        getSession().getTransaction().commit();
    }

    @Test
    public void delete() {
        getSession().beginTransaction();
        Review review = new Review(u1.getUserId(), u2.getUserId(), "Good!", 4.5);
        Date date = new Date();
        review.setDate(date);
        Review r = dao.create(review);
        dao.delete(r.getReviewId());
        assertTrue(!dao.findById(r.getReviewId()).isPresent());
        getSession().getTransaction().commit();
    }


    @Test
    public void findBySenderId() {
        getSession().beginTransaction();

        for (int i = 0; i < 11; i++) {
            Review review = new Review(u1.getUserId(), u2.getUserId(), "Good!", 4.5);
            Date date = new Date();
            review.setDate(date);
            dao.create(review);
        }

        assertEquals(dao.findBySenderId(u1.getUserId()).size(), 11);
        getSession().getTransaction().commit();
    }

    @Test
    public void findByReceiverId() {
        getSession().beginTransaction();

        for (int i = 0; i < 12; i++) {
            Review review = new Review(u1.getUserId(), u2.getUserId(), "Good!", 4.5);
            Date date = new Date();
            review.setDate(date);
            dao.create(review);
        }

        assertEquals(dao.findByReceiverId(u2.getUserId()).size(), 12);
        getSession().getTransaction().commit();
    }
}
