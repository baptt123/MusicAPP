package com.example.appnghenhac.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.MusicForNavigation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
class này dùng cho thiết lập adapter ở chức năng hiển thị danh sách phát nhạc ở navigation
 */
public class ListSongNavAdapter extends ArrayAdapter<MusicForNavigation> {
    private Activity context;
    private int resourceID;
    private ArrayList<MusicForNavigation> music_items;

    public ListSongNavAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<MusicForNavigation> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;
        this.music_items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //tao khung de chua layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        //dua id cua layout custom listview vao de tao view
        convertView = layoutInflater.inflate(resourceID, null);
        //todo
        MusicForNavigation musicForNavigation = music_items.get(position);
        ImageView imageView = convertView.findViewById(R.id.image_view_for_list_song_navigation);
        Picasso.get().load(musicForNavigation.getImg()).into(imageView);
        TextView textView = convertView.findViewById(R.id.text_view_for_list_song_navigation);
        textView.setText(musicForNavigation.getName());

        return convertView;
    }
}
