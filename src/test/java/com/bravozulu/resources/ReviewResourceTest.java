package com.bravozulu.resources;

/**
 * Created by ying on 7/19/16.
 */
import com.bravozulu.core.Review;
import com.bravozulu.core.User;
import com.bravozulu.db.ReviewDAO;
import com.bravozulu.db.UserDAO;
import com.google.common.collect.ImmutableList;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReviewResourceTest {
    private static final ReviewDAO reviewDao = mock(ReviewDAO.class);
    private static final UserDAO userDao = mock(UserDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ReviewResource(reviewDao, userDao))
            .build();

    private final User user = new User(100l, "hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
    private final Review review = new Review(1L, 2L, "Good!", 4.5);

    @Before
    public void setup() {
        review.setReviewId(1);
    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(reviewDao);
        reset(userDao);
    }

/*
    @Test
    public void findAllReviews() {
        final ImmutableList<Review> reviews = ImmutableList.of(review);
        when(reviewDao.findAll()).thenReturn(reviews);

        final List<Review> response = resources.client().target("/review")
                .request().get(new GenericType<List<Review>>() {});

        verify(reviewDao).findAll();
        assertThat(response).containsAll(reviews);
    }
*/
    @Test
    public void createReview() {

    }

    @Test
    public void updateReview() {

    }

    @Test
    public void deleteReview() {

    }

    @Test
    public void findReviewsForSender() {

    }

    @Test
    public void findReviewsForReceiver() {

    }
}
