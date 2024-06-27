package com.example.appnghenhac.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.appnghenhac.activity.PlayListActivity;
import com.example.appnghenhac.model.Song;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MusicAsynctask extends AsyncTask<ArrayList<String>, Void, String> {
    private PlayListActivity ac;
    String TAG = "MusicAsynctask";
    public MusicAsynctask(PlayListActivity ac) {
        this.ac = ac;
    }

    @Override
    protected String doInBackground(ArrayList<String>... strings) {
        OkHttpClient client = new OkHttpClient();
        ArrayList<String> idSongs = strings[0];
        StringBuilder idsBuilder = new StringBuilder();
        for (String id : idSongs) {
            if (idsBuilder.length() > 0) {
                idsBuilder.append(",");
            }
            idsBuilder.append(id);
        }
        Log.d(TAG, idsBuilder.toString());
        String url = "https://v1.nocodeapi.com/tam/spotify/gBsXWERIORAqClxR/tracks?ids=" + idsBuilder;
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
        if (s != null) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
            JsonArray tracks = jsonObject.getAsJsonArray("tracks");


            for (JsonElement element : tracks) {
                Song song = new Song();

                JsonObject track = element.getAsJsonObject();
                JsonObject album = track.getAsJsonObject("album");
                JsonArray images = album.getAsJsonArray("images");

                String name =track.get("name").toString();
                song.setName(name);

                String id = track.get("id").toString();
                song.setId(id);


                for (JsonElement e : images) {
                    JsonObject image = e.getAsJsonObject();
                    if (image.get("width").getAsString().equals("64")) {
                        String url = image.get("url") .toString();
                        song.setUrl(url);
                    }
                }

                Log.d(TAG+ "album", "onPostExecute: "+song.getUrl());

                ac.setSong(song);
            }
        }

    }
}
