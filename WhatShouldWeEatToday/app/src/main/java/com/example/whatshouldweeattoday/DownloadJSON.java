package com.example.whatshouldweeattoday;

/**
 * Created by seungleechoi on 12/14/17.
 */
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DownloadJSON extends IntentService {
    public static final String URL = "url";

    public static final String ACTION_DOWNLOAD = "com.example.whatshouldweeattoday.action.download";
    private DatabaseManager manager;

    public DownloadJSON() {
        super("DownloadJSON");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {

                populateDB(download(intent.getStringExtra(URL))

                );
            }
        }

    }
    private String download(String url)
    {
        final String API_KEY = "Bearer PP2xQpc5fTEEiqJgH-rUkPYMP1BqcwePfRUXDncTCFLsG-YJ6vl7WEgjbx-Kw7DguVoGEvcW6qy0SqE2BMbZup0Kz5OS97kTUSY1EQpf-yMoqF8UAGcjNH4GrDwzWnYx";

        manager = new DatabaseManager(this.getApplicationContext());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).header("authorization", API_KEY).build();
        Log.d("REQUEST", request.toString());
        Call call = client.newCall(request);
        Response response = null;

        String jsonData = null;

        try {
            response = call.execute();

            if (response.isSuccessful()) {
                jsonData = response.body().string();

            } else {
                Log.d("SIBAL", "AH");
                jsonData = null;
            }

        } catch (IOException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return jsonData;
    }

    String name = "";
    String closed = "Unknown";
    String price = "Unknown";
    String location = "Unknown";
    String website = "Unknown";
    String image = "";
    double rating = 0;

    private void populateDB(String jsonData) {

        //Log.d("jsondata", "can we see it:???" + jsonData);
        manager.open();
        JSONObject jsonResponse = null;
        try {
            if (jsonData != null) {
                jsonResponse = new JSONObject(jsonData);
            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        JSONArray foods = null;
        try {
            if (jsonResponse != null) {
                foods = jsonResponse.getJSONArray("businesses");
            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int food_list_size = 0;

        if (foods != null) {
            food_list_size = foods.length();
        }


        for (int i = 0; i < food_list_size; i++) {
            JSONObject jsonFilm = null;
            try {
                jsonFilm = foods.getJSONObject(i);
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            try {
                name = jsonFilm.getString("name");
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            if (jsonFilm.has("is_closed")) {
                try {
                    if (jsonFilm.getBoolean("is_closed")) {
                        closed = "Closed";
                    } else {
                        closed = "Open";
                    }
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }

            //TODO
            try {
                if (jsonFilm.has("price") && jsonFilm.getString("price") != null) {
                    price = jsonFilm.getString("price");
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            //TODO
            if (jsonFilm.has("location")) {
                JSONObject addressJSON = null;
                try {
                    addressJSON = jsonFilm.getJSONObject("location");
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                try {
                    location = addressJSON.getString("address1");
                    location = location + ", " + addressJSON.getString("city");
                    location += (", " + addressJSON.getString("state"));
                    location += (" " + addressJSON.getString("zip_code"));
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }

            if (jsonFilm.has("url")) {
                try {
                    website = jsonFilm.getString("url");
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }

            if (jsonFilm.has("image_url")) {
                try {
                    image = jsonFilm.getString("image_url");
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }

            if (jsonFilm.has("rating")) {
                try {
                    rating = jsonFilm.getDouble("rating");
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }

            if (name != null && !name.equals("null")) {
                manager.insertFoodInfo(name, closed, price, location, website, image, rating);
            }
        }

        manager.close();

        //broadcasting that it worked

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MenuActivity.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);
        jsonResponse = null;
    }
}