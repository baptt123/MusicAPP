package com.example.appnghenhac.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnghenhac.service.MusicService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MusicReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String name_song = intent.getStringExtra("name_song");
        //khai báo intent đẻ gọi đến service
        Intent serviceIntent = new Intent(context, MusicService.class);
        //gán action nhận được cho service
        serviceIntent.setAction(action);
        //gửi dữ liệu cho service
        serviceIntent.putExtra("name_song", name_song);
        //khởi chạy
        context.startService(serviceIntent);
    }
    }

