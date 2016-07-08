package com.bravozulu.resources;

import com.bravozulu.core.MusicInstrument;
import com.bravozulu.db.MusicInstrumentDAO;
import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

/**
 * Created by bonicma on 7/6/16.
 */
@Path("/musicalInstrument")
@Produces(MediaType.APPLICATION_JSON)
public class MusicInstrumentResource {
    private MusicInstrumentDAO musicDAO;

    /**
     * Constructor for MusicInstrumentResource
     * @param musicDAO the music instrument DAO
     */
    public MusicInstrumentResource(MusicInstrumentDAO musicDAO) {
        this.musicDAO = musicDAO;
    }

    /**
     * Creates and adds instrument to database
     * @param instrument the instrument
     * @return the music instrument
     */
    @POST
    @UnitOfWork
    public MusicInstrument create(MusicInstrument instrument) {
        return this.musicDAO.create(instrument);
    }

    /**
     * Returns the musical instrument from the database given its id
     * @param itemId the instrument's id
     * @return the music instrument
     */
    @GET @Path("/{itemId}")
    @UnitOfWork
    @Produces("application/json")
    public Optional<MusicInstrument> findItemById(@PathParam("itemId") long itemId) {
        return this.musicDAO.findById(itemId);
    }

    /**
     * Returns list of all items
     * @return list of all items
     */
    @GET
    @UnitOfWork
    public List<MusicInstrument> findAllItems() {
        return this.musicDAO.findAll();
    }

    /**
     * Updates the music instrument's classifcation
     * @param instrument the instrument to be updated
     */
    @PUT
    @UnitOfWork
    public void update(MusicInstrument instrument) {
        this.musicDAO.updateClassification(instrument);
    }

    /**
     * Deletes the item
     * @param id the item's id
     */
    @DELETE
    @UnitOfWork
    public void delete(long id) {
        this.musicDAO.deleteMusicInstrument(id);
    }

}
