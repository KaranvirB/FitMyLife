package com.example.databasetest;

import android.graphics.Color;

public class User {

    public String Name, email;
    public double height, weight;

    public User(){

    }

    public User(String Name, String email, double height, double weight){
        this.Name = Name;
        this.email = email;
        this.height = height;
        this.weight = weight;
    }

}
