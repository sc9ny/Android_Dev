package com.example.seungleechoi.presenterhelper1;


import android.content.res.Configuration;
import android.net.Uri;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements listFragment.OnFragmentInteractionListener, controlFragment.OnFragmentInteractionListener
{

    //define frament variables
    listFragment listFragment;
    controlFragment controlFragment;
    FrameLayout fragmentContainer1;
    LinearLayout land;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

    ArrayAdapter<String> listAdapter ;

    ArrayList<String> timeList = new ArrayList<String>();
    Async async;
    boolean on_off =false;
    int hour = 0;
    int min =0;
    int sec = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // you need to have two layouts for portrait and landscape *DONE!!!
        setContentView(R.layout.activity_main);
        //initialize fragments depending on the layout and lifecycle
        listFragment = new listFragment();
        controlFragment = new controlFragment();
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, timeList);
        async = new Async();
        //you will have an if statement for detecting layout (landscape or portrait)
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
        {
            land = (LinearLayout) findViewById(R.id.land);
            transaction.add(R.id.land, controlFragment);
            transaction.add(R.id.land, listFragment);
            View b = findViewById(R.id.next);
            b.setVisibility(View.INVISIBLE);
            View c = findViewById(R.id.previous);
            c.setVisibility(View.INVISIBLE);

        }
        else
        {

            fragmentContainer1 = (FrameLayout) findViewById(R.id.fragment_container1);

            transaction.replace(R.id.fragment_container1, controlFragment, "CF");

            transaction.commit();

        }
        //listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, timeList);
        //listAdapter.add("");
        listAdapter.notifyDataSetChanged();



    }
    private static String HOUR = "HOUR";
    private static String MIN = "MIN";
    private static String SEC = "SEC";
    private static String THREAD_STATUS = "THREAD STATUS";
    private static String save_list = "";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // if running
        if (async != null &&
                async.getStatus() == AsyncTask.Status.RUNNING) {
            // thread was running
            outState.putBoolean(THREAD_STATUS, true);
            on_off = false;
            // asking it to stop
            async.cancel(true);
            //async = null;
        }
        //not running
        else
        {
            outState.putBoolean(THREAD_STATUS, false);


        }
        // saving the three numbers, the default are 7 7 7
        outState.putInt(SEC,sec);
        outState.putInt(HOUR, hour);
        outState.putInt(MIN,min);
        outState.putStringArrayList(save_list, timeList);
        //outState.putString(start, ""+controlFragment.start.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean isRunning = savedInstanceState.getBoolean(THREAD_STATUS);

        sec = savedInstanceState.getInt(SEC);
        hour = savedInstanceState.getInt(HOUR);
        min = savedInstanceState.getInt(MIN);
        timeList =savedInstanceState.getStringArrayList(save_list);
        // if it ran then
        if (isRunning) {

            async = new Async();
            //let it continue from the last step
            on_off =true;

            async.execute(sec,min,hour);

        }

        // button text
        //String b_t = savedInstanceState.getString(start);
        String sec1 = String.format("%02d", sec);
        String min1 = String.format("%02d", min);
        String hour1 = String.format("%02d", hour);
        TextView time = (TextView)findViewById(R.id.time);
        //Button start1 = (Button) findViewById(R.id.start);
        time.setText(hour1 + ":" + min1 +":" + sec1);

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, timeList);
        listAdapter.notifyDataSetChanged();
        //listAdapter.add(currentTime);
        if (listFragment.lv == null)
        {
            ListView lv = (ListView)findViewById(R.id.lv);
            //
            if (lv != null)
            {
                lv.setAdapter(listAdapter);
            }
        }
        else
            listFragment.lv.setAdapter(listAdapter);

        //
        listAdapter.notifyDataSetChanged();
        //start1.setText(b_t);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (async != null && async.getStatus() == AsyncTask.Status.RUNNING) {

            async.cancel(true);
            async = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Log.d("slot_machine","onResume was called");
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onButtonClicked(int id) {
        Button aa = (Button) findViewById(R.id.start);
        TextView time = (TextView)findViewById(R.id.time);
        if (id == 0) // start button is pressed
        {
            if (async.getStatus() == AsyncTask.Status.RUNNING)
            {
                aa.setText("START");
                on_off = false;
                async.cancel(true);
            }
            else
            {
                aa.setText("STOP");
                on_off = true;
                async = new Async();
                async.execute(sec,min,hour);
            }
//


        }
        if (id == 1) // next button is pressed
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container1, listFragment, "LF");
            transaction.commit();




        }
        if (id == 2) // lap button is pressed
        {
            String sec1 = String.format("%02d", sec);
            String min2 = String.format("%02d", hour);
            String hour3 = String.format("%02d", min);
            String currentTime = (hour3 + ":" +min2 +":" +sec1);
            //ListView lv = (ListView)findViewById(R.id.lv);
            //listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, timeList);
            listAdapter.add(currentTime);

            if (listFragment.lv == null)
            {
                ListView lv = (ListView)findViewById(R.id.lv);
                //
                if (lv != null)
                {
                    lv.setAdapter(listAdapter);
                }
            }
            else
                listFragment.lv.setAdapter(listAdapter);

            listAdapter.notifyDataSetChanged();

        }
        if (id == 3) // reset button is pressed
        {
            hour = 0;
            min = 0;
            sec = 0;
            if (async != null && async.getStatus() == AsyncTask.Status.RUNNING)
            {
                on_off = false;
                async.cancel(true);
                aa.setText("Start");
            }
            else if (async != null && async.getStatus() != AsyncTask.Status.RUNNING && aa.getText() != "Start")
                aa.setText("Stop");

            String sec1 = String.format("%02d", sec);
            String min2 = String.format("%02d", hour);
            String hour3 = String.format("%02d", min);
            time.setText(hour3 + ":" + min2 +":" + sec1);
//            listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, timeList);
            listAdapter.clear();
//            if (listFragment.lv != null)
//                listFragment.lv.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();

        }
        if (id == 4) // previous button is pressed
        {
            //onBackPressed();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container1, controlFragment);
            transaction.commit();
        }





    }


    private class Async extends AsyncTask <Integer, Integer, Void>
    {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            String sec1 = String.format("%02d", values[0]);
            String min1 = String.format("%02d", values[1]);
            String hour1 = String.format("%02d", values[2]);
            TextView time = (TextView)findViewById(R.id.time);
            //controlFragment.time.setText(hour1 + ":" + min1 +":" + sec1);
            Button a = (Button) findViewById(R.id.start);

            listFragment listFragment1 = (listFragment) getSupportFragmentManager().findFragmentByTag("LF");
            //controlFragment controlFragment= (controlFragment) getSupportFragmentManager().findFragmentByTag("CF");
            //controlFragment controlFragment1 = new controlFragment();
            //listFragment lf = new listFragment();
            //controlFragment cf = new controlFragment();
            if (listFragment1 != null )
            {

            }
            else
            {
                time.setText(hour1 + ":" + min1 +":" + sec1);
                a.setText("STOP");
            }
            if (listFragment.lv == null)
            {
                ListView lv = (ListView)findViewById(R.id.lv);
                //
                if (lv != null)
                {
                    lv.setAdapter(listAdapter);
                }
            }
            else
                listFragment.lv.setAdapter(listAdapter);

            listAdapter.notifyDataSetChanged();

        }
        @Override
        protected Void doInBackground(Integer... integers) {

            while(on_off)
            {
                try {
                    Thread.sleep(1000);
                    integers[0]++;
                    if (integers[0] ==60)
                    {
                        integers[1]++;
                        integers[0] = integers[0] % 60;
                    }
                    if (integers[1] == 60)
                    {
                        integers[2]++;
                    }
                    sec = integers[0];
                    min = integers[1];
                    hour = integers[2];
                    publishProgress(sec, min, hour);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;

        }
    }
}