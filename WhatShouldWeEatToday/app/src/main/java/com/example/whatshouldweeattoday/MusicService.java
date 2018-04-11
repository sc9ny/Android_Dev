package com.example.whatshouldweeattoday;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by seungleechoi on 12/15/17.
 */

public class MusicService extends Service {
    MusicPlayer musicPlayer;
    public static final String COMPLETE_INTENT = "complete intent";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ONBIND", "ONBIND CALLED");
        return iBinder;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        musicPlayer = new MusicPlayer(this);
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
    @Override
    public boolean onUnbind(Intent intent)
    {
        stopSelf();
        return super.onUnbind(intent);
    }
    public void startMusic()
    {
        musicPlayer.playMusic();
    }

    public void stopMusic() {
        musicPlayer.stopMusic();
        stopSelf();
    }

    public int getMusicStatus() {
        return musicPlayer.getMusicStatus();
    }

    private final IBinder iBinder = new MyBinder();

    public class MyBinder extends Binder{
        MusicService getService()
        {
            return MusicService.this;
        }
    }
}
