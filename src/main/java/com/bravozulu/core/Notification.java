package com.bravozulu.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dropwizard.jackson.JsonSnakeCase;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "notifications")
@NamedQueries(value = {
        @NamedQuery(
                name = "com.bravozulu.core.Notification.findAll",
                query = "SELECT u FROM Notification u"
        ),
        @NamedQuery(
                name = "com.bravozulu.core.Notification.findBytransactionId",
                query = "SELECT u FROM Notification u WHERE u.transactionId = transactionId"),

        @NamedQuery(
        name = "com.bravozulu.core.Notification.findByuserId",
        query = "SELECT u FROM Notification u WHERE u.userId = userId"
)
})


@JsonSnakeCase
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "notifications_notifyId_seq_name",
            sequenceName = "notifications_notifyId_seq",
            allocationSize = 1)
    private long notifyId;

    @Column(name = "transactionId", nullable = false)
    private long transactionId;

    @Column(name = "userId", nullable = false)
    private long userId;

    @Column(name = "content", nullable = false)
    private String content;

    public Notification() {
    }

    public Notification(long transactionId, long userId) {
        this.transactionId = transactionId;
        this.userId = userId;
    }

    public Notification(long transactionId, long userId, String content) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.content = content;
    }

    @JsonCreator
    public Notification(@JsonProperty("transactionId") Long transactionId,
                        @JsonProperty("userId") Long userId,
                        @JsonProperty("content") String content) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.content = content;
    }


    @JsonIgnore
    public long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(long notifyId) {
        this.notifyId = notifyId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "Notification{" + ",notifyId = '" + notifyId + '\'' +
                ",transactionId'" + transactionId + '\'' +
                ",UserId'" + userId + '\'' +
                ",content'" + content + '}';
    }


}
