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
import java.util.Optional;

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
     * Creates and adds item
     * @param item
     * @return
     */
    @POST
    @UnitOfWork
    public Item create(Item item) {
        return itemDAO.create(item);
    }


    /**
     * Returns the item given an item's identification
     * @param itemId the item id
     * @return the item in JSON format
     */
    @GET @Path("/{itemId}")
    @UnitOfWork
    @Produces("application/json")
    public Optional<Item> findItemById(@PathParam("itemId") long itemId) {
        return this.itemDAO.findById(itemId);
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
     * Updates an item based on some characteristic
     * @param item
     */
    @PUT
    @UnitOfWork
    public void update(Item item) {
        this.itemDAO.updateItem(item);
    }

    /**
     * Deletes the item
     * @param id the item's id
     */
    @DELETE
    @UnitOfWork
    public void delete(long id) {
        this.itemDAO.deleteItem(id);
    }
}
