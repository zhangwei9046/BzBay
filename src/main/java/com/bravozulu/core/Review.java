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
                name = "com.bravozulu.core.Review.findAll",
                query = "SELECT r FROM Review r"
        ),
        @NamedQuery(
                name = "com.bravozulu.core.Review.findBySenderId",
                query = "SELECT r FROM Review r WHERE r.senderId = :senderId"
        ),
        @NamedQuery(
                name = "com.bravozulu.core.Review.findByReceiverId",
                query = "SELECT r FROM Review r WHERE r.receiverId = :receiverId"
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
    private double score;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    public Review(long senderId, long receiverId, String content, double score) {
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (reviewId != review.reviewId) return false;
        if (senderId != review.senderId) return false;
        if (receiverId != review.receiverId) return false;
        if (Double.compare(review.score, score) != 0) return false;
        if (!content.equals(review.content)) return false;
        if (date != null && review.date != null) {
            return date.equals(review.date);
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (reviewId ^ (reviewId >>> 32));
        result = 31 * result + (int) (senderId ^ (senderId >>> 32));
        result = 31 * result + (int) (receiverId ^ (receiverId >>> 32));
        result = 31 * result + content.hashCode();
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", date=" + date +
                '}';
    }
}
