package com.bravozulu.core;

import javax.persistence.*;

/**
 * Represents a MusicInstrument which is a subclass of Item
 *
 * Created by mark on 6/28/16.
 *
 */

@Entity
@Table(name="MusicInstrument")
@NamedQueries(value = {
        @NamedQuery(
                name = "com.bravozulu.core.MusicInstrument.findAll",
                query = "SELECT u FROM User u"
        )
})

public class MusicInstrument extends Item {
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "classification", nullable = false)
    private String classification;

    /**
     * Constructor for MusicInstrument
     * @param brand the brand associated with the instrument (e.g. Yamaha)
     * @param classification the classification of the instrument based on
     *                       Western classification (i.e. string, woodwind,
     *                       brass, percussion)
     */
    public MusicInstrument(String brand, String classification) {
        this.brand = brand;
        this.classification = classification;
    }

    /**
     * Returns the brand
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand
     * @param brand the bran
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns the classification
     * @return classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Sets the classification
     * @param classification the classification
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicInstrument)) return false;
        if (!super.equals(o)) return false;

        MusicInstrument that = (MusicInstrument) o;

        if (!brand.equals(that.brand)) return false;
        return classification.equals(that.classification);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + classification.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MusicInstrument{" +
                "brand='" + brand + '\'' +
                ", classification='" + classification + '\'' +
                '}';
    }
}
