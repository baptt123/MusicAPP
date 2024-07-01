package com.example.appnghenhac.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.ListRatingAdapter;
import com.example.appnghenhac.model.Music;

import java.util.ArrayList;


public class RatingFragment extends Fragment {
    ProgressBar progressBar;
    private ImageView btn_return;
    private ListRatingAdapter userTopAdapter;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rating_board_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.listview_rating);
        progressBar = view.findViewById(R.id.progress_bar);
        setProgressBarDuration(5000, progressBar);
        ArrayList<Music> list = (ArrayList<Music>) getArguments().getSerializable("list");
        ListRatingAdapter musicArrayAdapter = new ListRatingAdapter(getActivity(), R.layout.list_item_rating_board, list);
        listView.setAdapter(musicArrayAdapter);
        Toast.makeText(getActivity(), "Hiển thị dữ liêu thành công ", Toast.LENGTH_SHORT).show();
    }
}
