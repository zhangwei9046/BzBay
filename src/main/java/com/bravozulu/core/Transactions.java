package com.bravozulu.core;

/**
 * Created by Melody on 7/1/16.
 */
import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="transactions")
@NamedQueries(value = {
        @NamedQuery(
                name = "com.bravozulu.core.Transactions.findAll",
                query = "SELECT u FROM Transactions u"
        )
})

public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    @Column(name = "itemId", nullable = false)
    private long itemId;

    @Column(name = "userId", nullable = false)
    private long userId;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    public Transactions(){}

    public Transactions(long itemId, long userId, float price, Timestamp time) {
        this.itemId = itemId;
        this.userId = userId;
        this.price = price;
        this.time = time;
    }

    public long gettransactionId() {
        return transactionId;
    }

    public void settransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getUserId() { return userId;}

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

        Transactions transaction = (Transactions) o;

        if (transactionId != transaction.transactionId) return false;
        if (itemId != transaction.itemId) return false;
        if (userId != transaction.userId) return false;
        if (Float.compare(transaction.price, price) != 0) return false;
        return time.equals(transaction.time);

    }

    @Override
    public int hashCode() {
        int result = (int) (transactionId ^ (transactionId >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + time.hashCode();
        return result;
    }
}
