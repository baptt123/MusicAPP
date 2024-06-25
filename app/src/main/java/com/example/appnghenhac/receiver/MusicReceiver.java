package com.example.appnghenhac.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.appnghenhac.activity.TestPlayMusicActivity;
import com.example.appnghenhac.service.MusicService;

import java.io.Serializable;

public class MusicReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;
    Bundle extras;

    @Override
    public void onReceive(Context context, Intent intent) {

    }
//    @Override
////    public void onReceive(Context context, Intent intent) {
////        String action = intent.getAction();
////        String name_song = intent.getStringExtra("song_name");
////        //khai báo intent đẻ gọi đến service
////        Intent serviceIntent = new Intent(context, MusicService.class);
////        //gán action nhận được cho service
////        serviceIntent.setAction(action);
////        //gửi dữ liệu cho service
////        serviceIntent.putExtra("name_song", name_song);
////        //khởi chạy
////        context.startService(serviceIntent);
////
////    }
}


