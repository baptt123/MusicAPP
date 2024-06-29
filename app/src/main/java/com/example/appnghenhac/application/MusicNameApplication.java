package com.example.appnghenhac.application;

import android.app.Application;
/*
*Class này dùng để lưu trữ dữ liệu tạm thời để xử lí logic cho các chức năng khác
* Dữ liệu sẽ được lưu trữ trong suốt quá trình app hoạt động
 */
public class MusicNameApplication extends Application {
    private String songName;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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
