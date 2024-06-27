//package com.example.appnghenhac.activity;
//
//
//import android.annotation.SuppressLint;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
//
//import com.example.appnghenhac.R;
//import com.example.appnghenhac.model.MusicFiles;
//import com.example.appnghenhac.notification.MusicNotification;
//import com.example.appnghenhac.receiver.MusicReceiver;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.database.*;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
//public class PlayerMusicActivity extends AppCompatActivity {
//    //    private ActivityMainBinding mBinding;
//    TextView song_name, artist_name, duration_played, duaration_total;
//    ImageView cover_art, nextBtn, prevBtn, backBtn, shuffleBtn, repeatBtn;
//    FloatingActionButton playPauseBtn;
//    SeekBar seekBar;
//
//    int position = 0;
//    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
//    static Uri uri;
//    static MediaPlayer mediaPlayer;
//    private Handler handler = new Handler();
//    private DatabaseReference mDatabase;
//
//    //    private  Thread playThread ,prevThread ,nextThread;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.player_music);
//        initView();
//        createNotification();
////        addSong();
//        khoitaoMediaPlayer();
//
////        Xử lý nút backbtn
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
////
//
////        Xử lý nút next
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                position++;
//                if (position > listSongs.size() - 1) {
//                    position = 0;
//                }
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                }
//                khoitaoMediaPlayer();
//                mediaPlayer.start();
//                playPauseBtn.setImageResource(R.drawable.baseline_pause);
//                SetTimeTotal();
//                UpdateTimeSong();
//            }
//        });
//
//        //        Xử lý nút prev
//        prevBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                position--;
//                if (position > 0) {
//                    position = listSongs.size() - 1;
//                }
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                }
//                khoitaoMediaPlayer();
//                mediaPlayer.start();
//                playPauseBtn.setImageResource(R.drawable.baseline_pause);
//                SetTimeTotal();
//                UpdateTimeSong();
//            }
//        });
//
////Xử lý nút play and pause
//        playPauseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mediaPlayer.isPlaying()) {
////                    Nếu đang phát -> pause ->Đổi hình play
//                    mediaPlayer.pause();
//                    playPauseBtn.setImageResource(R.drawable.baseline_play_arrow_24);
//                } else {
////                    Đang ngừng -> play -> đổi hình pause
//                    mediaPlayer.start();
//                    playPauseBtn.setImageResource(R.drawable.baseline_pause);
//                }
//                SetTimeTotal();
//                UpdateTimeSong();
//            }
//        });
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());
//            }
//        });
//    }
//
//    private void loadSongsFromFirebase(String song) {
////        String songname=getIntent().getStringExtra("name_song");
////        mDatabase = FirebaseDatabase.getInstance().getReference().child("list");
////        mDatabase.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
////                listSongs.clear();
////                for(DataSnapshot snapshot : datasnapshot.getChildren()){
////                    MusicFiles song =snapshot.getValue(MusicFiles.class);
////                    if(song != null){
////                        listSongs.add(song);
////                    }
//////                    listSongs.add(song);
////                }
////                if(!listSongs.isEmpty()){
////                    khoitaoMediaPlayer();
////                    mediaPlayer.start();
////                    playPauseBtn.setImageResource(R.drawable.baseline_pause);
////                    SetTimeTotal();
////                    UpdateTimeSong();
////                }else{
////                    Toast.makeText(PlayerMusicActivity.this, "No songs available", Toast.LENGTH_SHORT).show();
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull @NotNull DatabaseError error) {
////                Toast.makeText(PlayerMusicActivity.this, "Failed to load songs" + error.getMessage() , Toast.LENGTH_SHORT).show();
////            }
////        });
//        Log.d("song_name", song);
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        StorageReference storageReference = firebaseStorage.getReference();
//        StorageReference songRef = storageReference.child("/upload/file/" + song + ".mp3");
//        songRef.getDownloadUrl().addOnSuccessListener(uri -> {
//            String url = uri.toString();
//            Log.d("Download_URL", url);
//
//            // Initialize MediaPlayer
//            MediaPlayer mediaPlayer = new MediaPlayer();
//            try {
//                mediaPlayer.setDataSource(url);
//                mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                mediaPlayer.start();
//            } catch (IOException e) {
//                Log.e("MediaPlayer_Error", "Error initializing MediaPlayer: " + e.getMessage());
//            }
//        }).addOnFailureListener(exception -> {
//            Log.e("Firebase_Error", "Error getting download URL: " + exception.getMessage());
//        });
//
//    }
//    private void UpdateTimeSong() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
//                duration_played.setText(time.format(mediaPlayer.getCurrentPosition()));
////                update progress seebar
//                seekBar.setProgress(mediaPlayer.getCurrentPosition());
////                Kiểm tra thời gian bài hát -> nếu kết thúc ->next
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        position++;
//                        if (position > listSongs.size() - 1) {
//                            position = 0;
//                        }
//                        if (mediaPlayer.isPlaying()) {
//                            mediaPlayer.stop();
//                        }
//                        khoitaoMediaPlayer();
//                        mediaPlayer.start();
//                        playPauseBtn.setImageResource(R.drawable.baseline_pause);
//                        SetTimeTotal();
//                        UpdateTimeSong();
//                    }
//                });
//                handler.postDelayed(this, 500);
//            }
//        }, 100);
//    }
//
//    private void SetTimeTotal() {
//        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
////        duaration_total.setText(time.format(mediaPlayer.getDuration()+""));
//        duration_played.setText(time.format(mediaPlayer.getDuration()));
////        Gán max của seekBar =mediaPlayer.getDuaretion
//        seekBar.setMax(mediaPlayer.getDuration());
//
//    }
//
//    private void khoitaoMediaPlayer() {
//        mediaPlayer = MediaPlayer.create(PlayerMusicActivity.this, listSongs.get(position).getPath());
////        mediaPlayer.start();
//        song_name.setText(listSongs.get(position).getTitle());
//        artist_name.setText(listSongs.get(position).getArtist());
//        cover_art.setImageResource(listSongs.get(position).getCoverArt());
//    }
//
////    private void addSong() {
////        listSongs.add(new MusicFiles(R.raw.emcuangayhomqua, "Em của ngày hôm qua", "Sơn Tùng Mtp", R.drawable.emcuangayhomqua));
////        listSongs.add(new MusicFiles(R.raw.chayngaydi, "Chạy ngay đi", "Sơn Tùng Mtp", R.drawable.chayngaydi));
////        listSongs.add(new MusicFiles(R.raw.nauanchoem, "Nấu ăn cho em", "Đen, ", R.drawable.nauchoeman));
////    }
//
//    private void initView() {
//        song_name = findViewById(R.id.song_name);
//        song_name.setText(getIntent().getStringExtra("song_name"));
//        //bien nay dung de truyen gia tri vao de phat nhac trong firebase
//        String song_music=song_name.getText().toString();
//        loadSongsFromFirebase(song_music);
//        artist_name = findViewById(R.id.song_arist);
//        duration_played = findViewById(R.id.durationPlayed);
//        duaration_total = findViewById(R.id.durationTotal);
//
//        cover_art = findViewById(R.id.cover_art);
//        nextBtn = findViewById(R.id.id_next);
//        prevBtn = findViewById(R.id.id_prev);
//        backBtn = findViewById(R.id.back_btn);
//        shuffleBtn = findViewById(R.id.id_shuffle);
//        repeatBtn = findViewById(R.id.id_repeat);
//        playPauseBtn = findViewById(R.id.play_pause);
//        seekBar = findViewById(R.id.seekBar);
//
//
//    }
//
//    @SuppressLint("NotificationTrampoline")
//    public void createNotification() {
//        Intent changeActivity = new Intent(this, MusicReceiver.class);
//        changeActivity.setAction("Change");
//        PendingIntent changeActivityPendingIntent = PendingIntent.getBroadcast(this, 0, changeActivity, PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification notification = new NotificationCompat.Builder(this, MusicNotification.CHANNEL_ID_MUSIC)
//                .setContentTitle("Playing music")
//                .setContentText("Chúc bạn nghe nhạc vui vẻ")
//                .setSmallIcon(R.drawable.music_cd_svgrepo_com)
//                .addAction(R.drawable.baseline_play_circle, "Change", changeActivityPendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setOnlyAlertOnce(true)
//                .setAutoCancel(true)
//                .build();
//        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if(notificationManager!=null){
//            notificationManager.notify(1,notification);
//        }
//
//    }
//
//}
//
//
//
//
//
package com.example.appnghenhac.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_music);
        initView();
        createNotification();
        FirebaseApp.initializeApp(this);
        backBtn.setOnClickListener(v -> onBackPressed());

        nextBtn.setOnClickListener(v -> {
            position++;
            if (position > listSongs.size() - 1) {
                position = 0;
            }
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            khoitaoMediaPlayer();
        });

        prevBtn.setOnClickListener(v -> {
            position--;
            if (position < 0) {
                position = listSongs.size() - 1;
            }
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            khoitaoMediaPlayer();
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void loadSongsFromFirebase(String song) {
        Log.d("song_name", song);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference songRef = storageReference.child("/upload/file/" + song + ".mp3");
        songRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String url = uri.toString();
            Log.d("Download_URL", url);

            try {
                if (mediaPlayer != null) {
                    mediaPlayer.reset();
                } else {
                    mediaPlayer = new MediaPlayer();
                }
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
                playPauseBtn.setImageResource(R.drawable.baseline_pause);
                SetTimeTotal();
                UpdateTimeSong();
            } catch (IOException e) {
                Log.e("MediaPlayer_Error", "Error initializing MediaPlayer: " + e.getMessage());
            }
        }).addOnFailureListener(exception -> {
            Log.e("Firebase_Error", "Error getting download URL: " + exception.getMessage());
        });
    }

    private void UpdateTimeSong() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
                duration_played.setText(time.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(mp -> {
                    position++;
                    if (position > listSongs.size() - 1) {
                        position = 0;
                    }
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    khoitaoMediaPlayer();
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void SetTimeTotal() {
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        duration_played.setText(time.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void khoitaoMediaPlayer() {
        String song_music = song_name.getText().toString();
        loadSongsFromFirebase(song_music);
        song_name.setText(listSongs.get(position).getTitle());
        artist_name.setText(listSongs.get(position).getArtist());
        cover_art.setImageResource(listSongs.get(position).getCoverArt());
    }

    private void initView() {
        song_name = findViewById(R.id.song_name);
        Bundle bundle = getIntent().getExtras();
       String music_song= bundle.getString("name_song");
       song_name.setText(music_song);
       String music_song_string=song_name.getText().toString();
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
        playPauseBtn.setOnClickListener(v -> {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.pause();
//                playPauseBtn.setImageResource(R.drawable.baseline_play_arrow_24);
//            } else {
//                mediaPlayer.start();
//                playPauseBtn.setImageResource(R.drawable.baseline_pause);
//            }
            loadSongsFromFirebase(music_song_string);

        });
//        SetTimeTotal();
//        UpdateTimeSong();
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

