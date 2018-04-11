package com.example.cs3714.service_brodacast_receiver_db_demo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Array;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final String url = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "deea9711e0770caae3fc592b028bb17e";
    static favoriteMovies favoriteMovies;
    private ResponseReceiver receiver;
    private Button refresh, title, date, popularity, fav;
    static ListView movieList;
    ImageView low;
    static MovieAdapter adapter;
    static MovieAdapter adapter1;
    static DatabaseManager manager;

    //detailActivity act = new detailActivity();
    public List<Movie> movieList123 = new ArrayList<Movie>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteMovies = new favoriteMovies();
        movieList = (ListView)findViewById(R.id.moviesList);
        movieList.setOnItemClickListener(this);
        low = (ImageView) findViewById(R.id.low);

        fav = (Button) findViewById(R.id.fav);
        refresh = (Button) findViewById(R.id.refresh);
        title = (Button) findViewById(R.id.title);
        date = (Button) findViewById(R.id.date);
        popularity = (Button) findViewById(R.id.popularity);
        //refresh.setOnClickListener(convertView.OnClickListener());
        refresh.setOnClickListener(this);
        title.setOnClickListener(this);
        date.setOnClickListener(this);
        popularity.setOnClickListener(this);
        fav.setOnClickListener(this);
        //movieList123 = new ArrayList<Movie>();
        //registering a local broadcast receiver that is activated when "movies_fetched"
        //action happens
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);
        manager = new DatabaseManager(this);
        //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
//        String complete_url = url+ "movie/now_playing?api_key="+API_KEY+"&language=en-US&page=1";
//        Intent msgIntent = new Intent(this, DownloadJSON.class);
//        msgIntent.setAction(DownloadJSON.ACTION_DOWNLOAD);
//        msgIntent.putExtra(DownloadJSON.URL, complete_url);
//        startService(msgIntent);
//        displayMovies();


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
        TextView title = (TextView) findViewById(R.id.title);

        TextView date = (TextView) findViewById(R.id.date);

        TextView rating = (TextView)findViewById(R.id.rating);
        outState.putString("title", (String)title.getText());
        outState.putString("date", (String)date.getText());
        outState.putString("rating", (String)rating.getText());
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
////        Toast.makeText(getApplicationContext(), "hello123", Toast.LENGTH_SHORT).show();
//        TextView title = (TextView) findViewById(R.id.title);
//
//        TextView date = (TextView) findViewById(R.id.date);
//
//        TextView rating = (TextView)findViewById(R.id.rating);
//        title.setText(savedInstanceState.getString("title"));
//        date.setText(savedInstanceState.getString("date"));
//        rating.setText(savedInstanceState.getString("rating"));
//    }


    //this is called when we know that the list ready

