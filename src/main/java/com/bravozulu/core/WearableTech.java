package com.bravozulu.core;

import javax.persistence.Column;

/**
 * Represents a WearableTech which is a subclass of Item
 * Created by bonicma on 6/29/16.
 */
public class WearableTech {
    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "size", nullable = false)
    private String size;

    /**
     * Constructor for WearableTech
     * @param brand the brand of the item
     * @param size the size of the item
     */
    public WearableTech(String brand, String size) {
        this.brand = brand;
        this.size = size;
    }

    /**
     * Returns the brand
     * @return returns the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand
     * @param brand the brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns the size
     * @return returns the size
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the size
     * @param size the size
     */
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WearableTech)) return false;

        WearableTech that = (WearableTech) o;

        if (!brand.equals(that.brand)) return false;
        return size.equals(that.size);

    }

    @Override
    public int hashCode() {
        int result = brand.hashCode();
        result = 31 * result + size.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WearableTech{" +
                "brand='" + brand + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
