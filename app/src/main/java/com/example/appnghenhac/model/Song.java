package com.example.appnghenhac.model;

public class Song {
    private String id;
    private String name;
    private String url;

    public Song() {
    }

    public Song(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Song(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}