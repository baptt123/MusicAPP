package com.example.appnghenhac.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DownloadedMusicActivity extends AppCompatActivity {

    private ListView listViewDownloadedSongs;
    private TextView txtCurrentSong;
    private SeekBar seekBar;
    private ImageButton btnPlayPause, btnPrev, btnNext;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private ArrayList<File> downloadedSongs;
    private int currentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_downloaded_music);

        listViewDownloadedSongs = findViewById(R.id.listViewDownloadedSongs);
        txtCurrentSong = findViewById(R.id.txtCurrentSong);
        seekBar = findViewById(R.id.seekBarDownloadedMusic);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        downloadedSongs = getDownloadedSongs();
        if (downloadedSongs.isEmpty()) {
            Toast.makeText(this, "Không có bài hát nào đã tải!", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getSongNames(downloadedSongs));
        listViewDownloadedSongs.setAdapter(adapter);

        listViewDownloadedSongs.setOnItemClickListener((parent, view, position, id) -> {
            currentIndex = position;
            playSong(downloadedSongs.get(position));
        });

        btnPlayPause.setOnClickListener(v -> togglePlayPause());
        btnNext.setOnClickListener(v -> playNext());
        btnPrev.setOnClickListener(v -> playPrevious());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private ArrayList<File> getDownloadedSongs() {
        ArrayList<File> songs = new ArrayList<>();
        File musicDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "MyMusic");
        if (musicDir.exists()) {
            File[] files = musicDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".mp3")) {
                        songs.add(file);
                    }
                }
            }
        }
        return songs;
    }

    private ArrayList<String> getSongNames(ArrayList<File> songs) {
        ArrayList<String> names = new ArrayList<>();
        for (File song : songs) {
            names.add(song.getName().replace(".mp3", ""));
        }
        return names;
    }

    private void playSong(File songFile) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        } else {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        try {
            mediaPlayer.setDataSource(songFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            txtCurrentSong.setText(songFile.getName().replace(".mp3", ""));
            btnPlayPause.setImageResource(R.drawable.baseline_pause);
            updateSeekBar();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi phát nhạc", Toast.LENGTH_SHORT).show();
        }
    }

    private void togglePlayPause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlayPause.setImageResource(R.drawable.baseline_play_arrow_24);
            } else {
                mediaPlayer.start();
                btnPlayPause.setImageResource(R.drawable.baseline_pause);
                updateSeekBar();
            }
        }
    }

    private void playNext() {
        if (downloadedSongs != null && currentIndex < downloadedSongs.size() - 1) {
            currentIndex++;
            playSong(downloadedSongs.get(currentIndex));
        }
    }

    private void playPrevious() {
        if (downloadedSongs != null && currentIndex > 0) {
            currentIndex--;
            playSong(downloadedSongs.get(currentIndex));
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
