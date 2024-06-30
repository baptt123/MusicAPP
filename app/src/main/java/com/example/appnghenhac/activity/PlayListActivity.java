package com.example.appnghenhac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.SongInPlaylistAdapter;
import com.example.appnghenhac.adapter.SongPlayListRecyclerView;
import com.example.appnghenhac.asynctask.MusicAsynctask;
import com.example.appnghenhac.model.PlayList;
import com.example.appnghenhac.model.Song;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {
    private static final String TAG = "PlayListActivity";
    private PlayList playList;
    private ArrayList<Song> listSong;
    private SongPlayListRecyclerView recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
//    toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPlayLIst);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v ->{
            finish();
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            playList = (PlayList) bundle.getSerializable("playList");
        }

        TextView textViewPlayListName = findViewById(R.id.tvPlName);
        textViewPlayListName.setText(playList.getName());

        // data
        listSong = new ArrayList<>();
        getSong(playList.getListSong());
//      danh sach nhac recycle view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new SongPlayListRecyclerView(listSong, this, playList.getName());
        recyclerView.setAdapter(recyclerViewAdapter);
        //        RecyclerView scroll vertical
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if()
                Toast.makeText(PlayListActivity.this, "Them nhac", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<MusicAsynctask> runningTasks;

    private void getSong(ArrayList<String> listSong) {
//        asynctask lấy thông tin bài hát
            MusicAsynctask m = new MusicAsynctask(this);
            m.execute(listSong);
    }


    public void setSong(Song song) {
        this.listSong.add(song);
        this.recyclerViewAdapter.notifyDataSetChanged();
    }
}