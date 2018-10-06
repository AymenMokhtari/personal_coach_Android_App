package com.example.aymen.personalcoach.tools;

public class GPSData {
    public long timestamp;
    public double latitude;
    public double longitude;

    public GPSData(long timestamp, double latitude, double longitude) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
