package com.example.appnghenhac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appnghenhac.R;

import com.example.appnghenhac.asynctask.AsyncTaskFavourite;
import com.example.appnghenhac.fragment.FragmentThuVien;
import com.example.appnghenhac.fragment.FragmentTrangChu;
import com.example.appnghenhac.fragment.ListSongNavFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private ImageView search_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navication);
//        toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        fragment
        Fragment fragment = new FragmentTrangChu();
        loadFragment(fragment);
//        navigation
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // TODO yeu cay chuyen doi  sang dung frament chưa hoàn thành
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.home) {
                    toolbar.setTitle("Trang Chủ");
                    selectedFragment = new FragmentTrangChu();
                    loadFragment(selectedFragment);
                    return true;
                } else if (item.getItemId() == R.id.thuVien) {
                    toolbar.setTitle("Thư viện");
                    selectedFragment = new FragmentThuVien();
                    loadFragment(selectedFragment);
                    return true;
                    //dùng asynctask xử lí việc nhúng fragment danh sách yêu thích
                } else if (item.getItemId() == R.id.Favourite) {
                    toolbar.setTitle("Danh sách yêu thích");
                    loadFragmentFavourite();
                    return true;
                } else if (item.getItemId() == R.id.AddMusic) {
                    changeIntoAddMusic();
                    return true;
                }else if(item.getItemId()==R.id.PlayMusic){
                    toolbar.setTitle("Danh sách bài hát ");
                    loadFragmentMusic();
                    return true;
                }

                return false;
            }
        });
        search_icon=findViewById(R.id.search_icon);
        search_icon.setOnClickListener( v -> {
            Intent intent=new Intent(this, SearchActivity.class);
            startActivity(intent);
        });
    }

    private void loadFragmentMusic() {
        ListSongNavFragment listSongNavFragment=new ListSongNavFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,listSongNavFragment);
        ft.commit();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadFragmentFavourite() {
        AsyncTaskFavourite asyncTaskFavourite = new AsyncTaskFavourite(this);
        asyncTaskFavourite.execute(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void changeIntoAddMusic() {
        Intent intent = new Intent(this, UploadFileActivity.class);
        startActivity(intent);
    }

    public void changeIntoSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}