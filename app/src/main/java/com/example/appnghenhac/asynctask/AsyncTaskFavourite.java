package com.example.appnghenhac.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.NavicationActivity;
import com.example.appnghenhac.fragment.FavouriteFragment;
import com.example.appnghenhac.model.MusicForFavourite;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
/*
Author:Tân
Date:28/6/2024
Description:Class này dùng để mô tả asynctask xử lí đa luồng cho chức năng hiển thị danh sách yêu thích của người dùng
 */
public class AsyncTaskFavourite extends AsyncTask<String, Void, String> {
    private NavicationActivity navicationActivity;

    public AsyncTaskFavourite(NavicationActivity navicationActivity) {
        this.navicationActivity = navicationActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        ArrayList<MusicForFavourite> list = new ArrayList<>();
        String params = strings[0];
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        CountDownLatch latch = new CountDownLatch(1);

        if (uid != null && uid.equals(params)) {
            FirebaseApp.initializeApp(navicationActivity);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference dbref = firebaseDatabase.getReference("Register User").child(uid).child("favourite");

            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            MusicForFavourite musicForFavourite = new MusicForFavourite();
                            musicForFavourite.setName(key);
                            musicForFavourite.setLink_img(childSnapshot.getValue(String.class));
                            list.add(musicForFavourite);
                        }
                    } else {
                        Log.d("AsyncTaskFavourite", "No favourite songs found.");
                    }
                    latch.countDown();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("AsyncTaskFavourite", "Firebase Error: " + error.getMessage());
                    latch.countDown();
                }
            });

            try {
                latch.await(); // Wait until data is fully loaded
            } catch (InterruptedException e) {
                Log.e("AsyncTaskFavourite", "InterruptedException: " + e.getMessage());
            }
        }

        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Gson gson = new Gson();
        ArrayList<MusicForFavourite> list_result = gson.fromJson(result, new TypeToken<ArrayList<MusicForFavourite>>(){}.getType());
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_result", list_result);
        favouriteFragment.setArguments(bundle);
        FragmentManager fm = navicationActivity.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, favouriteFragment).commit();
    }
}
