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

import java.sql.Timestamp;

public class ItemTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
/*
    @Test
    public void serializesToJSON() throws Exception {
        final Item item = new Item("Macbook Air", true, 12345, "1.0", "Mail",
                "Computers", "Used", "mymackbook.com", "Works really well.",
                42.42, 420.20, new Timestamp(2345), new Timestamp(4569));
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/item.json"), Item.class));
        assertThat(MAPPER.writeValueAsString(item)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Item item = new Item("Macbook Air", true, 12345, "1.0", "Mail",
                "Computers", "Used", "mymackbook.com", "Works really well.",
                42.42, 420.20, new Timestamp(2345), new Timestamp(4569));
        assertThat(MAPPER.readValue(fixture("fixtures/item.json"), Item.class))
                .isEqualTo(item);
    }

    */
}
