package com.example.appnghenhac.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
    ImageView search_button;
    ProgressBar progressBar;
    ImageView back_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        initView();
    }

    public void initView() {
        back_home = findViewById(R.id.back_home);
        back_home.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(v -> {
            search_bar = findViewById(R.id.search_bar);
            AsyncTaskSearch asyncTaskSearch = new AsyncTaskSearch(this);
            String result_search = search_bar.getText().toString();
            progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            setProgressBarDuration(3000, progressBar);
            asyncTaskSearch.execute(result_search);
        });
        back_home=findViewById(R.id.back_home);
        back_home.setOnClickListener(v -> {
            Intent intent = new Intent(this, NavicationActivity.class);
            startActivity(intent);
        });
    }


    public ArrayList<MusicForSearch> sendArrayList(String s) {
        Gson gson = new Gson();
        // Phân tích JSON từ phản hồi
        JsonArray jsonArray = JsonParser.parseString(s).getAsJsonArray();
        ArrayList<MusicForSearch> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            MusicForSearch music = new MusicForSearch();
            String name = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
            music.setName(name);
            String link_img = jsonArray.get(i).getAsJsonObject().get("img").getAsString();
            music.setImg(link_img);
            arrayList.add(music);
        }
        return arrayList;
    }

    /*
     * Hàm xử lý thời gian ProgressBar
     */
    private void setProgressBarDuration(int duration, ProgressBar progressBar) {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        progressAnimator.setDuration(duration);
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(View.GONE); // Ẩn ProgressBar sau khi hoàn thành
            }
        });
        progressAnimator.start();
    }
}
