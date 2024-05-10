package com.example.appnghenhac.main;

import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.appnghenhac.R;
import com.example.appnghenhac.asynctask.GetArtist;
import com.example.appnghenhac.asynctask.GetUserTop;
import com.example.appnghenhac.model.Music;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //    GetArtist getArtist = new GetArtist(this);
    GetUserTop getUserTop = new GetUserTop(this);
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getArtist.execute();
//        try {
//            FirebaseApp.initializeApp(this);
//            getUserTop.execute();
//        } catch (Exception e) {
//            Log.e("FirebaseInit", "Firebase initialization failed: " + e.getMessage());
//        }
//        addDatatoFirebase();
//        getDatafromFirebase();
//        DeleteDatafromFirebase();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startMedia();
    }

    public void startMedia(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.emcuangayhomqua);
        mediaPlayer.start();
    }
}


//    public void getDataFromAsyncTask(JsonObject jsonObject) {
//        if (jsonObject != null) {
//            ArrayList<Music> musicList = new ArrayList<>();
//            // Kiểm tra xem đối tượng JSON có thuộc tính "popularity" không
//            if (jsonObject.has("external_url")) {
//                int popularity = jsonObject.get("external_url").getAsInt();
//                Music musicItem = new Music();
//                musicItem.setPopularity(popularity);
//                musicList.add(musicItem);
//            } else {
//                Log.e("JSON", "Object does not contain 'popularity' attribute");
//            }
//            // Xử lý dữ liệu ở đây
//        }
//    public void getDatafromAsyncTask(String result) {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference dbref = firebaseDatabase.getReference("data");
//        dbref.setValue(result, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError databaseError, @NonNull @NotNull DatabaseReference databaseReference) {
//                Toast.makeText(MainActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//}

//    public void addDatatoFirebase() {
//        //tao doi tuong firebasedatabase
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        //doi tuong nay dung de truy van den realtimedatabase
//        DatabaseReference databaseReference = firebaseDatabase.getReference("artist");
//        databaseReference.setValue("Ly ly", new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError databaseError, @NonNull @NotNull DatabaseReference databaseReference) {
//                Toast.makeText(MainActivity.this, "Them du lieu thanh cong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//    public void DeleteDatafromFirebase() {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        //doi tuong nay dung de truy van den realtimedatabase
//        DatabaseReference databaseReference = firebaseDatabase.getReference("artist");
//        databaseReference.removeValue(new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError databaseError, @NonNull @NotNull DatabaseReference databaseReference) {
//                Toast.makeText(MainActivity.this,"Xoa thanh cong",Toast.LENGTH_SHORT).show();
//            }
//        });

//doi tuong lang nghe su kien lien quan den truy xuat du lieu trong realtimedatabase
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String post = dataSnapshot.getValue(String.class);
//                textView = findViewById(R.id.textdata);
//                textView.setText(post);
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
//
//            }
//        };

//lay du lieu tu firebase len UI
//    public void getDatafromFirebase() {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        //doi tuong nay dung de truy van den realtimedatabase
//        DatabaseReference databaseReference = firebaseDatabase.getReference("artist");
//        //doi tuong lang nghe su kien lien quan den truy xuat du lieu trong realtimedatabase
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String post = dataSnapshot.getValue(String.class);
//                textView=findViewById(R.id.textdata);
//                textView.setText(post);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Hien thi loi trong log
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        databaseReference.addValueEventListener(postListener);

