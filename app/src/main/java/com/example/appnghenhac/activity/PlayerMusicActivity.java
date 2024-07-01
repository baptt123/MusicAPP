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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.appnghenhac.R;
import com.example.appnghenhac.application.MusicNameApplication;
import com.example.appnghenhac.login_register.DangNhapActivity;
import com.example.appnghenhac.model.MusicFiles;
import com.example.appnghenhac.notification.MusicNotification;
import com.example.appnghenhac.receiver.MusicReceiver;
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

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_music);
        MusicNameApplication musicNameApplication = (MusicNameApplication) getApplicationContext();
        String song = musicNameApplication.getSongName();
        initView(song);
//        createNotification(song);
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
                } else {
                    mediaPlayer.start();
                    playPauseBtn.setImageResource(R.drawable.baseline_pause);
                    updateSeekBar();
                }
            }
        });
    }

    private void initView(String song) {
        song_name = findViewById(R.id.song_name);
//        Bundle bundle = getIntent().getExtras();
//        String music_song = bundle.getString("name_song");
        song_name.setText(song);
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
        love_song = findViewById(R.id.id_love_song);
        love_song.setOnClickListener(v -> {
            addMusicFavorite();
        });
        checkFavourite();
        loadSongsFromFirebase(music_song_string);
    }
    private void checkFavourite(){
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
                                id_love_song=findViewById(R.id.id_love_song);
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
                } else {
//                    int current_pos = mediaPlayer.getCurrentPosition();
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
//                    mediaPlayer.seekTo(current_pos);
                    mediaPlayer.start();
                }
                mediaPlayer.setLooping(true);
                updateSeekBar();
                setTimeTotal();
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

    //tạo notification
//    @SuppressLint("NotificationTrampoline")
//    public void createNotification(String name) {
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
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (notificationManager != null) {
//            notificationManager.notify(1, notification);
//        }
//    }

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
            startActivity(new Intent(this, DangNhapActivity.class));
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
                                id_love_song=findViewById(R.id.id_love_song);
                                id_love_song.setImageResource(R.drawable.heart_svgrepo_com);
                                Toast.makeText(PlayerMusicActivity.this, "Dữ liệu bài hát đã được thêm vào!!!", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }

                } else {
                    favouriteRef.child(musicNameApplication.getSongName()).setValue(musicNameApplication.getImg());
                    id_love_song=findViewById(R.id.id_love_song);
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
}
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()) {
//                    String currentFavorites = snapshot.child("favourite").getValue(String.class);
//                    if (currentFavorites != null) {
//                        if (!currentFavorites.contains(song)) {
//                            String updatedFavorites = currentFavorites + "," + song;
//                            userRef.child("favourite_name").setValue(updatedFavorites);
//                            Toast.makeText(PlayerMusicActivity.this, "Thêm vào danh sách yêu thích thành công", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(PlayerMusicActivity.this, "Bài hát đã có trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        userRef.child("favourite").setValue(song);
//                        Toast.makeText(PlayerMusicActivity.this, "Tạo mới và thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(PlayerMusicActivity.this, "Không tìm thấy dữ liệu người dùng", Toast.LENGTH_SHORT).show();
//                    userRef.child("favourite").setValue(song);
//                    Toast.makeText(PlayerMusicActivity.this, "Tạo mới và thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
//                }

//phần này dùng để lưu trữ dữ liệu bài hát

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(PlayerMusicActivity.this, "Lỗi khi truy cập dữ liệu", Toast.LENGTH_SHORT).show();
//            }
//        });
//        favouriteRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                    if (childSnapshot.getKey().equals(musicNameApplication.getSongName())) {
//                        favouriteRef.child(musicNameApplication.getSongName()).setValue(musicNameApplication.getImg());
//                        Toast.makeText(PlayerMusicActivity.this, "Dữ liệu bài hát đã được thêm vào!!!", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(PlayerMusicActivity.this, "Thêm dữ liêu thất bại!!!", Toast.LENGTH_SHORT).show();
//            }
//        });



