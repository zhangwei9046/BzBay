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
    @Path("/createbid")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public BidHistory createBid(@Auth User user, long itemId, long userId, float price) {
        return bidhistoryDao.create(itemId, userId,price);
    }


    @POST
    @Path("/bidHistory")
    @Timed
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Place a bid",
            authorizations = {@Authorization(value="UserBasicAuth")},
            notes = "Place a valid bid",
            response = BidHistory.class)
    */
    public BidHistory bid(@Auth User user,
                          @@ApiParam(value = "Place a bid", required = true) BidHistory bidHistory) {
        long itemId = bidHistory.getItemId();
        Optional<Item> item = this.itemDAO.findById(itemId);
        if (item.isPresent()) {
            boolean itemAbleToBid = item.get().isAvailable();
            if (itemAbleToBid) {
                BidHistory bidprice =
                        this.bidhistoryDao.findByHighestPriceByItemId(itemId)
                                .isPresent()
                                ? this.bidhistoryDao
                                .findByHighestPriceByItemId(itemId).get() : null;
                double currentPrice = (bidprice == null) ? item.get().getInitialPrice()
                        : bidprice.getPrice();
                doule doublePrice = 2*currentPrice;
                if (bidHistory.getPrice() > currentPrice
                        if( bidHistory.getPrice()<doublePrice)
                                this.bidhistoryDao.create(bidHistory);
                else {new ServiceUnavailableException("Bid price need be lower than double the current price" );}
                else {new ServiceUnavailableException("Bid price need be higher than current price" );}
         return bidHistory;
                }}}



    @GET
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public List<BidHistory> findAllBidHistory(@Auth User user) {
        return bidhistoryDao.findAll();
    }


    @GET
    @Path("/{bidId}")
    @RolesAllowed("ADMIN")
    @UnitOfWork
    public BidHistory findBidById(@Auth User user, @PathParam("bidId") LongParam
            bidId) {
        return this.bidhistoryDao.findBybidId(bidId.get()).orElseThrow(() -> new
                NotFoundException("No such bid."));
    }

    @GET
    @Path("/userId={userId}/bidhistory")
    @UnitOfWork
    public List<BidHistory> findBidByUserId(@Auth User user, @PathParam("userId") Long userId) {
        return this.bidhistoryDao.findByUserId(userId).orElseThrow( () -> new
                NotFoundException("No Bid History for this user"));
    }

    @GET
    @Path("/itemId={itemId}/bidhistory")
    @UnitOfWork
    public List<BidHistory> findBidByItemId(@Auth User user, @PathParam("itemId") Long itemId) {
        return this.bidhistoryDao.findByItemId(itemId).orElseThrow( () -> new
                NotFoundException("No Bid History for this Item"));
    }


    @GET
    @Path("{itemId}")
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
