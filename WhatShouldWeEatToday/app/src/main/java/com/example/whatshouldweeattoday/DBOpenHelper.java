package com.example.whatshouldweeattoday;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 윤대원 on 2017-12-14.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 8;
    public static final String TABLE_NAME = "food";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_CLOSED = "is_closed";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String COLUMN_NAME_LOCATION = "location";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_NAME_IMAGE_URL = "image_url";
    public static final String COLUMN_NAME_RATING = "rating";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_NAME + " TEXT," + COLUMN_NAME_CLOSED + " TEXT," + COLUMN_NAME_PRICE + " TEXT,"
                    + COLUMN_NAME_LOCATION + " TEXT," + COLUMN_NAME_URL + " TEXT,"
                    + COLUMN_NAME_IMAGE_URL + " TEXT," + COLUMN_NAME_RATING + " REAL)";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
