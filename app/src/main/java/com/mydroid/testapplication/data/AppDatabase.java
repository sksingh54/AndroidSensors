package com.mydroid.testapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mydroid.testapplication.data.Contract.*;

/**
 * Created by sksingh on 21-Aug-17.
 */
public class AppDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "appData.db";

    private static final String COMMA_SEP = ",";
    public static final String SQLITE_CREATE_ACCELEROMETERENTRY =              //create table for tableentry
            "CREATE TABLE " + Accele.TABLE_NAME + " (" +
                    Accele._ID + " INTEGER PRIMARY KEY," +
                    Accele.TIME + " TEXT" + COMMA_SEP +
                    Accele.LOCATION + " TEXT" + COMMA_SEP +
                    Accele.ACCEL_COLUMN_NAME_X + " FLOAT"+ COMMA_SEP +
                    Accele.ACCEL_COLUMN_NAME_Y + " FLOAT"+ COMMA_SEP +
                    Accele.ACCEL_COLUMN_NAME_Z + " FLOAT" + ")";

    public static final String SQLITE_DELETE_ACCELEROMETERENTRY =          //statement to delete feedentry table
            "DROP TABLE IF EXISTS " + Accele.TABLE_NAME;

    public static final String SQLITE_CREATE_GYROSCOPEENTRY =              //create table for tableentry
            "CREATE TABLE " + Accele.TABLE_NAME + " (" +
                    Gyro._ID + " INTEGER PRIMARY KEY," +
                    Gyro.GYRO_COLUMN_NAME_X + " FLOAT"+ COMMA_SEP +
                    Gyro.GYRO_COLUMN_NAME_Y + " FLOAT"+ COMMA_SEP +
                    Gyro.GYRO_COLUMN_NAME_Z + " FLOAT" + ")";

    public static final String SQLITE_DELETE_GYROSCOPEENTRY =          //statement to delete feedentry table
            "DROP TABLE IF EXISTS " + Gyro.TABLE_NAME;

    public static final String SQLITE_CREATE_MAGNETOMETERENTRY =              //create table for tableentry
            "CREATE TABLE " + Accele.TABLE_NAME + " (" +
                    Magneto._ID + " INTEGER PRIMARY KEY," +
                    Magneto.MAG_COLUMN_NAME_X + " FLOAT"+ COMMA_SEP +
                    Magneto.MAG_COLUMN_NAME_Y + " FLOAT"+ COMMA_SEP +
                    Magneto.MAG_COLUMN_NAME_Z + " FLOAT" + ")";

    public static final String SQLITE_DELETE_MAGNETOMETERENTRY =          //statement to delete feedentry table
            "DROP TABLE IF EXISTS " + Gyro.TABLE_NAME;



    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLITE_CREATE_ACCELEROMETERENTRY);
//        db.execSQL(SQLITE_CREATE_GYROSCOPEENTRY);
//        db.execSQL(SQLITE_CREATE_MAGNETOMETERENTRY);
        Log.e("Table created", "All 3 Tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



}
