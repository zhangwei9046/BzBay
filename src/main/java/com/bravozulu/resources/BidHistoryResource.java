package com.bravozulu.resources;



import com.bravozulu.core.BidHistory;
import com.bravozulu.core.Item;
import com.bravozulu.core.User;
import com.bravozulu.db.BidHistoryDAO;
import com.bravozulu.db.ItemDAO;
import com.bravozulu.db.UserDAO;
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


@Path("/bidhistory")
@Api(value = "/bidhistory", description = "Place a bid")
@Produces(MediaType.APPLICATION_JSON)
public class BidHistoryResource {
    private final BidHistoryDAO bidhistoryDao;
    private final ItemDAO itemDAO;
    private final UserDAO userDAO;
    private static Logger logger = LoggerFactory.getLogger(BidHistoryResource.class);

    public BidHistoryResource(BidHistoryDAO bidhistoryDao, ItemDAO itemDAO, UserDAO userDAO) {
        this.bidhistoryDao = bidhistoryDao;
        this.itemDAO = itemDAO;
        this.userDAO = userDAO;
    }

    @POST
    @Path("/bid")
    @Timed
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    /*@ApiOperation(value = "Place a bid",
            authorizations = {@Authorization(value="UserBasicAuth")},
            notes = "Pass BidHistory json object. Return back object with different status field."+
                    "Only Succeed will create bid.",
            response = BidHistory.class)
    */
    public BidHistory bid(@Auth User user,
                          @ApiParam(value = "Place a bid", required = true) BidHistory bidHistory) {
        long itemId = bidHistory.getItemId();
        Optional<Item> item = this.itemDao.findById(itemId);
        if (item.isPresent()) {
            boolean itemAbleToBid = item.get().isAvailable();
            if (itemAbleToBid) {
                BidHistory bidprice =
                        this.bidHistoryDao.findByHighestPriceByItemId(itemId).isPresent()
                                ? this.bidHistoryDao.findByHighestPriceByItemId(itemId).get() : null;
                double currentPrice = (bidprice == null) ? item.get().getInitialPrice()
                        : bidprice.getPrice();
                if (bidHistory.getPrice() > currentPrice) {
                    bidHistory.setStatus("Succeed.");
                    this.bidHistoryDao.create(bidHistory);
                } else {
                    bidHistory.setStatus("Failure: bidding price is lower than current price.");
                }
            } else {
                bidHistory.setStatus("Failure: item not able to bid.");
            }
        } else {
            bidHistory.setStatus("Failure: item does not exist.");
        }
        return bidHistory;
    }



    @GET
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public List<BidHistory> findAllBidHistory(@Auth User user) {
        return bidhistoryDao.findAll();
    }


    @GET
    @Path("bid")
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public BidHistory findBidById(@Auth User user, @PathParam("bidId") LongParam
            bidId) {
        return this.bidhistoryDao.findBybidId(bidId.get()).orElseThrow(() -> new
                NotFoundException("No such bid."));
    }

    @GET
    @Path("bid/userId={userId}/bidhistory")
    @UnitOfWork
    public List<BidHistory> findBidByUserId(@Auth User user, @PathParam("userId") Long userId) {
        return this.bidhistoryDao.findByUserId(userId).orElseThrow( () -> new
                NotFoundException("No Bid History for this user"));
    }

    @GET
    @Path("bid/itemId={itemId}/bidhistory")
    @UnitOfWork
    public List<BidHistory> findBidByItemId(@Auth User user, @PathParam("itemId") Long itemId) {
        return this.bidhistoryDao.findByItemId(itemId).orElseThrow( () -> new
                NotFoundException("No Bid History for this Item"));
    }


    @GET
    @UnitOfWork
    public BidHistory findHighestPrice(@Auth User user, @PathParam("itemId")
            Long itemId) {
        return this.bidhistoryDao.findByHighestPriceByItemId(itemId).orElseThrow( () -> new
                NotFoundException("No Bid History for this Item"));
    }



//    @DELETE
//    @Path("/{bidId}")
//    @UnitOfWork
//    public void deleteBidHistory(@PathParam("bidId") LongParam bidId) {
//        bidhistoryDao.delete(bidId.get());
//    }
//

}
