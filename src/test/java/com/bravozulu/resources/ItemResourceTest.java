package com.bravozulu.resources;

import com.bravozulu.auth.BzbayAuthenticator;
import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.UserDAO;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * Created by bonicma on 8/16/16.
 */
public class ItemResourceTest {
    private static final ItemDAO itemDAO = mock(ItemDAO.class);
    private static final UserDAO userDAO = mock(UserDAO.class);
    private static final BzbayAuthenticator auth = Mockito.mock
            (BzbayAuthenticator.class);

    @ClassRule
    public static final ResourceTestRule resources =
            ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
                    .addProvider(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                            .setAuthenticator(ItemResourceTest.auth).setRealm
                                    ("Validate User")
                            .setPrefix("Basic").buildAuthFilter()))
                    .addProvider(RolesAllowedDynamicFeature.class)
                    .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
                    .addResource(new ItemResource(itemDAO, userDAO))
                    .build();

    @Captor
    private ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item
            .class);
    private Item item1;
    private Item item2;

    @Before
    public void setUp() throws Exception {
        item1.setItemId(42);

    }

    @After
    public void tearDown() throws Exception {
        reset(itemDAO);
    }

    @Test
    public void findAllItems() throws Exception {

    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void findAllAvailableItems() throws Exception {

    }

    @Test
    public void search() throws Exception {

    }

    @Test
    public void findItemByName() throws Exception {

    }

    @Test
    public void findItemById() throws Exception {

    }

}