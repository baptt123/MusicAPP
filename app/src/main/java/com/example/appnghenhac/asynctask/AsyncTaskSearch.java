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

public class AsyncTaskSearch extends AsyncTask<String, Void, String> {
    SearchActivity searchActivity;

    public AsyncTaskSearch(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        Gson gson=new Gson();
        String param = strings[0];
        ArrayList<String> listlinkimage=new ArrayList<>();
        FirebaseApp.initializeApp(searchActivity);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("list");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Đọc dữ liệu từ dataSnapshot
                String childKey = dataSnapshot.getKey();
                String childValue = dataSnapshot.getValue(String.class);
                listlinkimage.add(childValue);
                Log.d("TAG", "Child key: " + childKey + ", Child value: " + childValue);
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
            }
        });


//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://v1.nocodeapi.com/thanhtan/spotify/dLRHJVlVhqyRGneg/search?q="+param)
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                //dữ liệu lấy về là dạng json
//                String json = response.body().string();
//                return json;
//            } else {
//                Log.e("HTTP", "Request was not successful: " + response.code());
//                return null;
//            }
//        } catch (IOException e) {
//            Log.e("HTTP", "IOException: " + e.getMessage());
//            return null;
//        }

       String json=gson.toJson(listlinkimage);
       Log.e("json",json);
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
