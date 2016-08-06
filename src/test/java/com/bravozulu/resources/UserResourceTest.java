package com.bravozulu.resources;

/**
 * Created by ying on 7/19/16.
 */
import com.bravozulu.auth.BzbayAuthenticator;
import com.bravozulu.core.User;
import com.bravozulu.db.UserDAO;
import com.google.common.collect.ImmutableList;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserResourceTest {
    private static final UserDAO dao = mock(UserDAO.class);
    private static final BzbayAuthenticator bzbayAuthenticator = Mockito.mock(BzbayAuthenticator.class);

    @ClassRule
//    public static final ResourceTestRule resources = ResourceTestRule.builder()
//            .addResource(new UserResource(dao))
//            .build();

    public static final ResourceTestRule resources =
            ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
                    .addProvider(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                            .setAuthenticator(UserResourceTest.bzbayAuthenticator).setRealm("Validate User")
                            .setPrefix("Basic").buildAuthFilter()))
                    .addProvider(RolesAllowedDynamicFeature.class)
                    .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
                    .addResource(new UserResource(dao))
                    .build();

    @Captor
    private ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    private User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
    private User user1 = new User("alice", "Alice", "Wonderland", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
    @Before
    public void setup() {
        user.setUserId(100L);
        when(bzbayAuthenticator.authenticate(new BasicCredentials("hello", "111")))
                .thenReturn(com.google.common.base.Optional.fromNullable(this.user));

        when(dao.findById(100L)).thenReturn(Optional.of(user));
        when(dao.findByUsername(eq("hello"))).thenReturn(Optional.of(user));
        when(dao.findByUsername(eq("alice"))).thenReturn(Optional.empty());
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

        final List<User> response = resources.getJerseyTest().target("/user")
                .request().header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx").get(new GenericType<List<User>>() {});

        verify(dao).findAll();
        assertThat(response).containsAll(users);
    }

    @Test
    public void createUser() {
        when(dao.create(any(User.class))).thenReturn(user1);
        final Response response = resources.getJerseyTest().target("/user")
                .request(MediaType.APPLICATION_JSON_TYPE).
                        header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .post(Entity.entity(user1, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).create(userCaptor.capture());
        assertThat(userCaptor.getValue()).isEqualTo(user1);

    }

    @Test
    public void getUserById() {
        assertThat(resources.getJerseyTest().target("/user/100").request(MediaType.APPLICATION_JSON).
                header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx").get(User.class))
                .isEqualTo(user);
        verify(dao).findById(100l);
    }

    @Test
    public void getUserByUsername() {
        assertThat(resources.getJerseyTest().target("/user/username=hello").request().
                header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx").get(User.class))
                .isEqualTo(user);
        verify(dao).findByUsername("hello");
    }

    @Test
    public void updateUser() {
        when(dao.update(any(User.class))).thenReturn(user);
        final Response response = resources.getJerseyTest().target("/user")
                .request(MediaType.APPLICATION_JSON_TYPE).
                        header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .put(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));

        verify(dao).update(userCaptor.capture());
        assertThat(userCaptor.getValue()).isEqualTo(user);
    }

    @Test
    public void deleteUser() {
        final Response response = resources.getJerseyTest().target("/user/100")
                .request(MediaType.APPLICATION_JSON_TYPE).
                        header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx").delete();
        verify(dao).delete(100L);
    }


}
