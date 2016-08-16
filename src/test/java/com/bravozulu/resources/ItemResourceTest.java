package com.bravozulu.resources;

import com.bravozulu.auth.BzbayAuthenticator;
import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.UserDAO;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.testing.junit.ResourceTestRule;
import jersey.repackaged.com.google.common.collect.ImmutableList;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemResourceTest {
    private static final ItemDAO itemDAO = mock(ItemDAO.class);
    private static final UserDAO userDAO = mock(UserDAO.class);
    private static final BzbayAuthenticator auth = mock(BzbayAuthenticator.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addProvider(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(ItemResourceTest.auth)
                    .setRealm("Validate User")
                    .setPrefix("Basic").buildAuthFilter()))
            .addProvider(RolesAllowedDynamicFeature.class)
            .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
            .addResource(new ItemResource(itemDAO, userDAO))
            .build();

    @Captor
    private ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
    private final User user = new User("hello", "Hello", "World", "111", "1@1",
            "Seattle", "WA", "401 Terry Ave N", true);
    private Item item1 = new Item("Bose Headphones", true, 3, "25QC", "Amazon" +
            " Drone", "headphones", true, "www.bose.com", "Really expensive " +
            "headphones.", 299.99, 0.00, new Timestamp(1471379415), new
            Timestamp(1471379715));
    private Item item2 = new Item("Apple Headphones", true, 3, "White Model",
            "Amazon" +
            " Drone", "headphones", true, "www.apple.com", "Really cheap " +
            "headphones.", 10.99, 0.00, new Timestamp(1471379415), new
            Timestamp(1471379715));

    @Before
    public void setUp() throws Exception {
        user.setUserId(1L);
        item1.setItemId(1);
        when(auth.authenticate(new BasicCredentials("hello", "111")))
                .thenReturn(com.google.common.base.Optional.fromNullable(this
                        .user));
        when(userDAO.findByUsername(eq("hello"))).thenReturn(Optional.of(user));
        when(itemDAO.findById(3)).thenReturn(Optional.of(item1));
        when(itemDAO.findByName("Bose Headphones")).thenReturn(Optional.of
                (item1));
    }

    @After
    public void tearDown() throws Exception {
        reset(itemDAO);
        reset(userDAO);
    }

    @Test
    public void findAllItems() throws Exception {
        // Construct a list of all items in the database
        final ImmutableList<Item> list = ImmutableList.of(item1);
        when(itemDAO.findAll()).thenReturn(list);

        // Get the list of all items from the resource endpoint
        final List<Item> response = resources.getJerseyTest()
                .target("/item")
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(new GenericType<List<Item>>() {} );

        // Check the associated DAO method call and run test
        verify(itemDAO).findAll();
        response.get(0).setAvailable(true);
        response.get(0).setStartDate(new Timestamp(1471379415));
        response.get(0).setEndDate(new Timestamp(1471379715));
        assertThat(response).containsAll(list);
    }

    @Test
    public void create() throws Exception {
        when(itemDAO.create(any(Item.class))).thenReturn(this.item2);

        final Response response = resources.getJerseyTest().target
                ("/item")
                .request(MediaType.APPLICATION_JSON_TYPE).
                        header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .post(Entity.entity(this.item2, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(itemDAO).create(itemCaptor.capture());
        Item createdItem = itemCaptor.getValue();
        createdItem.setAvailable(true);
        createdItem.setStartDate(new Timestamp(1471379415));
        createdItem.setEndDate(new Timestamp(1471379715));
        createdItem.setSellerId(3);
        assertThat(createdItem).isEqualTo(item2);
    }

    @Test
    public void findAllAvailableItems() throws Exception {
        // Construct a list of all items in the database
        final ImmutableList<Item> list = ImmutableList.of(item1);
        when(itemDAO.findAllAvailable()).thenReturn(list);

        // Get the list of all items from the resource endpoint
        final List<Item> response = resources.getJerseyTest()
                .target("/item/available")
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(new GenericType<List<Item>>() {} );

        response.get(0).setAvailable(true);
        response.get(0).setStartDate(new Timestamp(1471379415));
        response.get(0).setEndDate(new Timestamp(1471379715));
        verify(itemDAO).findAllAvailable();
        assertThat(response).isEqualTo(list);
    }

    @Test
    public void search() throws Exception {
        /*
        final ImmutableList<Item> list = ImmutableList.of(item1);
        when(itemDAO.search("headphones")).thenReturn(list);

        final List<Item> response = resources.getJerseyTest().target
                ("/item/search").request().header
                (HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(new GenericType<List<Item>>() {});
        System.out.println(response);

        //verify(itemDAO).search("headphones");

        */

    }

    @Test
    public void findItemByName() throws Exception {
        final Item response = resources.getJerseyTest()
                .target("/item/name/Bose%20Headphones")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(Item.class);
        response.setAvailable(true);
        response.setStartDate(new Timestamp(1471379415));
        response.setEndDate(new Timestamp(1471379715));
        assertThat(response).isEqualTo(item1);
        verify(itemDAO).findByName("Bose Headphones");
    }

    @Test
    public void findItemById() throws Exception {
        final Item response = resources.getJerseyTest()
                .target("/item/3")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(Item.class);
        response.setAvailable(true);
        response.setStartDate(new Timestamp(1471379415));
        response.setEndDate(new Timestamp(1471379715));
        assertThat(response).isEqualTo(item1);
        verify(itemDAO).findById(3);
    }

}