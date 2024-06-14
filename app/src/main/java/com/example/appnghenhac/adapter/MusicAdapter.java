package com.example.appnghenhac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnghenhac.model.MusicFiles;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyVieHolder> {
    private Context mContex;
    private ArrayList<MusicFiles> mFile;
//    MusicAdapter

    @NonNull
    @NotNull
    @Override
    public MyVieHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyVieHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyVieHolder extends RecyclerView.ViewHolder{

        public MyVieHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
