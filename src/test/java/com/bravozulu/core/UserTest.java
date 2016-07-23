package com.bravozulu.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by ying on 7/22/16.
 */
public class UserTest {
    private User user = new User(100L, "hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }



    @Test
    public void serializesToJSON() throws Exception {
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/user.json"), User.class));

        assertThat(MAPPER.writeValueAsString(user)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/user.json"), User.class))
                .isEqualTo(user);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(user.getName(), "hello");
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(user.getUserId(), 100L);
    }

    @Test
    public void setUserId() throws Exception {
        user.setUserId(101L);
        assertEquals(user.getUserId(), 101L);
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals(user.getPassword(), "111");
    }

    @Test
    public void setPassword() throws Exception {
        user.setPassword("222");
        assertEquals(user.getPassword(), "222");
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals(user.getEmail(), "1@1");
    }

    @Test
    public void setEmail() throws Exception {
        user.setEmail("2@2");
        assertEquals(user.getEmail(), "2@2");
    }

    @Test
    public void getCity() throws Exception {
        assertEquals(user.getCity(), "Seattle");
    }

    @Test
    public void setCity() throws Exception {
        user.setCity("Bellevue");
        assertEquals(user.getCity(), "Bellevue");
    }

    @Test
    public void getState() throws Exception {
        assertEquals(user.getState(), "WA");
    }

    @Test
    public void setState() throws Exception {
        user.setState("CA");
        assertEquals(user.getState(), "CA");
    }

    @Test
    public void getAddress() throws Exception {
        assertEquals(user.getAddress(), "401 Terry Ave N");
    }

    @Test
    public void setAddress() throws Exception {
        user.setAddress("422 Yale Ave N");
        assertEquals(user.getAddress(), "422 Yale Ave N");
    }

    @Test
    public void isAdmin() throws Exception {
        assertEquals(user.isAdmin(), true);
    }

    @Test
    public void setAdmin() throws Exception {
        user.setAdmin(false);
        assertEquals(user.isAdmin(), false);
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals(user.getUsername(), "hello");
    }

    @Test
    public void setUsername() throws Exception {
        user.setUsername("good");
        assertEquals(user.getUsername(), "good");
    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals(user.getFirstName(), "Hello");
    }

    @Test
    public void setFirstName() throws Exception {
        user.setFirstName("Good");
        assertEquals(user.getFirstName(), "Good");
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals(user.getLastName(), "World");
    }

    @Test
    public void setLastName() throws Exception {
        user.setLastName("Morning");
        assertEquals(user.getLastName(), "Morning");
    }

    @Test
    public void toStringTest() throws Exception {
        String res = "User{userId=100, username='hello', firstName='Hello', lastName='World', password='111', email='1@1', city='Seattle', state='WA', address='401 Terry Ave N', isAdmin=true}";
        assertEquals(user.toString(), res);
    }

    @Test
    public void equals() throws Exception {
        User userObj = new User(100L, "hello", "Hello", "World", "111", "1@1", "Seattle", "WA", "401 Terry Ave N", true);
        assertEquals(user, userObj);
    }

    @Test
    public void hashCodeTest() throws Exception {
        assertEquals(user.hashCode(), -883573497);
    }

}