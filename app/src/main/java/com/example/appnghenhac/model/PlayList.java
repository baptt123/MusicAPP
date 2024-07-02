package com.example.appnghenhac.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayList implements Serializable {
    private String name;
    private ArrayList<String> listSong;

    public PlayList(String name, String listSong) {
        this.name = name;
        this.listSong = compareStringToListSong(listSong);
    }

    public PlayList(String name, ArrayList<String> listSong) {
        this.name = name;
        this.listSong = listSong;
    }

    private ArrayList<String> compareStringToListSong(String listSong) {
        String[] s = listSong.split(",");

        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            res.add(s[i]);
        }
        return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getListSong() {
        return listSong;
    }

    public void setListSong(ArrayList<String> listSong) {
        this.listSong = listSong;
    }

    public int getNum() {
        return listSong.size();
    }

    @Override
    public String toString() {
        return "PlayList{" +
                "name='" + name + '\'' +
                ", listSong=" + listSong +
                '}';
    }
}
