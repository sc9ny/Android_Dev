package com.example.whatshouldweeattoday;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.HttpUrl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener {

    private ResponseReceiver receiver;

    private final String url = "https://api.yelp.com/v3/businesses/search?term=restaurants&radius=500&latitude=37.2330964&longitude=-80.4218461";

    static DatabaseManager databaseManager;
    ListView menuList;
    DownloadImageTask downloadImageTask;

    InformationFragment informationFragment;
    ListFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        informationFragment = (InformationFragment) getSupportFragmentManager().findFragmentById(R.id.detailFrag);
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);

        menuList = listFragment.menuList;
        Button button = listFragment.refresh;
        RadioGroup radio = listFragment.orderBy;

        databaseManager = new DatabaseManager(this);

        databaseManager.open();
        databaseManager.deleteAll();
        databaseManager.close();

        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        String URL1 =urlBuilder.build().toString();
        Log.d("URL", URL1);
        String complete_url = URL1;
        Intent msgIntent = new Intent(this, DownloadJSON.class);
        msgIntent.setAction(DownloadJSON.ACTION_DOWNLOAD);
        msgIntent.putExtra(DownloadJSON.URL, complete_url);
        startService(msgIntent);

        //when choose button was clicked
        Intent intent = getIntent();

        if (intent.getStringExtra(NAME) != null) {

            if (informationFragment != null && informationFragment.isInLayout()) {

                //informationFragment.setText(selected.getImage_url(), selected.getName(), selected.getClosed(), selected.getRating(), selected.getPrice(), selected.getLocation(), selected.getUrl());
            }
            else {
                if (findViewById(R.id.fragment_container) != null) {

                    button.setVisibility(View.INVISIBLE);
                    radio.setVisibility(View.INVISIBLE);

                    Log.d("menu_activity", "fragment_container is not null");

                    informationFragment = new InformationFragment();

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, informationFragment).commit();

                    Bundle args = new Bundle();
                    args.putString(NAME, intent.getStringExtra(NAME));
                    informationFragment.setArguments(args);

                    Log.d("menu_activity", "commit fragment replace");
                    //getSupportFragmentManager().beginTransaction().addToBackStack(null);
                    //getSupportFragmentManager().beginTransaction().commit();
                }
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static String NAME = DBOpenHelper.COLUMN_NAME_NAME;
    public static String CLOSED = DBOpenHelper.COLUMN_NAME_CLOSED;
    public static String PRICE = DBOpenHelper.COLUMN_NAME_PRICE;
    public static String LOCATION = DBOpenHelper.COLUMN_NAME_LOCATION;
    public static String URL = DBOpenHelper.COLUMN_NAME_URL;
    public static String IMAGE = DBOpenHelper.COLUMN_NAME_IMAGE_URL;
    public static String RATING = DBOpenHelper.COLUMN_NAME_RATING;

    @Override
    public void onItemClicked(int i) {

        Food selected = (Food) menuList.getItemAtPosition(i);

        if (informationFragment != null && informationFragment.isInLayout()) {

            informationFragment.setText(selected.getImage_url(), selected.getName(), selected.getClosed(), selected.getRating(), selected.getPrice(), selected.getLocation(), selected.getUrl());
        }
        else {
            //informationFragment is null in port mode
            Intent intent = new Intent(this, DetailActivity.class);

            intent.putExtra(NAME, selected.getName());
            intent.putExtra(CLOSED, selected.getClosed());
            intent.putExtra(PRICE, selected.getPrice());
            intent.putExtra(LOCATION, selected.getLocation());
            intent.putExtra(URL, selected.getUrl());
            intent.putExtra(IMAGE, selected.getImage_url());
            intent.putExtra(RATING, Double.toString(selected.getRating()));

            startActivity(intent);
        }
    }

    @Override
    public void onButtonClicked(int infoID) {

        if (infoID == 0) {
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
            MenuAdapter adapter = (MenuAdapter) menuList.getAdapter();
            adapter.clear();
            adapter.addAll(databaseManager.getAllRecords());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onCheckChange(int i) {
        List<Food> sortedRecords;
        String sort = "";

        Log.d("radio group", "i = " + i);

        switch (i%3) {
            case 1:
                Log.d("test", "ordered by name");
                sort = "Alphabetically";
                sortedRecords = databaseManager.getAllRecordsOrderedBy(DBOpenHelper.COLUMN_NAME_NAME);
                updateInfo(sortedRecords, menuList);
                break;
            case 2:
                Log.d("test", "ordered by rating");
                sort = "by Rating";
                sortedRecords = databaseManager.getAllRecordsOrderedBy(DBOpenHelper.COLUMN_NAME_RATING);
                updateInfo(sortedRecords, menuList);
                break;
            case 0:
                Log.d("test", "ordered by price");
                sort = "by Price";
                sortedRecords = databaseManager.getAllRecordsOrderedBy(DBOpenHelper.COLUMN_NAME_PRICE);
                updateInfo(sortedRecords, menuList);
                break;
        }

        Toast.makeText(this, "Ordered " + sort, Toast.LENGTH_SHORT).show();
    }

    public void displayMenu() {
        databaseManager = new DatabaseManager(this);
        databaseManager.open();

        List<Food> arrayOfMenu = databaseManager.getAllRecords();

        MenuAdapter adapter = new MenuAdapter(this, (ArrayList<Food>)arrayOfMenu);

        menuList.setAdapter(adapter);
    }

    public void updateInfo(List<Food> sortedRecords, ListView menuList) {
        MenuAdapter adapter = (MenuAdapter) menuList.getAdapter();
        adapter.clear();
        adapter.addAll(sortedRecords);
        adapter.notifyDataSetChanged();
    }

    public class MenuAdapter extends ArrayAdapter<Food> {

        public MenuAdapter(Context context, ArrayList<Food> foods) {
            super(context, 0, foods);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Food food = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_menu, parent, false);
            }

            ImageView thumbnail = (ImageView) convertView.findViewById(R.id.foodImage);
            TextView name = (TextView) convertView.findViewById(R.id.shibal);
            TextView isClosed = (TextView) convertView.findViewById(R.id.isOpen);
            TextView rating = (TextView) convertView.findViewById(R.id.rating);
            TextView price = (TextView) convertView.findViewById(R.id.price);

            downloadImageTask = new DownloadImageTask(thumbnail);
            downloadImageTask.execute(food.getImage_url());


            name.setText(food.getName());
            isClosed.setText(food.getClosed());
            rating.setText("Rating: " + food.getRating());
            price.setText("Price: " + food.getPrice());

            return convertView;
        }
    }

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "menus_fetched";

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("menu_activity","menus are fetched");

            if (listFragment.isInLayout()) {
                displayMenu();
            }
        }
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView thumbnail;

        public DownloadImageTask(ImageView thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlDisplay = strings[0];
            Bitmap myBitmap = null;

            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                myBitmap = BitmapFactory.decodeStream(in);
            }
            catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return myBitmap;
        }

        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            thumbnail.setImageBitmap(result);
        }
    }
}
