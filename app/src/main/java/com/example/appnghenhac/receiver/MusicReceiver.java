package com.example.appnghenhac.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MusicReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {

            case "Play":
                String name_song = intent.getStringExtra("name_song");
                // Khởi tạo Firebase Storage
                FirebaseStorage storage = FirebaseStorage.getInstance();
                // Tham chiếu đến nơi bạn muốn lưu trữ file
                StorageReference storageRef = storage.getReference();
                StorageReference fileRef = storageRef.child("uploads/"+name_song);
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUrl = uri.toString();
                        Log.d("Firebase", "Download URL: " + downloadUrl);
                        // Xử lý URL tải xuống tại đây
                        // Ví dụ: Chơi nhạc từ URL
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(downloadUrl);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
//                            int currentPosition = mediaPlayer.getCurrentPosition();
//                            mediaPlayer.seekTo(currentPosition);
//                            mediaPlayer.start();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Xử lý lỗi nếu không lấy được URL
                        Log.d("Firebase", exception.getMessage());
                    }
                });
                break;
            case "Pause":
                mediaPlayer.stop();
                break;
        }
    }

}
