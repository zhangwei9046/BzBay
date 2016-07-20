package com.bravozulu.core;

/**
 * Created by Mark on 7/20/16.
 */

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bravozulu.core.Item;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        //TODO create Item test object
        final Item item = new Item();
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/item.json"), Item.class));
        assertThat(MAPPER.writeValueAsString(item)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        //TODO create Item test object
        final Item item = new Item();
        assertThat(MAPPER.readValue(fixture("fixtures/item.json"), Item.class))
                .isEqualTo(item);
    }
}
