package com.bravozulu.db;

import com.bravozulu.core.Item;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import java.util.List;

/**
 * Created by bonicma on 7/1/16.
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
     * Returns a list of all items in the database
     * @return returns list of all items
     */
    public List<Item> findAll(){
        return list(namedQuery("com.bravozulu.core.Item.findAll"));
    }

    /**
     *
     * @param item
     */
    public Item updateItem(Item item){ return item;};

    /**
     *
     * @param item
     */
    public void deleteItem(Item item) {
        // delete the item on the database and orint message
    };

}
