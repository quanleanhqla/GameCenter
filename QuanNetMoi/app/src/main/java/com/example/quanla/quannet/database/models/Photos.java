package com.example.quanla.quannet.database.models;

/**
 * Created by QuanLA on 3/20/2017.
 */

public class Photos {
    private int image;

    public Photos(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "image=" + image +
                '}';
    }
}
