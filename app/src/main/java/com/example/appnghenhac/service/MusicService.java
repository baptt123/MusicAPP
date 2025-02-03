package com.example.appnghenhac.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.appnghenhac.R;

public class MusicService extends Service {
    private static final String CHANNEL_ID = "MusicServiceChannel";
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
//    private int[] trackList = {R.raw.sample_music, R.raw.sample_music2}; // Danh sách bài hát
    private int currentTrackIndex = 0;

    @Override
    public void onCreate() {
        super.onCreate();
//        mediaPlayer = MediaPlayer.create(this, trackList[currentTrackIndex]);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if ("ACTION_TOGGLE_PLAYBACK".equals(action)) {
                if (isPlaying) {
                    pauseMusic();
                } else {
                    playMusic();
                }
            } else if ("ACTION_NEXT_TRACK".equals(action)) {
                nextTrack();
            } else if ("ACTION_PREV_TRACK".equals(action)) {
                prevTrack();
            }
        }
        createNotification();
        return START_STICKY;
    }

    private void playMusic() {
        mediaPlayer.start();
        isPlaying = true;
    }

    private void pauseMusic() {
        mediaPlayer.pause();
        isPlaying = false;
    }

    private void nextTrack() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
//        currentTrackIndex = (currentTrackIndex + 1) % trackList.length;
//        mediaPlayer = MediaPlayer.create(this, trackList[currentTrackIndex]);
        playMusic();
    }

    private void prevTrack() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
//        currentTrackIndex = (currentTrackIndex - 1 + trackList.length) % trackList.length;
//        mediaPlayer = MediaPlayer.create(this, trackList[currentTrackIndex]);
        playMusic();
    }

    @SuppressLint("ForegroundServiceType")
    private void createNotification() {
        createNotificationChannel();

        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.setAction("ACTION_TOGGLE_PLAYBACK");
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, MusicService.class);
        nextIntent.setAction("ACTION_NEXT_TRACK");
        PendingIntent nextPendingIntent = PendingIntent.getService(this, 1, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent prevIntent = new Intent(this, MusicService.class);
        prevIntent.setAction("ACTION_PREV_TRACK");
        PendingIntent prevPendingIntent = PendingIntent.getService(this, 2, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Player")
                .setContentText(isPlaying ? "Playing" : "Paused")
//                .setSmallIcon(R.drawable.ic_music_note)
//                .addAction(R.drawable.ic_prev, "Previous", prevPendingIntent)
//                .addAction(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play, isPlaying ? "Pause" : "Play", playPendingIntent)
//                .addAction(R.drawable.ic_next, "Next", nextPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build();

        startForeground(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
