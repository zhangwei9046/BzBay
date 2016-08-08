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
public class ItemDAO extends AbstractDAO<Item>{
    private SessionFactory factory;

    public ItemDAO (SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    private UserDAO getUserDAO() {
        return new UserDAO(factory);
    }

    /**
     * Creates and returns an Item
     * @param item the item
     * @param seller the seller
     * @return the item
     */
    public Item create(Item item, User seller) {
        long sellerId = seller.getUserId();
        item.setSellerId(sellerId);
        return this.persist(item);
    }

    /**
     * Returns the Item if present in the database
     * @param id the id of the item
     * @return the item if present in the database; nothing otherwise
     */
    public Optional<Item> findById(long id) {
        return Optional.ofNullable(this.get(id));
    }

    /**
     * Returns an Item given the name of the item
     * @param name the name to search for
     * @return an Item; if there is no item to return, return empty
     */
    public Optional<Item> findByName(String name) {
        Query query = Preconditions.checkNotNull(namedQuery("com.bravozulu" +
                ".core.Item.findByName"));
        query.setParameter("name", name);
        List<Item> items = list(query);
        return items.size() == 0 ? Optional.empty() : Optional.of(items.get(0));
    }

    /**
     * Returns a list of all items in the database
     * @return returns list of all items
     */
    public List<Item> findAll(){
        return list(namedQuery("com.bravozulu.core.Item.findAll"));
    }

    /**
     *
     * @return
     */
    public List<Item> findAllAvailable(){
        return list(namedQuery("com.bravozulu.core.Item.available"));
    }

    /**
     * Updates the name of the item in the database
     * @param item the item
     */
    public Item updateItem(long itemId, Item item){
        item.setItemId(itemId);
        return persist(item);
    }

    /**
     * Deletes the item from the database
     * @param itemId the item's id
     */
    public void deleteItem(Long itemId, User seller) {
        if (checkItemToSeller(itemId, seller)) {
        Item itemObj = findById(itemId).orElseThrow(() -> new NotFoundException("No such " +
                "user."));
        currentSession().delete(itemObj);
        }
    }

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

    public void updateAvailable(Boolean available, Long itemId) {
        namedQuery("com.bravozulu.core.item.updateAvailable").setLong("itemId", itemId).setBoolean("available", available)
                .executeUpdate();
    }
}
