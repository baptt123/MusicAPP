package com.example.appnghenhac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.ListRatingAdapter;
import com.example.appnghenhac.asynctask.GetListRatingTracks;
import com.example.appnghenhac.model.Music;

import java.util.ArrayList;


public class RatingActivity extends AppCompatActivity {
    private ImageView btn_return;
    GetListRatingTracks getListRatingTracks = new GetListRatingTracks(this);
    private ListRatingAdapter userTopAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_board);
        getListRatingTracks.execute();
        initView();
    }

    public void getMusicNameFromAsyncTask(ArrayList<Music> list) {
        displayListViewRanking(list);
    }

    public void displayListViewRanking(ArrayList<Music> list) {
        ListView listView = findViewById(R.id.listview_rating);
      ListRatingAdapter musicArrayAdapter = new ListRatingAdapter(this, R.layout.list_item_rating_board, list);
        listView.setAdapter(musicArrayAdapter);
        Toast.makeText(this, "Hiển thị dữ liêu thành công ", Toast.LENGTH_SHORT).show();
    }

    public void initView() {
        btn_return = findViewById(R.id.back);
        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });
    }

    public void getRatingImageTrack(String s) {

    }

//    public void getMusicFromFirebase() {
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
//    }
}
