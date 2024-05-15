package com.example.appnghenhac.home;

import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.example.appnghenhac.R;
import com.example.appnghenhac.fragment.SearchFragment;

public class HomeActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
//        addFragment();
        addSpotify();
    }

    //    public void addFragment(){
//        SearchFragment searchFragment=new SearchFragment();
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.fragment_playmusic,searchFragment).commit();
//
//    }
    //nhung spotify html vao android
    public void addSpotify() {
        webView = findViewById(R.id.webviewspotify);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://open.spotify.com/embed/track/2vYaldhZyuRk6me6yowg8e?utm_source=generator&theme=0");
    }
}
