package com.example.appnghenhac.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.PlayList;

import java.util.ArrayList;

public class PlayListAdapter extends ArrayAdapter<PlayList> {
    private Activity context;
    private int resource;
    private ArrayList<PlayList> objects;

    public PlayListAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<PlayList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(resource, null);
        PlayList playList = objects.get(position);
        TextView textViewPlayListName = (TextView) convertView.findViewById(R.id.textViewPlayListName);
        textViewPlayListName.setText(playList.getName());

        TextView textViewPlayListNum = (TextView) convertView.findViewById(R.id.textviewPlayListNum);
        textViewPlayListNum.setText(playList.getListSong().size() + "");

        ImageButton imageButton = convertView.findViewById(R.id.playMusic);
        imageButton.setOnClickListener(v->{
//            TODO chức năng phát nhạc
            Toast.makeText(context, "phat nhac", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }

}
