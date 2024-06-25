package com.example.appnghenhac.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.SongAdapter;
import com.example.appnghenhac.model.PlayList;
import com.example.appnghenhac.model.Song;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {
    private PlayList playList;
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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            playList = (PlayList) bundle.getSerializable("playList");
            Log.d("TAG", "onCreate: " +playList.toString());
        }

        TextView textViewPlayListName = findViewById(R.id.tvPlName);
        textViewPlayListName.setText(playList.getName());

        ListView lvSong = findViewById(R.id.listView);
        ArrayList<Song> listSong=  getSong(playList.getListSong());
        SongAdapter songAdapter = new SongAdapter(this, R.layout.list_item_song,listSong );
        lvSong.setAdapter(songAdapter);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayListActivity.this, "Them nhac", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Song> getSong(ArrayList<String> listSong) {
        ArrayList<Song> res = new ArrayList<>();
        for (String s : listSong) {
//        asynctask lấy thông tin bài hát
//        new musicService(this).execute("1J3SmWwlYAG68LGKr86MVH");
//
            Song song = new Song(s,"");
            res.add(song);
        }
        return res;
    }


}