package com.example.shuo.musicplayer;


import android.content.Intent;

import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements
        View.OnClickListener,SeekBar.OnSeekBarChangeListener {

    //private final static String TAG = "tutorial_4";

    // play/pause button
    Button play,next,prev;

    static Playing playing;

    SeekBar seekBar, seekBar2,seekBar3;
    // status & audio file name
    TextView music;

    Spinner spinner, spinner2,spinner3, spinner4;


    static indexMem ind;
    //static MusicPlayer musicPlayer;


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //keys for retrieving info on restore
    public static final String INITIALIZE_STATUS = "is_initialized";
    public static final String MUSIC_PLAYING = "is_music_playing";

    // saving state of the mplayer and service
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("key", ind);
        //outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        //outState.putString(MUSIC_PLAYING, music.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar2=(SeekBar)findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3=(SeekBar)findViewById(R.id.seekBar3);
        seekBar3.setOnSeekBarChangeListener(this);
        //Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT).show();
        playing = new Playing();
        ind = new indexMem();
        //setting up the views
        play = (Button) findViewById(R.id.play);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 =(Spinner) findViewById(R.id.spinner3);
        spinner4 =(Spinner) findViewById(R.id.spinner4);

        spinner4.setOnItemSelectedListener(new CustomOnItemSelectedListener3());
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener1());
        spinner3.setOnItemSelectedListener(new CustomOnItemSelectedListener2());

        //setting up the buttons with the callback
        play.setOnClickListener(this);
        //prev.setOnClickListener(this);
        //next.setOnClickListener(this);
        populateSpinner();
        populateSpinner234();
        //restoring info for the boolean and the 'song' label
        if (savedInstanceState != null) {
            //isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            //music.setText(savedInstanceState.getString(MUSIC_PLAYING));
            ind = savedInstanceState.getParcelable("key");
        }
//
//
//        // preparing the intent object that will launch the service
//        startMusicServiceIntent = new Intent(this, MusicService.class);
//
//        // if not started we go ahead and start it
//        if (!isInitialized) {
//            startService(startMusicServiceIntent);
//            isInitialized = true;
//        }
//
//        //also registering the broadcast receiver
//        musicCompletionReceiver = new MusicCompletionReceiver(this);
    }


    public void populateSpinner()
    {

        List<String> list = new ArrayList<String>();
        list.add("Go Tech Go");
        list.add("Mario");
        list.add("Tetris");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }
    public void populateSpinner234()
    {
        List<String> list = new ArrayList<String>();
        list.add("");
        list.add("Clapping");
        list.add("Cheering");
        list.add("Go Hokies!");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner3.setAdapter(dataAdapter);
        spinner4.setAdapter(dataAdapter);
    }
    //handlign the button clikc
    @Override
    public void onClick(View view) {


            if(view.equals(play)){
                Intent intent = new Intent(getApplicationContext(), Playing.class);
                startActivity(intent);


        }


    }

//

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (seekBar == this.seekBar) {
            ind.setSeekBarPro(progress);
            //ind.seekBarPro = progress;
            //Toast.makeText(getApplicationContext(),"seekbar progress: "+seekBarPro, Toast.LENGTH_SHORT).show();

        }
        if (seekBar == seekBar2) {
            ind.setSeekBarPro2(progress);
            //ind.seekBarPro2 = progress;
            //Toast.makeText(getApplicationContext(),"seekbar progress2: "+seekBarPro2, Toast.LENGTH_SHORT).show();

        }
        if (seekBar == seekBar3) {
            ind.setSeekBarPro3(progress);
            //ind.seekBarPro3 = progress;
            //Toast.makeText(getApplicationContext(), "seekbar progress3: " + seekBarPro3, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    //this is what happens when connected to server
    public class CustomOnItemSelectedListener1 implements AdapterView.OnItemSelectedListener {



        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            ind.setIndexOver(pos);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
    public class CustomOnItemSelectedListener2 implements AdapterView.OnItemSelectedListener {



        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

            ind.setIndexOver2(pos);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
    public class CustomOnItemSelectedListener3 implements AdapterView.OnItemSelectedListener {



        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            ind.setIndexOver3(pos);
//
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {



        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

            ind.setIndex(pos);
//
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }


}
