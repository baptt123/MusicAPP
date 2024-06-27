package com.example.appnghenhac.model;

import java.io.Serializable;

/*
class này xử lí cho chức năng tìm kiếm
 */
public class MusicForSearch implements Serializable {
    private String name;
    private String img;


    public MusicForSearch() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
