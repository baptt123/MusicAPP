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
import com.example.appnghenhac.model.MusicForFavourite;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListFavouriteAdapter extends ArrayAdapter<MusicForFavourite> {
    private Activity context;
    private int resource;
    private ArrayList<MusicForFavourite> list;

    public ListFavouriteAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<MusicForFavourite> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //tao khung de chua layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        //dua id cua layout custom listview vao de tao view
        convertView = layoutInflater.inflate(resource, null);
        //todo
        MusicForFavourite musicForFavourite = list.get(position);
        ImageView imageView = convertView.findViewById(R.id.img_view_item_favourite);
        Picasso.get().load(musicForFavourite.getLink_img()).into(imageView);
        TextView textView = convertView.findViewById(R.id.txt_view_item_favourite);
        textView.setText(musicForFavourite.getName());

        return convertView;
    }
}
