package com.bravozulu.resources;

/**
 * Created by ying on 7/19/16.
 */
import com.bravozulu.auth.BzbayAuthenticator;
import com.bravozulu.core.Review;
import com.bravozulu.core.User;
import com.bravozulu.db.ReviewDAO;
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

public class ReviewResourceTest {
    private static final ReviewDAO reviewDao = mock(ReviewDAO.class);
    private static final UserDAO userDao = mock(UserDAO.class);
    private static final BzbayAuthenticator bzbayAuthenticator = mock(BzbayAuthenticator.class);

    @ClassRule
//    public static final ResourceTestRule resources = ResourceTestRule.builder()
//            .addResource(new ReviewResource(reviewDao, userDao))
//            .build();

    public static final ResourceTestRule resources =
            ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
                    .addProvider(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                            .setAuthenticator(ReviewResourceTest.bzbayAuthenticator).setRealm("Validate User")
                            .setPrefix("Basic").buildAuthFilter()))
                    .addProvider(RolesAllowedDynamicFeature.class)
                    .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
                    .addResource(new ReviewResource(reviewDao, userDao))
                    .build();

    @Captor
    private ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);
    private final User user = new User("hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
    private final User user1 = new User("alice", "Alice", "Wonderland", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
    private Review review = new Review(1L, 2L, "Good!", 4.5);;

    @Before
    public void setup() {
        review.setReviewId(1L);
        user.setUserId(1L);
        user1.setUserId(2L);
        when(bzbayAuthenticator.authenticate(new BasicCredentials("hello", "111")))
                .thenReturn(com.google.common.base.Optional.fromNullable(this.user));
        when(bzbayAuthenticator.authenticate(new BasicCredentials("alice", "111")))
                .thenReturn(com.google.common.base.Optional.fromNullable(this.user));
        when(userDao.findByUsername(eq("hello"))).thenReturn(Optional.of(user));
        when(userDao.findByUsername(eq("alice"))).thenReturn(Optional.of(user1));
        when(reviewDao.findById(1L)).thenReturn(Optional.of(review));
    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(reviewDao);
        reset(userDao);
    }


    @Test
    public void findAllReviews() {
        final ImmutableList<Review> reviews = ImmutableList.of(review);
        when(reviewDao.findAll()).thenReturn(reviews);

        final List<Review> response = resources.getJerseyTest().target("/review")
                .request().header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(new GenericType<List<Review>>() {});

        verify(reviewDao).findAll();
        assertThat(response).containsAll(reviews);
    }

    @Test
    public void leaveAReview() {
        when(reviewDao.create(any(Review.class))).thenReturn(review);
        final Response response = resources.getJerseyTest().target("/review")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .post(Entity.entity(review, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(reviewDao).create(reviewCaptor.capture());
        assertThat(reviewCaptor.getValue()).isEqualTo(review);
    }

    @Test
    public void deleteReview() {
        final Response response = resources.getJerseyTest().target("/review/1")
                .request(MediaType.APPLICATION_JSON_TYPE).
                        header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx").delete();
        verify(reviewDao).delete(1L);
    }

    @Test
    public void findReviewsForSender() {
        final ImmutableList<Review> reviews = ImmutableList.of(review);
        when(reviewDao.findBySenderId(1L)).thenReturn(reviews);

        final List<Review> response = resources.getJerseyTest().target("/review/sendername=hello")
                .request().header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(new GenericType<List<Review>>() {});

        verify(reviewDao).findBySenderId(1L);
        assertThat(response).containsAll(reviews);
    }

    @Test
    public void findReviewsForReceiver() {
        final ImmutableList<Review> reviews = ImmutableList.of(review);
        when(reviewDao.findByReceiverId(2L)).thenReturn(reviews);

        final List<Review> response = resources.getJerseyTest().target("/review/receivername=alice")
                .request().header(HttpHeaders.AUTHORIZATION, "Basic YWxpY2U6MTEx")
                .get(new GenericType<List<Review>>() {});

        verify(reviewDao).findByReceiverId(2L);
        assertThat(response).containsAll(reviews);
    }
}
