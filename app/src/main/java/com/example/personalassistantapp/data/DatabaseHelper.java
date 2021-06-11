package com.example.personalassistantapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.personalassistantapp.model.Event;
import com.example.personalassistantapp.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Util.EVENT_NAME + " TEXT , " + Util.EVENT_DATE + " TEXT , " + Util.EVENT_TIME + " TEXT, " + Util.STATUS + " TEXT )";

        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_EVENT_TABLE = "DROP TABLE IF EXISTS";
        db.execSQL(DROP_EVENT_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(db);

    }

    public boolean updateEvent (Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.EVENT_NAME, event.getName());
        contentValues.put(Util.EVENT_DATE, event.getDate());
        contentValues.put(Util.EVENT_TIME, event.getTime());
        contentValues.put(Util.STATUS, event.getStatus());

        db.update(Util.TABLE_NAME, contentValues, Util.EVENT_NAME + "=?", new String[]{String.valueOf(event.getName())});
        db.close();
        return true;
    }

    public long insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.EVENT_NAME, event.getName());
        contentValues.put(Util.EVENT_DATE, event.getDate());
        contentValues.put(Util.EVENT_TIME, event.getTime());
        contentValues.put(Util.STATUS, event.getStatus());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close(); //to prevent memory leaks.
        return newRowId;
    }

    public List<Event> fetchEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events_list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String event_name = cursor.getString(cursor.getColumnIndex(Util.EVENT_NAME));
                String event_date = cursor.getString(cursor.getColumnIndex(Util.EVENT_DATE));
                String event_time = cursor.getString(cursor.getColumnIndex(Util.EVENT_TIME));
                String status = cursor.getString(cursor.getColumnIndex(Util.STATUS));
                events_list.add(new Event(event_name, event_date, event_time,status));
                cursor.moveToNext();
            }
        }
        db.close();
        return events_list;
    }

    public List<Event> fetchCompleted() {
        String state = "off";
       // DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        SQLiteDatabase db = this.getReadableDatabase();
        //String sql = "SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.STATUS + "=" + state + "";


        List<Event> events_list = new ArrayList<>();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.STATUS + " = 'off'" , null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String event_name = cursor.getString(cursor.getColumnIndex(Util.EVENT_NAME));
                String event_date = cursor.getString(cursor.getColumnIndex(Util.EVENT_DATE));
                String event_time = cursor.getString(cursor.getColumnIndex(Util.EVENT_TIME));
                String status = cursor.getString(cursor.getColumnIndex(Util.STATUS));
                events_list.add(new Event(event_name, event_date, event_time,status));
                cursor.moveToNext();
            }
        }
        db.close();
        return events_list;
    }

    public List<Event> fetchUpcoming() {
        String state = "o";
        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.STATUS + "=" + state;

        List<Event> events_list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.STATUS + " = 'o'", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String event_name = cursor.getString(cursor.getColumnIndex(Util.EVENT_NAME));
                String event_date = cursor.getString(cursor.getColumnIndex(Util.EVENT_DATE));
                String event_time = cursor.getString(cursor.getColumnIndex(Util.EVENT_TIME));
                String status = cursor.getString(cursor.getColumnIndex(Util.STATUS));
                events_list.add(new Event(event_name, event_date, event_time,status));
                cursor.moveToNext();
            }
        }
        db.close();
        return events_list;
    }
}
