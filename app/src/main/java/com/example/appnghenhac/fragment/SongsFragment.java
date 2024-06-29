package com.example.appnghenhac.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.MainActivity;
import com.example.appnghenhac.activity.PlayerMusicActivity;
import com.example.appnghenhac.adapter.MusicAdapter;
import com.example.appnghenhac.model.MusicFiles;
import org.jetbrains.annotations.NotNull;

//import static com.example.appnghenhac.R.id.recyclerView;
import java.util.ArrayList;

import static com.example.appnghenhac.R.id.recyclerView;
import static com.example.appnghenhac.activity.MainActivity.musicFiles;


public class SongsFragment extends Fragment {
    RecyclerView recyclerView;
    MusicAdapter musicAdapter;
    ArrayList<MusicFiles> musicFiles;

        public SongsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        Khởi tạo danh sách bài hát
        if(musicFiles == null || musicFiles.isEmpty()){
            addSong();
        }
//        !(musicFiles.size() <1 )
        if(musicFiles != null && !musicFiles.isEmpty()){
            musicAdapter = new MusicAdapter(getContext(), musicFiles);
            recyclerView.setAdapter(musicAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), recyclerView.VERTICAL, false));

            musicAdapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getContext(), PlayerMusicActivity.class);
                    intent.putExtra("Song_position",position);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
    private void addSong() {
        musicFiles = new ArrayList<>();
        musicFiles.add(new MusicFiles(R.raw.emcuangayhomqua,"Em của ngày hôm qua", "Sơn Tùng Mtp",R.drawable.sontung));
        musicFiles.add(new MusicFiles(R.raw.chayngaydi,"Chạy ngay đi", "Sơn Tùng Mtp",R.drawable.chayngaydi));
    }
}
