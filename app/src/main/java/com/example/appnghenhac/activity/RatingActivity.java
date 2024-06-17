package com.example.appnghenhac.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.ListRatingAdapter;
import com.example.appnghenhac.asynctask.GetListRatingTracks;
import com.example.appnghenhac.activity.MainActivity;
import com.example.appnghenhac.model.Music;

import java.util.ArrayList;


public class RatingActivity extends AppCompatActivity {
    private ImageView btn_return;
    ProgressBar progressBar;
    GetListRatingTracks getListRatingTracks = new GetListRatingTracks(this);
    private ListRatingAdapter userTopAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_board);
        initView();
        getListRatingTracks.execute();

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
        progressBar=findViewById(R.id.progress_bar);
        btn_return = findViewById(R.id.back);
        btn_return.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarDuration(5000,progressBar);

    }

    public void getRatingImageTrack(String s) {

    }

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
