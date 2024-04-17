package com.example.appnghenhac;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer BQAeW_jwSyaYJblKnGZhI4DrSBGiv0wnwsNpAHp1Gj_wjyXAMkgEhBcVhRm9KWeyuwfSZYugOYod7hpfOIST4Jdatjqdzpd4qbpZhhO6JFPG0XhQIm8wqfS-dP-ZLCdysES2KWN-8B4eU9qQzXOFGNKDaqIBMIoMZ_qichYMFzl8JbDGNL3RnpiYVDYDEXE02S16NQ");
            connection.setRequestMethod("GET");

            int responsecode = connection.getResponseCode();
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (responsecode == HttpURLConnection.HTTP_OK) {
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                connection.disconnect();
                return sb.toString();
            } else {
                return "Khong the ket noi API";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            Log.d("message", result);
        } else {
            Log.d("message", "Co loi xay ra khi lay du lieu");
        }
    } 

}
