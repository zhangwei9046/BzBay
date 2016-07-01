package com.bravozulu.resources;

/**
 * Created by bonicma on 7/1/16.
 */

import com.bravozulu.core.Item;
import com.bravozulu.db.ItemDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {
    private ItemDAO itemDAO;

    /**
     * Constructor for ItemResource
     * @param itemDAO the item DAO
     */
    public ItemResource(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    /**
     *
     * @param item
     * @return
     */
    @POST
    @UnitOfWork
    public Item create(Item item) {
        return itemDAO.create(item);
    }

    /**
     *
     * @return
     */
    @GET
    @UnitOfWork
    public List<Item> findAllItems() {
        return itemDAO.findAll();
    }

    /**
     *
     * @param item
     * @return
     */
    @PUT
    @UnitOfWork
    public Item update(Item item) {
        return this.itemDAO.updateItem(item);
    }

    /**
     *
     * @param item
     */
    @DELETE
    @UnitOfWork
    public void delete(Item item) {
        this.itemDAO.deleteItem(item);
    }
}
