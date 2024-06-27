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
import com.example.appnghenhac.model.MusicForSearch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListSearchAdapter extends ArrayAdapter<MusicForSearch> {
    private Activity context;
    private int IDResource;
    private ArrayList<MusicForSearch> objects;

    public ListSearchAdapter(@NonNull Activity context, int IDresource, @NonNull ArrayList<MusicForSearch> objects) {
        super(context, IDresource, objects);
        this.context = context;
        this.IDResource = IDresource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //tao khung de chua layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        //dua id cua layout vao de tao view
        convertView = layoutInflater.inflate(IDResource, null);
        //tao object va set up de xuat thanh listview
        MusicForSearch music = objects.get(position);
        ImageView imageView = convertView.findViewById(R.id.item_search_image_song);
        TextView textView = convertView.findViewById(R.id.textview_name_song);
        textView.setText(music.getName());
        Picasso.get().load(music.getImg()).into(imageView);
        return convertView;
    }
}
