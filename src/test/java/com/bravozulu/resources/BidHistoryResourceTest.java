package com.bravozulu.resources;

import com.bravozulu.auth.BzbayAuthenticator;
import com.bravozulu.core.BidHistory;
import com.bravozulu.core.User;
import com.bravozulu.db.BidHistoryDAO;
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


public class BidHistoryResourceTest {
    private static final BidHistoryDAO bhDAO = mock(BidHistoryDAO.class);
    private static final ItemDAO itemDAO = mock(ItemDAO.class);
    private static final UserDAO userDAO = mock(UserDAO.class);
    private static final BzbayAuthenticator auth = mock (BzbayAuthenticator
            .class);

    @ClassRule
    public static final ResourceTestRule resources =
            ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
                    .addProvider(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                            .setAuthenticator(BidHistoryResourceTest.auth).setRealm
                                    ("Validate User")
                            .setPrefix("Basic").buildAuthFilter()))
                    .addProvider(RolesAllowedDynamicFeature.class)
                    .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
                    .addResource(new BidHistoryResource(bhDAO, itemDAO, userDAO))
                    .build();

    @Captor
    private ArgumentCaptor<BidHistory> bidHistoryArgumentCaptor =
            ArgumentCaptor.forClass(BidHistory.class);
    private BidHistory bh1;
    private BidHistory bh2;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void bid() throws Exception {

    }

    @Test
    public void findAllBidHistory() throws Exception {

    }

    @Test
    public void findBidById() throws Exception {

    }

    @Test
    public void findBidByUserId() throws Exception {

    }

    @Test
    public void findBidByItemId() throws Exception {

    }

    @Test
    public void findHighestPrice() throws Exception {

    }

}