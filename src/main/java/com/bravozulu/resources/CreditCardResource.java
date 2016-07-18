package com.bravozulu.resources;

import com.bravozulu.core.CreditCards;
import com.bravozulu.db.CreditCardsDao;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;


/**
 * Created by Melody on 7/9/16.
 */
@Path("/creditcard")
@Produces(MediaType.APPLICATION_JSON)
public class CreditCardResource {
    private CreditCardsDao ccDAO;


    public CreditCardResource(CreditCardsDao ccDAO) {
        this.ccDAO = ccDAO;
    }


    @PUT
    @UnitOfWork
    public CreditCards createCard(CreditCards creditCard) {
        return ccDAO.create(creditCard);
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<CreditCards> getByCardNum(@PathParam("id") String id) {
        return this.ccDAO.findByCardNum(id);
    }


    @GET
    @UnitOfWork
    public List<CreditCards> findAll() {
        return this.ccDAO.findAll();
    }


    @DELETE
    @UnitOfWork
    public void deleteCard(String cardNum) {
        ccDAO.delete(cardNum);
    }

}
