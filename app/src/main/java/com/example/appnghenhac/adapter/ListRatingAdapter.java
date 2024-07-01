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
import com.example.appnghenhac.model.Music;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListRatingAdapter extends ArrayAdapter<Music> {
    private Activity context;
    private int IDlayout;
    private ArrayList<Music> objects;

    public ListRatingAdapter(@NonNull Activity context, int IDlayout, @NonNull ArrayList<Music> objects) {
        super(context, IDlayout, objects);
        this.context = context;
        this.IDlayout = IDlayout;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //tao khung de chua layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        //dua id cua layout vao de tao view
        convertView = layoutInflater.inflate(IDlayout, null);
        //tao object va set up de xuat thanh listview
        Music music = objects.get(position);
        TextView textView = convertView.findViewById(R.id.music_name);
        textView.setText(music.getName());
        ImageView imageView = convertView.findViewById(R.id.music_icon);
        Picasso.get().load(music.getImg()).into(imageView);
        return convertView;
    }
}
