package com.example.aymen.personalcoach.tools;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;

import com.example.aymen.personalcoach.RunningGps;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rbonick on 4/28/2015.
 */

public class GPSListener implements LocationListener {

    protected final String GPS_FILE_NAME = "gps.csv";

    protected long startTime;
    RunningGps fragment;
    protected int count;

    public GPSListener(Context context, RunningGps fragment) {
        this.fragment = fragment;

        startTime = getCurrentTime();

        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(date);
        String dirName = "/reading_"+dateString+"/";
        count = 0;
    }

    private long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void onLocationChanged(Location location) {
        long timeElapsed = getCurrentTime();
        count++;
        fragment.setGPSStatus("Connected " + count);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if(status == LocationProvider.AVAILABLE) {
            fragment.setGPSStatus("Connected");
        } else if (status == LocationProvider.OUT_OF_SERVICE) {
            count = 0;
            fragment.setGPSStatus("No Connection");
        } else if (status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
            count = 0;
            fragment.setGPSStatus("No Connection");
        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
