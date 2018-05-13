package com.example.agatarychter.music;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import static android.media.session.PlaybackState.ACTION_PLAY;

/**
 * Created by Agata Rychter on 13.05.2018.
 */

public class MyService extends Service {
    private MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
//            String action = intent.getAction();
//            if (action.equals(ACTION_PLAY))
//                processPlayRequest();
            return START_STICKY;
        }
}
