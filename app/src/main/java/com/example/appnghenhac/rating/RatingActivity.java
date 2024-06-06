package com.example.appnghenhac.rating;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.ListRatingAdapter;
import com.example.appnghenhac.asynctask.GetListRatingTracks;
import com.example.appnghenhac.main.MainActivity;
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

    public void getMusicNameFromAsyncTask(String s) {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        String inputnamestring = "\"name\":";
        int startIndex = s.indexOf(inputnamestring);
        while (startIndex != -1) {
            int endIndex = s.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = s.indexOf("}", startIndex);
            }
            String nameString = s.substring(startIndex + inputnamestring.length(), endIndex);
            Music music = new Music();
            music.setName(nameString);
            musicArrayList.add(music);
            startIndex = s.indexOf(inputnamestring, endIndex);
        }
        ArrayList<Music> musicimgArrayList = new ArrayList<>();
        String input_name_img="\"url\":";
        int startIndexImg = s.indexOf(input_name_img);
        while (startIndexImg != -1) {
            int endIndexImg = s.indexOf(",", startIndexImg);
            if (endIndexImg == -1) {
                endIndexImg = s.indexOf("}", startIndexImg);
            }
            String nameString = s.substring(startIndexImg + input_name_img.length(), endIndexImg);
            Music music_img= new Music();
            music_img.setImg(input_name_img);
            musicimgArrayList.add(music_img);
            startIndex = s.indexOf(input_name_img, endIndexImg);
        }
        ArrayList<Music> musicresult=new ArrayList<>();
        for (int i = 0; i < musicArrayList.size(); i++) {
            Music music=new Music();
            music.setName(musicArrayList.get(i).getName());
            music.setImg(musicimgArrayList.get(i).getImg());
            musicresult.add(music);
        }

        displayListViewRanking(musicArrayList);
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

//    public void getMusicFromFirebase() {
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
//    }
}
