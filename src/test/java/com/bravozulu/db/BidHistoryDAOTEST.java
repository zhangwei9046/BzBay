package com.bravozulu.db;

import com.bravozulu.core.BidHistory;
import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import org.hibernate.Query;
import org.junit.*;

import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

/**
 * Created by ying on 7/22/16.
 */

public class BidHistoryDAOTEST extends DAOTests {
    private BidHistoryDAO biddao;
    private ItemDAO itemDao;
    private UserDAO userDao;

    private BidHistory bid1;
    private BidHistory bid2;
    private BidHistory b1;
    private BidHistory b2;
    private String firstItemName = "Casio Watch";


    @Before
    public void setup() {
        this.biddao = new BidHistoryDAO(sessionFactory);
        this.itemDao = new ItemDAO(sessionFactory);
        this.userDao = new UserDAO(sessionFactory);
        getSession().beginTransaction();
        Query q1 = getSession().createQuery("delete from BidHistory");
        Query q2 = getSession().createQuery("delete from Item");
        Query q3 = getSession().createQuery("delete from User");
        q1.executeUpdate();
        q2.executeUpdate();
        q3.executeUpdate();

        User seller = new User("awalker", "Alice", "Walker", "awalker1",
                "1@1.com",
                "Seattle", "WA", "401 Terry Ave N", false);
        this.userDao.create(seller);
        User buyer = new User("bkennedy", "Bobby", "Kennedy", "bkennedy1",
                "1@1.com",
                "New York", "NY", "401 Terry Ave N", false);
        this.userDao.create(buyer);

        Optional<User> awalkerSeller = this.userDao.findByUsername("awalker");
        User sellerActual = awalkerSeller.get();
        long sellerid = awalkerSeller.get().getUserId();
        Item watch = new Item(this.firstItemName, true, sellerid,
                "F-28W",
                "USPS",
                "Watches", true, "www.casio.com", "Simple watch for the " +
                "simple person", 9.99, 0.00, new Timestamp(1470190003), new Timestamp(1472609203));
        Item item1 = itemDao.create(watch);

        long itemrealname = item1.getItemId();


        bid1 = new BidHistory(itemrealname, sellerid, 22F, new Timestamp(1470190003));

       this.biddao.create(bid1);

        getSession().getTransaction().commit();
    }

    @After
    public void tearDown() {
        getSession().beginTransaction();
        Query q1 = getSession().createQuery("delete from BidHistory");
        Query q2 = getSession().createQuery("delete from Item");
        Query q3 = getSession().createQuery("delete from User");
        q1.executeUpdate();
        q2.executeUpdate();
        q3.executeUpdate();
        getSession().getTransaction().commit();
    }
    @Test
    public void findBybidId() {
        getSession().beginTransaction();

        // Get required data
        List<BidHistory> list = this.biddao.findAll();
        long bidid = list.get(0).getBidId();
        long itemid = list.get(0).getItemId();
        long userid = list.get(0).getUserId();


        // Call the method to be tested and store the output
        Optional<BidHistory> firstbidoptional = this.biddao.findBybidId(bidid);
        BidHistory firstbid = firstbidoptional.get();

        // Run basic tests
        Assert.assertNotNull(firstbid);
        Assert.assertEquals(itemid, firstbid.getItemId());

        getSession().getTransaction().commit();

    }
    @Test
    public void testFindAll() {
        getSession().beginTransaction();

        // Call the method to be tested and store the output
        List<BidHistory> list = this.biddao.findAll();
        BidHistory firstbid = list.get(0);

        // Run basic tests
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 1);
        Assert.assertTrue(firstbid.getPrice()== 22F);
        Assert.assertFalse(firstbid.getPrice()== 23F);

        getSession().getTransaction().commit();
    }

    @Test
    public void testfindbyuserid() {
        getSession().beginTransaction();

        User seller = new User("awalker", "Alice", "Walker", "awalker1",
                "1@1.com",
                "Seattle", "WA", "401 Terry Ave N", false);
        this.userDao.create(seller);

        Optional<User> awalkerSeller = this.userDao.findByUsername("awalker");
        User sellerActual = awalkerSeller.get();
        long sellerid = awalkerSeller.get().getUserId();
        // Call the method to be tested and store the output
        List<BidHistory> list = this.biddao.findByUserId(sellerid);
        BidHistory firstbid = list.get(0);

        // Run basic tests
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 1);
        Assert.assertTrue(firstbid.getPrice()== 22F);
        Assert.assertFalse(firstbid.getPrice()== 23F);

        getSession().getTransaction().commit();
    }
/*
    @Test
    public void testfindbyitemid() {
        getSession().beginTransaction();
        List<BidHistory> list = this.biddao.findByItemId(34L);
        BidHistory firstbid = list.get(0);

        // Run basic tests
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(list.size(), 1);
        Assert.assertTrue(firstbid.getPrice()== 22F);
        Assert.assertFalse(firstbid.getPrice()== 23F);

        getSession().getTransaction().commit();
    }
*/
/*
    @Test
    public void create() {
        getSession().beginTransaction();
        BidHistory bidhistory = new BidHistory(bid1.getItemId(), bid1.getUserId(), bid1.getPrice());


        BidHistory b = biddao.create(bidhistory);
        assertEquals(biddao.findBybidId(b.getBidId()).get(), bidhistory);
        getSession().getTransaction().commit();
    }
*/

}
