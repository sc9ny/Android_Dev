package com.example.shuo.musicplayer;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Shuo on 6/12/2017.
 */


// music player class to handle music playback
public class MusicPlayer implements MediaPlayer.OnCompletionListener {
    int i, indexOver, indexOver2,indexOver3;
    long duration;
    Handler handler,handler2,handler3;
    long wait, wait2, wait3;
    Runnable run1,run2,run3;
    //audio files
    static final String[] MUSICPATH = new String[]{
            "gotechgo","mario","tetris"
    };
    static final String[] IMAGEPATH = new String[]{
      "image1", "image2", "image3"
    };
    static final String[] OVERIMAGEPATH = new String[]{
            "image4", "image5", "image6"
    };
    //titles to the files
    static final String[] MUSICNAME = new String[]{
            "Gotechgo", "Mario","Tetris"
    };
    static final String[] overlap  = new String[]
    {
        "","clapping", "cheering", "lestgohokies"
    };

    //reference of the service
    private MusicService musicService, musicService1;

    //Android's media player
    MediaPlayer player,player1,player2,player3;

    // seek possition
    int currentPosition = 0;
    int currentPosition1 = 0;
    int currentPosition2= 0;
    int currentPosition3 = 0;

    double pro1,pro2,pro3;
    //selected song
    int musicIndex = 0;


    //id of the file in the folder
    int resID=-1;

    //0: before starts 1: playing 2: paused
    private int musicStatus = 0;

    public int getMusicStatus() {
        return musicStatus;
    }

    public String getMusicName() {
        return MUSICNAME[i];
    }
    public String getImageName()
    {
        return IMAGEPATH[i];
    }
    public String getOverImageName1()
    {
        if (indexOver != 0 )
            return OVERIMAGEPATH[indexOver-1];
        else
            return OVERIMAGEPATH[0];
    }
    public String getOverImageName2()
    {
        if (indexOver2 != 0 )
            return OVERIMAGEPATH[indexOver2-1];
        else
            return OVERIMAGEPATH[0];
    }
    public String getOverImageName3()
    {
        if (indexOver3 != 0 )
            return OVERIMAGEPATH[indexOver3-1];
        else
            return OVERIMAGEPATH[0];
    }

    //starts playing
    public void playMusic() {

        //build the media player
        //play from res/raw directly
        try{
            player = new MediaPlayer();

            int resID=musicService.getResources().getIdentifier(MUSICPATH[i], "raw", musicService.getPackageName());

            player=MediaPlayer.create(musicService,resID);
            player.start();
            duration = player.getDuration();
            musicService.onUpdateMusicName(getMusicName(), getImageName());
            player.setOnCompletionListener(this);
            musicStatus = 1;

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }


    }

    public void playOver()
    {
        wait = (int)((duration *pro1)-currentPosition);
        handler = new Handler();
        run1 = new Runnable() {
            @Override
            public void run() {
                player1 = new MediaPlayer();
                int resID = musicService.getResources().getIdentifier(overlap[indexOver],"raw",musicService.getPackageName());
                try
                {
                    player1 = MediaPlayer.create(musicService,resID);

                }
                catch (Resources.NotFoundException e)
                {
                    e.printStackTrace();
                    Log.d("ERRORROROROR", "EXCEPTION!");
                }
                //player3 = MediaPlayer.
                // create(musicService,resID);
                player1.start();
                player1.setOnCompletionListener(MusicPlayer.this);
                //player1.setOnCompletionListener();
                if (indexOver != 0 )
                    musicService.onUpdateMusicName(getMusicName(), getOverImageName1());

            }
        };
        handler.postDelayed(run1, wait);
        //player1.setOnCompletionListener(this);

    }
    public void playOver2()
    {
        wait2 = (int)((duration *pro2)-currentPosition);
        handler2 = new Handler();
        run2 = new Runnable() {
            @Override
            public void run() {
                player2 = new MediaPlayer();
                int resID = musicService.getResources().getIdentifier(overlap[indexOver2],"raw",musicService.getPackageName());
                try
                {
                    player2 = MediaPlayer.create(musicService,resID);
                }
                catch (Resources.NotFoundException e)
                {
                    e.printStackTrace();
                    //Log.d("ERRORROROROR", "EXCEPTION!");
                }
                //player3 = MediaPlayer.
                // create(musicService,resID);
                player2.start();
                player2.setOnCompletionListener(MusicPlayer.this);
                if (indexOver2 != 0)
                    musicService.onUpdateMusicName(getMusicName(), getOverImageName2());
            }
        };
        handler2.postDelayed(run2, wait2);
        //player2.setOnCompletionListener(this);
    }
    public void playOver3()
    {
        wait3 = (int)((duration *pro3)-currentPosition);
        handler3 = new Handler();
        run3 = new Runnable() {
            @Override
            public void run() {
                player3 = new MediaPlayer();
                int resID = musicService.getResources().getIdentifier(overlap[indexOver3],"raw",musicService.getPackageName());
                    try
                    {
                        player3 = MediaPlayer.create(musicService,resID);
                    }
                    catch (Resources.NotFoundException e)
                    {
                        e.printStackTrace();
                        //Log.d("ERRORROROROR", "EXCEPTION!");
                    }
                    //player3 = MediaPlayer.
                // create(musicService,resID);
                    player3.start();
                    player3.setOnCompletionListener(MusicPlayer.this);
                    if (indexOver3 != 0)
                        musicService.onUpdateMusicName(getMusicName(), getOverImageName3());
            }
        };
        handler3.postDelayed(run3, wait3);

//        try
//        {
//            wait3 = (int)(duration * pro3);
//            handler3 = new Handler();
//            handler3.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    player3 = new MediaPlayer();
//                    int resID = musicService.getResources().getIdentifier(overlap[indexOver3],"raw",musicService.getPackageName());
//                    try
//                    {
//                        player3 = MediaPlayer.create(musicService,resID);
//                    }
//                    catch (Resources.NotFoundException e)
//                    {
//                        e.printStackTrace();
//                        Log.d("ERRORROROROR", "EXCEPTION!");
//                    }
//                    //player3 = MediaPlayer.create(musicService,resID);
//                    player3.start();
//                }
//            }, wait3);
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }
    public void pauseMusic() {
        Log.d("status", "" +musicStatus);
        if (player != null && player.isPlaying()) {
            Log.d("how are you", "1");
            // pause the player
            player.pause();
            //save current position
            currentPosition = player.getCurrentPosition();
            //update status
            //musicStatus = 2;
        }
        if (player1 != null && player1.isPlaying())
        {
            Log.d("how are you", "2");

            player1.pause();
            currentPosition1 = player1.getCurrentPosition();

        }
        if (player2 != null && player2.isPlaying())
        {
            Log.d("how are you", "3");

            player2.pause();
            currentPosition2 = player2.getCurrentPosition();
        }
        if (player3!=null && player3.isPlaying())
        {
            player3.pause();
            currentPosition3 = player3.getCurrentPosition();
        }
        if (handler != null)
        {
            //player1.release();
            //player1 = null;
            handler.removeCallbacks(run1);

        }

        if (handler2 != null)
        {
            //player2.release();
            //player2 = null;
            handler2.removeCallbacks(run2);

        }

        if (handler3 != null)
        {
            //player3.release();
            //player3 = null;
            handler3.removeCallbacks(run3);

        }
            //handler3.removeCallbacks(run3);
        musicStatus =2;
    }

