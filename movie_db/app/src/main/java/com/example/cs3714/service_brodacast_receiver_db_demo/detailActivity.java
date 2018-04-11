package com.example.cs3714.service_brodacast_receiver_db_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class detailActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView image;
    Button like;
    String title;
    //String path;
    Movie m;

    public List<Movie> movieList123 = new ArrayList<Movie>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        image = (ImageView) findViewById(R.id.imageView3);
        like = (Button) findViewById(R.id.like);

        like.setOnClickListener(this);
        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String rating = intent.getStringExtra("rating");
        String overview1 = intent.getStringExtra("overview");
        String back_path = intent.getStringExtra("back_path");
        String path = intent.getStringExtra("path_poster");

        TextView textView = (TextView)findViewById((R.id.textView4));
        TextView textView1 = (TextView)findViewById(R.id.textView5);
        TextView textView2 = (TextView)findViewById(R.id.textView6);
        TextView overview = (TextView) findViewById(R.id.overview);
        textView1.setText(title);
        textView.setText("Release Date: " +date);
        textView2.setText("Rating: "+rating);
        overview.setText("overview: " +overview1);
        downloadImage down = new downloadImage();
        down.execute(path);
        m = new Movie();
        m.setTitle(title);
        m.setOverview(overview1);
        m.setDate(date);
        m.setRating(rating);
        m.setPath_poster(path);
        m.setBackdrop_path(back_path);
        if (MainActivity.favoriteMovies.isLiked(m.getTitle())){
            like.setText("UNLIKE");
        }
        else
        {
            like.setText("LIKE");
        }
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("like", (String)like.getText());



    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        like.setText(savedInstanceState.getString("like"));


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == like.getId()) {
            if (like.getText().toString().equals("UNLIKE")) {
                MainActivity.favoriteMovies.removeTitle(m.getTitle());
                like.setText("LIKE");
            } else {
                MainActivity.favoriteMovies.addTitle(m.getTitle(), m);
                like.setText("UNLIKE");
            }
            //
            //MainActivity.manager.deleteAll();
            if (MainActivity.adapter != null)
            {
                ArrayAdapter<Movie> adapter = MainActivity.adapter;
                ListView listView = MainActivity.movieList;
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(), "" + MainActivity.favoriteMovies.getLikedMovies().size(), Toast.LENGTH_SHORT).show();
            }
            if (MainActivity.adapter == null && MainActivity.adapter1 != null)
            {
                ArrayAdapter<Movie> adapter = MainActivity.adapter1;
                ListView listView = MainActivity.movieList;
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(), "" + MainActivity.favoriteMovies.getLikedMovies().size(), Toast.LENGTH_SHORT).show();
            }

        }
//        if (view.getId() == like.getId())
//        {
////
//            //MAYBE START AN INTENT with everything (title,overview
//            SharedPreferences pref = getSharedPreferences("movie", z);
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putInt("index", z);
//            editor.commit();
//            //ArrayList<Integer> list = new ArrayList<Integer>();
//            //list.add(z);
////            Intent intent = new Intent(this, MainActivity.class);
////            intent.putExtra("index", z);
////            //intent.putExtra("list", list);
////            //startActivity(intent);
//            like.setText("LIKED!");
//        }
    }


    public class downloadImage extends AsyncTask<String, Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                //Log.e("src",src);
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }
        }
        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }
    }

}

