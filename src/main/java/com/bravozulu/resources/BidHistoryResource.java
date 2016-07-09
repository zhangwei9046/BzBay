package com.bravozulu.resources;

import com.bravozulu.core.BidHistory;
import com.bravozulu.db.BidHistoryDAO;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

/**
 * Created by bonicma on 7/6/16.
 */
@Path("/bidhistory")
@Produces(MediaType.APPLICATION_JSON)
public class BidHistoryResource {
    private BidHistoryDAO bdDAO;

    /**
     *
     * @param bdDAO
     */
    public BidHistoryResource(BidHistoryDAO bdDAO) {
        this.bdDAO = bdDAO;
    }

    /**
     *
     * @param bHist
     * @return
     */
    @POST
    @UnitOfWork
    public BidHistory create(BidHistory bHist)  {
        //return this.bdDAO.create(bHist);
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    @GET @Path("/{id}")
    @UnitOfWork
    public Optional<BidHistory> findById(@PathParam("id") long id) {
        return this.bdDAO.findBybidId(id);
    }

    /**
     *
     * @return
     */
    @GET
    @UnitOfWork
    public List<BidHistory> findAll() {
        // return this.bdDAO.findAllItems;
        return null;
    }

    /**
     *
     * @param bdhistory
     */
    @PUT
    @UnitOfWork
    public void update(BidHistory bdhistory) {
        // this.bdDAO.update(bdhistory);
    }

    /**
     *
     * @param id
     */
    @DELETE
    @UnitOfWork
    public void delete(long id) {
        this.bdDAO.deleteBidHistory(id);
    }
}
