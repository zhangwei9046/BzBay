package com.bravozulu.core;

import javax.persistence.Column;

/**
 * Created by bonicma on 6/29/16.
 */
public class ComputerTabletSmartphone {
    @Column(name ="size", nullable = false)
    private int size;

    @Column(name="storage", nullable = false)
    private int storage;

    @Column(name="cpu", nullable = false)
    private String cpu;

    /**
     * Constructor for ComputerTabletSmartphone
     * @param size the size
     * @param storage the storage
     * @param cpu the cpu
     */
    public ComputerTabletSmartphone(int size, int storage, String cpu) {
        this.size = size;
        this.storage = storage;
        this.cpu = cpu;
    }

    /**
     * Returns the size
     * @return returns size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size
     * @param size the size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the storage
     * @return returns storage
     */
    public int getStorage() {
        return storage;
    }

    /**
     * Sets the storage
     * @param storage the storage
     */
    public void setStorage(int storage) {
        this.storage = storage;
    }

    /**
     * Returns the cpu
     * @return returns cpu
     */
    public String getCpu() {
        return cpu;
    }

    /**
     * Sets the cpu
     * @param cpu the cpu
     */
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComputerTabletSmartphone)) return false;
        if (!super.equals(o)) return false;

        ComputerTabletSmartphone that = (ComputerTabletSmartphone) o;

        if (size != that.size) return false;
        if (storage != that.storage) return false;
        return cpu.equals(that.cpu);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + size;
        result = 31 * result + storage;
        result = 31 * result + cpu.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ComputerTabletSmartphone{" +
                "size=" + size +
                ", storage=" + storage +
                ", cpu='" + cpu + '\'' +
                '}';
    }
}
