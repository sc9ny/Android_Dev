package com.example.shuo.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by seungleechoi on 10/25/17.
 */

public class service1 implements ServiceConnection {

    Intent startMusicServiceIntent;
    boolean isInitialized = false;
    boolean isBound = false;
    MusicService musicService;
    Playing p ;
    public service1(Playing playing)
    {
        p =playing;
        startMusicServiceIntent = new Intent(playing, MusicService.class);
        //Toast.makeText(p, "ABC", Toast.LENGTH_SHORT).show();
        if (!isInitialized) {
            playing.startService(startMusicServiceIntent);
            isInitialized = true;
        }
        if (isInitialized && !isBound) {
            playing.bindService(startMusicServiceIntent, this, Context.BIND_AUTO_CREATE);
        }

    }
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder binder_to_service) {

        //Toast.makeText(p, "CCC", Toast.LENGTH_SHORT).show();
        MusicService.MyBinder binder = (MusicService.MyBinder) binder_to_service;

        // extracting the service object
        musicService = binder.getService();
        //musicService1 = binder.getService();
        // it is bound so we set the boolean
        isBound = true;
        if ( musicService != null)
        {
            //Toast.makeText(p, "GGGG", Toast.LENGTH_SHORT).show();

        }
    }
    public MusicService getMusicService()
    {

        return musicService;
    }
    @Override
    public void onServiceDisconnected(ComponentName componentName) {

        musicService = null;
        //musicService1 = null;
        isBound = false;
    }

}
