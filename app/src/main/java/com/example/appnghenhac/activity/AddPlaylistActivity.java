package com.example.appnghenhac.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.SongInAddPlaylistRecyclerView;
import com.example.appnghenhac.model.Song;
import com.example.appnghenhac.service.UserService;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPlaylistActivity extends AppCompatActivity {

    private String TAG = "AddPlaylistActivity";
    private String root = "Register User";
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


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
//        songs.add(new Song("2nR51wakN5K3AJENqGaNg9","Bài Này Chill Phết", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
//        songs.add(new Song("2vYaldhZyuRk6me6yowg8e","Buông Đôi Tay Nhau Ra", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
//        songs.add(new Song("6ubB2VCE4z6q66zdGfusJZ","Be The One", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));

//                 Đọc dữ liệu từ Firebase
        reference.child("ListSong").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Song s = null;
                    s = snapshot.getValue(Song.class);
                     if(s!=null)
                        s.setId(snapshot.getKey());
                    if(s!= null){
                        songs.add(s);
                    }
                    Log.d(TAG, "onDataChange: " + s.toString());
                }

                SongInAddPlaylistRecyclerView songAdapter = new SongInAddPlaylistRecyclerView(AddPlaylistActivity.this, songs);
                recyclerView.setAdapter(songAdapter);
//                RecyclerView scroll vertical
                GridLayoutManager gridLayout = new GridLayoutManager(AddPlaylistActivity.this, 2);
                recyclerView.setLayoutManager(gridLayout);

//      buton save
                Button button = findViewById(R.id.buttonDone);
                button.setOnClickListener(v->{
                    if (editText.getText().toString().isEmpty() || editText.getText() == null) {
                        Toast.makeText(AddPlaylistActivity.this, "chua nhap ten play list ko the tao", Toast.LENGTH_SHORT).show();
                    }else{
                        if(songAdapter.getElementClicked().size()<=0){
                            Toast.makeText(AddPlaylistActivity.this, "chua chon bai hat ch play list ko the tao", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            String str = "";
                            for (String song : songAdapter.getElementClicked()) {
                                str += song + ",";
                            }
                            listSong.put(editText.getText().toString(), str);
//                      ghi du lieu
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//
    }



}