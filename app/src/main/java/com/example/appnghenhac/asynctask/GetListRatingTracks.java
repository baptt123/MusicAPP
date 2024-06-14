package com.example.appnghenhac.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.appnghenhac.model.Music;
import com.example.appnghenhac.activity.RatingActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetListRatingTracks extends AsyncTask<Void, Void, String> {
    private RatingActivity ratingActivity;

    public GetListRatingTracks(RatingActivity ratingActivity) {
        this.ratingActivity = ratingActivity;
    }

    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://v1.nocodeapi.com/thanhtan/spotify/dLRHJVlVhqyRGneg/usersTop?type=tracks&time_range=short_term")
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
            ratingActivity.getMusicNameFromAsyncTask(musicArrayList);

        }
    }


}

