package com.example.whatshouldweeattoday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView image;
    TextView name;
    TextView closed;
    TextView rating;
    TextView price;
    TextView location;
    TextView url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_menu);

        Intent intent = getIntent();

        if (intent != null) {
            String infoImage = intent.getStringExtra(MenuActivity.IMAGE);
            String infoName = intent.getStringExtra(MenuActivity.NAME);
            String infoClosed = intent.getStringExtra(MenuActivity.CLOSED);
            String infoRating = intent.getStringExtra(MenuActivity.RATING);
            String infoPrice = intent.getStringExtra(MenuActivity.PRICE);
            String infoLocation = intent.getStringExtra(MenuActivity.LOCATION);
            String infoUrl = intent.getStringExtra(MenuActivity.URL);

            image = (ImageView) findViewById(R.id.imageD);
            name = (TextView) findViewById(R.id.nameD);
            closed = (TextView) findViewById(R.id.isOpenD);
            rating = (TextView) findViewById(R.id.ratingD);
            price = (TextView) findViewById(R.id.priceD);
            location = (TextView) findViewById(R.id.locationD);
            url = (TextView) findViewById(R.id.urlD);

            //if (!infoImage.isEmpty()) {
            new MenuActivity.DownloadImageTask(image).execute(infoImage);
            name.setText(infoName);
            closed.setText(infoClosed.toUpperCase());
            rating.setText("Rating: " + infoRating);
            price.setText("Price: " + infoPrice);
            location.setText("Location: " + infoLocation);
            url.setText("URL: " + infoUrl);
        }
    }
}
