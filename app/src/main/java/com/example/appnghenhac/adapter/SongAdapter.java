package com.example.appnghenhac.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.PlayList;
import com.example.appnghenhac.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {
    private Context context;
    private int resource;
    private ArrayList<Song> objects;

    public SongAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Song> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, null);
        Song song = objects.get(position);

        TextView tv = convertView.findViewById(R.id.tv);
        tv.setText(song.getName());

        return convertView;
    }
}
