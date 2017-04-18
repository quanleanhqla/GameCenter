package com.example.quanla.quannet.events;

import android.net.Uri;

/**
 * Created by Quoc Viet Dang on 4/18/2017.
 */

public class ImageProfile {
    Uri uri;

    public ImageProfile(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ImageProfile{" +
                "uri=" + uri +
                '}';
    }
}
