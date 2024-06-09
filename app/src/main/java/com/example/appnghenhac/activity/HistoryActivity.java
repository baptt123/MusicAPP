package com.example.appnghenhac.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.HistoryAdapter;
import com.example.appnghenhac.asyncfirebase.DataLoadedCallback;
import com.example.appnghenhac.model.Music;
import com.google.firebase.database.*;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_playlist);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Lịch Sử Nghe Nhạc");
//        actionBar.setDisplayHomeAsUpEnabled(true);


//        addDatatoFirebase();
//        FirebaseApp.initializeApp(this);
        setListView();




    }


    //    public void addDatatoFirebase() {
//        //tao doi tuong firebasedatabase
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        //doi tuong nay dung de truy van den realtimedatabase
//        DatabaseReference databaseReference = firebaseDatabase.getReference("click");
//        databaseReference.setValue("1", new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError databaseError, @NonNull @org.jetbrains.annotations.NotNull DatabaseReference databaseReference) {
//                Toast.makeText(HistoryActivity.this, "Them du lieu thanh cong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }



    public void DeleteDatafromFirebase(ArrayList<Music> list) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //doi tuong nay dung de truy van den realtimedatabase
        DatabaseReference databaseReference = firebaseDatabase.getReference("artist");
        databaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError databaseError, @NonNull @NotNull DatabaseReference databaseReference) {
                Toast.makeText(HistoryActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

//doi tuong lang nghe su kien lien quan den truy xuat du lieu trong realtimedatabase
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post = dataSnapshot.getValue(String.class);
                Music music = new Music();
                music.setHistory(Integer.parseInt(post));
                list.add(music);
//                textView = findViewById(R.id.textdata);
//                textView.setText(post);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        };
    }

    public void setListView() {
        getDatafromFirebase(new DataLoadedCallback() {
            @Override
            public void onDataLoaded(ArrayList<Music> arrayList) {
                ListView listView = findViewById(R.id.list_history);
                HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this, R.layout.sub_list_history, arrayList);
                listView.setAdapter(historyAdapter);
                Log.d("setListView", "Adapter set with " + arrayList.size() + " items.");
            }
        });
    }

    public void getDatafromFirebase(DataLoadedCallback callback) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("click");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Music> list = new ArrayList<>();
                String post = dataSnapshot.getValue(String.class);
                if (post != null) {
                    try {
                        Music music = new Music();
                        music.setHistory(Integer.parseInt(post));
                        list.add(music);
                        Log.d("getDatafromFirebase", "Added music with history: " + post);
                    } catch (NumberFormatException e) {
                        Log.e("getDatafromFirebase", "Error parsing history: " + post, e);
                    }
                } else {
                    Log.w("getDatafromFirebase", "Null post encountered.");
                }
                callback.onDataLoaded(list);
                Log.d("getDatafromFirebase", "Data loaded with " + list.size() + " items.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getDatafromFirebase", "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);
    }

}

