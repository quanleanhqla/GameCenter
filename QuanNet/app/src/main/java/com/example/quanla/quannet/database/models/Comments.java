package com.example.quanla.quannet.database.models;

import android.net.Uri;

/**
 * Created by Quoc Viet Dang on 4/3/2017.
 */

public class Comments {
    private String username;
    private String comment;
    private String uri;
    public Comments(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    public Comments(String username, String comment, String uri) {
        this.username = username;
        this.comment = comment;
        this.uri = uri;
    }

    public Comments() {

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
