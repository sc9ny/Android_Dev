package com.example.shuo.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Shuo on 6/12/2017.
 */

public class MusicCompletionReceiver extends BroadcastReceiver {
    Playing playing;
// constructor takes a reference to play activity so we can communicate with it
    public MusicCompletionReceiver(Playing playing) {
        this.playing = playing;
    }

    // when we receive the udpate...
    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(playing, "Con", Toast.LENGTH_SHORT).show();
        //...extract the song name
        MainActivity.ind.musicName = intent.getStringExtra(MusicService.MUSICNAME);
        MainActivity.ind.imageName = intent.getStringExtra("IMAGENAME");
        //...pass it to play activity
        playing.updateName(MainActivity.ind.musicName);
        playing.setImage(MainActivity.ind.imageName);
        //playing.playNext();
    }
}
