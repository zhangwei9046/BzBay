package com.bravozulu.db;

import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import com.google.common.base.Preconditions;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

import org.hibernate.Query;

import javax.ws.rs.NotFoundException;

/**
 * Implementation of the the ItemDAO
 * Created by Mark on 7/1/16.
 */
public class ItemDAO extends AbstractDAO<Item> {
    private SessionFactory factory;

    public ItemDAO(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    private UserDAO getUserDAO() {
        return new UserDAO(factory);
    }

    /**
     * Creates and returns an Item
     *
     * @param item the item
     * @return the item
     */
    public Item create(Item item) {
        item.setAvailable(true);
        return this.persist(item);
    }

    /**
     * Returns the Item given it's id
     *
     * @param id the id of the item
     * @return the item if item exists; appropriate message otherwise
     */
    public Optional<Item> findById(long id) {
        return Optional.ofNullable(this.get(id));
    }

    /**
     * Returns the Item given its name
     *
     * @param name the name to search for
     * @return the item if item exists; appropriate message otherwise
     */
    public Optional<Item> findByName(String name) {
        Query query = namedQuery("com.bravozulu.core.Item.findByName")
                .setParameter("name", name);
        return Optional.ofNullable(this.uniqueResult(query));
    }

    /**
     *
     * @param category
     * @return
     */
    public List<Item> search(String category, Boolean condition, long
            sellerId) {
        return list(namedQuery("com.bravozulu.core.item.search").setParameter
                ("category", category).setParameter("condition", condition)
                .setParameter("sellerId", sellerId));
    }

    /**
     * Returns a list of all items in the database
     *
     * @return returns list of all items
     */
    public List<Item> findAll() {
        return list(namedQuery("com.bravozulu.core.Item.findAll"));
    }

    /**
     * Returns list of all available items
     *
     * @return list
     */
    public List<Item> findAllAvailable() {
        return list(namedQuery("com.bravozulu.core.Item.available"));
    }

    /**
     * Deletes the item from the database
     *
     * @param itemId the item's id
     */
    public void deleteItem(Long itemId, User seller) {
        if (checkItemToSeller(itemId, seller)) {
            Item itemObj = findById(itemId).orElseThrow(() -> new NotFoundException("No such " +
                    "user."));
            currentSession().delete(itemObj);
        }
    }

    /**
     * Checks if item is associated with the seller
     *
     * @param itemId the item id
     * @param seller the seller
     * @return return true if the item belongs to the seller; false otherwise
     */
    private boolean checkItemToSeller(long itemId, User seller) {
        Item item = findById(itemId).get();
        long itemSellerId = item.getSellerId();
        long sellerId = seller.getUserId();

        UserDAO userDAO = this.getUserDAO();
        String itemSellerIdPassword = userDAO.findById(itemSellerId).get()
                .getPassword();
        String sellerPassword = seller.getPassword();

        return (itemSellerId == sellerId) && itemSellerIdPassword.equals(sellerPassword);
    }

    /**
     * Updates the item's availability status
     *
     * @param available the availability status
     * @param itemId    the item's id
     */
    public void updateAvailable(Boolean available, Long itemId) {
        namedQuery("com.bravozulu.core.item.updateAvailable").setLong("itemId", itemId).setBoolean("available", available)
                .executeUpdate();
    }
}


