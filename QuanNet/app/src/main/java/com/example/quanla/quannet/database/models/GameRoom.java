package com.example.quanla.quannet.database.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QuanLA on 3/19/2017.
 */

public class GameRoom {
    private String khuyenmai;
    private float km;
    private String money;
    private boolean canNight;
    private boolean canSmoke;
    private boolean canPark;
    private boolean canFood;
    private String urlImage;
    private String title;
    private String address;
    private double rate;
//    private int[] mayCon =  {6, 13, 20};
    private double latitude;
    private double longitude;
    private ArrayList<Comments> comment;
    public GameRoom(){

    }

    public GameRoom(String title, String address, double latitude, double longitude, ArrayList<Comments> comment) {
        this.title = title;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comment = comment;
    }

    public GameRoom(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GameRoom(String urlImage, String title, String address, double rate, double latitude, double longitude) {
        this.address = address;
        this.rate = rate;
        this.title = title;
        this.urlImage = urlImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean isCanNight() {
        return canNight;
    }

    public String getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(String khuyenmai) {
        this.khuyenmai = khuyenmai;
    }

    public void setCanNight(boolean canNight) {
        this.canNight = canNight;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isCanFood() {
        return canFood;
    }

    public void setCanFood(boolean canFood) {
        this.canFood = canFood;
    }

    public ArrayList<Comments> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comments> comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
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
                "khuyenmai='" + khuyenmai + '\'' +
                ", km=" + km +
                ", money='" + money + '\'' +
                ", canNight=" + canNight +
                ", canSmoke=" + canSmoke +
                ", canPark=" + canPark +
                ", canFood=" + canFood +
                ", urlImage='" + urlImage + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", rate=" + rate +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", comment=" + comment +
                '}';
    }
}
