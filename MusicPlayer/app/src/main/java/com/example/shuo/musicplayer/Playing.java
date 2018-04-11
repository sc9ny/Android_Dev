package com.example.shuo.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Playing extends AppCompatActivity implements View.OnClickListener, ServiceConnection {



    Button pp,restart;

    String storeImage;
    //indexMem ind;
    TextView title;
    ImageView imageView;
    MusicService musicService, musicService1,musicService2,musicService3;
    MusicCompletionReceiver musicCompletionReceiver;
    Intent startMusicServiceIntent;
    boolean isInitialized = false;
    boolean isBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        imageView = (ImageView) findViewById(R.id.imageView);
        title = (TextView) findViewById(R.id.title);
        pp = (Button) findViewById(R.id.pp);
        restart = (Button) findViewById(R.id.restart);
        pp.setOnClickListener(this);
        restart.setOnClickListener(this);
        //imageView.setImageResource(R.drawable.image1);
        if (MainActivity.ind.musicName != null)
            updateName(MainActivity.ind.musicName);
        if (MainActivity.ind.imageName != null)
            setImage(MainActivity.ind.imageName);
        if (savedInstanceState != null) {
            //Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            title.setText(savedInstanceState.getString("MUSIC_PLAYING"));
            setImage(savedInstanceState.getString("picture"));
        }

        //ind = new indexMem();
        // preparing the intent object that will launch the service
        startMusicServiceIntent = new Intent(this, MusicService.class);

        // if not started we go ahead and start it
        if (!isInitialized) {
            startService(startMusicServiceIntent);
            isInitialized = true;
        }

        //also registering the broadcast receiver
        musicCompletionReceiver = new MusicCompletionReceiver(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause()");
        // unbinding from service
        // the service will have onUnbind() called after this
        // inside that method we will handle the logic of unbinding
//        if (isBound) {
//            unbindService(this);
//            isBound = false;
//        }
        //remove the broadcast receiver

        //unregisterReceiver(musicCompletionReceiver);
    }
    @Override
    protected void onResume() {
        super.onResume();

        //is service is initialized and not boud we bind to it
        if (isInitialized && !isBound) {
            bindService(startMusicServiceIntent, this, Context.BIND_AUTO_CREATE);
        }

        // registering the broadcast receiver
        registerReceiver(musicCompletionReceiver, new IntentFilter(MusicService.COMPLETE_INTENT));
    }
    public static final String INITIALIZE_STATUS = "is_initialized";
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        outState.putString("MUSIC_PLAYING", title.getText().toString());
        outState.putString("picture", storeImage);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        if (isBound)
        {
            if (view.getId() == pp.getId())
            {
                switch (musicService.getMusicStatus()) {
                    // 0 - means not playing, so we start it then label the button 'pause'
                    case 0: {
                        //
                        //musicService.pauseMusic();

                        if (MainActivity.ind.getMusic() != null)
                        {
                            //Toast.makeText(getApplicationContext(), "hihi", Toast.LENGTH_SHORT).show();
                            MainActivity.ind.getMusic().pauseMusic();


                        }

                        MainActivity.ind.setMusic(musicService);

                        musicService.startMusic();
                        musicService.playOver();
                        musicService.playOver2();
                        musicService.playOver3();
                        //serv.musicService.playOver();
                        //serv2.musicService.playOver2();
                        //serv3.getMusicService().playOver3();
                        //imageView.setImageResource(R.drawable.image1);
                        break;
                    }
                    // 1 - means playing, we pause it and then label the button 'resume'
                    case 1:
                        musicService.pauseMusic();
//                        unbindService(serv);
//                        unbindService(serv2);
//                        unbindService(serv3);
                       // serv.musicService.pauseMusic();
                        //serv2.musicService.pauseMusic();
                        //serv3.musicService.pauseMusic();
                        break;
                    case 2:
                        // 2 - means paused, we resume it and then label the button 'pause'
                        musicService.resumeMusic();
                        //serv.getMusicService().resumeMusic();
                        //serv2.getMusicService().resumeMusic();
                        //serv3.getMusicService().resumeMusic();
                        break;
                }
            }
            if (view.getId() == restart.getId())
            {
                musicService.restartMusic();
//                serv.getMusicService().restartMusic();
//                serv2.getMusicService().restartMusic();
//                serv3.getMusicService().restartMusic();
            }
        }


    }



    @Override
    public void onServiceConnected(ComponentName name, IBinder binder_to_service) {

        //Log.d(TAG, "onServiceConnected()");
       // Toast.makeText(getApplicationContext(), "Con", Toast.LENGTH_SHORT).show();
        // the biner object gets us an object that we use to extract a reference to service
        MusicService.MyBinder binder = (MusicService.MyBinder) binder_to_service;

        // extracting the service object
        musicService = binder.getService();
        //musicService1 = binder.getService();
        // it is bound so we set the boolean
        isBound = true;


    }

    public void updateName(String musicName)
    {
        title.setText(musicName);
    }
    // once disconnected we set the reference to null
    public void setImage(String imageName) {
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        storeImage = imageName;
        imageView.setImageResource(resId);
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {
        //Log.d(TAG, "onServiceDisconnected()");
        //Toast.makeText(getApplicationContext(), "DISC", Toast.LENGTH_SHORT).show();
        musicService = null;
        //musicService1 = null;
        isBound = false;
    }
}
