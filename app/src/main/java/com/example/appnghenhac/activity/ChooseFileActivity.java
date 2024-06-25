package com.example.appnghenhac.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseFileActivity extends AppCompatActivity {
    public MediaPlayer mediaPlayer;
    public final int PICK_FILE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openFilePicker();
    }
    public void openFilePicker(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,PICK_FILE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_FILE_REQUEST_CODE && resultCode==RESULT_OK){
            if(data!=null){
                Uri uri=data.getData();
                mediaPlayer=MediaPlayer.create(this,uri);
                mediaPlayer.start();
            }
        }
    }
}
