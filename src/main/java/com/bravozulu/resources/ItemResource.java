package com.bravozulu.resources;

/**
 * Created by bonicma on 7/1/16.
 */

import com.bravozulu.core.Item;
import com.bravozulu.db.ItemDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.security.RolesAllowed;
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
     * Creates and adds item
     * @param item the item
     * @return the item
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Item create(Item item) {
        return itemDAO.create(item);
    }

    /**
     * Returns the item given an item's identification
     * @param itemId the item id
     * @return the item in JSON format
     */
    @GET
    @Path("/{itemId}")
    @Produces("application/json")
    @UnitOfWork
    public Item findItemById(@PathParam("itemId") LongParam itemId) {
        return this.itemDAO.findById(itemId.get()).orElseThrow(() -> new
                NotFoundException("No such item."));
    }

    /**
     * Returns information of an item given its name
     * @param name the name of the item
     * @return information about the item
     */
    @GET
    @Path("/{name}")
    @UnitOfWork
    public Item findItemByName(@PathParam("name") String name) {
        return this.itemDAO.findByName(name).orElseThrow( () -> new
                NotFoundException("No such item"));
    }

    /**
     * Returns list of all items
     * @return list of all items
     */
    @GET
    @RolesAllowed("Admin")
    @UnitOfWork
    public List<Item> findAllItems() {
        return itemDAO.findAll();
    }

    /**
     * Updates the item's name
     * @param item the item to be updated
     */
    @PUT
    @Path("/{itemId}")
    @UnitOfWork
    public void updateItem(@PathParam("itemId") LongParam itemId, @Auth Item
            item) {
        this.itemDAO.updateItem(itemId.get(), item);
    }

    /**
     * Deletes the item
     * @param itemId the item's id
     */
    @DELETE
    @Path("/{itemId}")
    @RolesAllowed("Admin")
    @UnitOfWork
    public void delete(@PathParam("itemId") LongParam itemId) {
        this.itemDAO.deleteItem(itemId.get());
    }
}
