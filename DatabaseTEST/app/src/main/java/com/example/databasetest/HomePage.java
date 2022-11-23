package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

public class HomePage extends AppCompatActivity {

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        logout.findViewById(R.id.logout);

        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomePage.this,MainActivity.class));

            //(lb, in)
            calculate_BMI(50,43);

            BMI_category(calculate_BMI(50,43));
        });
    }

    private double calculate_BMI(double weight, double height){
        //Imperial
        return Math.round(((703 * weight) / Math.pow(height,2)) * 100.0) / 100.0;
    }
    private String BMI_category(double BMI){
        String category;
        if (BMI<18.5){
            category = "UNDERWEIGHT";
        } else if (BMI > 18.5 || BMI < 24.9){
            category = "NORMAL";
        } else if (BMI > 25 || BMI < 29.9){
            category = "OVERWEIGHT";
        } else if (BMI> 30 || BMI < 34.9){
            category = "OBESE";
        } else {
            category = "EXTREMELY OBESE";
        }
        return category;
    }
}