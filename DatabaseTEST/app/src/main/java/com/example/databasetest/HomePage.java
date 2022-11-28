package com.example.databasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePage extends AppCompatActivity {

    Button BMIstats, goalsButton;
    //Navigation Buttons
    ImageView search_Button, profile_Button;
    private TextView Greeting, Greeting2;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Greeting = findViewById(R.id.greeting);
        Greeting2 = findViewById(R.id.greeting2);

        BMIstats = findViewById(R.id.BMIstats);
        goalsButton = findViewById(R.id.goalsButton);
        //Navigation buttons
        search_Button = findViewById(R.id.search_Button);
        profile_Button = findViewById(R.id.profile_Button);


//            if (prevPounds > currPounds) {
//                textviewPoundsGreeting.setText("You have lost: \n" + (Integer.toString((prevPounds - currPounds))) + " pounds! 😀");
//            } else {
//                textviewPoundsGreeting.setText("You have gained: \n" + (Integer.toString((currPounds - prevPounds))) + " pounds! 😠");
//            }


        goalsButton.setOnClickListener(view3 -> {
            startActivity(new Intent(HomePage.this,SetReminders.class));
        });

        //Navigation Buttons
        profile_Button.setOnClickListener(view4 -> {
            startActivity(new Intent(HomePage.this,Profile.class));
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.Name;
                    double height = userProfile.height;
                    double weight = userProfile.weight;

                    //Friendly greeting
                    Greeting2.setText(fullName);

                    //(lb, in, ex: 200, 69)
                    double BMI = calculate_BMI(weight,height);

                    BMIstats.setText("Current Body Mass: " + Integer.toString((int) BMI) + " - " + BMI_category((int)BMI));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "An error has occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        //Friendly greeting message
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String currentTimeString = sdf.format(new Date());
        int currentTime = Integer.parseInt(currentTimeString);
        if (currentTime < 12){
                Greeting.setText("Good Morning,");
            } else {
                Greeting.setText("Good Afternoon,");
            }


    }

    private double calculate_BMI(double weight, double height){
        //Imperial
        return Math.round(((703 * weight) / Math.pow(height,2)) * 100.0) / 100.0;
    }
    private String BMI_category(int BMI){
        String category;
        if (BMI < 18.5){
            category = "UNDERWEIGHT";
            BMIstats.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else if (BMI > 18.5 && BMI < 24.9){
            category = "NORMAL";
            BMIstats.setBackgroundColor(Color.parseColor("#FF14E02C"));
        }else if (BMI > 25 && BMI < 29.9){
            category = "OVERWEIGHT";
            BMIstats.setBackgroundColor(Color.parseColor("#FFE0D914"));
        }else if (BMI> 30 && BMI < 34.9){
            category = "OBESE";
            BMIstats.setBackgroundColor(Color.parseColor("#FFE08E14"));
        } else {
            category = "EXTREMELY OBESE";
            BMIstats.setBackgroundColor(Color.parseColor("#FFE01414"));
        }
        return category;
    }
}