package com.example.appnghenhac.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.PlayerMusicActivity;
import com.example.appnghenhac.adapter.ListSongNavAdapter;
import com.example.appnghenhac.application.MusicNameApplication;
import com.example.appnghenhac.model.MusicForNavigation;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
 * class này dùng để hiển thị fragment danh sách phát nhạc khi click vào nút play trong navigation bottom
 */
public class ListSongNavFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO
        ListView listView = view.findViewById(R.id.list_song_navigation);
        FirebaseApp.initializeApp(getActivity());
        ArrayList<MusicForNavigation> list_music = new ArrayList<>();
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference df = fd.getReference("list");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    if (snapshot.getChildrenCount() > 0) {
                        for (DataSnapshot ds : snapshot.getChildren()) {

                            String name = ds.getKey();
                            String link_img = ds.getValue(String.class);
                            MusicForNavigation musicForNavigation = new MusicForNavigation();
                            musicForNavigation.setName(name);
                            musicForNavigation.setImg(link_img);
                            list_music.add(musicForNavigation);

                        }
                        ListSongNavAdapter listSongNavAdapter = new ListSongNavAdapter(getActivity(), R.layout.list_item_song_navigation, list_music);
                        listView.setAdapter(listSongNavAdapter);
                        listView.setOnItemClickListener((parent, view1, position, id) -> {
                            MusicForNavigation musicForNavigation = list_music.get(position);
                            String name = musicForNavigation.getName();
                            String link_img = musicForNavigation.getImg();
                            MusicNameApplication musicNameApplication = (MusicNameApplication) getActivity().getApplicationContext();
                            musicNameApplication.setSongName(name);
                            musicNameApplication.setImg(link_img);
                            Intent intent = new Intent(getActivity(), PlayerMusicActivity.class);
                            startActivity(intent);
                        });
                        Toast.makeText(getActivity(), "Đã lấy dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_song_navigation, container, false);
    }
}
