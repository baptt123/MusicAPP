package com.example.appnghenhac.activity;

import static com.example.appnghenhac.notification.MusicNotification.CHANNEL_ID_MUSIC;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.appnghenhac.R;
import com.example.appnghenhac.config.Constants;
import com.example.appnghenhac.service.MusicService;
import com.google.firebase.FirebaseApp;

public class TestPlayMusicActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageView imageDisk;
    ImageView playButton;
    ImageView pauseButton;
    MediaSessionCompat mediaSessionCompat;
    TextView titleSong;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_play_music);

        FirebaseApp.initializeApp(this);
        initView();
    }

    private void initView() {
        imageDisk = findViewById(R.id.image_disk);
        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
        setAnimation(imageDisk);

        playButton.setOnClickListener(v -> {
            Intent playAction = new Intent(this, MusicService.class);
            playAction.setAction(Constants.ACTION_PLAY);
            String songName = getIntent().getStringExtra("name_song");
            playAction.putExtra("song_name", songName);
            startService(playAction);
        });

        pauseButton.setOnClickListener(v -> {
            Intent pauseAction = new Intent(this, MusicService.class);
            pauseAction.setAction(Constants.ACTION_PAUSE);
            String songName = getIntent().getStringExtra("name_song");
            pauseAction.putExtra("song_name", songName);
            startService(pauseAction);
        });


    }

    public void setAnimation(ImageView imageDisk) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageDisk, "rotation", 0f, 360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.start();
    }


}
