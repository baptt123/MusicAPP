package com.example.appnghenhac.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.R;
import com.example.appnghenhac.asynctask.AsyncTaskSearch;
import com.example.appnghenhac.model.MusicForSearch;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText search_bar;
    Button search_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        initView();
    }

    public void initView() {

        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(v -> {
            search_bar = findViewById(R.id.search_bar);
            AsyncTaskSearch asyncTaskSearch = new AsyncTaskSearch(this);
            String result_search = search_bar.getText().toString();
            asyncTaskSearch.execute(result_search);
        });
    }


    public ArrayList<MusicForSearch> sendArrayList(String s) {
        Gson gson = new Gson();
        // Phân tích JSON từ phản hồi
        JsonArray jsonArray=JsonParser.parseString(s).getAsJsonArray();
        ArrayList<MusicForSearch> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            MusicForSearch music=new MusicForSearch();
            String link_img=jsonArray.get(i).getAsString();
            music.setImg(link_img);
            arrayList.add(music);
        }
        return arrayList;
    }
}
