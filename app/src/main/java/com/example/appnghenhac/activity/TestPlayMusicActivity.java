package com.example.appnghenhac.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.R;

public class TestPlayMusicActivity extends AppCompatActivity {
    ImageView image_disk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_play_music);
        initView();
    }

    private void initView() {
        //TO DO
        image_disk=findViewById(R.id.image_disk);
        setAnimation(image_disk);
    }
    public void setAnimation(ImageView image_disk){
       ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(image_disk,"rotation",0f,360f);
       objectAnimator.setDuration(10000);
       objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
       objectAnimator.start();
    }
}
