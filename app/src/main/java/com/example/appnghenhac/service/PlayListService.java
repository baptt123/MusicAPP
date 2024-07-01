package com.example.appnghenhac.service;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.example.appnghenhac.activity.PlayListActivity;
import com.example.appnghenhac.model.Song;
import com.example.appnghenhac.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class PlayListService {
    FirebaseDatabase data ;
    DatabaseReference reference ;
    private String root;

    public PlayListService(){
        root = "user";
        data = FirebaseDatabase.getInstance();
        reference = data.getReference();
    }

    private static PlayListService playListService = new PlayListService();

    public static PlayListService getInstance() {
        return playListService;
    }
    //    ghi du lieu
    public void updatePlayList(String playListName, ArrayList<Song> objects) {
        ////        ghi du lieu
        Map<String, Object> pl = new ArrayMap<>();
        ArrayList<String> musicId = new ArrayList<>();
        for (Song s : objects ) {
            musicId.add(s.getId() + ",");
        }
        pl.put("playListName", musicId);

        reference.child("user").child(UserService.getInstance().getUserId())
                .child("playList")
                .updateChildren(pl);
//
    }
}
