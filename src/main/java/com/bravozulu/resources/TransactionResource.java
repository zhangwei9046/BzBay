package com.bravozulu.resources;

import com.bravozulu.core.Transactions;
import com.bravozulu.core.User;
import com.bravozulu.db.TransactionsDao;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

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
    private final TransactionsDao transactionDAO;

    public TransactionResource(TransactionsDao transactionDAO) {
        this.transactionDAO = transactionDAO;
    }


    @GET
    @UnitOfWork
    public List<Transactions> findAllTransactions() {
        return transactionDAO.findAll();
    }

    @POST
    @UnitOfWork
    public Transactions createTransaction(Transactions trans) {
        return transactionDAO.create(trans);

    }

    //get byid
    @GET
    @Path("/{transactionId}")
    @UnitOfWork
    public Transactions getTransactionById(@PathParam("transactionId") LongParam transactionId) {
        return transactionDAO.findBytransactionId(transactionId.get()).orElseThrow(() -> new NotFoundException("No such transaction."));

    }

    @DELETE
    @Path("/{transactionId}")
    @UnitOfWork
    public void deleteTransaction(@PathParam("transactionId") LongParam transactionId) {
        transactionDAO.delete(transactionId.get());
    }


}
}