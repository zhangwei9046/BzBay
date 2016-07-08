package com.bravozulu.core;

/**
 * Created by ying on 6/25/16.
 */
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="reviews")
@NamedQueries(value = {
        @NamedQuery(
                name = "com.bravozulu.core.User.findAll",
                query = "SELECT r FROM Reviews r"
        )
})

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @Column(name = "senderId", nullable = false)
    private long senderId;

    @Column(name = "receiverId", nullable = false)
    private long receiverId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "score", nullable = false)
    private boolean score;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    public Review(long senderId, long receiverId, String content, boolean score) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.score = score;
    }

    public Review() {
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isScore() {
        return score;
    }

    public void setScore(boolean score) {
        this.score = score;
    }
}
