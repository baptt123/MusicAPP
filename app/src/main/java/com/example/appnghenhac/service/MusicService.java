package com.example.appnghenhac.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.appnghenhac.R;
import com.example.appnghenhac.config.Constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
/*
class này dùng để xử lí ngầm các tác vụ
 */
public class MusicService extends Service {
    private static final String CHANNEL_ID = "MusicServiceChannel";
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    private Notification getNotification(String songName, boolean isPlaying) {
        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.setAction(Constants.ACTION_PLAY_NOTIFICATION);
        playIntent.putExtra("song_name", songName);
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(this, MusicService.class);
        pauseIntent.putExtra("song_name", songName);
        pauseIntent.setAction(Constants.ACTION_PAUSE_NOTIFICATION);
        PendingIntent pausePendingIntent = PendingIntent.getService(this, 1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Playing music")
                .setContentText(songName)
                .setSmallIcon(R.drawable.music_cd_svgrepo_com)
                .addAction(isPlaying ? R.drawable.baseline_pause : R.drawable.baseline_play_arrow_24,
                        isPlaying ? "Pause" : "Play", isPlaying ? pausePendingIntent : playPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            String songName = intent.getStringExtra("song_name");
            if (songName != null) {
                switch (action) {
                    case Constants.ACTION_PAUSE:
                    case Constants.ACTION_PAUSE_NOTIFICATION:
                        pauseMusic();
                        updateNotification(songName, false);
                        break;
                    case Constants.ACTION_PLAY:
                    case Constants.ACTION_PLAY_NOTIFICATION:
                        playMusic(songName);
                        updateNotification(songName, true);
                        break;
                }
            }
        }
        return START_STICKY;
    }

    private void updateNotification(String songName, boolean isPlaying) {
        Notification notification = getNotification(songName, isPlaying);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void playMusic(String song) {
        Log.d("song_name", song);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference songRef = storageReference.child("/upload/file/" + song + ".mp3");
        songRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String url = uri.toString();
            try {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } else {
                    int current_pos = mediaPlayer.getCurrentPosition();
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(current_pos);
                    mediaPlayer.start();
                }

                // Set a completion listener
                mediaPlayer.setLooping(true);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
