package com.bravozulu.core;

/**
 * Created by ying on 7/19/16.
 */

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bravozulu.core.Review;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ReviewTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
/*
    @Test
    public void serializesToJSON() throws Exception {
        final Review review = new Review();

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/user.json"), Review.class));

        assertThat(MAPPER.writeValueAsString(review)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Review user = new Review();
        assertThat(MAPPER.readValue(fixture("fixtures/user.json"), Review.class))
                .isEqualTo(user);
    }
    */
}
