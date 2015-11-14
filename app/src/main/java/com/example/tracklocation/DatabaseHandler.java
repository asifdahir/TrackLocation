package com.example.tracklocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tracklocation.Model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/13/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String TAG = DatabaseHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tracklocation";
    private static final String TABLE_LOCATION = "location";

    // location Table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_SPEED = "speed";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + KEY_DATE + " TEXT,"
                    + KEY_LATITUDE + " REAL,"
                    + KEY_LONGITUDE + " REAL,"
                    + KEY_SPEED + " REAL"
                    + ")";
            db.execSQL(CREATE_LOCATION_TABLE);
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
    }

    public void addLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, location.getDate());
        values.put(KEY_LATITUDE, location.getLatitude());
        values.put(KEY_LONGITUDE, location.getLongitude());
        values.put(KEY_SPEED, location.getSpeed());

        db.insert(TABLE_LOCATION, null, values);
        db.close();
    }

    public List<Location> getAllLocations() {
        SQLiteDatabase db = null;
        List<Location> locations = null;
        Cursor cursor = null;

        locations = new ArrayList<Location>();
        try {
            db = this.getWritableDatabase();
            cursor = db.query(
                    TABLE_LOCATION,
                    new String[]{KEY_ID, KEY_DATE, KEY_LATITUDE, KEY_LONGITUDE, KEY_SPEED},
                    null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Location location = cursorToLocation(cursor);
                locations.add(location);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            cursor.close();
        }

        return locations;
    }

    private Location cursorToLocation(Cursor cursor) {
        Location location = new Location();
        location.setDate(cursor.getString(1));
        location.setLatitude(cursor.getDouble(2));
        location.setLongitude(cursor.getDouble(3));
        location.setSpeed(cursor.getFloat(4));
        return location;
    }
}
