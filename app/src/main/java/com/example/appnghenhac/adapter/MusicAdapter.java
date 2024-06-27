package com.example.appnghenhac.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnghenhac.R;
import com.example.appnghenhac.activity.PlayerMusicActivity;
import com.example.appnghenhac.model.MusicFiles;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private Context mContex;
    private ArrayList<MusicFiles> songs;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MusicAdapter(Context mContex, ArrayList<MusicFiles> songs) {
        this.songs =songs;
        this.mContex = mContex;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContex).inflate(com.example.appnghenhac.R.layout.fragment_songs, parent, false);
        return new MyViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        MusicFiles song = songs.get(position);
        holder.songName.setText(song.getTitle());
        holder.artistName.setText(song.getArtist());
        holder.coverArt.setImageResource(song.getCoverArt());

        // Xử lý sự kiện click vào item thì hiển thị activity của phát nhạc
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang PlayerMusicActivity
                Intent intent = new Intent(mContex, PlayerMusicActivity.class);

                // Đưa dữ liệu của bài hát được chọn vào Intent
                intent.putExtra("song_title", song.getTitle());
                intent.putExtra("song_artist", song.getArtist());
                intent.putExtra("song_path", song.getPath()); // hoặc bất kỳ thông tin nào khác bạn cần truyền

                // Chuyển sang PlayerMusicActivity bằng Context của Adapter
                mContex.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView songName, artistName;
        ImageView coverArt;
        public MyViewHolder(@NonNull @NotNull View itemView, OnItemClickListener listener) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name);
            artistName = itemView.findViewById(R.id.song_arist);
            coverArt = itemView.findViewById(R.id.cover_art);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MusicAdapter.this.listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            MusicAdapter.this.listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}








//    @NotNull
//    @Override
//    public MyVieHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
////        View view = LayoutInflatery.from(mContex).inf
//        View view = LayoutInflater.from(mContex).inflate(com.example.appnghenhac.R.layout.music_items,parent, false);
//        return new MyVieHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull @NotNull MyVieHolder holder, int position) {
//        holder.file_name.setText(mFile.get(position).getTitle());
////        byte[] image = getAlbumArt(mFile.get(position).getPath());
////        if(image != null){
////
////        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mFile.size();
//    }
//
//    public class MyVieHolder extends RecyclerView.ViewHolder{
//        TextView file_name;
//        ImageView album_art;
//        public MyVieHolder(@NotNull View itemView) {
//            super(itemView);
////            file_name = itemView.findViewById(R.id.music_file_name);
//            file_name = itemView.findViewById(com.example.appnghenhac.R.id.music_file_name);
//            album_art = itemView.findViewById(com.example.appnghenhac.R.id.music_img);
//        }
//    }
//    private  byte[] getAlbumArt(Uri uri) throws IOException {
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        retriever.setDataSource(uri.toString());
//        byte[]art = retriever.getEmbeddedPicture();
//        retriever.release();
//        return  art;
//    }
//}
