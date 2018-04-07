package com.codeblasters.qrcampus;

import android.media.Image;
import android.net.Uri;

import java.util.Date;

/**
 * Created by Rohan on 4/7/2018.
 */

public class info {
    private String title;
    private String imageUri;
    private String date;
    private String info;

    public info() {
    }

    public info(String title, String imageUri, String date, String info) {
        this.title = title;
        this.imageUri = imageUri;
        this.date = date;
        this.info = info;

    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date.toString();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}

