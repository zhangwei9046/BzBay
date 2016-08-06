package com.bravozulu.db;
import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import org.hibernate.Query;
import org.junit.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Created by bonicma on 7/20/16.
 */
public class ItemDAOTest extends DAOTests {
    private ItemDAO itemDAO;
    private UserDAO userDAO;
    private String firstItemName = "Casio Watch";
    private static Logger logger = LoggerFactory.getLogger(ItemDAOTest
            .class);

    @Before
    public void setup() {
        this.itemDAO = new ItemDAO(this.sessionFactory);
        this.userDAO = new UserDAO(this.sessionFactory);
        getSession().beginTransaction();
        Query q = getSession().createQuery("delete from Item");
        Query q2 = getSession().createQuery("delete from User");
        q.executeUpdate();
        q2.executeUpdate();

        // Add two users: Alice as the seller and Bob as the buyer
        User seller = new User("awalker", "Alice", "Walker", "awalker1",
                "1@1.com",
                "Seattle", "WA", "401 Terry Ave N", false);
        this.userDAO.create(seller);
        User buyer = new User("bkennedy", "Bobby", "Kennedy", "bkennedy1",
                "1@1.com",
                "New York", "NY", "401 Terry Ave N", false);
        this.userDAO.create(buyer);

        // Add an item to the database so there is an auction marketplace with
        // at least one item
        Optional<User> awalkerSeller = this.userDAO.findByUsername("awalker");
        User sellerActual = awalkerSeller.get();
        long sellerid = awalkerSeller.get().getUserId();
        Item watch = new Item(this.firstItemName, true, sellerid,
                "F-28W",
                "USPS",
                "Watches", true, "www.casio.com", "Simple watch for the " +
                "simple person", 9.99, 0.00, new Timestamp(1470190003), new Timestamp(1472609203));
        this.itemDAO.create(watch, sellerActual);
        getSession().getTransaction().commit();
    }

    @After
    public void tearDown() {
        getSession().beginTransaction();
        Query q = getSession().createQuery("delete from Item");
        Query q2 = getSession().createQuery("delete from User");
        q.executeUpdate();
        q2.executeUpdate();
        getSession().getTransaction().commit();
    }

    /**
     * Test for findAll method in ItemDAO class
     */
    @Test
    public void testFindAll() {
        getSession().beginTransaction();

        // Call the method to be tested and store the output
        List<Item> list = this.itemDAO.findAll();
        Item firstItem = list.get(0);

        // Run basic tests
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(firstItem.getName(), this.firstItemName);
        Assert.assertFalse(firstItem.getName().equals("meatballs"));

        getSession().getTransaction().commit();
    }

    /**
     * Test for findById in ItemDAO class
     */
    @Test
    public void testFindById() {
        getSession().beginTransaction();

        // Get required data
        List<Item> list = this.itemDAO.findAll();
        long firstItemId = list.get(0).getItemId();
        String firstItemName = list.get(0).getName();

        // Call the method to be tested and store the output
        Optional<Item> firstItemOptional = this.itemDAO.findById(firstItemId);
        Item firstItem = firstItemOptional.get();

        // Run basic tests
        Assert.assertNotNull(firstItem);
        Assert.assertEquals(firstItemName, firstItem.getName());

        getSession().getTransaction().commit();
    }

    /**
     * Test for findByName in ItemDAO class
     */
    @Test
    public void testFindByName() {
        getSession().beginTransaction();

        // Call the method to be tested and store the output
        Optional<Item> firstItemOptional = this.itemDAO.findByName(this
                .firstItemName);
        Item firstItem = firstItemOptional.get();

        // Run basic tests
        Assert.assertNotNull(firstItem);
        Assert.assertEquals(firstItem.getName(), this.firstItemName);

        getSession().getTransaction().commit();
    }

    /**
     * Test for create in ItemDAO class
     */
    @Test
    public void testCreate() {
        getSession().beginTransaction();

        // Get required data
        Optional<User> awalkerSeller = this.userDAO.findByUsername("awalker");
        long sellerid = awalkerSeller.get().getUserId();
        String addedItemName = "Macbook Air";
        Item macbook =  new Item(addedItemName, "MacBookAir6,2", "USPS",
                "Computers", true, "www.apple.com", "The best " +
                "lightweight laptop on the market.", 899.99, new Timestamp
                (1470190003), new Timestamp(1472609203));

        // Call the method to be tested
        User seller = awalkerSeller.get();
        Item createdItem = this.itemDAO.create(macbook, seller);

        // Run basic tests
        List<Item> list = this.itemDAO.findAll();
        Optional<Item> addedItemOptional = this.itemDAO.findByName
                (addedItemName);
        Item addedItem = addedItemOptional.get();
        Assert.assertNotNull(createdItem);
        Assert.assertEquals(createdItem.getName(), addedItemName);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(addedItem.getName(), addedItemName);

        getSession().getTransaction().commit();
    }

    /**
     * Test for updateItem in ItemDAO class
     */
    @Test
    public void testUpdateItem() {
        getSession().beginTransaction();

        // Get the required data
        List<Item> list = this.itemDAO.findAll();
        Item firstItem = list.get(0);
        long firstItemId = list.get(0).getItemId();
        firstItem.setName("Rolex");

        // Call the method to be tested
        Item firstItemUpdated = this.itemDAO.updateItem(firstItemId, firstItem);

        // Run basic tests
        Assert.assertNotNull(firstItemUpdated);
        Assert.assertEquals(firstItemUpdated.getName(), "Rolex");
        String firstItemUpdatedName = list.get(0).getName();
        Assert.assertEquals(firstItemUpdatedName, "Rolex");

        getSession().getTransaction().commit();
    }

    /**
     * Test for deleteItem in ItemDAO class
     */
    @Test
    public void testDeleteItem() {
        getSession().beginTransaction();

        // Get the required data
        List<Item> list = this.itemDAO.findAll();
        long itemId = list.get(0).getItemId();
        Optional<User> sellerOptional = this.userDAO.findByUsername("awalker");
        Optional<User> buyerOptional = this.userDAO.findByUsername("bkennedy");
        User seller = sellerOptional.get();
        User buyer = buyerOptional.get();

        // Call the method to be tested
        this.itemDAO.deleteItem(itemId, buyer);

        // Run basic tests
        Assert.assertEquals(list.size(), 1);
        Assert.assertTrue(this.itemDAO.findById(itemId).isPresent());

        // Call the method to be tested
        this.itemDAO.deleteItem(itemId, seller);

        // Run basic tests
        List<Item> listAfterDelete = this.itemDAO.findAll();
        Assert.assertEquals(listAfterDelete.size(), 0);
        Assert.assertFalse(this.itemDAO.findById(itemId).isPresent());

        getSession().getTransaction().commit();
    }
}
