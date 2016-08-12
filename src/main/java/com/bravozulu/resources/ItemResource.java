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
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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

    /* Admin API's */

    /**
     * Returns list of all available and unavailable items
     * @return list of all items
     */
    @GET
    @RolesAllowed("ADMIN")
    @UnitOfWork
    @ApiOperation(value = "Find all items",
            authorizations = {@Authorization(value = "UserBasicAuth")},
            notes = "This API only meant for Admin use.",
            response = Item.class, responseContainer = "List")
    public List<Item> findAllItems(@Auth User user) {
        return itemDAO.findAll();
    }

    /* Client-only API's */

    /**
     * Creates and adds item to auction
     * @param item the item
     * @return the item
     */
    @POST
    @UnitOfWork
    @Timed
    @ApiOperation(value = "Post an item on the auction.",
        notes = "This API must work in order to meet the client's specs.",
        response = Item.class)
    public Item create(@Auth User user, Item item) {
        User tempUser = userDAO.findByUsername(user.getUsername()).get();
        item.setSellerId(tempUser.getUserId());
        return itemDAO.create(item);
    }

    /**
     * Returns list of all available items
     * @return returns a list of available items
     */
    @GET
    @Path("/available")
    @UnitOfWork
    @ApiOperation(value = "Find all available items.",
            notes = "This API must work in order to meet the client's specs.",
            response = Item.class, responseContainer = "List")
    public List<Item> findAllAvailableItems() {
        return this.itemDAO.findAllAvailable();
    }

    /**
     * Returns a list of all available items based on category filter
     * @param user an authorized user
     * @param category the category to filter
     * @return a list of items
     */
    @GET
    @Path("/search")
    @UnitOfWork
    @ApiOperation(value = "Find item by name",
            notes = "This API must work in order to meet the client's specs.",
            response = Item.class)
    public List<Item> search(@Auth User user,
                             @QueryParam("category") String category) {
        return this.itemDAO.search(category);
    }

    /**
     * Returns an Item object given its name; if Item not exists, return
     * appropriate feedback message
     * @param name the name of the item
     * @return an Item object
     */
    @GET
    @Path("/name/{name}")
    @Produces("application/json")
    @UnitOfWork
    @ApiOperation(value = "Find item by name",
            notes = "This API must work in order to meet the client's specs.",
            response = Item.class)
    public Item findItemByName(@Auth User user, @PathParam("name") String
            name) {
        return this.itemDAO.findByName(name).orElseThrow( () -> new
                NotFoundException("No such item"));
    }

    /**
     * Returns an Item object given an item's id number; if Item not exists,
     * return appropriate feedback message
     * @param itemId the item's id
     * @return an Item object
     */
    @GET
    @Path("/{itemId}")
    @Produces("application/json")
    @UnitOfWork
    @ApiOperation(value = "Find item by id",
            authorizations = {@Authorization(value = "UserBasicAuth")},
            notes = "This API must work in order to meet the client's specs.",
            response = Item.class)
    public Item findItemById(@Auth User user, @PathParam("itemId") LongParam
            itemId) {
        return this.itemDAO.findById(itemId.get()).orElseThrow(() -> new
                NotFoundException("No such item."));
    }



    /*-----------------------------------------------------------------------*/

    /* EXTRA FEATURES UNDER CONSTRUCTION */

    /*
    @PUT
    @Path("/{itemId}")
    @UnitOfWork
    @ApiOperation(value = "Updates the item's availability.",
            authorizations = {@Authorization(value = "UserBasicAuth")},
            notes = "This API must work in order to meet the client's specs.",
            response = Item.class)
    public void updateAvailable(@Auth User user, @PathParam("itemId") LongParam
            itemId, boolean available) {
        this.itemDAO.updateAvailable(available, itemId.get());
    }
    */

    /*
    @DELETE
    @Path("/{itemId}")
    @UnitOfWork
    @ApiOperation(value = "Delete an item by id and seller id",
            authorizations = {@Authorization(value = "UserBasicAuth")},
            notes = "This API must work in order to meet the client's specs.",
            response = Item.class)
    public void delete(@Auth User user, @PathParam("itemId") LongParam itemId) {
         this.itemDAO.deleteItem(itemId.get(), user);
    }
    */
}
