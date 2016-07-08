package com.bravozulu.resources;

import com.bravozulu.core.CreditCards;
import com.bravozulu.db.CreditCardsDao;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;


/**
 * Created by bonicma on 7/6/16.
 */
@Path("/creditcard")
@Produces(MediaType.APPLICATION_JSON)
public class CreditCardResource {
    private CreditCardsDao ccDAO;

    /**
     *
     * @param ccDAO
     */
    public CreditCardResource(CreditCardsDao ccDAO) {
        this.ccDAO = ccDAO;
    }

    /**
     *
     * @param creditCard
     * @return
     */
    @PUT
    @UnitOfWork
    public CreditCards create(CreditCards creditCard) {
        // return this.ccDAO.create(creditCard);
    }

    /**
     *
     * @param id
     * @return
     */
    @GET @Path("/id")
    @UnitOfWork
    public Optional<CreditCards> findbyId(@PathParam("id") String id) {
        return this.ccDAO.findByuserId(id);
    }

    /**
     *
     * @return
     */
    @GET
    @UnitOfWork
    public List<CreditCards> findAll() {
        //return this.ccDAO.findAll();
    }

    /**
     *
     * @param ccard
     */
    @PUT
    @UnitOfWork
    public void update(CreditCards ccard) {
        // this.ccDAO.update(ccard);
    }

    /**
     *
     * @param id
     */
    @DELETE
    @UnitOfWork
    public void delete(String id) {
        // this.ccDAO.delete(id);
    }

}
