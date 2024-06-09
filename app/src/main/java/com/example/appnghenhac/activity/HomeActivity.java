package com.example.appnghenhac.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.*;
import android.widget.Button;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.example.appnghenhac.R;
import com.example.appnghenhac.fragment.SearchFragment;

public class  HomeActivity extends AppCompatActivity {
    private WebView webView;
    private Button btn_dowload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
//        addFragment();
        addSpotify();
        changeIntent();
    }

    //    public void addFragment(){
//        SearchFragment searchFragment=new SearchFragment();
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.fragment_playmusic,searchFragment).commit();
//
//    }
    //nhung spotify html vao android
    @SuppressLint("SetJavaScriptEnabled")
    public void addSpotify() {
        webView = findViewById(R.id.webviewspotify);
        if (webView != null) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            webView.setWebViewClient(new WebViewClient() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    Log.e("HomeActivity", "Error loading page: " + error.getDescription());
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    Log.i("HomeActivity", "Page loaded: " + url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url == null || url.startsWith("http://") || url.startsWith("https://")) return false;
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        view.getContext().startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        return true;
                    }
                }
            });

            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    runOnUiThread(new Runnable() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void run() {
                            request.grant(request.getResources());
                        }
                    });
                }
            });

            String html = "<html>\n" +
                    "<body>\n" +
                    "<h2>Hello World!</h2>\n" +
                    "<iframe style=\"border-radius:12px\" src=\"https://open.spotify.com/embed/track/4inMQ83GNpQ2OHDredH5hW?utm_source=generator&theme=0\" width=\"100%\" height=\"352\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\" loading=\"lazy\"></iframe>\n" +
                    "</body>\n" +
                    "</html>";
            webView.loadDataWithBaseURL("http://localhost:8080/TestSpotify_war/index.jsp", html, "text/html", "UTF-8", null);
        } else {
            Log.e("HomeActivity", "WebView is null");
        }
    }

    public void changeIntent() {
        btn_dowload = findViewById(R.id.chuyenhuongdowload);
        btn_dowload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://spotifydown.com/vi"));
            startActivity(intent);
        });
    }
}













