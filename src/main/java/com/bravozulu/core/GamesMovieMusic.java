package com.bravozulu.core;

import javax.persistence.Column;
import java.sql.Timestamp;

/** Represents a GamesMovieMusic which is a sublcass of Item
 * Created by bonicma on 6/29/16.
 */
public class GamesMovieMusic {
    @Column(name ="releaseDate", nullable = false)
    private Timestamp releaseDate;

    /**
     * Constructor for GamesMovieMusic
     * @param releaseDate the release date of the item
     */
    public GamesMovieMusic(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Returns the release date
     * @return the release date
     */
    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date
     * @param releaseDate the release date
     */
    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GamesMovieMusic)) return false;
        if (!super.equals(o)) return false;

        GamesMovieMusic that = (GamesMovieMusic) o;

        return releaseDate.equals(that.releaseDate);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + releaseDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GamesMovieMusic{" +
                "releaseDate=" + releaseDate +
                '}';
    }
}
