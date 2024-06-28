package com.example.appnghenhac.application;

import android.app.Application;

public class MusicNameApplication extends Application {
    private String songName;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
