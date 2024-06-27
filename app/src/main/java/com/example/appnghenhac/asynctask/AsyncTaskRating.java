package com.example.appnghenhac.asynctask;

import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.TestAddFragmentRatingActivity;
import com.example.appnghenhac.fragment.RatingFragment;
import com.example.appnghenhac.model.Music;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class AsyncTaskRating extends AsyncTask<String, Void, String> {
    /*
    AsyncTask chịu trách nhiệm xử lí dữ liệu và nhúng vào fragment rating để hiển thị
     */
    private TestAddFragmentRatingActivity fragmentRatingActivity;


    //thuộc tính khi gộp code
    //private HomeActivity(MainActivity)
    public AsyncTaskRating(TestAddFragmentRatingActivity fragmentRatingActivity) {
        this.fragmentRatingActivity = fragmentRatingActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        Gson gson = new Gson();
        String param = strings[0];
        ArrayList<Music> listmusic = new ArrayList<>();
        FirebaseApp.initializeApp(fragmentRatingActivity);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("list");

        // Đặt CountDownLatch với giá trị lớn, để chờ cho đến khi tất cả dữ liệu được tải về
        CountDownLatch latch = new CountDownLatch(1);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Đọc dữ liệu từ dataSnapshot
                String childKey = dataSnapshot.getKey();
                String childValue = dataSnapshot.getValue(String.class);
                if (childKey.equals(param)) {
                    Music music = new Music();
                    music.setName(childKey);
                    music.setImg(childValue);
                    listmusic.add(music);
                    Log.i("FirebaseData", "Name: " + childKey + ", Img: " + childValue);
                } else {
                    Log.e("FirebaseData", "Name: " + childKey + ", Img: " + childValue);
                }
                // Giảm latch
                latch.countDown();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        try {
            latch.await(); // Chờ cho đến khi dữ liệu được tải về
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return gson.toJson(listmusic); // Chuyển danh sách nhạc thành chuỗi JSON và trả về
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            ArrayList<Music> musicArrayList = fragmentRatingActivity.sendArrayList(s);
            Bundle bundle = new Bundle();
            bundle.putSerializable("list", musicArrayList);
            RatingFragment ratingFragment = new RatingFragment();
            ratingFragment.setArguments(bundle);
            FragmentTransaction ft = fragmentRatingActivity.getSupportFragmentManager().beginTransaction().replace(R.id.listview_fragment_rating, ratingFragment);
            ft.commit();
        }
    }
}
