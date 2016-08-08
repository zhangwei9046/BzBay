package com.bravozulu.resources;

import com.bravozulu.core.Transaction;
import com.bravozulu.db.TransactionsDao;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Mark on 7/6/16.
 */

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    private final TransactionsDao transactionDAO;

    public TransactionResource(TransactionsDao transactionDAO) {
        this.transactionDAO = transactionDAO;
    }


    @GET
    @UnitOfWork
    public List<Transaction> findAllTransactions() {
        return transactionDAO.findAll();
    }

    @POST
    @UnitOfWork
    public Transaction createTransaction(Transaction trans) {
        return transactionDAO.create(trans);

    }

    @GET
    @Path("/transactionId={transactionId}")
    @UnitOfWork
    public Transaction getTransactionById(@PathParam("transactionId") LongParam transactionId) {
        return transactionDAO.findBytransactionId(transactionId.get()).orElseThrow(() -> new NotFoundException("No such transaction."));

    }

    @DELETE
    @Path("/{transactionId}")
    @UnitOfWork
    public void deleteTransaction(@PathParam("transactionId") LongParam transactionId) {
        transactionDAO.delete(transactionId.get());
    }


}
