package com.bravozulu.resources;

/**
 * Created by bonicma on 7/1/16.
 */

import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.UserDAO;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/item", description = "Post an item on the auction.")
public class ItemResource {
    private final ItemDAO itemDAO;
    private final UserDAO userDAO;

    /**
     * Constructor for ItemResource
     * @param itemDAO the item DAO
     */
    public ItemResource(ItemDAO itemDAO, UserDAO userDAO) {
        this.itemDAO = itemDAO;
        this.userDAO = userDAO;
    }

    /**
     * Creates and adds item
     * @param item the item
     * @return the item
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    @ApiOperation(value = "Post an item on the auction.",
        authorizations = {@Authorization(value = "UserBasicAuth")},
        notes = "Post an item to the auction service.",
        response = Item.class)
    public Item create(@Auth User user, Item item) {
        return itemDAO.create(item, user);
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
    public Item findItemById(@Auth User user, @PathParam("itemId") LongParam
            itemId) {
        return this.itemDAO.findById(itemId.get()).orElseThrow(() -> new
                NotFoundException("No such item."));
    }

    /**
     * Returns information of an item given its name
     * @param name the name of the item
     * @return information about the item
     */
    @GET
    @Path("/name = {name}")
    @UnitOfWork
    public Item findItemByName(@Auth User user, @PathParam("name") String
            name) {
        return this.itemDAO.findByName(name).orElseThrow( () -> new
                NotFoundException("No such item"));
    }

    /**
     * Returns list of all items
     * @return list of all items
     */
    @GET
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public List<Item> findAllItems(@Auth User user) {
        return itemDAO.findAll();
    }

    /**
     *
     * @return
     */
    @GET
    @Path("/available")
    @UnitOfWork
    public List<Item> findAllAvailableItems() {
        return this.itemDAO.findAllAvailable();
    }

    /**
     * Updates the item's availability status
     * @param user the seller
     * @param itemId the item's id
     * @param available the new availability status
     */
    @PUT
    @Path("/{itemId}")
    @UnitOfWork
    public void updateAvailable(@Auth User user, @PathParam("itemId") LongParam
            itemId, boolean available) {
        this.itemDAO.updateAvailable(available, itemId.get());
    }

    /**
     * Deletes the item
     * @param itemId the item's id
     */
    @DELETE
    @Path("/{itemId}")
    @UnitOfWork
    public void delete(@Auth User user, @PathParam("itemId") LongParam itemId) {
         this.itemDAO.deleteItem(itemId.get(), user);
    }
}
