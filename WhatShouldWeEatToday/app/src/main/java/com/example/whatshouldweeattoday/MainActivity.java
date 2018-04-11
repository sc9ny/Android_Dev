package com.example.whatshouldweeattoday;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    TextView place;
    Button list;
    Button random;
    Button choose;

    RandomPick randomPick;

    int currentStep = 20;

    SpinAsyncTask spinAsyncTask;

    MusicService musicService;
    Intent startMusicServiceIntent;
    boolean isInitialized = false;
    boolean isBound = false;
    MusicService.MyBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        place = (TextView) findViewById(R.id.place);
        random = (Button) findViewById(R.id.random);
        choose = (Button) findViewById(R.id.choose);
        list = (Button) findViewById(R.id.list);

        random.setOnClickListener(this);
        choose.setOnClickListener(this);
        list.setOnClickListener(this);

        randomPick = new RandomPick((int) System.currentTimeMillis());

        spinAsyncTask = new SpinAsyncTask();

        startMusicServiceIntent = new Intent(this, MusicService.class);

        if (!isInitialized)
        {
            startService(startMusicServiceIntent);
            isInitialized = true;
        }

        if (savedInstanceState != null) {
            if (musicService != null && musicService.getMusicStatus() == 1) {
                stopService(startMusicServiceIntent);
                musicService.stopMusic();
            }
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
        }
    }

    private static final String THREAD_STATUS = "thread status";
    private static final String THREAD_STEP = "thread step";
    private static final String NUMBER = "number";

    public static final String INITIALIZE_STATUS = "initialization status";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        super.onSaveInstanceState(outState);
        Log.d("random_pick","onSaveInstanceState was called");

        if (spinAsyncTask != null &&
                spinAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            outState.putBoolean(THREAD_STATUS, true);
            outState.putInt(THREAD_STEP, currentStep);
            currentStep = 0;
            spinAsyncTask.cancel(true);

        }
        //not running
        else {
            outState.putBoolean(THREAD_STATUS, false);
        }

        // saving the number, the default is 0
        outState.putInt(NUMBER, randomPick.getNumber());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("random_pick","onrestoreInstanceState was called");
        boolean isRunning = savedInstanceState.getBoolean(THREAD_STATUS);

        if (isRunning) {

            // restore steps
            currentStep = savedInstanceState.getInt(THREAD_STEP);
            //create the async task
            spinAsyncTask = new SpinAsyncTask();
            //let it continue from the last step
            spinAsyncTask.execute(currentStep);
        }
        randomPick = new RandomPick((int) System.currentTimeMillis());
        //restoring the last numbers
        randomPick.setNumber(savedInstanceState.getInt(NUMBER));
        //getting numbers
        place.setText(randomPick.check(randomPick.getNumber()));
        choose.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("random_pick","OnDestroyWasCalled");
        if (spinAsyncTask != null && spinAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            currentStep = 0;
            spinAsyncTask.cancel(true);
            spinAsyncTask = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isInitialized && !isBound) {
            Log.d("BINDED", "BINDED");
            bindService(startMusicServiceIntent, this, Context.BIND_AUTO_CREATE);
        }

        Log.d("random_pick","onResume was called");
    }

    @Override
    public void onClick(View view) {
        if (spinAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {

            if (view.getId() == random.getId()) {
                random.setText("Spinning");
                choose.setVisibility(View.INVISIBLE);
                spinAsyncTask = new SpinAsyncTask();
                spinAsyncTask.execute(20);
            }

            if (view.getId() == list.getId()) {

                Intent intent = new Intent(this, MenuActivity.class);

                startActivity(intent);
            }

            //TODO
            if (view.getId() == choose.getId()) {

                if (place.getText().equals("Pick a Place!")) {
                    Toast.makeText(getApplicationContext(), "Press Random", Toast.LENGTH_LONG).show();
                }

                else {
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.putExtra(MenuActivity.NAME, place.getText());

                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d ("connected", "started");
        binder = (MusicService.MyBinder) iBinder;
        musicService = binder.getService();
        musicService.startMusic();
        isBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicService = null;
        isBound = false;
    }

    private class SpinAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            choose.setVisibility(View.VISIBLE);
            random.setText("Re-roll");
            String result = "Press CHOOSE to see menu";
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            place.setText(randomPick.check(values[0]));

        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int interval = 0;
            currentStep = integers[0];
            //we let it spin 20 times
            int count = 20;
            while (currentStep > 0) {
                try {
                    randomPick.spin();
                    publishProgress(randomPick.getNumber());
                    Thread.sleep(interval);
                    currentStep--;
                    interval = (count - currentStep) * 10;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }
    }
}
