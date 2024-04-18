package com.example.appnghenhac.rating;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.model.Music;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_board);
    }
    public void displayListViewRanking(){
        ListView listView=findViewById(R.id.list_view_rating);
        List<Music> musicList=new ArrayList<>();
        ArrayAdapter<Music> musicArrayAdapter=new ArrayAdapter<>(this,R.layout.rating_board,musicList);
        listView.setAdapter(musicArrayAdapter);
    }
}
