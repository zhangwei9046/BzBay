package com.bravozulu.core;

/**
 * Created by ying on 7/19/16.
 */

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bravozulu.core.User;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;


public class UserTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final User user= new User(100l, "hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/user.json"), User.class));

        assertThat(MAPPER.writeValueAsString(user)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final User user = new User(100l, "hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
        assertThat(MAPPER.readValue(fixture("fixtures/user.json"), User.class))
                .isEqualTo(user);
    }
}
