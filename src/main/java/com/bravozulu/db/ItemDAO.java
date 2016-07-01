package com.bravozulu.db;

import com.bravozulu.core.Item;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

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
     *
     * @param item
     */
    public Item updateItem(Item item){ return item;};

    /**
     *
     * @param id
     */
    public void deleteItem(Long id) {
        // delete the item on the database and print message
    };

}
