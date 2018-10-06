package com.example.aymen.personalcoach.tools;

/**
 * Created by Aymen on 12/29/2017.
 */

public class RunningData {


    private Double distance ;
    private String avg_speed;
    private  Double calories_burned;
    private  String date ;

    public RunningData(Double distance, String avg_speed, Double calories_burned, String date) {
        this.distance = distance;
        this.avg_speed = avg_speed;
        this.calories_burned = calories_burned;
        this.date = date;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(String avg_speed) {
        this.avg_speed = avg_speed;
    }

    public Double getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(Double calories_burned) {
        this.calories_burned = calories_burned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
