package com.example.appnghenhac.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appnghenhac.model.Music;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<Music> {
    private Activity context;
    private int IDlayout;
    private ArrayList<Music> objects;

    public HistoryAdapter(@NonNull Activity context, int IDlayout, @NonNull ArrayList<Music> objects) {
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
        convertView = layoutInflater.inflate(IDlayout,null);
        //tao object va set up de xuat thanh listview
        return convertView;
    }
}
