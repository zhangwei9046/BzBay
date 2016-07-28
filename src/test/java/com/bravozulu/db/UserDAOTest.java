package com.bravozulu.db;

import com.bravozulu.core.User;
import com.google.common.collect.ImmutableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by ying on 7/22/16.
 */
public class UserDAOTest {
    private static final UserDAO dao = mock(UserDAO.class);
//    private static final UserDAO dao = new UserDAO();

    private User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);

    @Before
    public void setup() {
        user.setUserId(100L);
    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(dao);

    }

    @Test
    public void findById() {
        when(dao.findById(100L)).thenReturn(Optional.of(user));
        assertThat(dao.findById(100L)).isEqualTo(Optional.of(user));
        verify(dao).findById(100L);
    }

    @Test
    public void findByUsername() {
        when(dao.findByUsername(eq("hello"))).thenReturn(Optional.of(user));
        assertThat(dao.findByUsername("hello")).isEqualTo(Optional.of(user));
        verify(dao).findByUsername("hello");
    }

    @Test
    public void create() {
        when(dao.create(any(User.class))).thenReturn(user);
        assertThat(dao.create(user)).isEqualTo(user);
        verify(dao).create(user);
    }

    @Test
    public void findAll() {
        final ImmutableList<User> users = ImmutableList.of(user);
        when(dao.findAll()).thenReturn(users);
        assertThat(dao.findAll()).isEqualTo(users);
        verify(dao).findAll();
    }

    @Test
    public void delete() {
        dao.delete(100L);
        verify(dao).delete(100L);
    }

    @Test
    public void update() {
        when(dao.update(eq(101L), any(User.class))).thenReturn(user);
        assertThat(dao.update(101L, user)).isEqualTo(user);
        verify(dao).update(101L, user);
    }
}
