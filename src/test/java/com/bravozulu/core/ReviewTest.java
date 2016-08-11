package com.bravozulu.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by ying on 7/22/16.
 */
public class ReviewTest {

    private final Review review = new Review(1L, 2L, "Good!", 4.5);

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Before
    public void setUp() throws Exception {
        review.setReviewId(1L);
    }

    @After
    public void tearDown() throws Exception {

    }

//    @Test
//    public void serializesToJSON() throws Exception {
//        final String expected = MAPPER.writeValueAsString(
//                MAPPER.readValue(fixture("fixtures/review.json"), Review.class));
//
//        assertThat(MAPPER.writeValueAsString(review)).isEqualTo(expected);
//    }
//
//    @Test
//    public void deserializesFromJSON() throws Exception {
//        assertThat(MAPPER.readValue(fixture("fixtures/review.json"), Review.class))
//                .isEqualTo(review);
//    }

    @Test
    public void getReviewId() throws Exception {
        assertEquals(review.getReviewId(), 1L);
    }

    @Test
    public void setReviewId() throws Exception {
        review.setReviewId(2);
        assertEquals(review.getReviewId(), 2);
    }

    @Test
    public void getSenderId() throws Exception {
        assertEquals(review.getSenderId(), 1);
    }

    @Test
    public void setSenderId() throws Exception {
        review.setSenderId(2);
        assertEquals(review.getSenderId(), 2);
    }

    @Test
    public void getReceiverId() throws Exception {
        assertEquals(review.getReceiverId(), 2);
    }

    @Test
    public void setReceiverId() throws Exception {
        review.setReceiverId(3);
        assertEquals(review.getReceiverId(), 3);
    }

    @Test
    public void getContent() throws Exception {
        assertEquals(review.getContent(), "Good!");
    }

    @Test
    public void setContent() throws Exception {
        review.setContent("Bad!");
        assertEquals(review.getContent(), "Bad!");
    }

    @Test
    public void getScore() throws Exception {
        assertTrue(review.getScore() == 4.5);
    }

    @Test
    public void setScore() throws Exception {
        review.setScore(4.6);
        assertTrue(review.getScore() == 4.6);
    }

    @Test
    public void getDate() {
        assertEquals(review.getDate(), null);
    }

    @Test
    public void setDate() {
        review.setDate(new Date(8765));
        assertEquals(review.getDate(), new Timestamp(8765));
    }

    @Test
    public void equals() {
        review.setReviewId(0);
        Review r = new Review(1L, 2L, "Good!", 4.5);
        assertEquals(r, review);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(review.hashCode(), 864048834);
    }

    @Test
    public void toStringTest() {
        String s = "Review{reviewId=1, senderId=1, receiverId=2, content='Good!', score=4.5, date=null}";
        assertEquals(review.toString(), s);
    }
}