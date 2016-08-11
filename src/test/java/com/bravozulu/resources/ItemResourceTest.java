package com.bravozulu.resources;

import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.UserDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import static org.mockito.Mockito.*;

/**
 * Created by bonicma on 7/20/16.
 */
public class ItemResourceTest {
    private static final ItemDAO itemDAO = mock(ItemDAO.class);
    private static final UserDAO userDAO = mock(UserDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder
            ().addResource(new ItemResource(itemDAO, userDAO)).build();






}
