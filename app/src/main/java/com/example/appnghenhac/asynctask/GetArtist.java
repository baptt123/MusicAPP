package com.example.appnghenhac.asynctask;
import android.widget.Toast;
import com.example.appnghenhac.activity.MainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import okhttp3.Request;
import okhttp3.Response;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.OkHttpClient;

import java.io.IOException;


public class GetArtist extends AsyncTask<Void, Void, String> {
    private MainActivity mcontext;

    public GetArtist(MainActivity mcontext) {
        this.mcontext = mcontext;
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://v1.nocodeapi.com/thanhtan/spotify/dLRHJVlVhqyRGneg/usersTop?type=tracks&time_range=long_term")
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                //du lieu lay ve la dang json
               String json=response.body().string();
                Gson gson=new Gson();
                JsonArray jsonObject= JsonParser.parseString(json).getAsJsonArray();

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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            Log.d("message", result);
            Toast.makeText(mcontext, result, Toast.LENGTH_SHORT).show();
        } else {
            Log.d("message", "Co loi xay ra khi lay du lieu");
        }
    }

    }






