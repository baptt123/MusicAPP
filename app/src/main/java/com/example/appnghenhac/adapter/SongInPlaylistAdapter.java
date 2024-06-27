package com.example.appnghenhac.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.Song;

import java.util.ArrayList;

public class SongInPlaylistAdapter extends ArrayAdapter<Song> {
    private Context context;
    private int resource;
    private ArrayList<Song> objects;

    public SongInPlaylistAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Song> objects) {
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
        Log.d("TAG", "getView: "+song.toString());

        TextView tv = convertView.findViewById(R.id.tv);
        tv.setText(song.getName());

//        ImageView imageView = convertView.findViewById(R.id.imvUrl);
//        Picasso.get()
//                .load(song.getUrl())
//                .placeholder(com.denzcoskun.imageslider.R.drawable.default_placeholder) // Hình ảnh hiển thị khi đang tải
//                .error(R.drawable.baseline_account_circle_24) // Hình ảnh hiển thị khi có lỗi
//                .into(imageView);

        return convertView;
    }
}
