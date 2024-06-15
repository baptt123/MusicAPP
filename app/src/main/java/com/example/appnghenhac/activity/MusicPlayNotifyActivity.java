package com.example.appnghenhac.activity;


import static com.example.appnghenhac.notification.MusicNotification.CHANNEL_ID_MUSIC;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.appnghenhac.R;
import com.example.appnghenhac.receiver.MusicReceiver;

public class MusicPlayNotifyActivity extends AppCompatActivity {
    Button btn_play_music;
    MediaSessionCompat mediaSessionCompat;
    static final int OPEN_FILE_REQUEST = 1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music_notification);
        initView();
    }

    public void initView() {
        btn_play_music = findViewById(R.id.btn_play_music);
        btn_play_music.setOnClickListener(v -> {
            createNotification();
        });
    }

    /*
      PendingIntent dùng để lưu trữ intent và sẽ được gọi lại tự động mỗi khi có 1 intent được tạo
      Dùng receiver để lắng nghe sự kiện chuyển intent
      Khi truyền intent vào thì notification sẽ quản lí các intent dựa trên tên được đặt trong notification
      Khi đưa qua receiver thì phải đặt tên riêng cho từng intent có trong notification để receiver nhận diện và xử lí
     */
    public void createNotification() {

        Intent playintent = new Intent(this, MusicReceiver.class);
        playintent.setAction("Play");
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playintent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent pauseintent = new Intent(this, MusicReceiver.class);
        pauseintent.setAction("Pause");
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(this, 1, pauseintent, PendingIntent.FLAG_UPDATE_CURRENT);
        mediaSessionCompat = new MediaSessionCompat(this, "MusicPlayNotifyActivity");
        @SuppressLint("NotificationTrampoline") Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_MUSIC).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle("Playing Music").
                setContentText("Hope you happy with current music!!!").
                addAction(R.drawable.play, "Play", playPendingIntent).
                addAction(R.drawable.pause, "Pause", pausePendingIntent).
                setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSessionCompat.getSessionToken())).
                setPriority(NotificationCompat.PRIORITY_LOW).
                build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

    public void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, OPEN_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_FILE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                mediaPlayer = MediaPlayer.create(this, uri);
                mediaPlayer.start();
            }
        }
    }
}
