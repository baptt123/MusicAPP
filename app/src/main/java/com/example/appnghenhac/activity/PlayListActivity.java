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

import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.SongInPlaylistAdapter;
import com.example.appnghenhac.asynctask.MusicAsynctask;
import com.example.appnghenhac.model.PlayList;
import com.example.appnghenhac.model.Song;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {
    private static final String TAG = "PlayListActivity";
    private PlayList playList;
    private ArrayList<Song> listSong;
    private SongInPlaylistAdapter showSongInPlaylistAdapter;

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
            Log.d(TAG, "onCreate: " + playList.toString());
        }

        TextView textViewPlayListName = findViewById(R.id.tvPlName);
        textViewPlayListName.setText(playList.getName());

//      danh sach nhac
        ListView lvSong = findViewById(R.id.listView);
        listSong = new ArrayList<>();
        getSong(playList.getListSong());

         showSongInPlaylistAdapter = new SongInPlaylistAdapter(this, R.layout.list_item_song, listSong);
        lvSong.setAdapter(showSongInPlaylistAdapter);

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
            Log.d(TAG+ "before", "getSong: "+listSong.toString());
            MusicAsynctask m = new MusicAsynctask(this);
            m.execute(listSong);
    }




    public void setSong(Song song) {
        Log.d(TAG, "setSong: "+ song.toString());
        this.listSong.add(song);
        this.showSongInPlaylistAdapter.notifyDataSetChanged();
    }
}