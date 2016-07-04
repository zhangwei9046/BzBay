package com.bravozulu.core;

/**
 * Created by ying on 6/25/16.
 */
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="bidHistory")
public class BidHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bidId;

    @Column(name = "itemId", nullable = false)
    private long itemId;

    @Column(name = "userId", nullable = false)
    private long userId;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    public BidHistory(long itemId, long userId, float price, Timestamp time) {
        this.itemId = itemId;
        this.userId = userId;
        this.price = price;
        this.time = time;
    }

    public long getBidId() {
        return bidId;
    }

    public void setBidId(long bidId) {
        this.bidId = bidId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BidHistory that = (BidHistory) o;

        if (bidId != that.bidId) return false;
        if (itemId != that.itemId) return false;
        if (userId != that.userId) return false;
        if (Float.compare(that.price, price) != 0) return false;
        return time.equals(that.time);

    }

    @Override
    public int hashCode() {
        int result = (int) (bidId ^ (bidId >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + time.hashCode();
        return result;
    }
}
