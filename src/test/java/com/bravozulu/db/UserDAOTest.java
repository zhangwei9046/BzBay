package com.bravozulu.db;

import com.bravozulu.core.User;
import com.google.common.collect.ImmutableList;
import org.hibernate.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by ying on 7/22/16.
 */
public class UserDAOTest extends DAOTests {
    //    private static final UserDAO dao = mock(UserDAO.class);
    private UserDAO dao;

//    private User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);

    @Before
    public void setup() {
        dao = new UserDAO(sessionFactory);
        getSession().beginTransaction();
        Query q = getSession().createQuery("delete from User");
        q.executeUpdate();
        getSession().getTransaction().commit();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findById() {
        getSession().beginTransaction();
        User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
        User u = dao.create(user);
        assertEquals(dao.findById(u.getUserId()).get(), user);
    }

    @Test
    public void create() {
        getSession().beginTransaction();
        User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
        dao.create(user);
        assertEquals(dao.findByUsername("hello").get(), user);
        getSession().getTransaction().commit();

    }

//    @Test
//    public void findByUsername() {
//        getSession().beginTransaction();
//        User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
//        assertEquals(dao.findByUsername("hello").get(), user);
//        getSession().getTransaction().commit();
//    }

    @Test
    public void findAll() {
        getSession().beginTransaction();

        for (int i = 0; i < 10; i++) {
            User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
            dao.create(user);
        }

        assertEquals(dao.findAll().size(), 10);

        getSession().getTransaction().commit();
    }

    @Test
    public void delete() {
        getSession().beginTransaction();
        User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
        User u = dao.create(user);
        dao.delete(u.getUserId());
        getSession().getTransaction().commit();
    }

    @Test
    public void update() {
        getSession().beginTransaction();
        User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
//        User update_user = new User("alice", "Alice", "Wonderland", "alice", "alice@wonderland.com", "Seattle", "WA", "225 Terry Ave N", false);

        User u = dao.create(user);

        user.setUsername("alice");
        user.setFirstName("Alice");
        user.setLastName("Wonderland");

        User updated_u = dao.update(u.getUserId(), user);
        assertEquals(updated_u, user);
        getSession().getTransaction().commit();
    }
}
