package com.example.appnghenhac.service;

import android.util.Log;

import com.example.appnghenhac.model.Song;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlayListService {
    FirebaseDatabase data ;
    DatabaseReference reference ;
    private final String root = "Register User";
    private String TAG = "PlayListService";

    public PlayListService(){
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
        String songids = "";

        for (Song s : objects) {
            String so = s.getId();
            if (so.startsWith("\"")) {
                so = so.substring(1);
            }
            if (so.endsWith("\"")) {
                so = so.substring(0, so.length() - 1);
            }
            Log.d(TAG, "updatePlayList: "+so);
        }
        Log.d(TAG, "updatePlayList: "+songids);
        reference.child("user").child(UserService.getInstance().getUserId())
                .child("playList")
                .child(playListName)
                .setValue(songids);
    }

    public void deletePlayList(String name) {
        reference.child(root).child(UserService.userService.getUserId())
                .child("playList").child(name).removeValue();
    }
}
