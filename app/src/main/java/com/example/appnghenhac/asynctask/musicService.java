package com.example.appnghenhac.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.appnghenhac.fregment.FragmentThuVien;
import com.example.appnghenhac.model.Song;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class musicService extends AsyncTask<String, Void, String> {
    private FragmentThuVien thuvien;

    public musicService(FragmentThuVien thuvien) {
        this.thuvien = thuvien;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        String idSong = strings[0];
        String url = "https://v1.nocodeapi.com/tam/spotify/gBsXWERIORAqClxR/tracks?ids=" + idSong;
        Request request = new Request
                .Builder()
                .url(url)
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Song song = new Song();
        if (s != null) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
            JsonArray tracks = jsonObject.getAsJsonArray("tracks");


            for (JsonElement element : tracks) {
                JsonObject track = element.getAsJsonObject();
                JsonArray images = track.getAsJsonObject("album").getAsJsonArray("images");
                String name = track.getAsJsonObject("album").get("name").toString();
                song.setName(name);


                for (JsonElement e : images) {
                    JsonObject image = e.getAsJsonObject();
                    if (image.get("width").getAsString().equals("64")) {
                        String url = image.get("url") + "";
                        song.setUrl(url);
                    }
                }
                Log.d("TAM", song.toString());
            }
            thuvien.setSong(song);
        }

    }
}
