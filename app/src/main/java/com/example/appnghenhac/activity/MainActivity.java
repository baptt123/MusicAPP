package com.example.appnghenhac.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.appnghenhac.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //    GetArtist getArtist = new GetArtist(this);
//    GetUserTop getUserTop = new GetUserTop(this);
    private TextView textView;
    private Button btn;
    private Button btnhistory;

    public void initView() {
        btn = findViewById(R.id.chuyenhuong);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RatingActivity.class);
            startActivity(intent);
        });
        btnhistory = findViewById(R.id.chuyenhuonglichsu);
        btnhistory.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        startMedia();
//    }

//    public void startMedia(){
//        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.emcuangayhomqua);
//        mediaPlayer.start();
//    }
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

