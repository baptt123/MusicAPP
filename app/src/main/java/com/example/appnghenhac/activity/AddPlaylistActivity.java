package com.example.appnghenhac.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.SongInAddPlaylistAdapter;
import com.example.appnghenhac.fragment.FragmentThuVien;
import com.example.appnghenhac.model.Song;
import com.example.appnghenhac.model.user;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddPlaylistActivity extends AppCompatActivity {

    private String TAG = "AddPlaylistActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
//        toolbar
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        // play list name
        EditText editText = findViewById(R.id.editTextText);

//        arraylist resturn
        Map<String,Object> listSong = new HashMap<>();
        ArrayList<String> elementClickeds = new ArrayList<>();

//      listview
        RecyclerView recyclerView = findViewById(R.id.recycler);
// danh sach cac bai hat
//        TODO lay tu firebase mot danh sach cac bai nhac
        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song("3fw9v7CztM2mqu1jCtbg9f","chung ta cua hien tai", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("211PBKJlAG1CxXUEjN5nqq","chung ta cua hien tai 2", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("513yY4zlOPYCAnqH614sl1","chung ta cua hien tai 3", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("3fw9v7CztM2mqu1jCtbg9f","chung ta cua hien tai", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("211PBKJlAG1CxXUEjN5nqq","chung ta cua hien tai 2", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("513yY4zlOPYCAnqH614sl1","chung ta cua hien tai 3", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("3fw9v7CztM2mqu1jCtbg9f","chung ta cua hien tai", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("211PBKJlAG1CxXUEjN5nqq","chung ta cua hien tai 2", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));
        songs.add(new Song("513yY4zlOPYCAnqH614sl1","chung ta cua hien tai 3", "https://i.scdn.co/image/ab67616d00001e02bc146f67374ea7e19c5d0c80"));

        SongInAddPlaylistAdapter songAdapter = new SongInAddPlaylistAdapter(this, songs);
        recyclerView.setAdapter(songAdapter);

//        RecyclerView scroll vertical
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);



//      buton save
        Button button = findViewById(R.id.buttonDone);
        button.setOnClickListener(v->{
                if (editText.getText().toString().isEmpty() || editText.getText() == null) {
                    Log.d(TAG, "onCreate: "+editText.getText() +","+songAdapter.getElementClicked().toString());
                    Toast.makeText(this, "chua nhap ten play list ko the tao", Toast.LENGTH_SHORT).show();
                }else{
                    listSong.put(editText.getText().toString(), "soo1,s002,s003");
//                ghi du lieu
                    FirebaseDatabase data = FirebaseDatabase.getInstance();
                    DatabaseReference reference = data.getReference();
//                  TODO tai khoan user o dau
                    user u = new user("tam2", new Date("14/04/2003"), "name", "013131313", null);
                    reference.child("user").child(u.getFullName()).child("playList").updateChildren(listSong);

                    Intent returnItent = new Intent();
                    setResult(Activity.RESULT_OK, returnItent);
                    finish();
                }
        });
    }

    public void recreateFragment() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(currentFragment);
            fragmentTransaction.commitNow();
        }
        Fragment newFragment = new FragmentThuVien();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, newFragment);
        fragmentTransaction.commit();
    }
}