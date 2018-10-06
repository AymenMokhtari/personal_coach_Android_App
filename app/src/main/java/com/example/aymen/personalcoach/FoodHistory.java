package com.example.aymen.personalcoach;

/**
 * Created by Aymen on 12/10/2017.
 */

public class FoodHistory {

    private  int id ;
    private  String Foodname;
    private  Double Qte;
    private  Double calories;

    public FoodHistory(int id, String foodname, Double qte, Double calories) {
        this.id = id;
        Foodname = foodname;
        Qte = qte;
        this.calories = calories;
    }

    public FoodHistory(String foodname, Double qte, Double calories) {
        Foodname = foodname;
        Qte = qte;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public Double getQte() {
        return Qte;
    }

    public void setQte(Double qte) {
        Qte = qte;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }
}
