package com.example.appnghenhac.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.R;
import com.example.appnghenhac.asynctask.AsyncTaskRating;
import com.example.appnghenhac.model.Music;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/*
activity cho việc test thêm fragment bảng xếp hạng
 */
public class TestAddFragmentRatingActivity extends AppCompatActivity {
    Button btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_add_fragment_rating);
        initView();
    }

    public void initView() {
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            AsyncTaskRating asyncTaskRating = new AsyncTaskRating(this);
            asyncTaskRating.execute();
        });
    }

    public ArrayList<Music> sendArrayList(String s) {
        Gson gson = new Gson();
        // Phân tích JSON từ phản hồi
        JsonArray jsonArray = JsonParser.parseString(s).getAsJsonArray();
        ArrayList<Music> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Music music = new Music();
            String name = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
            music.setName(name);
            String link_img = jsonArray.get(i).getAsJsonObject().get("img").getAsString();
            music.setImg(link_img);
            arrayList.add(music);
        }
        return arrayList;
    }
}
