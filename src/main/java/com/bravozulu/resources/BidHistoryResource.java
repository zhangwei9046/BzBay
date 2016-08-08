package com.bravozulu.resources;



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

@Path("/bidhistory")
@Produces(MediaType.APPLICATION_JSON)
public class BidHistoryResource {
    private final BidHistoryDao bidhistoryDao;
    private final ItemDAO itemDAO;
    private final UserDAO userDAO;


    public BidHistoryResource(BidHistoryDao bidhistoryDao,ItemDAO itemDAO, UserDAO userDAO) {
        this.bidhistoryDao = bidhistoryDao;
        this.itemDAO = itemDAO;
        this.userDAO = userDAO;
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public BidHistory createBid(BidHistory bidhistory, @Auth User user ) {
        User cur_user = userDAO.findByUsername(user.getUsername()).orElseThrow(() -> new NotFoundException("Please log in first."));
        return bidhistoryDao.create(bidhistory);
    }

    @GET
    @Path("/{bidId}")
    @Produces("application/json")
    @UnitOfWork
    public Item findBidById(@Auth User user, @PathParam("bidId") LongParam
            bidId) {
        return this.bidhistoryDao.findBybidId(bidId.get()).orElseThrow(() -> new
                NotFoundException("No such BidHistory."));
    }






    //get byid
    @GET
    @Path("/bidId={bidId}")
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
