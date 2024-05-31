package com.example.notifiaction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;

import static com.example.notifiaction.MusicNotification.CHANNEL_ID_MUSIC;

public class MainActivity extends AppCompatActivity {
    Button btn_play;
    MediaSessionCompat mediaSessionCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaSessionCompat = new MediaSessionCompat(this, "PlayMusic");
        initView();
    }

    public void initView() {
        btn_play = findViewById(R.id.btn_play);
        btn_play.setOnClickListener(v -> createNotification());
    }
    /*
      PendingIntent dùng để lưu trữ intent và sẽ được gọi lại tự động mỗi khi có 1 intent được tạo
      Dùng receiver để lắng nghe sự kiện chuyển intent
      Khi truyền intent vào thì notification sẽ quản lí các intent dựa trên tên được đặt trong notification
      Khi đưa qua receiver thì phải đặt tên riêng cho từng intent có trong notification để receiver nhận diện và xử lí
     */
    public void createNotification() {

        Intent playintent=new Intent(this,MusicReceiver.class);
        playintent.setAction("Play");
        PendingIntent playPendingIntent=PendingIntent.getBroadcast(this,0,playintent,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent pauseintent=new Intent(this,MusicReceiver.class);
        pauseintent.setAction("Pause");
        PendingIntent pausePendingIntent=PendingIntent.getBroadcast(this,1,pauseintent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_MUSIC).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle("Playing Music").
                setContentText("Hope you happy with current music!!!").
                addAction(R.drawable.baseline_play_arrow_24,"Play",playPendingIntent).
                addAction(R.drawable.pause,"Pause",pausePendingIntent).
                setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSessionCompat.getSessionToken())).
                setPriority(NotificationCompat.PRIORITY_LOW).
                build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}