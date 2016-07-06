package com.bravozulu.db;

import com.bravozulu.core.Item;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

/**
 * Implementation of the the ItemDAO
 * Created by Mark on 7/1/16.
 */
public class ItemDAO extends AbstractDAO<Item>{
    public ItemDAO (SessionFactory factory) {
         super(factory);
    }

    /**
     * Creates and returns an Item
     * @param item the item
     * @return the item
     */
    public Item create(Item item) {return this.persist(item);}

    /**
     * Returns the Item if present in the database
     * @param id the id of the item
     * @return the item if present in the database; nothing otherwise
     */
    public Optional<Item> findById(long id) {
        return Optional.ofNullable(this.get(id));
    }

    /**
     * Returns a list of all items in the database
     * @return returns list of all items
     */
    public List<Item> findAll(){
        return list(namedQuery("com.bravozulu.core.Item.findAll"));
    }

    /**
     * Updates the name of the item in the database
     * @param item the item
     */
    public void updateItem(Item item){
        long itemId = item.getItemId();
        Session session = this.currentSession();

        // Create a temp item object to access change data in 'item'
        Item tempItem = (Item) session.get(Item.class, itemId);
        // update the item's name with the name form 'item'
        String updatedName = item.getName();
        tempItem.setName(updatedName);
        session.update(tempItem);

        // Produce feedback message and close session
        System.out.println("Item sucessfully updated.");
        session.close();
    }

    /**
     * Deletes the item from the database
     * @param id the item's id
     */
    public void deleteItem(Long id) {
        Session session = this.currentSession();

        // Get the item and delete
        Item itemTobeDeleted = (Item) session.get(Item.class, id);
        session.delete(itemTobeDeleted);

        // Produce feedback message and close session
        System.out.print("Item successfully deleted.");
        session.close();
    }
}
