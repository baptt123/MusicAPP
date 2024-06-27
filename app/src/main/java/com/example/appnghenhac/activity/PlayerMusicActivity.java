package com.example.appnghenhac.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.appnghenhac.R;
import com.example.appnghenhac.config.Constants;
import com.example.appnghenhac.model.MusicFiles;
import com.example.appnghenhac.receiver.MusicReceiver;
import com.example.appnghenhac.service.MusicService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PlayerMusicActivity extends AppCompatActivity {
    //    private ActivityMainBinding mBinding;
    TextView song_name, artist_name, duration_played, duaration_total;
    ImageView cover_art, nextBtn, prevBtn, backBtn, shuffleBtn, repeatBtn;
    FloatingActionButton playPauseBtn;
    SeekBar seekBar;

    int position = 0;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    //    private  Thread playThread ,prevThread ,nextThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_music);
        initView();
        addSong();
        khoitaoMediaPlayer();

//        Xử lý nút backbtn
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//

//        Xử lý nút next
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > listSongs.size() - 1) {
                    position = 0;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                khoitaoMediaPlayer();
                mediaPlayer.start();
                playPauseBtn.setImageResource(R.drawable.baseline_pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        //        Xử lý nút prev
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position > 0) {
                    position = listSongs.size() - 1;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                khoitaoMediaPlayer();
                mediaPlayer.start();
                playPauseBtn.setImageResource(R.drawable.baseline_pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

//Xử lý nút play and pause
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
//                    Nếu đang phát -> pause ->Đổi hình play
                    mediaPlayer.pause();
                    playPauseBtn.setImageResource(R.drawable.baseline_play_arrow_24);
                } else {
//                    Đang ngừng -> play -> đổi hình pause
                    mediaPlayer.start();
                    playPauseBtn.setImageResource(R.drawable.baseline_pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
            }
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

    private void UpdateTimeSong() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
                duration_played.setText(time.format(mediaPlayer.getCurrentPosition()));
//                update progress seebar
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
//                Kiểm tra thời gian bài hát -> nếu kết thúc ->next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > listSongs.size() - 1) {
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        khoitaoMediaPlayer();
                        mediaPlayer.start();
                        playPauseBtn.setImageResource(R.drawable.baseline_pause);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void SetTimeTotal() {
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
//        duaration_total.setText(time.format(mediaPlayer.getDuration()+""));
        duration_played.setText(time.format(mediaPlayer.getDuration()));
//        Gán max của seekBar =mediaPlayer.getDuaretion
        seekBar.setMax(mediaPlayer.getDuration());

    }

    private void khoitaoMediaPlayer() {
        mediaPlayer = MediaPlayer.create(PlayerMusicActivity.this, listSongs.get(position).getPath());
//        mediaPlayer.start();
        song_name.setText(listSongs.get(position).getTitle());
        artist_name.setText(listSongs.get(position).getArtist());
        cover_art.setImageResource(listSongs.get(position).getCoverArt());
    }

    private void addSong() {
        listSongs.add(new MusicFiles(R.raw.emcuangayhomqua, "Em của ngày hôm qua", "Sơn Tùng Mtp", R.drawable.emcuangayhomqua));
        listSongs.add(new MusicFiles(R.raw.chayngaydi, "Chạy ngay đi", "Sơn Tùng Mtp", R.drawable.chayngaydi));
        listSongs.add(new MusicFiles(R.raw.nauanchoem, "Nấu ăn cho em", "Đen, ", R.drawable.nauchoeman));
    }

    private void initView() {
        song_name = findViewById(R.id.song_name);
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


    }
public void createNotification(){

}

}



