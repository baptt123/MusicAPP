package com.example.appnghenhac.model;

public class Music {
    private int popularity;

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
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