public void displayMovies(){
     manager = new DatabaseManager(this);

    manager.open();

    List<Movie> arrayOfMovies = manager.getAllRecords();

// Create the adapter to convert the array to views

    adapter = new MovieAdapter(this, (ArrayList<Movie>)arrayOfMovies);

// Attach the adapter to a ListView

    ListView listView = (ListView) findViewById(R.id.moviesList);

    listView.setAdapter(adapter);

    adapter.notifyDataSetChanged();
}

    @Override
    public void onClick(View view) {

        manager = new DatabaseManager(this);

        manager.open();

        if (view.getId() == refresh.getId())
        {
            manager.deleteAll();
            favoriteMovies.removeAll();
            String complete_url = url+ "movie/now_playing?api_key="+API_KEY+"&language=en-US&page=1";
            Intent msgIntent = new Intent(this, DownloadJSON.class);
            msgIntent.setAction(DownloadJSON.ACTION_DOWNLOAD);
            msgIntent.putExtra(DownloadJSON.URL, complete_url);
            startService(msgIntent);
            displayMovies();
        }

        //Get the Data from the database.
        List<Movie> arrayOfMovies = manager.getAllRecords();
        if (view.getId() == date.getId())
        {
            if (arrayOfMovies.size() >0)
                Collections.sort(arrayOfMovies,new customComparatorDate());

            //Collections.sort (arrayOfMovies,Collections.reverseOrder());
            if (adapter != null)
                adapter.clear();
            adapter = new MovieAdapter(this, (ArrayList<Movie>)arrayOfMovies);
            ListView listView = (ListView) findViewById(R.id.moviesList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        //sort by popularity
        if (view.getId() == popularity.getId())
        {
            if (arrayOfMovies.size() >0)
                Collections.sort(arrayOfMovies, new customComparatorRating());

            if (adapter != null)
                adapter.clear();
            adapter = new MovieAdapter(this, (ArrayList<Movie>)arrayOfMovies);
            ListView listView = (ListView) findViewById(R.id.moviesList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if (view.getId() == title.getId())
        {
            if (arrayOfMovies.size() >0)
                Collections.sort(arrayOfMovies, new customComparatorTitle());
            if (adapter != null)
                adapter.clear();
            adapter = new MovieAdapter(this, (ArrayList<Movie>)arrayOfMovies);
            ListView listView = (ListView) findViewById(R.id.moviesList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }
        if (view.getId() == fav.getId())
        {

            adapter = null;
            adapter1 = new MovieAdapter (this, (ArrayList<Movie>)favoriteMovies.getLikedMovies()); // some Array NAME
            ListView listView = (ListView) findViewById(R.id.moviesList);
            listView.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
//            for (int z = 0; z < favoriteMovies.getLikedMovies().size(); z++)
//            {
//                Movie movie = adapter1.getItem(z);
//                downloadImage down = new downloadImage();
//                down.execute("http://image.tmdb.org/t/p/w185"+movie.getBackdrop_path());
//            }
            //Toast.makeText(getApplicationContext(),""+movieList123.size(), Toast.LENGTH_SHORT).show();

//            SharedPreferences bb = getSharedPreferences("movie", 0);
//            if (adapter != null)
//            {
//
//                int ind = bb.getInt("index", -1);
//                if (ind >= 0 )
//                {
//                    //Toast.makeText(getApplicationContext(), ""+ind, Toast.LENGTH_SHORT).show();
//                    Movie m = adapter.getItem(ind);
//                    movieList123.add(m);
//
//                    adapter = new MovieAdapter (this, (ArrayList<Movie>)movieList123); // some Array NAME
//                    ListView listView = (ListView) findViewById(R.id.moviesList);
//                    listView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }
//
//            }

            //Intent intent = getIntent();
//            ArrayList<Integer> indices = intent.getIntegerArrayListExtra("list");
//            if (adapter != null)
//            {
//                for (int i =0; i < indices.size(); i++)
//                {
//                    Movie m = adapter.getItem(indices.get(i));
//                    movieList123.add(m);
//                }
//            }
//            int index = intent.getIntExtra("index", -1);
//            if (adapter != null && index >= 0)
//            {
//                Movie m = adapter.getItem(index);
//                movieList123.add(m);
//            }


        }

        manager.close();

    }
    // i = position
    // l = id
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {

        Intent intent = new Intent(getApplicationContext(), detailActivity.class);

        if (adapter != null)
        {
            String overview_ = adapter.getItem(i).getOverview();
            String path_poster_ = adapter.getItem(i).getPath_poster();
            String rating_ = adapter.getItem(i).getRating();
            String date_  = adapter.getItem(i).getDate();
            String title_ = adapter.getItem(i).getTitle();
            String back_path = adapter.getItem(i).getBackdrop_path();
            intent.putExtra("overview", overview_);
            intent.putExtra("path_poster", "http://image.tmdb.org/t/p/w185"+path_poster_);
            intent.putExtra("title", title_ );
            intent.putExtra("rating", rating_);
            intent.putExtra("date", date_);
            intent.putExtra("index", i);
            intent.putExtra("back_path", "http://image.tmdb.org/t/p/w185"+back_path);
            startActivity(intent);
        }

        if (adapter == null && adapter1 != null)
        {
            String overview_ = adapter1.getItem(i).getOverview();
            String path_poster_ = adapter1.getItem(i).getPath_poster();
            String rating_ = adapter1.getItem(i).getRating();
            String date_  = adapter1.getItem(i).getDate();
            String title_ = adapter1.getItem(i).getTitle();
            String back_path = adapter1.getItem(i).getBackdrop_path();
            intent.putExtra("overview", overview_);
            intent.putExtra("path_poster", "http://image.tmdb.org/t/p/w185"+path_poster_);
            intent.putExtra("title", title_ );
            intent.putExtra("rating", rating_);
            intent.putExtra("date", date_);
            intent.putExtra("index", i);
            intent.putExtra("back_path", "http://image.tmdb.org/t/p/w185"+back_path);
            startActivity(intent);
        }


    }


    // this adapter takes an ArrayList of movies and outputs it into a ListView
    public class MovieAdapter extends ArrayAdapter<Movie> {

        public MovieAdapter(Context context, ArrayList<Movie> movies) {

            super(context, 0, movies);

        }



        //this is where you would add the like button
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            // Get the data item for this position

            Movie movie = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view

            if (convertView == null) {

                //the like button would need to be in single_movie.xml
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_movie, parent, false);

            }

            // Lookup view for data population


            TextView title = (TextView) convertView.findViewById(R.id.title);

            TextView date = (TextView) convertView.findViewById(R.id.date);

            TextView rating = (TextView) convertView.findViewById(R.id.rating);
            low = (ImageView) convertView.findViewById(R.id.low);


            title.setText(movie.getTitle());


            date.setText(movie.getDate());

            rating.setText(movie.getRating()+"");

            downloadImage down = new downloadImage();
            down.execute("http://image.tmdb.org/t/p/w185"+movie.getBackdrop_path());

            // Return the completed view to render on screen
//            SharedPreferences bb = getSharedPreferences("movie", -2);
//            if (adapter != null)
//            {
//
//                int ind = bb.getInt("index", -2);
//                //Toast.makeText(getApplicationContext(), ""+ind, Toast.LENGTH_SHORT).show();
//                if (ind >= 0 )
//                {
//                    //
//                    Movie m = adapter.getItem(ind);
//                    movieList123.add(m);
//                    ind = -1;
//
//                }
//
//            }

            return convertView;

        }

    }



    // this BroadcastReceiver is waiting for DownloadJSON (IntentService) to issue a broadcast

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "movies_fetched";

        @Override
        public void onReceive(Context context, Intent intent) {
;

            Log.d("demoapp","movies are fetched");

            displayMovies();
        }
    }
    public class customComparatorTitle implements Comparator<Movie>
    {
        @Override
        public int compare (Movie m1, Movie m2)
        {
            return m1.getTitle().compareToIgnoreCase(m2.getTitle());
        }
    }
    public class customComparatorDate implements Comparator<Movie>
    {
        @Override
        public int compare (Movie m1, Movie m2)
        {
            return (m1.getDate().compareToIgnoreCase(m2.getDate()))*-1;
        }
    }
    public class customComparatorRating implements Comparator<Movie>
    {
        @Override
        public int compare (Movie m1, Movie m2)
        {
            return (m1.getRating().compareTo(m2.getRating()))*-1;
        }
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
                //image.setImageBitmap(myBitmap);
                //Log.e("Bitmap","returned");
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                //Log.e("Exception",e.getMessage());
                return null;
            }
        }
        protected void onPostExecute(Bitmap result) {
            low.setImageBitmap(result);
        }
    }


}
