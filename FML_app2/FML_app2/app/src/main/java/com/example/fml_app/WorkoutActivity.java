package com.example.fml_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class WorkoutActivity extends AppCompatActivity {
private GridView gridView;
int [] pictures = {R.drawable.cycling,R.drawable.dance,R.drawable.elliptical,R.drawable.hiking,R.drawable.jogging,R.drawable.jumprope,R.drawable.pilates,R.drawable.pushup,R.drawable.stair,R.drawable.strength, R.drawable.stretching,R.drawable.swimming,R.drawable.walking,R.drawable.yoga};

String[] exerciseTypes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        exerciseTypes =getResources().getStringArray(R.array.exercise);
        gridView=(GridView)findViewById(R.id.gridViewID);

        CustomAdapter adapter = new CustomAdapter(this,exerciseTypes,pictures);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            //position 0 is cycling
            if (position==0) {
                Intent intent = new Intent(WorkoutActivity.this,NewActivity.class);
                startActivity(intent);
            }
            //position 1 is dancing
            if (position==1) {
                Intent intent = new Intent(WorkoutActivity.this,SecondActivity.class);
                startActivity(intent);
            }
            if (position ==2) {
                Intent intent = new Intent(WorkoutActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
            if (position ==3) {
                Intent intent = new Intent(WorkoutActivity.this, FourthActivity.class);
                startActivity(intent);

            }
            if(position ==4) {
                Intent intent = new Intent(WorkoutActivity.this, FifthActivity.class);
                startActivity(intent);
            }
            if(position ==5) {
                Intent intent = new Intent(WorkoutActivity.this, SixthActivity.class);
                startActivity(intent);
            }
            if(position ==6) {
                Intent intent = new Intent(WorkoutActivity.this, SeventhActivity.class);
                startActivity(intent);
            }
            if(position ==7) {
                Intent intent = new Intent(WorkoutActivity.this, EighthActivity.class);
                startActivity(intent);
            }
            if(position ==8) {
                Intent intent = new Intent(WorkoutActivity.this, NinthActivity.class);
                startActivity(intent);
            }
            if(position ==9) {
                Intent intent = new Intent(WorkoutActivity.this, TenthActivity.class);
                startActivity(intent);
            }

        });

    }
}