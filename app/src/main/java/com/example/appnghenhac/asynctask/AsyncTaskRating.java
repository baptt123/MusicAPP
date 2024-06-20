package com.example.appnghenhac.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.HomeActivity;
import com.example.appnghenhac.activity.TestAddFragmentRatingActivity;
import com.example.appnghenhac.fragment.RatingFragment;
import com.example.appnghenhac.model.Music;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncTaskRating extends AsyncTask<Void, Void, String> {
  /*
  AsyncTask chịu trách nhiệm xử lí dữ liệu và nhúng vào fragment rating để hiển thị
   */
    private TestAddFragmentRatingActivity fragmentRatingActivity;

    public AsyncTaskRating(TestAddFragmentRatingActivity fragmentRatingActivity) {
       this.fragmentRatingActivity=fragmentRatingActivity;
    }

    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://v1.nocodeapi.com/thanhtan/spotify/dLRHJVlVhqyRGneg/usersTop?type=tracks&time_range=long_term")
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                //dữ liệu lấy về là dạng json
                String json = response.body().string();
                return json;
            } else {
                Log.e("HTTP", "Request was not successful: " + response.code());
                return null;
            }
        } catch (IOException e) {
            Log.e("HTTP", "IOException: " + e.getMessage());
            return null;
        }
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            ArrayList<Music> musicArrayList = new ArrayList<>();
            // Sử dụng Gson để xử lý JSON
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(s, JsonObject.class);
            JsonArray tracks = jsonResponse.getAsJsonArray("items");

            // Duyệt qua danh sách các track và lấy ra thuộc tính 'images' của mỗi track
            for (int i = 0; i < tracks.size(); i++) {
                JsonObject track = tracks.get(i).getAsJsonObject();
                String name = track.get("name").getAsString();
                JsonArray images = track.getAsJsonObject("album").getAsJsonArray("images");
                JsonObject image = images.get(0).getAsJsonObject();
                String urlStr = image.get("url").getAsString();
                Music music = new Music();
                music.setName(name);
                music.setImg(urlStr);
                musicArrayList.add(music);
                System.out.println("Name: " + name + ", Image URL: " + urlStr);

            }
            Bundle bundle=new Bundle();
            bundle.putSerializable("list",musicArrayList);
            RatingFragment ratingFragment=new RatingFragment();
            ratingFragment.setArguments(bundle);
            FragmentTransaction ft=fragmentRatingActivity.getSupportFragmentManager().beginTransaction().replace(R.id.listview_fragment_rating,ratingFragment);
            ft.commit();
        }
    }


}

