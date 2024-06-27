package com.example.appnghenhac.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongInAddPlaylistAdapter extends RecyclerView.Adapter<SongInAddPlaylistAdapter.SongViewHolder> {
    private static final String TAG = "SongInAddPlaylistAdapter";
    private List<Song> Songs;
    private Context mContext;
    private ArrayList<String> elementClicked;
    private LayoutInflater mLayoutInflater;

    public SongInAddPlaylistAdapter(Context mContext,List<Song> songs ) {
        this.Songs = songs;
        this.mContext = mContext;
        elementClicked = new ArrayList<>();
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.list_item_add_song, parent, false);
        return new SongViewHolder(itemView);
    }

    public ArrayList<String> getElementClicked() {
        return elementClicked;
    }

    public void setElementClicked(ArrayList<String> elementClicked) {
        this.elementClicked = elementClicked;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        // Get song in mSong via position
        Song song = Songs.get(position);

        //bind data to viewholder
        holder.getTextView().setText(song.getName());
        if (song.getUrl() != null) {
            Picasso.get()
                    .load(song.getUrl())
                    .into(holder.getImageView());
        }
        holder.getLinearLayout().setOnClickListener(v -> {
            if (!elementClicked.contains(song.getId())) {
                    holder.getCheckBox().setChecked(true);
                    elementClicked.add(song.getId());
                }else{
                    holder.getCheckBox().setChecked(false);
                    elementClicked.remove(song.getId());
                }
        });
        holder.getCheckBox().setOnClickListener(v -> {
            if (!elementClicked.contains(song.getId())) {
                holder.getCheckBox().setChecked(true);
                elementClicked.add(song.getId());
            }else{
                holder.getCheckBox().setChecked(false);
                elementClicked.remove(song.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return Songs.size();
    }



    class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private CheckBox checkBox;
        private LinearLayout linearLayout;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvSongName);
            imageView = (android.widget.ImageView) itemView.findViewById(R.id.imvUrl);
            checkBox =  itemView.findViewById(R.id.checkbox);
            linearLayout = itemView.findViewById(R.id.linearLayout);

        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }
    }


}
