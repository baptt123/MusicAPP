package com.example.appnghenhac.model;

import java.io.Serializable;

public class Music implements Serializable {
    private int popularity;
    //test de lay du lieu ve so lan phat nhac
    private int history;
    private String name;
    private String img;

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void getInforPopular(String jsonstring) {
        int startIndex = jsonstring.indexOf("\"popularity\":");
        while (startIndex != -1) {
            int endIndex = jsonstring.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = jsonstring.indexOf("}", startIndex);
            }
            String popularityString = jsonstring.substring(startIndex, endIndex);
            System.out.println("Popularity: " + popularityString);
            startIndex = jsonstring.indexOf("\"popularity\":", endIndex);
        }
    }
}
