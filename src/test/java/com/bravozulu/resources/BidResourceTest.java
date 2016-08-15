package com.bravozulu.resources;

import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.UserDAO;
import com.bravozulu.db.BidHistoryDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import static org.mockito.Mockito.*;

/*
public class BidResourceTest {

    private static final BidHistoryDAO bidhistorydao = mock(BidHistoryDAO.class);
    private static final ItemDAO itemDAO = mock(ItemDAO.class);
    private static final UserDAO userDAO = mock(UserDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder
            ().addResource(new ItemResource(itemDAO, userDAO)).build();



*?/


}
