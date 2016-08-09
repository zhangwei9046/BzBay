package com.bravozulu.resources;

import com.bravozulu.core.BidHistory;
import com.bravozulu.core.Item;
import com.bravozulu.core.Transactions;
import com.bravozulu.core.User;
import com.bravozulu.db.BidHistoryDAO;
import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.UserDAO;
import com.bravozulu.db.TransactionsDao;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;


@Path("/transaction")
@Api(value = "/transaction", description = "Operatin on transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    private final TransactionsDao transactionDao;
    private final BidHistoryDAO bidhistoryDao;
    private final ItemDAO itemDAO;
    private final UserDAO userDAO;


    public TransactionResource(TransactionsDao transactionDao , BidHistoryDAO
            bidhistoryDao, ItemDAO itemDAO, UserDAO userDAO) {
        this.transactionDao = transactionDao;
        this.bidhistoryDao = bidhistoryDao;
        this.itemDAO = itemDAO;
        this.userDAO = userDAO;
    }



    @GET
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public List<Transactions> findAllTransaction(@Auth User user) {
        return transactionDao.findAll();
    }


    @GET
    @Path("{transactionId}")
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public Transactions findBytransactionId(@Auth User user, @PathParam
            ("transactionId") LongParam
            transactionId) {
        return this.transactionDao.findBytransactionId(transactionId.get()).orElseThrow(() -> new
                NotFoundException("No such trans."));
    }


    @GET
    @Path("/bidId={bidId}/transactions")
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public Transactions findTransByBidId(@Auth User user, @PathParam("bidId") Long bidId) {
        return this.transactionDao.findByBidHistoryId(bidId).orElseThrow( () -> new
                NotFoundException("No Trans for this Bid History"));
    }

    @GET
    @Path("/userId={userId}/transactions")
    @UnitOfWork
    public List<Transactions> findTransByUserId(@Auth User user, @PathParam("userId") Long userId) {
        return this.transactionDao.findByUserId(userId);
    }

    @GET
    @Path("/itemId={itemId}/transactions")
    @UnitOfWork
    public Transactions findTransByItemId(@Auth User user, @PathParam("itemId") Long itemId) {
        return this.transactionDao.findByItemId(itemId).orElseThrow( () -> new
                NotFoundException("No Trans for this Item"));
    }
}
