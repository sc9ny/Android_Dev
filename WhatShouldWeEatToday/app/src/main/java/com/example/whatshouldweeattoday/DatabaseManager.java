package com.example.whatshouldweeattoday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 윤대원 on 2017-12-14.
 */

public class DatabaseManager {
    private SQLiteOpenHelper dbOpenHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void open() {
        database = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        dbOpenHelper.close();
    }

    public void insertFoodInfo(String name, String closed, String price, String location, String url, String image_url, double rating)
    {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_NAME_NAME, name);
        values.put(DBOpenHelper.COLUMN_NAME_CLOSED, closed);
        values.put(DBOpenHelper.COLUMN_NAME_PRICE, price);
        values.put(DBOpenHelper.COLUMN_NAME_LOCATION, location);
        values.put(DBOpenHelper.COLUMN_NAME_URL, url);
        values.put(DBOpenHelper.COLUMN_NAME_IMAGE_URL, image_url);
        values.put(DBOpenHelper.COLUMN_NAME_RATING, rating);

        database.insert(DBOpenHelper.TABLE_NAME, null, values);
    }

    public List<Food> getAllRecordsOrderedBy(String key) {
        List<Food> sorted = getAllRecords();

        if (key.equals(DBOpenHelper.COLUMN_NAME_NAME)) {
            Collections.sort(sorted);
        }
        else if (key.equals(DBOpenHelper.COLUMN_NAME_RATING)) {
            Collections.sort(sorted, Food.RatingComparator);
        }
        else if (key.equals(DBOpenHelper.COLUMN_NAME_PRICE)) {
            Collections.sort(sorted, Food.PriceComparator);
        }

        return sorted;
    }

    public List<Food> getAllRecords() {
        Cursor cursor = database.query(DBOpenHelper.TABLE_NAME,
                new String[]{
                        DBOpenHelper.COLUMN_ID,
                        DBOpenHelper.COLUMN_NAME_NAME,
                        DBOpenHelper.COLUMN_NAME_CLOSED,
                        DBOpenHelper.COLUMN_NAME_PRICE,
                        DBOpenHelper.COLUMN_NAME_LOCATION,
                        DBOpenHelper.COLUMN_NAME_URL,
                        DBOpenHelper.COLUMN_NAME_IMAGE_URL,
                        DBOpenHelper.COLUMN_NAME_RATING,


                }, null,null,null,null,null,null);
        cursor.moveToFirst();
        Food food;
        List<Food> result = new ArrayList<Food>();
        while (!cursor.isAfterLast())
        {
            food = new Food();
            food.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_NAME)));
            food.setClosed(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_CLOSED)));
            food.setPrice(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_PRICE)));
            food.setLocation(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_LOCATION)));
            food.setUrl(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_URL)));
            food.setImage_url(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_IMAGE_URL)));
            food.setRating(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME_RATING)));
            cursor.moveToNext();
            result.add(food);
        }
        return result;
    }

    public void deleteAll() {
        if (database.isOpen()) {
            database.execSQL("DELETE FROM " + DBOpenHelper.TABLE_NAME);
        }
    }
}
