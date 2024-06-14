package com.example.appnghenhac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyVieHolder> {
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

        private Context mContex;


        public MyVieHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
