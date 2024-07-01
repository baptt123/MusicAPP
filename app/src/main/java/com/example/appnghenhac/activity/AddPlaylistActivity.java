package com.example.appnghenhac.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.SongInAddPlaylistRecyclerView;
import com.example.appnghenhac.model.Song;
import com.example.appnghenhac.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPlaylistActivity extends AppCompatActivity {

    private String TAG = "AddPlaylistActivity";
    private String root = "Register User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
//        toolbar
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        // play list name
        EditText editText = findViewById(R.id.editTextText);

//        arraylist resturn
        Map<String,Object> listSong = new HashMap<>();

//      listview
        RecyclerView recyclerView = findViewById(R.id.recycler);
// danh sach cac bai hat
//        TODO lay tu firebase mot danh sach cac bai nhac
        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song("3fw9v7CztM2mqu1jCtbg9f","chung ta cua hien tai", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("211PBKJlAG1CxXUEjN5nqq","chung ta cua hien tai 2", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("513yY4zlOPYCAnqH614sl1","chung ta cua hien tai 3", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));

        SongInAddPlaylistRecyclerView songAdapter = new SongInAddPlaylistRecyclerView(this, songs);
        recyclerView.setAdapter(songAdapter);

//        RecyclerView scroll vertical
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);

//      buton save
        Button button = findViewById(R.id.buttonDone);
        button.setOnClickListener(v->{
                if (editText.getText().toString().isEmpty() || editText.getText() == null) {
                    Toast.makeText(this, "chua nhap ten play list ko the tao", Toast.LENGTH_SHORT).show();
                }else{
                    if(songAdapter.getElementClicked().size()<=0){
                        Toast.makeText(this, "chua chon bai hat ch play list ko the tao", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        String s = "";
                        for (String song : songAdapter.getElementClicked()) {
                            s += song + ",";
                        }
                        listSong.put(editText.getText().toString(), s);
//                      ghi du lieu
                        FirebaseDatabase data = FirebaseDatabase.getInstance();
                        DatabaseReference reference = data.getReference();
//                      TODO tai khoan user o dau
                        String userID = UserService.getInstance().getUserId();
                        reference.child(root).child(userID).child("playList").updateChildren(listSong);

                        Intent returnItent = new Intent();
                        Bundle bundle = new Bundle();
                        ArrayList<String> lists = new ArrayList<>();
                        for (Map.Entry<String, Object> en : listSong.entrySet()) {
                            lists.add(en.getValue().toString());
                        }
                        bundle.putString("playlistName", editText.getText().toString());
                        bundle.putStringArrayList("lists", lists);
                        returnItent.putExtras(bundle);
                        setResult(Activity.RESULT_OK, returnItent);
                        finish();
                    }
                }
        });
    }



}