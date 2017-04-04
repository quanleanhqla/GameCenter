package com.example.quanla.quannet.database.models;

/**
 * Created by QuanLA on 4/1/2017.
 */

public class Computer {
    private boolean isAvailable;
    private int number;

    public Computer(boolean isAvailable, int number) {
        this.isAvailable = isAvailable;
        this.number = number;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "isAvailable=" + isAvailable +
                ", number=" + number +
                '}';
    }
}
