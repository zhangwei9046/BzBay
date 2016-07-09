package com.bravozulu.resources;

import com.bravozulu.core.Transactions;
import com.bravozulu.db.TransactionsDao;

import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mark on 7/6/16.
 */

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    private Transactions transactionDAO;

    /**
     * Constructor for TransactionResource
     * @param transactionDAO the transaction DAO
     */
    public TransactionResource(Transactions transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    /**
     *
     * @param trans
     * @return
     */
    @POST
    @UnitOfWork
    public Transactions create(Transactions trans) {
        //return this.transactionDAO.create(trans);
        return trans;
    }

    /**
     *
     * @param itemId
     * @return
     */
    @GET @Path("/{itemId}")
    @UnitOfWork
    public Transactions findItemById(@PathParam("itemId") long itemId) {
        // return this.transactionDAO.findById(itemId);
        return null;
    }

    /**
     *
     * @return
     */
    @GET
    @UnitOfWork
    public List<Transactions> findAllItems() {
        // return this.transactionDAO.findAll();
        return null;
    }

    /**
     *
     * @param trans
     * @return
     */
    @PUT
    @UnitOfWork
    public void update(Transactions trans) {
        // update what?
    }

    /**
     *
     * @param itemId
     */
    @DELETE
    @UnitOfWork
    public void delete(long itemId) {
        //this.transactionDAO.deleteItem(itemId);
    }
}