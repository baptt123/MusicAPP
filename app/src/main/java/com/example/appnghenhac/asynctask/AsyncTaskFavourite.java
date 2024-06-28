package com.example.appnghenhac.asynctask;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.NavicationActivity;
import com.example.appnghenhac.fragment.FavouriteFragment;
import com.example.appnghenhac.model.MusicForFavourite;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AsyncTaskFavourite extends AsyncTask<String, Void, String> {
    private NavicationActivity navicationActivity;

    public AsyncTaskFavourite(NavicationActivity navicationActivity) {
        this.navicationActivity = navicationActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        ArrayList<MusicForFavourite> list = new ArrayList<>();
        String params = strings[0];
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        if (uid != null) {
            if (uid.equals(params)) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference dbref = firebaseDatabase.getReference("Register User").child(uid).child("Favourite");
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String value = snapshot.getValue(String.class);
                            if (value != null) {
                                String[] values = value.split(",");
                                for (String parts : values) {
                                    MusicForFavourite musicForFavourite = new MusicForFavourite();
                                    musicForFavourite.setName(parts);
                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                    StorageReference storageReference = firebaseStorage.getReference("/upload/picture" + parts + ".jpg");
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            musicForFavourite.setLink_img(uri.toString());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Xử lý lỗi khi lấy URL tải xuống thất bại
                                            Log.d("Error: ", exception.getMessage());
                                        }
                                    });
                                    list.add(musicForFavourite);
                                }
                            }
                        } else {
                            Toast.makeText(navicationActivity, "Chưa có bài hát yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        Gson gson = new Gson();

        return gson.toJson(list);
    }

    @Override
    protected void onPostExecute(String list) {
        super.onPostExecute(list);
        Gson gson = new Gson();
        ArrayList<MusicForFavourite> list_result = gson.fromJson(list, ArrayList.class);
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_result", list_result);
        favouriteFragment.setArguments(bundle);
        FragmentManager fm = navicationActivity.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, favouriteFragment).commit();
    }
}

