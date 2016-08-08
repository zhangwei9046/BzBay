package com.bravozulu.core;

/**
 * Created by Melody on 7/1/16.
 */
import io.dropwizard.jackson.JsonSnakeCase;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="transactions")
@NamedQueries(value = {
        @NamedQuery(
                name = "com.bravozulu.core.Transactions.findAll",
                query = "SELECT u FROM Transactions u"
        )
        @NamedQuery(
        name = "com.bravozulu.core.Transactions.findByuserId",
        query = "SELECT u FROM Transactions u WHERE u.userId = userId"
        )
        @NamedQuery(
        name = "com.bravozulu.core.Transactions.findByitemId",
        query = "SELECT u FROM Transactions u WHERE u.itemId = itemId"
        )
        @NamedQuery(name = "com.bravozulu.core.Transactions.findBybidhistoryId",
        query = "SELECT u FROM Transaction u WHERE u.bidhistoryId = bidhistoryId")

})

@JsonSnakeCase
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "trans_transactionId_seq_name",
            sequenceName = "trans_transactionId_seq",
            allocationSize = 1)
    private long transactionId;

    @Column(name = "bidhistoryId", nullable = false)
    private Long bidhistoryId;

    @Column(name = "itemId", nullable = false)
    private long itemId;

    @Column(name = "userId", nullable = false)
    private long userId;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    public Transactions(){}

    public Transactions(long itemId, long userId, float price){
        this.itemId = itemId;
        this.userId = userId;
        this.price = price;
    }
    public Transactions(@JsonProperty("bidhistoryId") Long bidhistoryId,
                        @JsonProperty("itemId") Long itemId,
                      @JsonProperty("userId") Long userId,
                      @JsonProperty("price") Float price,
    @JsonProperty("time") @JsonFormat(shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd HH:mm:ss") Timestamp time)
    {
        this.itemId = itemId;
        this.userId = userId;
        this.price = price;
        this.time = time;
    }
    @JsonIgnore
    @Override

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getBidHistoryId() {
        return this.bishistoryId;
    }
    public void setBidHistoryId(Long bidhistoryId) {
        this.bidhistoryId = bidhistoryId;
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
    public String toString() {
        return "Transaction{" + ",transactionId = '" + transactionId + '\'' +
                ",ItemId'" + itemId + '\'' +
                ",UserId'" + userId + '\'' +
                ",price'" + price + '\'' +
                ",time '" + time + '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transactions transaction = (Transactions) o;
        if (transactionId != transaction.transactionId) return false;
        if (bidhistoryId != transaction.bidhistoryId) return false;

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
