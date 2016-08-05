package com.bravozulu.resources;

/**
 * Created by ying on 7/19/16.
 */
import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import com.google.common.collect.ImmutableList;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserResourceTest {
    private static final UserDAO dao = mock(UserDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UserResource(dao))
            .build();

    @Captor
    private ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    private User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);

    @Before
    public void setup() {
        user.setUserId(100L);
        when(dao.findById(100L)).thenReturn(Optional.of(user));
        when(dao.findByUsername(eq("hello"))).thenReturn(Optional.of(user));
        when(dao.create(any(User.class))).thenReturn(user);

    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(dao);

    }

    @Test
    public void findAllUsers() {
        final ImmutableList<User> users = ImmutableList.of(user);
        when(dao.findAll()).thenReturn(users);

        final List<User> response = resources.client().target("/user")
                .request().get(new GenericType<List<User>>() {});

        verify(dao).findAll();
        assertThat(response).containsAll(users);
    }

    @Test
    public void createUser() {
        when(dao.create(any(User.class))).thenReturn(user);
        final Response response = resources.client().target("/user")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).create(userCaptor.capture());
        assertThat(userCaptor.getValue()).isEqualTo(user);

    }

    @Test
    public void getUserById() {
        assertThat(resources.client().target("/user/100").request().get(User.class))
                .isEqualTo(user);
        verify(dao).findById(100l);
    }

    @Test
    public void getUserByUsername() {
        assertThat(resources.client().target("/user/username=hello").request().get(User.class))
                .isEqualTo(user);
        verify(dao).findByUsername("hello");
    }

    @Test
    public void updateUser() {
        when(dao.update(any(User.class))).thenReturn(user);
        final Response response = resources.client().target("/user/101")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));

        verify(dao).update(userCaptor.capture());
        assertThat(userCaptor.getValue()).isEqualTo(user);
    }

    @Test
    public void deleteUser() {
        final Response response = resources.client().target("/user/100")
                .request(MediaType.APPLICATION_JSON_TYPE).delete();
        verify(dao).delete(100L);
    }


}
