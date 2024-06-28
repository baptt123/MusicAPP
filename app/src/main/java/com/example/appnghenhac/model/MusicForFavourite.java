package com.example.appnghenhac.model;

import java.io.Serializable;

public class MusicForFavourite implements Serializable {
    private String link_img;
    private String name;

    public MusicForFavourite() {
    }

    public String getLink_img() {
        return link_img;
    }

    public void setLink_img(String link_img) {
        this.link_img = link_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
