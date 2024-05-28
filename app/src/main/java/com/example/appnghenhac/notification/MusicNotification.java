package com.example.appnghenhac.notification;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
//cấu hình để chạy notification trong app
public class MusicNotification extends Application {
    public  static final String CHANNEl_ID="MUSIC_APP";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel(){
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
        NotificationChannel notification=new NotificationChannel(CHANNEl_ID,"Music Channel", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager=getSystemService(NotificationManager.class);
        if(manager!=null){
            manager.createNotificationChannel(notification);
        }
    }
    }
}
