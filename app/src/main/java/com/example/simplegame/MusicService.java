package com.example.simplegame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Created by ph666 on 13.05.2017.
 */

public class MusicService extends Service {
    int maxVolume = 50;
    MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Random rnd = new Random();
        int track = 0;
        switch (rnd.nextInt(2)){
            case 0:
                track = R.raw.mainmusicmb;
                break;
            case 1:
                track = R.raw.notamario;
                break;
        }

        player = MediaPlayer.create(this, track);
        player.setLooping(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.pause();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        player.start();
    }
}