    public void resumeMusic() {
        Log.d("status2", "" +musicStatus);
        Log.d("resume called", "1");
        if (player != null ) {
            //reusme to the saved position
            player.seekTo(currentPosition);
            //start player
            player.start();
            //udpate status

        }



        if (player1 != null)
        {
            player1.seekTo(currentPosition1);
            player1.start();
        }
        if (player1 == null)
        {
            playOver();
        }
        if (player2 != null)
        {
            player2.seekTo(currentPosition2);
            player2.start();

        }
        if (player2 == null)
            playOver2();
        if (player3 != null)
        {
            player3.seekTo(currentPosition3);
            player3.start();
        }
        if (player3 == null)
            playOver3();
        musicStatus = 1;
    }
    public void restartMusic()
    {
        musicService.onUpdateMusicName(getMusicName(), getImageName());
        if (handler != null)
            handler.removeCallbacks(run1);
        if (handler2 != null)
            handler2.removeCallbacks(run2);
        if (handler3 != null)
            handler3.removeCallbacks(run3);
        if (player == null)
        {
            playMusic();
        }
        if (player!= null)
        {
            currentPosition = 0;
            player.seekTo(currentPosition);
            player.start();
        }
        if (player1 == null)
        {
            playOver();
            //Log.d("player1", "!!!!!!!mm");
        }
        if (player1 != null && player1.isPlaying())
        {
            currentPosition1=0;
            player1.seekTo(currentPosition1);
            player1.start();
        }
        if (player2 == null)
        {
            playOver2();
            //Log.d("player2", "!!!");
        }
        if (player2 != null && player2.isPlaying())
        {
            currentPosition2=0;
            player2.seekTo(currentPosition2);
            player2.start();
        }
        if (player3 == null)
        {
            playOver3();

        }
        if (player3 != null && player3.isPlaying())
        {
            currentPosition3=0;
            player3.seekTo(currentPosition3);
            player3.start();
        }
        musicStatus =1;
    }



    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (mediaPlayer == player)
        {
            player.release();
            player=null;
            musicStatus = 0;
            //Log.d("complete", "hihi");
            //musicService.onUpdateMusicName(getMusicName(), getImageName());
        }
        if (mediaPlayer == player1)
        {
            player1.release();
            player1=null;
            //Log.d("complete123", "hihi");
            if (player2 == null && player3 == null) {
                musicService.onUpdateMusicName(getMusicName(), getImageName());
            }

        }
        if (mediaPlayer == player2)
        {
            player2.release();
            player2=null;
            if (player3 == null && player1 == null)
            {

                musicService.onUpdateMusicName(getMusicName(), getImageName());
            }

        }
        if (mediaPlayer == player3)
        {
            player3.release();
            player3=null;
            if (player2 == null && player1 == null)
            {
                musicService.onUpdateMusicName(getMusicName(), getImageName());

            }


            //musicService.onUpdateMusicName(getMusicName(), getImageName());
        }


    }

    public MusicPlayer(MusicService musicService)
    {

        this.musicService = musicService;
        if (MainActivity.ind != null)
        {
            i = MainActivity.ind.getIndex();
            indexOver = MainActivity.ind.getIndexOver();
            indexOver2 = MainActivity.ind.getIndexOver2();
            indexOver3 = MainActivity.ind.getIndexOver3();
            pro1 = MainActivity.ind.getSeekBarPro()/100.0;
            pro2 = MainActivity.ind.getSeekBarPro2()/100.0;
            pro3 = MainActivity.ind.getSeekBarPro3()/100.0;
            Log.d("PRO", ""+pro1);
        }


    }


}
