package com.bravozulu.core;

/**
 * Created by ying on 6/25/16.
 */
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Column(name = "like", nullable = false)
    private boolean like;

    public Review(long senderId, long receiverId, String content, boolean like) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.like = like;
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

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (reviewId != review.reviewId) return false;
        if (senderId != review.senderId) return false;
        if (receiverId != review.receiverId) return false;
        if (like != review.like) return false;
        return content.equals(review.content);

    }

    @Override
    public int hashCode() {
        int result = (int) (reviewId ^ (reviewId >>> 32));
        result = 31 * result + (int) (senderId ^ (senderId >>> 32));
        result = 31 * result + (int) (receiverId ^ (receiverId >>> 32));
        result = 31 * result + content.hashCode();
        result = 31 * result + (like ? 1 : 0);
        return result;
    }
}
