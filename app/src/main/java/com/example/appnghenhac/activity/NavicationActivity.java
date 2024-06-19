package com.example.appnghenhac.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appnghenhac.R;
import com.example.appnghenhac.fregment.fragmentThuVien;
import com.example.appnghenhac.fregment.fragment_trangChu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navication);
//        toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        fragment
        Fragment fragment = new fragment_trangChu();
        loadFragment(fragment);

//        navigation
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // TODO yeu cay chuyen doi  sang dung frament chưa hoàn thành
                Fragment selectedFragment = null;
                if(item.getItemId()==R.id.home){
                    toolbar.setTitle("Trang Chủ");
                    selectedFragment = new fragment_trangChu();
                    loadFragment(selectedFragment);
                    return true;
                } else if (item.getItemId()==R.id.thuVien) {
                    toolbar.setTitle("Thư viện");
                    selectedFragment = new fragmentThuVien();
                    loadFragment(selectedFragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}