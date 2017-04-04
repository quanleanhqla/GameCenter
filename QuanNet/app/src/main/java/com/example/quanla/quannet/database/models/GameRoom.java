package com.example.quanla.quannet.database.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QuanLA on 3/19/2017.
 */

public class GameRoom {
    private float km;
    private boolean canSmoke;
    private boolean canPark;
    private String urlImage;
    private String title;
    private String address;
    private String rate;
//    private int[] mayCon =  {6, 13, 20};
    private double latitude;
    private double longitude;

    public GameRoom(){

    }

    public GameRoom(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GameRoom(String urlImage, String title, String address, String rate, double latitude, double longitude) {
        this.address = address;
        this.rate = rate;
        this.title = title;
        this.urlImage = urlImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

//    public int[] getMayCon() {
//        return mayCon;
//    }
//
//    public void setMayCon(int[] mayCon) {
//        this.mayCon = mayCon;
//    }

    public boolean isCanPark() {
        return canPark;
    }

    public void setCanPark(boolean canPark) {
        this.canPark = canPark;
    }

    public boolean isCanSmoke() {
        return canSmoke;
    }

    public void setCanSmoke(boolean canSmoke) {
        this.canSmoke = canSmoke;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }

    @Override
    public String toString() {
        return "GameRoom{" +
                "address='" + address + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", title='" + title + '\'' +
                ", rate='" + rate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }


}
