package com.example.appnghenhac.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import com.example.appnghenhac.R;

public class PlayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
//    toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPlayLIst);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//
        ImageButton imageButton = findViewById(R.id.buttonReturn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}