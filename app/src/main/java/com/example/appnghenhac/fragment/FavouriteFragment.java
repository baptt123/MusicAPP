package com.example.appnghenhac.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.ListFavouriteAdapter;
import com.example.appnghenhac.model.MusicForFavourite;
import com.example.appnghenhac.model.MusicForSearch;

import java.util.ArrayList;
/*
Fragment dùng hiển thị dữ liệu của bài hát yêu thích
 */
public class FavouriteFragment extends Fragment {
    private ListView listView_favourite;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView_favourite=view.findViewById(R.id.listView_favourite);
        ArrayList<MusicForFavourite> list = (ArrayList<MusicForFavourite>) getArguments().getSerializable("list_result");
        ListFavouriteAdapter adapter = new ListFavouriteAdapter(getActivity(),R.layout.listview_item_favourite,list);
        listView_favourite.setAdapter(adapter);
        listView_favourite.setOnItemClickListener((parent, view1, position, id) -> {
            MusicForFavourite musicForFavourite=(MusicForFavourite) parent.getItemAtPosition(position);
        });
    }

}
