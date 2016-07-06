package com.bravozulu.db;

import com.bravozulu.core.MusicInstrument;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Implementation of MusicInstrumentDAO
 * Created by Mark on 7/6/16.
 */
public class MusicInstrumentDAO extends AbstractDAO<MusicInstrument> {
    public MusicInstrumentDAO(SessionFactory factory) {
        super(factory);
    }

    /**
     * Creates and add instrument to database
     * @param instrument the instrument
     * @return a MusicInstrument
     */
    public MusicInstrument create(MusicInstrument instrument) {
        return this.persist(instrument);
    }

    /**
     * Returns a copy of the instrument
     * @param id the instrument's id
     * @return the music instrument
     */
    public Optional<MusicInstrument> findById(long id) {
        return Optional.ofNullable(this.get(id));
    }

    /**
     * Returns list of all music instruments
     * @return list of music instruments
     */
    public List<MusicInstrument> findAll() {
        return list(namedQuery("com.bravozulu.core.MusicInstrument.findAll"));
    }

    /**
     * Update the instrument's classification
     * @param instrument the instrument
     */
    public void updateClassification(MusicInstrument instrument) {
        long itemId = instrument.getItemId();
        Session session = this.currentSession();

        MusicInstrument tempInstrument = (MusicInstrument) session.get
                (MusicInstrument.class, itemId);
        String updatedClassfication = instrument.getClassification();
        tempInstrument.setClassification(updatedClassfication);
        session.update(tempInstrument);

        System.out.println("Classification successfully updated");
        session.close();
    }

    /**
     * Deletes the musical instrument from the database
     * @param id the id of the musical instrument
     */
    public void deleteMusicInstrument(long id) {
        Session session = this.currentSession();

        MusicInstrument instrumentToBeDeleted = (MusicInstrument) session.get
                (MusicInstrument.class, id);
        session.delete(instrumentToBeDeleted);

        System.out.print("Item successfully deleted.");
        session.close();
    }
}
