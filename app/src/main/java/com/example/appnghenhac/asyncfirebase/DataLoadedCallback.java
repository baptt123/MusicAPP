package com.example.appnghenhac.asyncfirebase;

import com.example.appnghenhac.model.Music;

import java.util.ArrayList;

public interface DataLoadedCallback {
    void onDataLoaded(ArrayList<Music> data);
}
