package com.bravozulu.resources;

import com.bravozulu.auth.BzbayAuthenticator;
import com.bravozulu.core.BidHistory;
import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import com.bravozulu.db.BidHistoryDAO;
import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.UserDAO;
import com.google.common.collect.ImmutableList;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
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


public class BidHistoryResourceTest {
    private static final BidHistoryDAO bidDAO = mock(BidHistoryDAO.class);
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
                    .addResource(new BidHistoryResource(bidDAO, itemDAO, userDAO))
                    .build();

    @Captor
    private ArgumentCaptor<BidHistory> bidHistoryArgumentCaptor =
            ArgumentCaptor.forClass(BidHistory.class);
    private final User user = new User("hello", "Hello", "World", "111",
            "1@1", "Seattle", "WA", "401 Terry Ave N", true);
    private final Item item1 = new Item("Bose Headphones", true, 2, "25QC", "Amazon" +
            " Drone", "headphones", true, "www.bose.com", "Really expensive " +
            "headphones.", 299.99, 0.00, new Timestamp(1471379415), new
            Timestamp(1471379715));
    private BidHistory bid1 = new BidHistory(1L,2L,33F, new Timestamp(1313773206));



    @Before
    public void setUp() throws Exception {
        bid1.setBidId(0);
        item1.setItemId(2L);
        user.setUserId(1L);
        when(auth.authenticate(new BasicCredentials("hello", "111")))
                .thenReturn(com.google.common.base.Optional.fromNullable(this.user));
        when(userDAO.findByUsername(eq("hello"))).thenReturn(Optional.of(user));
        when(itemDAO.findById(2)).thenReturn(Optional.of(item1));
        when(bidDAO.findBybidId(0)).thenReturn(Optional.of(bid1));
    }

    @After
    public void tearDown() throws Exception {
        reset(bidDAO);
        reset((itemDAO));
        reset(userDAO);
    }


    @Test
    public void findAllBidHistory() throws Exception {
        final ImmutableList<BidHistory> bid = ImmutableList.of(bid1);
        when(bidDAO.findAll()).thenReturn(bid);

        final List<BidHistory> response = resources.getJerseyTest().target("/bidhistory")
                .request().header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(new GenericType<List<BidHistory>>() {});
        response.get(0).setTime(new Timestamp(1313773206));
        verify(bidDAO).findAll();
        assertThat(response).containsAll(bid);

    }

    @Test
    public void findBidById() throws Exception{
        final BidHistory response = resources.getJerseyTest()
                .target("/bidhistory/0")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic aGVsbG86MTEx")
                .get(BidHistory.class);
       response.setTime(new Timestamp(1313773206));
        assertThat(response).isEqualTo(bid1);
        verify(bidDAO).findBybidId(0);

    }



}