package com.example.appnghenhac.activity;

import static com.example.appnghenhac.notification.MusicNotification.CHANNEL_ID_MUSIC;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.appnghenhac.R;
import com.example.appnghenhac.receiver.MusicReceiver;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class TestPlayMusicActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageView image_disk;
    ImageView play_button;
    ImageView pause_button;
    MediaSessionCompat mediaSessionCompat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_play_music);
        FirebaseApp.initializeApp(this);
        initView();
    }

    private void initView() {
        //TO DO
        image_disk = findViewById(R.id.image_disk);
        play_button = findViewById(R.id.play_button);
        pause_button = findViewById(R.id.pause_button);
        setAnimation(image_disk);
        play_button.setOnClickListener(v -> {
            playMusic();
            createNotification();
        });
        pause_button.setOnClickListener(v -> {
           pauseMusic();
        });
    }

    public void setAnimation(ImageView image_disk) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image_disk, "rotation", 0f, 360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.start();
    }

    public void playMusic() {
        String song_name = getIntent().getStringExtra("song_name");
        Log.d("song_name", song_name + "");
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference songRef = storageReference.child("/upload/file/" + song_name+".mp3");
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

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public void createNotification() {
        String song_name= getIntent().getStringExtra("song_name");
        Intent playintent = new Intent(this, MusicReceiver.class);
        playintent.setAction("Play");
        //cái này để tạm thời truyền dữ liệu sang intent
        playintent.putExtra("name_song",song_name);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playintent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent pauseintent = new Intent(this, MusicReceiver.class);
        pauseintent.setAction("Pause");
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(this, 1, pauseintent, PendingIntent.FLAG_UPDATE_CURRENT);
        mediaSessionCompat = new MediaSessionCompat(this, "MusicPlayNotifyActivity");
        @SuppressLint("NotificationTrampoline") Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_MUSIC).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle("Playing Music").
                setContentText("Hope you happy with current music!!!").
                addAction(R.drawable.baseline_play_arrow_24, "Play", playPendingIntent).
                addAction(R.drawable.baseline_pause, "Pause", pausePendingIntent).
                setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSessionCompat.getSessionToken())).
                setPriority(NotificationCompat.PRIORITY_LOW).
                build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}
