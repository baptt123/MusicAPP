package com.example.appnghenhac.notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MusicNotification extends Application {
    public static final String CHANNEL_ID_MUSIC = "CHANNEL_ID_MUSIC";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    public void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID_MUSIC,
                    "CHANNEL_MUSIC",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("Music Notification");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }
}
