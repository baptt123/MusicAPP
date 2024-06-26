package com.example.appnghenhac.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.TestPlayMusicActivity;
import com.example.appnghenhac.adapter.ListSearchAdapter;
import com.example.appnghenhac.model.MusicForSearch;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.fragment_search_list);
        ArrayList<MusicForSearch> list = (ArrayList<MusicForSearch>) getArguments().getSerializable("list");
        ListSearchAdapter listSearchAdapter = new ListSearchAdapter(getActivity(), R.layout.listview_search_item, list);
        listView.setAdapter(listSearchAdapter);
        listView.setOnItemClickListener((parent, viewparent, position, id) -> {
            //lấy ra phần tử trong list view
            MusicForSearch musicForSearch = (MusicForSearch) parent.getItemAtPosition(position);
            //tạo intent để chuyển hướng sang trang phát nhạc
            Intent intent = new Intent(getActivity(), TestPlayMusicActivity.class);
            String song_name = musicForSearch.getName();
            Bundle bundle = new Bundle();
            bundle.putString("name_song", song_name);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    public ArrayList<MusicForSearch> getListMusic(ArrayList<MusicForSearch> result) {
        return result;
    }


}
