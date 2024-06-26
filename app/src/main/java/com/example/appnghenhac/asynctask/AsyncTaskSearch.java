package com.example.appnghenhac.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.SearchActivity;
import com.example.appnghenhac.fragment.SearchFragment;
import com.example.appnghenhac.model.MusicForSearch;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsyncTaskSearch extends AsyncTask<String, Void, String> {
    SearchActivity searchActivity;

    public AsyncTaskSearch(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }


    @Override
    protected String doInBackground(String... strings) {
        Gson gson = new Gson();
        String param = strings[0];
        ArrayList<MusicForSearch> listlinkimage = new ArrayList<>();
        FirebaseApp.initializeApp(searchActivity);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("list");
        // Đặt CountDownLatch với giá trị lớn, để chờ cho đến khi tất cả dữ liệu được tải về
        CountDownLatch latch = new CountDownLatch(1);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Đọc dữ liệu từ dataSnapshot

                String childKey = dataSnapshot.getKey();
                String childValue = dataSnapshot.getValue(String.class);
                if(childKey.contains(param)){
                    MusicForSearch music = new MusicForSearch();
                    music.setName(childKey);
                    music.setImg(childValue);
                    listlinkimage.add(music);
                    Log.i("FirebaseData", "Name: " + childKey + ", Img: " + childValue);
                    // Giảm latch
                    latch.countDown();
                }else{
                    Log.e("FirebaseData", "Name: " + childKey + ", Img: " + childValue );
                    // Giảm latch
                    latch.countDown();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Xử lý khi có sự thay đổi dữ liệu trong node con
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Xử lý khi node con bị xóa
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Xử lý khi có sự di chuyển node con
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
                latch.countDown();
            }
        });
        try {
            latch.await(30, TimeUnit.SECONDS); // Chờ đến khi latch giảm về 0 hoặc hết thời gian chờ
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String json = gson.toJson(listlinkimage);
        Log.i("json", json);
        return json;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            ArrayList<MusicForSearch> list = searchActivity.sendArrayList(s);
            Bundle bundle = new Bundle();
            bundle.putSerializable("list", list);
            // Tạo Fragment mới và truyền dữ liệu
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setArguments(bundle);

            // Thực hiện FragmentTransaction để thêm Fragment vào Activity
            FragmentTransaction fragmentTransaction = searchActivity.getSupportFragmentManager().beginTransaction().replace(R.id.linear_fragment_search, searchFragment);
            fragmentTransaction.commit();
        }
    }
}
