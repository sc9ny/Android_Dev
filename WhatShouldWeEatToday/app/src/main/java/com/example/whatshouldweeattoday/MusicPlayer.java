package com.example.whatshouldweeattoday;

/**
 * Created by seungleechoi on 12/15/17.
 */
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
public class MusicPlayer implements  MediaPlayer.OnCompletionListener{
    static final String MUSIC = "marble";
    private int musicStatus = 0;
    private MusicService musicService;
    MediaPlayer player;

    public MusicPlayer(MusicService musicService)
    {
        this.musicService = musicService;

    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playMusic();
    }

    public int getMusicStatus()
    {
        return musicStatus;
    }

    public void playMusic()
    {
        try
        {
            if (player == null) {
                player = new MediaPlayer();
                int resID = musicService.getResources().getIdentifier(MUSIC, "raw", musicService.getPackageName());
                player = MediaPlayer.create(musicService, resID);
                player.start();
                player.setOnCompletionListener(this);
                musicStatus = 1;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void stopMusic() {
        if (player != null) {
            player.release();
        }
        player = null;
        musicStatus = 0;
    }

}
