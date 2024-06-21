package com.example.appnghenhac.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    private int lastPosition=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        String name_song = intent.getStringExtra("name_song");

        switch (action) {
            case "Play":
                playSong(name_song);
                break;
            case "Pause":
                pauseSong();
                break;
        }

        return START_NOT_STICKY;
    }
    private void playSong(String name_song) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference songRef = storageReference.child("/upload/file/" + name_song + ".mp3");

        songRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String url = uri.toString();

            try {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(lastPosition);
                    mediaPlayer.start();
                } else {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(lastPosition);
                    mediaPlayer.start();
                }

            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).addOnFailureListener(exception -> Log.e("MusicService", "Failed to get song URL", exception));
    }

    private void pauseSong() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            lastPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
