package com.bravozulu.resources;

import com.bravozulu.core.BidHistory;
import com.bravozulu.core.Transactions;
import com.bravozulu.core.User;
import com.bravozulu.db.BidHistoryDAO;
import com.bravozulu.db.TransactionsDao;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

/**
 * Created by Melody on 7/7/16.
 */

@Path("/bidhistory")
@Produces(MediaType.APPLICATION_JSON)
public class BidHistoryResource {
    private final BidHistoryDao bidhistoryDao;

    public BidHistoryResource(BidHistoryDao bidhistoryDao) {
        this.bidhistoryDao = bidhistoryDao;
    }

    /* findAll
    @GET
    @UnitOfWork
    public List<Transactions> findAllTransactions() {
        return transactionDAO.findAll();
    }
*/
    @POST
    @UnitOfWork
    public BidHistory createBidHistory(BidHistory bid) {
        return BidHistoryDAO.create(bid);

    }

    //get byid
    @GET
    @Path("/{bidId}")
    @UnitOfWork
    public BidHistory getBidById(@PathParam("bidId") LongParam bidId) {
        return bidhistoryDao.findBybidId(bidId.get()).orElseThrow(() -> new NotFoundException("No such Bid."));

    }

    @DELETE
    @Path("/{bidId}")
    @UnitOfWork
    public void deleteBidHistory(@PathParam("bidId") LongParam bidId) {
        bidhistoryDao.delete(bidId.get());
    }


}
}