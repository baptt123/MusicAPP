package com.example.appnghenhac.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.R;
import com.example.appnghenhac.asynctask.AsyncTaskRating;
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
            AsyncTaskRating    asyncTaskRating = new AsyncTaskRating(this);
            asyncTaskRating.execute();
        });
    }
}
