package com.example.appnghenhac.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appnghenhac.Manifest;
import com.example.appnghenhac.R;
import com.example.appnghenhac.application.MusicNameApplication;
import com.example.appnghenhac.model.MusicFiles;
import com.example.appnghenhac.service.MusicService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
class này dùng cho chức năng phát nhạc online
 */
public class PlayerMusicActivity extends AppCompatActivity {
    TextView song_name, artist_name, duration_played, duaration_total;
    ImageView cover_art, nextBtn, prevBtn, backBtn, shuffleBtn, repeatBtn;
    ImageView love_song;
    FloatingActionButton playPauseBtn;
    SeekBar seekBar;
    ImageView id_love_song;
    int position = 0;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    Animation animation;
    ImageView  downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_music);
        MusicNameApplication musicNameApplication = (MusicNameApplication) getApplicationContext();
        String song = musicNameApplication.getSongName();
        initView(song);
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
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
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
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
                    cover_art.startAnimation(animation);
                }
            }
        });
    }

    private void initView(String song) {
//        downloadBtn=findViewById(R.id.)
        song_name = findViewById(R.id.song_name);
        song_name.setText(song);
        String music_song_string = song_name.getText().toString();
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
        love_song = findViewById(R.id.id_love_song);
        love_song.setOnClickListener(v -> {
            addMusicFavorite();
        });
        checkFavourite();
        loadSongsFromFirebase(music_song_string);
        playPauseBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MusicService.class);
            intent.setAction("ACTION_TOGGLE_PLAYBACK");
            startService(intent);
        });
        downloadBtn.setOnClickListener(v -> {
            downloadSongFromFirebase(song);
        });
    }

    private void checkFavourite() {
        MusicNameApplication musicNameApplication = (MusicNameApplication) getApplicationContext();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userID = auth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference().child("Register User").child(userID);
        DatabaseReference favouriteRef = userRef.child("favourite");
        favouriteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.getChildren() != null) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.getKey().equals(musicNameApplication.getSongName())) {
                                id_love_song = findViewById(R.id.id_love_song);
                                id_love_song.setImageResource(R.drawable.heart_svgrepo_com);
                                Toast.makeText(PlayerMusicActivity.this, "Đã tải dữ liệu bài hát thành công!!!", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }

                } else {
                    Toast.makeText(PlayerMusicActivity.this, "Không tìm thấy dữ liệu người dùng", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //load nhạc từ Firebase
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
                    playPauseBtn.setImageResource(R.drawable.baseline_pause);
                } else {
//                    int current_pos = mediaPlayer.getCurrentPosition();
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
//                    mediaPlayer.seekTo(current_pos);
                    mediaPlayer.start();
                    playPauseBtn.setImageResource(R.drawable.baseline_pause);
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

    //xử lí seekbar cho bài hát
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

    //hàm xét tổng thời gian phát cho đoạn nhạc
    private void setTimeTotal() {
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        if (mediaPlayer != null) {
            duaration_total.setText(time.format(mediaPlayer.getDuration()));
            seekBar.setMax(mediaPlayer.getDuration());
        }
    }



    //hàm thêm bài hát yêu thích
    public void addMusicFavorite() {
        MusicNameApplication musicNameApplication = (MusicNameApplication) getApplicationContext();
        String song = musicNameApplication.getSongName();
        if (song == null || song.isEmpty()) {
            Toast.makeText(this, "Không có tên bài hát để thêm", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Bạn chưa đăng nhập! Sẽ quay về trang đăng nhập", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        String userID = auth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference().child("Register User").child(userID);
        DatabaseReference favouriteRef = userRef.child("favourite");
        //phần này dùng để thêm và lưu trữ tên bài hát
        favouriteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.getChildren() != null) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (!dataSnapshot.getKey().equals(musicNameApplication.getSongName())) {
                                favouriteRef.child(musicNameApplication.getSongName()).setValue(musicNameApplication.getImg());
                                id_love_song = findViewById(R.id.id_love_song);
                                id_love_song.setImageResource(R.drawable.heart_svgrepo_com);
                                Toast.makeText(PlayerMusicActivity.this, "Dữ liệu bài hát đã được thêm vào!!!", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }

                } else {
                    favouriteRef.child(musicNameApplication.getSongName()).setValue(musicNameApplication.getImg());
                    id_love_song = findViewById(R.id.id_love_song);
                    id_love_song.setImageResource(R.drawable.heart_svgrepo_com);
                    Toast.makeText(PlayerMusicActivity.this, "Đã thêm bài hát thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PlayerMusicActivity.this, "Lỗi khi thêm dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Kiểm tra quyền lưu trữ
    private void checkStoragePermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
    }

    // Phương thức tải bài hát từ Firebase
    private void downloadSongFromFirebase(String song) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference songRef = storageReference.child("/upload/file/" + song + ".mp3");

        songRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String url = uri.toString();
            File file = new File(Environment.getExternalStorageDirectory(), song + ".mp3");

            // Kiểm tra nếu bài hát đã được tải về chưa
            if (file.exists()) {
                Toast.makeText(this, "Bài hát đã được tải về", Toast.LENGTH_SHORT).show();
            } else {
                // Tiến hành tải bài hát
                new Thread(() -> {
                    try {
                        URL downloadUrl = new URL(url);
                        URLConnection connection = downloadUrl.openConnection();
                        connection.connect();
                        InputStream inputStream = connection.getInputStream();
                        OutputStream outputStream = openFileOutput(file.getName(), Context.MODE_PRIVATE);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }

                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();

                        runOnUiThread(() -> {
                            Toast.makeText(PlayerMusicActivity.this, "Tải bài hát thành công!", Toast.LENGTH_SHORT).show();
                        });
                    } catch (IOException e) {
                        Log.e("Download Error", e.getMessage());
                        runOnUiThread(() -> {
                            Toast.makeText(PlayerMusicActivity.this, "Lỗi tải bài hát", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            }
        }).addOnFailureListener(e -> {
            Log.e("Download Error", "Không thể lấy URL bài hát: " + e.getMessage());
        });
    }

    // Kiểm tra xem bài hát đã được tải xuống chưa
    private boolean isSongDownloaded(String song) {
        File file = new File(Environment.getExternalStorageDirectory(), song + ".mp3");
        return file.exists();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền lưu trữ được cấp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cần quyền lưu trữ để tải bài hát", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



