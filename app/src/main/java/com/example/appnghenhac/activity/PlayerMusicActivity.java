package com.example.appnghenhac.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.example.appnghenhac.R;
import com.example.appnghenhac.model.MusicFiles;
import com.example.appnghenhac.notification.MusicNotification;
import com.example.appnghenhac.receiver.MusicReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PlayerMusicActivity extends AppCompatActivity {
    TextView song_name, artist_name, duration_played, duaration_total;
    ImageView cover_art, nextBtn, prevBtn, backBtn, shuffleBtn, repeatBtn;
    FloatingActionButton playPauseBtn;
    SeekBar seekBar;

    int position = 0;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    Animation animation;
//    float currentRotation = 0; // Biến lưu góc quay hiện tại của cover_art

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_music);
        initView();
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        createNotification();
        FirebaseApp.initializeApp(this);
        backBtn.setOnClickListener(v -> onBackPressed());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        playPauseBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playPauseBtn.setImageResource(R.drawable.baseline_play_arrow_24);
                    cover_art.clearAnimation();
                } else {
                    mediaPlayer.start();
                    playPauseBtn.setImageResource(R.drawable.baseline_pause);
                    updateSeekBar();
//                    currentRotation=cover_art.setRotation(c);
                    cover_art.startAnimation(animation);
                }
            }
        });
    }

    private void initView() {
        song_name = findViewById(R.id.song_name);
        Bundle bundle = getIntent().getExtras();
        String music_song = bundle.getString("name_song");
        song_name.setText(music_song);
        String music_song_string = song_name.getText().toString();
        artist_name = findViewById(R.id.song_arist);
        duration_played = findViewById(R.id.durationPlayed);
        duaration_total = findViewById(R.id.durationTotal);
        cover_art = findViewById(R.id.cover_art);
        nextBtn = findViewById(R.id.id_next);
        prevBtn = findViewById(R.id.id_prev);
        backBtn = findViewById(R.id.back_btn);
        shuffleBtn = findViewById(R.id.id_shuffle);
        repeatBtn = findViewById(R.id.id_repeat);
        playPauseBtn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekBar);
        loadSongsFromFirebase(music_song_string);
    }

    private void loadSongsFromFirebase(String song) {
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
                mediaPlayer.setLooping(true);
                updateSeekBar();
                setTimeTotal();
                cover_art.startAnimation(animation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void updateSeekBar() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    duration_played.setText(new SimpleDateFormat("mm:ss").format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void setTimeTotal() {
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        if (mediaPlayer != null) {
            duaration_total.setText(time.format(mediaPlayer.getDuration()));
            seekBar.setMax(mediaPlayer.getDuration());
        }
    }

    @SuppressLint("NotificationTrampoline")
    public void createNotification() {
        Intent changeActivity = new Intent(this, MusicReceiver.class);
        changeActivity.setAction("Change");
        PendingIntent changeActivityPendingIntent = PendingIntent.getBroadcast(this, 0, changeActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, MusicNotification.CHANNEL_ID_MUSIC)
                .setContentTitle("Playing music")
                .setContentText("Chúc bạn nghe nhạc vui vẻ")
                .setSmallIcon(R.drawable.music_cd_svgrepo_com)
                .addAction(R.drawable.baseline_play_circle, "Change", changeActivityPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }
}
