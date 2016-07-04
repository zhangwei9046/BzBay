package com.bravozulu.core;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ying on 6/27/16.
 */

@Entity
@Table(name="item")

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "available", nullable = false)
    private boolean available;

    @Column(name = "sellerId", nullable = false)
    private long sellerId;

    @Column(name = "model")
    private String model;

    @Column(name = "shipping")
    private String shipping;

    @Column(name = "category")
    private String category;

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "image")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "initialPrice", nullable = false)
    private double initialPrice;

    @Column(name = "finalPrice")
    private double finalPrice;

    @Column(name = "startDate", nullable = false)
    private Timestamp startDate;

    @Column(name = "endDate")
    private Timestamp endDate;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (itemId != item.itemId) return false;
        if (available != item.available) return false;
        if (sellerId != item.sellerId) return false;
        if (Double.compare(item.initialPrice, initialPrice) != 0) return false;
        if (Double.compare(item.finalPrice, finalPrice) != 0) return false;
        if (!name.equals(item.name)) return false;
        if (!model.equals(item.model)) return false;
        if (!shipping.equals(item.shipping)) return false;
        if (!category.equals(item.category)) return false;
        if (!condition.equals(item.condition)) return false;
        if (!url.equals(item.url)) return false;
        if (!description.equals(item.description)) return false;
        if (startDate != null ? !startDate.equals(item.startDate) : item.startDate != null) return false;
        return endDate != null ? endDate.equals(item.endDate) : item.endDate == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (available ? 1 : 0);
        result = 31 * result + (int) (sellerId ^ (sellerId >>> 32));
        result = 31 * result + model.hashCode();
        result = 31 * result + shipping.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + condition.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + description.hashCode();
        temp = Double.doubleToLongBits(initialPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }


}
