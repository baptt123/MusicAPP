package com.example.appnghenhac.rating;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.model.Music;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RatingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_board);

    }

    public void displayListViewRanking() {
        ListView listView = findViewById(R.id.list_view_rating);
        List<Music> musicList = new ArrayList<>();
        ArrayAdapter<Music> musicArrayAdapter = new ArrayAdapter<>(this, R.layout.rating_board, musicList);
        listView.setAdapter(musicArrayAdapter);
    }

    public void getMusicFromFirebase() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
    }
}
