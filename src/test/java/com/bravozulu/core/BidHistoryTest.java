package com.bravozulu.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by yuga on 8/04/16.
 */
public class BidHistoryTest {
    private BidHistory bid = new BidHistory(20, 21, 22);
    private BidHistory bid2 = new BidHistory(40, 41, 42);
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Before
    public void setUp() throws Exception {
        bid.setBidId(0);
        bid2.setBidId(1);
    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void serializesToJSON() throws Exception {
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/bidhistory.json"), BidHistory.class));

        /* assertThat(MAPPER.writeValueAsString(user)).isEqualTo(expected); */
    }
/*
    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/bidhistory.json"), BidHistory.class))
                .isEqualTo(bid);
    }
*/


    @Test
    public void getBidId() throws Exception {
        assertEquals(bid.getBidId(), 0);
        assertEquals(bid2.getBidId(), 1);
    }

    @Test
    public void setBidId() throws Exception {
        bid.setBidId(101L);
        assertEquals(bid.getBidId(), 101L);
    }

    @Test
    public void getItemId() throws Exception {
        assertEquals(bid.getItemId(), 20);
    }

    @Test
    public void setItemId() throws Exception {
        bid.setItemId(30);
        assertEquals(bid.getItemId(), 30);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(bid.getUserId(), 21);
    }

    @Test
    public void setUserId() throws Exception {
        bid.setUserId(31);
        assertEquals(bid.getUserId(), 31);
    }

    @Test
    public void getPrice() throws Exception {
        assertTrue(bid.getPrice() == 22);
    }

    @Test
    public void setPrice() throws Exception {
        bid.setPrice(32);
        assertTrue(bid.getPrice() == 32);
    }



}