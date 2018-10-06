package com.example.aymen.personalcoach.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Aymen on 10/22/2017.
 */

public  class MyDbAdapter {

    MyDbHelper myhelper;

    public MyDbAdapter(Context context)
    {
        myhelper = new MyDbHelper(context);
    }

    public long insertData(String food , float calories , float weight , String day  )
    {

        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDbHelper.FOOD, food);
        contentValues.put(MyDbHelper.CALORIES, calories);
       contentValues.put(MyDbHelper.WEIGHT, weight);
        contentValues.put(MyDbHelper.DAY, day);
        long id = dbb.insert(MyDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }


}