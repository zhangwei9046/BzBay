package com.bravozulu.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ying on 6/27/16.
 */

@Entity
@Table(name="items")
@NamedQueries(value = {
        @NamedQuery(
                name = "com.bravozulu.core.Item.findAll",
                query = "SELECT u FROM Item u"
        ),
        @NamedQuery(
                name = "com.bravozulu.core.Item.findByName",
                query = "SELECT u FROM Item u WHERE u.name = :name"
        ),
        @NamedQuery(
                name = "com.bravozulu.core.Item.available",
                query = "SELECT u FROM Item u WHERE u.available = true"
        ),
        @NamedQuery(name = "com.bravozulu.core.item.updateAvailable",
        query = "UPDATE Item u SET u.available = :available where u.itemId = :itemId")
})
@JsonSnakeCase
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "item_itemId_seq_name",
            sequenceName = "item_itemId_seq",
            allocationSize = 1)
    private long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "available")
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
    private boolean condition;

    @Column(name = "image")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "initialprice", nullable = false)
    private double initialPrice;

    @Column(name = "finalprice")
    private double finalPrice;

    @Column(name = "startdate")
    private Timestamp startDate;

    @Column(name = "enddate")
    private Timestamp endDate;

    /**
     * Constructor for Item
     * @param name the name of the item
     * @param available the availability status
     * @param sellerId the id of the seller of the item
     * @param model the model
     * @param shipping the shipping type
     * @param category the category
     * @param condition the condition
     * @param url a url to the item
     * @param description the description
     * @param initialPrice the initial price
     * @param finalPrice the final price at which the item sold
     * @param startDate the start date of the item's auction
     * @param endDate the end date of the item's auction
     */
    public Item(@JsonProperty("name") String name,
                @JsonProperty("available") boolean available,
                @JsonProperty("sellerId") long sellerId,
                @JsonProperty("model") String model,
                @JsonProperty("shipping") String shipping,
                @JsonProperty ("category") String category,
                @JsonProperty("condition") boolean condition,
                @JsonProperty("url") String url,
                @JsonProperty("description") String description,
                @JsonProperty("initialPrice") double initialPrice,
                @JsonProperty("finalPrice") double finalPrice,
                @JsonProperty("startDate") @JsonFormat(shape = JsonFormat.Shape.STRING,
                        pattern = "yyyy-MM-dd HH:mm:ss") Timestamp startDate,
                @JsonProperty("endDate") @JsonFormat(shape = JsonFormat.Shape.STRING,
                        pattern = "yyyy-MM-dd HH:mm:ss") Timestamp endDate) {
        this.name = name;
        this.available = available;
        this.sellerId = sellerId;
        this.model = model;
        this.shipping = shipping;
        this.category = category;
        this.condition = condition;
        this.url = url;
        this.description = description;
        this.initialPrice = initialPrice;
        this.finalPrice = finalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     *
     */
    public Item() {

    }

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

    @JsonIgnore
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

    public boolean getCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
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

    @JsonIgnore
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
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (itemId != item.itemId) return false;
        if (available != item.available) return false;
        if (sellerId != item.sellerId) return false;
        if (condition != item.condition) return false;
        if (Double.compare(item.initialPrice, initialPrice) != 0) return false;
        if (Double.compare(item.finalPrice, finalPrice) != 0) return false;
        if (!name.equals(item.name)) return false;
        if (!model.equals(item.model)) return false;
        if (!shipping.equals(item.shipping)) return false;
        if (!category.equals(item.category)) return false;
        if (!url.equals(item.url)) return false;
        if (!description.equals(item.description)) return false;
        if (!startDate.equals(item.startDate)) return false;
        return endDate.equals(item.endDate);

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
        result = 31 * result + (condition ? 1 : 0);
        result = 31 * result + url.hashCode();
        result = 31 * result + description.hashCode();
        temp = Double.doubleToLongBits(initialPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
