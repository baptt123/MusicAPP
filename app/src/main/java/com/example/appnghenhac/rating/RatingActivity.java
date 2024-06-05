package com.example.appnghenhac.rating;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.UserTopAdapter;
import com.example.appnghenhac.asynctask.GetUserTop;
import com.example.appnghenhac.main.MainActivity;
import com.example.appnghenhac.model.Music;

import java.util.ArrayList;


public class RatingActivity extends AppCompatActivity {
    private ImageView btn_return;
    GetUserTop getUserTop = new GetUserTop(this);
    private UserTopAdapter userTopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_board);
        getUserTop.execute();
        initView();
    }

    public void getDataFromAsyncTask(String s) {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        String inputstring = "\"popularity\":";
        int startIndex = s.indexOf("\"popularity\":");
        while (startIndex != -1) {
            int endIndex = s.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = s.indexOf("}", startIndex);
            }
            String popularityString = s.substring(startIndex + inputstring.length(), endIndex);
            Music music = new Music();
            music.setPopularity(Integer.parseInt(popularityString));
            musicArrayList.add(music);
            startIndex = s.indexOf("\"popularity\":", endIndex);
        }
//        displayListViewRanking(musicArrayList);
    }

//    public void displayListViewRanking(ArrayList<Music> list) {
//        ListView listView = findViewById(R.id.list_view_rating);
//        UserTopAdapter musicArrayAdapter = new UserTopAdapter(this, R.layout.list_item, list);
//        listView.setAdapter(musicArrayAdapter);
//        Toast.makeText(this, "Hien thi du lieu thanh cong", Toast.LENGTH_LONG).show();
//    }

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
