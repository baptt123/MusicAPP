package com.example.appnghenhac.historyplaylist;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appnghenhac.R;
import com.google.firebase.database.*;
import com.google.firebase.database.annotations.NotNull;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_playlist);
        addDatatoFirebase();
    }

    public void addDatatoFirebase() {
        //tao doi tuong firebasedatabase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //doi tuong nay dung de truy van den realtimedatabase
        DatabaseReference databaseReference = firebaseDatabase.getReference("artist");
        databaseReference.setValue("Ly ly", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError databaseError, @NonNull @org.jetbrains.annotations.NotNull DatabaseReference databaseReference) {
                Toast.makeText(HistoryActivity.this, "Them du lieu thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void DeleteDatafromFirebase() {
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
//                textView = findViewById(R.id.textdata);
//                textView.setText(post);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        };
    }

    //lay du lieu tu firebase len UI
    public void getDatafromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //doi tuong nay dung de truy van den realtimedatabase
        DatabaseReference databaseReference = firebaseDatabase.getReference("artist");
        //doi tuong lang nghe su kien lien quan den truy xuat du lieu trong realtimedatabase
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post = dataSnapshot.getValue(String.class);
//                textView = findViewById(R.id.textdata);
//                textView.setText(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Hien thi loi trong log
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);
    }
}

