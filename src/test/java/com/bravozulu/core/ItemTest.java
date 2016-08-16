package com.bravozulu.core;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;


public class ItemTest {
    private Item item;

    @Before
    public void setUp() throws Exception {
        System.out.println("Setting up test.");
        item = new Item("iPhone6", true, 1, "6th Generation",
                "USPS", "Electronics", true, "www.apple.com", "A smartphone.",
                599.99, 0, new Timestamp(1471360218), new Timestamp(1471360299));
        item.setItemId(42);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tearing down test.");
        item = null;
    }

    @Test
    public void getItemId() throws Exception {
        Assert.assertEquals(item.getItemId(), 42);
    }

    @Test
    public void setItemId() throws Exception {
        item.setItemId(13);
        Assert.assertEquals(item.getItemId(), 13);
    }

    @Test
    public void getName() throws Exception {
        Assert.assertEquals(item.getName(), "iPhone6");
    }

    @Test
    public void setName() throws Exception {
        item.setName("Nexus");
        Assert.assertEquals(item.getName(), "Nexus");
    }

    @Test
    public void isAvailable() throws Exception {
        Assert.assertTrue(item.isAvailable());
    }

    @Test
    public void setAvailable() throws Exception {
        item.setAvailable(false);
        Assert.assertFalse(item.isAvailable());
    }

    @Test
    public void getSellerId() throws Exception {
        Assert.assertEquals(item.getSellerId(), 1);
    }

    @Test
    public void setSellerId() throws Exception {
        item.setSellerId(45);
        Assert.assertEquals(item.getSellerId(), 45);
    }

    @Test
    public void getModel() throws Exception {
        Assert.assertEquals(item.getModel(), "6th Generation");
    }

    @Test
    public void setModel() throws Exception {
        item.setModel("Old gen");
        Assert.assertEquals(item.getModel(), "Old gen");
    }

    @Test
    public void getShipping() throws Exception {
        Assert.assertEquals(item.getShipping(), "USPS");
    }

    @Test
    public void setShipping() throws Exception {
        item.setShipping("FedEx");
        Assert.assertEquals(item.getShipping(), "FedEx");
    }

    @Test
    public void getCategory() throws Exception {
        Assert.assertEquals(item.getCategory(), "Electronics");
    }

    @Test
    public void setCategory() throws Exception {
        item.setCategory("Phones");
        Assert.assertEquals(item.getCategory(), "Phones");
    }

    @Test
    public void getCondition() throws Exception {
        Assert.assertTrue(item.getCondition());
    }

    @Test
    public void setCondition() throws Exception {
        item.setCondition(false);
        Assert.assertFalse(item.getCondition());
    }

    @Test
    public void getUrl() throws Exception {
        Assert.assertEquals(item.getUrl(), "www.apple.com");
    }

    @Test
    public void setUrl() throws Exception {
        item.setUrl("www.google.com");
        Assert.assertEquals(item.getUrl(), "www.google.com");
    }

    @Test
    public void getDescription() throws Exception {
        Assert.assertEquals(item.getDescription(), "A smartphone.");
    }

    @Test
    public void setDescription() throws Exception {
        item.setDescription("A dumbphone.");
        Assert.assertEquals(item.getDescription(), "A dumbphone.");
    }

    @Test
    public void getInitialPrice() throws Exception {
        Assert.assertEquals(item.getInitialPrice(), 599.99, 0);
    }

    @Test
    public void setInitialPrice() throws Exception {
        item.setInitialPrice(42.19);
        Assert.assertEquals(item.getInitialPrice(), 42.19, 0);
    }

    @Test
    public void getFinalPrice() throws Exception {
        Assert.assertEquals(item.getFinalPrice(), 0, 0);
    }

    @Test
    public void setFinalPrice() throws Exception {
        item.setFinalPrice(1000.99);
        Assert.assertEquals(item.getFinalPrice(), 1000.99, 0);
    }

    @Test
    public void getStartDate() throws Exception {
        Assert.assertEquals(item.getStartDate(), new Timestamp(1471360218));
    }

    @Test
    public void setStartDate() throws Exception {
        item.setStartDate(new Timestamp(1471360220));
        Assert.assertEquals(item.getStartDate(), new Timestamp(1471360220));
    }

    @Test
    public void getEndDate() throws Exception {
        Assert.assertEquals(item.getEndDate(), new Timestamp(1471360299));
    }

    @Test
    public void setEndDate() throws Exception {
        item.setEndDate(new Timestamp(1471369482));
        Assert.assertEquals(item.getEndDate(), new Timestamp(1471369482));
    }

    @Test
    public void equals() throws Exception {
        Item testItem = new Item("iPhone6", true, 1, "6th Generation",
                "USPS", "Electronics", true, "www.apple.com", "A smartphone.",
                599.99, 0, new Timestamp(1471360218), new Timestamp(1471360299));
        testItem.setItemId(42);
        Assert.assertEquals(item, testItem);
    }

    @Test
    public void testHashCode() throws Exception {
        Assert.assertEquals(item.hashCode(), 2092040144, 0);
    }

}