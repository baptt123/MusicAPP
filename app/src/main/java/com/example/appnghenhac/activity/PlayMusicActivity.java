package com.example.appnghenhac.activity;

import android.Manifest;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.session.MediaStyleNotificationHelper;
import com.example.appnghenhac.R;

public class PlayMusicActivity extends AppCompatActivity {
    private Button btn_playmusic;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_play_music);
//        initView();
    }
    //hàm khởi tạo giao diện và sự kiện
//    public void initView(){
//        btn_playmusic=findViewById(R.id.btn_playmusic);
//        btn_playmusic.setOnClickListener(v -> {
//            sendNotificationMedia();
//        });
//    }
//hàm gửi notification và hiển thị lên thiết bị
//    @OptIn(markerClass = UnstableApi.class)
//    private void sendNotificationMedia() {
//        //thư viện bitmap mã hóa ảnh sang nhị phân để xử lí
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_music_note_24);
//        MediaSessionCompat mediaSessionCompat=new MediaSessionCompat(this,"tag");
//                //các thông số để cấu hình notification
//        Notification notificationmedia = new NotificationCompat.Builder(this, MusicNotification.CHANNEl_ID).
//                setSmallIcon(R.drawable.baseline_music_note_24).
//                setSubText("Music").
//                setContentText("Playing Music No 1").
//                setContentTitle("Playing Music").
//                addAction(R.drawable.baseline_play_arrow_24, "Play music", null)
//                // Apply the media style template.
//                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                        .setShowActionsInCompactView(0)
//                        .setMediaSession(mediaSessionCompat.getSessionToken()))
//                .setContentTitle("Wonderful music")
//                .setContentText("My Awesome Band")
//                .setLargeIcon(bitmap)
//                .build();
//        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(this);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            notificationCompat.notify(1, notificationmedia);
//        }
//
//    }
}
