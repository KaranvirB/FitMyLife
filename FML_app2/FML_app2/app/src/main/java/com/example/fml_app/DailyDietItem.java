package com.example.fml_app;

import java.io.Serializable;

public class DailyDietItem implements Serializable {


    private String title;

    private int caloriePerUnit;

    private double unitNumber;

    private String unitName;


    public DailyDietItem(String title, int caloriePerUnit, double unitNumber, String unitName) {
        this.title = title;
        this.caloriePerUnit = caloriePerUnit;
        this.unitNumber = unitNumber;
        this.unitName = unitName;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public int getCaloriePerUnit() {
        return caloriePerUnit;
    }


    public void setCaloriePerUnit(int caloriePerUnit) {
        this.caloriePerUnit = caloriePerUnit;
    }


    public double getUnitNumber() {
        return unitNumber;
    }


    public void setUnitNumber(double unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getUnitName() {
        return unitName;
    }


    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }


    public int getTotalCalorie(){
        return (int) (caloriePerUnit * unitNumber);
    }
}
