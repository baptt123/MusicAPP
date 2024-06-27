package com.example.appnghenhac.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.appnghenhac.activity.PlayerMusicActivity;

public class MusicReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;
    Bundle extras;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action!=null){
            switch (action){
                case "Change":
                    Intent activityIntent = new Intent(context, PlayerMusicActivity.class);
                    activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Đảm bảo đặt cờ để khởi động từ BroadcastReceiver
                    context.startActivity(activityIntent);
            }
        }
    }
    /*
  *  hàm setflag dùng để ghi đè 1 flag cho intent dẫn đến intent sẽ luôn được gọi mới
  * hàm addflag dùng để thêm 1 flag cho intent dẫn đến intent sẽ không bị mất do luôn được thêm flag để đánh dấu
     */
//    @Override
////    public void onReceive(Context context, Intent intent) {
////        String action = intent.getAction();
////        String name_song = intent.getStringExtra("song_name");
////        //khai báo intent đẻ gọi đến service
////        Intent serviceIntent = new Intent(context, MusicService.class);
////        //gán action nhận được cho service
////        serviceIntent.setAction(action);
////        //gửi dữ liệu cho service
////        serviceIntent.putExtra("name_song", name_song);
////        //khởi chạy
////        context.startService(serviceIntent);
////
////    }
}


