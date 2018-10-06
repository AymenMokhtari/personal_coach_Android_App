package com.example.aymen.personalcoach.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Aymen on 10/22/2017.
 */

public class MyDbHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "myDatabase";    // Database Name
    public static final String TABLE_NAME = "History";   // Table Name
    public static final int DATABASE_Version = 1;    // Database Version
    public static final String ID="_id";     // Column I (Primary Key)
    public static final String CALORIES = "Cloaries";    //Column II
    public static final String DAY= "Day";    // Column III
    public static final String FOOD= "Food";    // Column III
    public static final String WEIGHT= "Weight";    // Column III


    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +FOOD+" VARCHAR(255) ,"
            +CALORIES+" VARCHAR(255) ,"
            +WEIGHT+" VARCHAR(255) ,"
            +DAY+" VARCHAR(225));";


    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    private Context context;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context=context;
    }

    public void onCreate(SQLiteDatabase db) {

        try {
            Log.i("created","978");
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Message.message(context,"");
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {

        }
    }



}