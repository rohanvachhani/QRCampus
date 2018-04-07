package com.codeblasters.qrcampus;

import android.media.Image;

import java.util.Date;

/**
 * Created by Rohan on 4/7/2018.
 */

public class info {
    private String title;
    private Image image;
    private Date date;
    private String info;

    public info() {
    }

    public info(String title, Image image, Date date, String info) {
        this.title = title;
        this.image = image;
        this.date = date;
        this.info = info;
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}

