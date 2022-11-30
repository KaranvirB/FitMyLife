package com.example.fml_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SetReminders extends AppCompatActivity {

    EditText edit_goal_name;
    ImageView add, confirm_name;
    CalendarView calendarView;
    TextView selectGoalDateTitle;

    private ArrayList<item> itemsList;
    private RecyclerView recyclerView;

    //Navigation Buttons
    ImageView search_Button,home_Button, profile_Button;

    int daysLeft = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminders);

        recyclerView = findViewById(R.id.recyclerView);
        itemsList = new ArrayList<>();

        //Adding new goals
        add = findViewById(R.id.add);
        add.setOnClickListener(view -> {
            daysLeft = 0;
            TextView txt = findViewById(R.id.textView);
            edit_goal_name = findViewById(R.id.edit_goal_name);
            confirm_name = findViewById(R.id.confirm_name);
            calendarView = findViewById(R.id.calendarView);
            selectGoalDateTitle = findViewById(R.id.selectGoalDateTitle);

            txt.setVisibility(view.GONE);
            add.setVisibility(view.GONE);
            edit_goal_name.setVisibility(view.VISIBLE);
            confirm_name.setVisibility(view.VISIBLE);
            calendarView.setVisibility(view.VISIBLE);
            recyclerView.setVisibility(view.GONE);
            selectGoalDateTitle.setVisibility(view.VISIBLE);

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    int selectedDay = dayOfMonth;
                    int selectedMonth = month;
                    int selectedYear = year;

                    String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
                    int todayDay = 0;
                    int todayMonth = 0;
                    int todayYear = 0;

                    String[] arrOfStr = date.split("-", 3);
                    for (int i = 0; i < 2; i++){
                        if (i == 0)
                            todayMonth = Integer.parseInt(arrOfStr[i]);
                        else if (i == 1)
                            todayDay = Integer.parseInt(arrOfStr[i]);
                        else
                            todayYear = Integer.parseInt(arrOfStr[i]);
                    }
                    daysLeft = (selectedMonth*31 + selectedDay + 31) - (todayMonth*31 + todayDay);

                }
            });

            confirm_name.setOnClickListener(view1 -> {
                String name = edit_goal_name.getText().toString();
                setUserInfo(name,daysLeft);
                setAdapter();

                txt.setVisibility(view.VISIBLE);
                add.setVisibility(view.VISIBLE);
                edit_goal_name.setVisibility(view.GONE);
                confirm_name.setVisibility(view.GONE);
                calendarView.setVisibility(view.GONE);
                recyclerView.setVisibility(view.VISIBLE);
                selectGoalDateTitle.setVisibility(view.GONE);
            });
        });



        //Navigation buttons
        search_Button = findViewById(R.id.search_Button);
        home_Button = findViewById(R.id.home_Button);
        profile_Button = findViewById(R.id.profile_Button);

        //Navigation Buttons
        profile_Button.setOnClickListener(view -> {
            startActivity(new Intent(SetReminders.this,Profile.class));
        });
        home_Button.setOnClickListener(view -> {
            startActivity(new Intent(SetReminders.this,HomePage.class));
        });
    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(itemsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setUserInfo(String name,int daysLeft) {
        itemsList.add(new item(name, daysLeft));
    }
}