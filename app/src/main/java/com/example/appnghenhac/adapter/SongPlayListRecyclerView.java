package com.example.appnghenhac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.Song;
import com.example.appnghenhac.service.MusicService;
import com.example.appnghenhac.service.PlayListService;
import com.example.appnghenhac.service.UserService;
import com.google.android.play.core.integrity.t;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongPlayListRecyclerView extends RecyclerView.Adapter<SongPlayListRecyclerView.SongViewHolder> {
    private ArrayList<Song> objects;
    private Context context;
    private String playListName;

    public SongPlayListRecyclerView(ArrayList<Song> objects, Context context, String playListName) {
        this.objects = objects;
        this.context = context;
        this.playListName = playListName;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_song,parent,false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = objects.get(position);

        TextView tv = holder.textView;
        tv.setText(song.getName());

        ImageView imageView = holder.imageView;
        Picasso.get()
                .load(song.getUrl().substring(1,song.getUrl().length()-1))
                .into(imageView);
        holder.button.setOnClickListener(v->{
            objects.remove(objects.get(position));
            this.notifyDataSetChanged();
            PlayListService.getInstance().updatePlayList(playListName, objects);
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public Button button;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            imageView = itemView.findViewById(R.id.imageView);
            button = itemView.findViewById(R.id.btDelete);
        }

    }
}
