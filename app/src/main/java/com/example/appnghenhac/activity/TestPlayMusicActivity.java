package com.example.appnghenhac.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.appnghenhac.R;
import com.example.appnghenhac.config.Constants;
import com.example.appnghenhac.service.MusicService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class TestPlayMusicActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "MusicServiceChannel";
    MediaPlayer mediaPlayer;
    ImageView imageDisk;
    ImageView playButton;
    ImageView pauseButton;
    MediaSessionCompat mediaSessionCompat;
    TextView titleSong;
    ImageView love_music;

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
        titleSong = findViewById(R.id.title_song);
        titleSong.setText(getIntent().getStringExtra("name_song"));
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
        love_music = findViewById(R.id.love_music);
        love_music.setOnClickListener(v -> {
            setLoveMusic();
        });
    }

    public void setAnimation(ImageView imageDisk) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageDisk, "rotation", 0f, 360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.start();
    }

    //test lay du lieu tu firebase
    public void setLoveMusic() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user").child("tam2").child("danhsachyeuthich");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String value = snapshot.getValue(String.class);
                    Log.d("FirebaseValue", value);
                    String title_song = titleSong.getText().toString();
                    if (value != null && !value.contains(title_song)) {
                        StringBuilder newValue = new StringBuilder(value);
                        newValue.append(title_song); // Append title_song and a newline for separation
                        databaseReference.setValue(newValue.toString());
                        Toast.makeText(TestPlayMusicActivity.this, "Set Value thanh cong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TestPlayMusicActivity.this, "Set Value that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", error.getMessage());
            }
        });
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

    private void updateNotification(String songName, boolean isPlaying) {
        Notification notification = getNotification(songName, isPlaying);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(1, notification);
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

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}
