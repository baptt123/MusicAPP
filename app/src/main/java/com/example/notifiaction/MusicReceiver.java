package com.example.notifiaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MusicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case "Play":
                Toast.makeText(context, "Playing", Toast.LENGTH_SHORT).show();
                break;
            case "Pause":
                Toast.makeText(context, "Paused", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
