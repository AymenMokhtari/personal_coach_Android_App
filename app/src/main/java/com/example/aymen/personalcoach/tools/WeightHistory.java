package com.example.aymen.personalcoach.tools;

/**
 * Created by Aymen on 12/28/2017.
 */

public class WeightHistory {
    String date ;
    Float wieght;

    public WeightHistory(Float wieght ,String date) {
        this.date = date;
        this.wieght = wieght;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getWieght() {
        return wieght;
    }

    public void setWieght(Float wieght) {
        this.wieght = wieght;
    }
}
