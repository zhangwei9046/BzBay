package com.bravozulu.db;

import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import com.bravozulu.db.HibernateUtil;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metamodel.relational.Database;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created by bonicma on 7/20/16.
 */
public class ItemDAOTest extends DAOTests {
    private ItemDAO itemDAO;
    private UserDAO userDAO;

    @Before
    public void setup() {
        this.itemDAO = new ItemDAO(this.sessionFactory);
        this.userDAO = new UserDAO(this.sessionFactory);
        getSession().beginTransaction();
        Query q = getSession().createQuery("delete from Item");
        q.executeUpdate();

        // Add two users: Alice as the seller and Bob as the buyer
        User seller = new User("awalker", "Alice", "Walker", "awalker1",
                "1@1.com",
                "Seattle", "WA", "401 Terry Ave N", false);
        this.userDAO.create(seller);
        User buyer = new User("bkennedy", "Bobby", "Kennedy", "bkennedy1",
                "1@1.com",
                "New York", "NY", "401 Terry Ave N", false);

        // Add an item to the database so there is an auction with at least
        // one item
        Optional<User> awalkerSeller = this.userDAO.findByUsername("awalker");
        long sellerid = awalkerSeller.get().getUserId();
        Item watch = new Item("Casio Watch", true, sellerid,
                "F-28W",
                "USPS",
                "Watches", true, "www.casio.com", "Simple watch for the " +
                "simple person", 9.99, 0.00, new Timestamp(1470190003), new Timestamp(1472609203));
        this.itemDAO.create(watch);
        getSession().getTransaction().commit();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test for create in ItemDAO class
     */
    @Test
    public void testCreate() {
        getSession().beginTransaction();
        Optional<User> awalkerSeller = this.userDAO.findByUsername("awalker");
        long sellerid = awalkerSeller.get().getUserId();
        Item macbook =  new Item("Macbook Air", true, sellerid,
                "MacBookAir6,2",
                "USPS",
                "Computers", true, "www.apple.com", "The best " +
                "lightweight laptop on the market.", 899.99,
                0.00, new Timestamp(1470190003), new Timestamp(1472609203));
        this.itemDAO.create(macbook);
        List<Item> list = this.itemDAO.findAll();
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Macbook Air", this.itemDAO.findByName("Macbook " +
                "Air").get().getName());
        //Assert.assertNotNull(findItem);
        //Item foundMac = findItem.get();
        getSession().getTransaction().commit();
    }

    /**
     * Test for findById in ItemDAO class
     */
    @Test
    public void testFindById() {
        getSession().beginTransaction();

        getSession().getTransaction().commit();
    }

    /**
     * Test for findByName in ItemDAO class
     */
    @Test
    public void testFindByName() {
        getSession().beginTransaction();

        getSession().getTransaction().commit();
    }

    /**
     * Test for findAll method in ItemDAO class
     */
    @Test
    public void testFindAll() {
       // getSession().beginTransaction();
        List<Item> list = this.itemDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getName(), "Casio Watch");
        //getSession().getTransaction().commit();
    }

    /**
     * Test for updateItem in ItemDAO class
     */
    @Test
    public void testUpdateItem() {
        getSession().beginTransaction();

        getSession().getTransaction().commit();
    }

    /**
     * Test for deleteItem in ItemDAO class
     */
    @Test
    public void testDeleteItem() {
        getSession().beginTransaction();

        getSession().getTransaction().commit();
    }
}
