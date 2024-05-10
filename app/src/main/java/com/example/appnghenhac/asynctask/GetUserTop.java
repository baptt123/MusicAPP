package com.example.appnghenhac.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import com.example.appnghenhac.main.MainActivity;
import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GetUserTop extends AsyncTask<Void, Void, String> {
    private MainActivity mcontext;

    public GetUserTop(MainActivity mcontext) {
        this.mcontext = mcontext;
    }

    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://v1.nocodeapi.com/thanhtan/spotify/dLRHJVlVhqyRGneg/usersTop?type=tracks")
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                //du lieu lay ve la dang json
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
//        if (s != null) {
//            try {
//                // Kiểm tra xem chuỗi JSON có hợp lệ hay không
//                JsonElement jsonElement = JsonParser.parseString(s);
//                if (jsonElement.isJsonObject()) {
//                    JsonObject jsonObject = jsonElement.getAsJsonObject();
//                    // Gọi hàm xử lý dữ liệu với đối tượng JsonObject
//                    mcontext.getDataFromAsyncTask(jsonObject);
//                } else {
//                    Log.e("JSON", "Not a JSON object");
//                }
//            } catch (JsonSyntaxException e) {
//                Log.e("JSON", "Invalid JSON string: " + e.getMessage());
//            }
//        }
        if(s!=null){
//            mcontext.getDatafromAsyncTask(s);
        }
    }


//        if (s != null) {
//            Toast.makeText(ratingcontext, s, Toast.LENGTH_SHORT).show();
//        } else {
//            Log.e("error", s);
//        }
}

