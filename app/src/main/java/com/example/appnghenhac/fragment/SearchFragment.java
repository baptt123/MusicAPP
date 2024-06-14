package com.example.appnghenhac.fragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.appnghenhac.R;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
/*
lop de test cho chuc nang search
 */
public class SearchFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private EditText editText;
    private Button btn_play;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        editText=getView().findViewById(R.id.timkiem);
        btn_play=getView().findViewById(R.id.btnplay);
        btn_play.setOnClickListener(v -> {
         String value_edittext=   editText.getText().toString();
            try {
                mediaPlayer=new MediaPlayer();
                //xet loai am thanh dang duoc chay
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //lay nguon am thanh
                mediaPlayer.setDataSource(value_edittext);
                //khoi chạy(sau xu li them bat dong bo)
                mediaPlayer.start();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
