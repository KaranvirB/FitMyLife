package com.example.fml_app;

import static com.example.fml_app.Storage.loadFromStorage;
import static com.example.fml_app.Storage.writeToStorage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {
    Button goBack;

    private static String currentDateString;

    private static PersonalData personalData;

    private DBService dbService;

    public static final int REQUEST_ADD_BREAKFAST = 11;

    public static final int REQUEST_ADD_LUNCH = 12;

    public static final int REQUEST_ADD_DINNER = 13;

    public static final int REQUEST_ADD_SNACKS = 14;

    public static final int REQUEST_ADD_EXERCISE = 15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        try {
            dbService = new DBService(this);
            dbService.optimizeDB();
        } catch (SQLiteDatabaseCorruptException ex) {
            Toast.makeText(MainActivity2.this, "Cannot open db", Toast.LENGTH_LONG).show();
        }
        personalData = loadFromStorage(MainActivity2.this);
        currentDateString = PickDate.millisecondTimeToString(System.currentTimeMillis());
        setToolbar();
        setDate();

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity2.this,HomePage.class));
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        setAllDataInMain();
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeToStorage(MainActivity2.this, personalData);
        dbService.close();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void setToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_settings_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
                setAllDataInMain();
            }
        });
    }

    private void setDate() {
        final TextView textView = findViewById(R.id.main_toolbar_title);
        textView.setText(currentDateString);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = PickDate.stringToDate(currentDateString);
                Dialog dialog = new DatePickerDialog(MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        currentDateString = PickDate.dateToString(new Date(year - 1900, monthOfYear, dayOfMonth));
                        textView.setText(currentDateString);
                        setAllDataInMain();
                    }
                }, date.getYear() + 1900, date.getMonth(), date.getDate());
                dialog.show();
            }
        });
    }


    private void showInputDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_text, null);
        final EditText editText = view.findViewById(R.id.text_editor_of_calorie_restriction);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.set_goal_hint);
        builder.setView(editText);
        builder.setPositiveButton(R.string.dialog_positive_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("")){
                    return;
                }
                personalData.setGoal(Integer.parseInt(editText.getText().toString()));
                setAllDataInMain();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }


    private void setTableData() {
        DailyDiet dailyFood = personalData.getDiary(currentDateString);
        ((TextView) findViewById(R.id.goal_number)).setText(String.format("%,d", personalData.getGoal()));
        ((TextView) findViewById(R.id.food_number)).setText(String.format("%,d", dailyFood.getGainedCalorie()));
        ((TextView) findViewById(R.id.exercise_number)).setText(String.format("%,d", dailyFood.getBurnedCalorie()));
        ((TextView) findViewById(R.id.remaining_number)).setText(String.format("%,d", dailyFood.getRemainingCalorie(personalData.getGoal())));
    }

    private void setListData(int R_id_header, int R_id_footer, int R_id_item, final List<DailyDietItem> itemList, int totalCalorie, final int R_string_item, int R_string_add_item) {
        View header = getLayoutInflater().inflate(R.layout.food_content_header, null);
        View footer = getLayoutInflater().inflate(R.layout.food_content_footer, null);
        ((TextView) header.findViewById(R.id.diary_tag_title)).setText(R_string_item);
        ((TextView) header.findViewById(R.id.diary_tag_value)).setText(Integer.toString(totalCalorie));
        ((TextView) footer.findViewById(R.id.diary_add_button)).setText(R_string_add_item);
        ((FrameLayout) findViewById(R_id_header)).addView(header);
        ((FrameLayout) findViewById(R_id_footer)).addView(footer);

        RecyclerView listViewItem = findViewById(R_id_item);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity2.this, LinearLayoutManager.VERTICAL, false);
        listViewItem.setLayoutManager(layoutManager);
        ListItemAdapter listItemAdapter = new ListItemAdapter(new ArrayList<>(itemList));
        listItemAdapter.setOnItemClickListner(new ListItemAdapter.OnItemClickListner() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemClick(int position) {
                Intent intent;
                switch (R_string_item) {
                    case R.string.diary_meal_breakfast:
                        intent = new Intent(MainActivity2.this, EditMealActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Food", (DailyDietItem) itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "breakfast");
                        startActivityForResult(intent, REQUEST_ADD_BREAKFAST);
                        break;
                    case R.string.diary_meal_lunch:
                        intent = new Intent(MainActivity2.this, EditMealActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Food", (DailyDietItem) itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "lunch");
                        startActivityForResult(intent, REQUEST_ADD_LUNCH);
                        break;
                    case R.string.diary_meal_dinner:
                        intent = new Intent(MainActivity2.this, EditMealActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Food", (DailyDietItem) itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "dinner");
                        startActivityForResult(intent, REQUEST_ADD_DINNER);
                        break;
                    case R.string.diary_meal_snacks:
                        intent = new Intent(MainActivity2.this, EditMealActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Food", (DailyDietItem) itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "snack");
                        startActivityForResult(intent, REQUEST_ADD_SNACKS);
                        break;
                    case R.string.diary_exercise:
                        intent = new Intent(MainActivity2.this, EditExerciseActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Exercise", itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        startActivity(intent);
                        startActivityForResult(intent, REQUEST_ADD_EXERCISE);
                        break;
                }
            }
        });
        listViewItem.setAdapter(listItemAdapter);

        //set footer (add button)
        footer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (R_string_item) {
                    case R.string.diary_meal_breakfast:
                        intent = new Intent(MainActivity2.this, SearchNutrientsActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "breakfast");
                        startActivity(intent);
                        break;
                    case R.string.diary_meal_lunch:
                        intent = new Intent(MainActivity2.this, SearchNutrientsActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "lunch");
                        startActivity(intent);
                        break;
                    case R.string.diary_meal_dinner:
                        intent = new Intent(MainActivity2.this, SearchNutrientsActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "dinner");
                        startActivity(intent);
                        break;
                    case R.string.diary_meal_snacks:
                        intent = new Intent(MainActivity2.this, SearchNutrientsActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "snack");
                        startActivity(intent);
                        break;
                    case R.string.diary_exercise:
                        intent = new Intent(MainActivity2.this, AddExerciseActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        startActivity(intent);
                        break;
                }
            }
        });

    }


    private void setAllDataInMain(){
        setTableData();
        DailyDiet dailyFood = personalData.getDiary(currentDateString);
        setListData(R.id.diary_breakfast_header, R.id.diary_breakfast_footer, R.id.diary_breakfast, dailyFood.getBreakfastList() , dailyFood.getBreakfastCalorie(), R.string.diary_meal_breakfast, R.string.diary_add_meal);
        setListData(R.id.diary_lunch_header, R.id.diary_lunch_footer, R.id.diary_lunch, dailyFood.getLunchList() , dailyFood.getLunchCalorie(), R.string.diary_meal_lunch, R.string.diary_add_meal);
        setListData(R.id.diary_dinner_header, R.id.diary_dinner_footer, R.id.diary_dinner, dailyFood.getDinnerList() , dailyFood.getDinnerCalorie(), R.string.diary_meal_dinner, R.string.diary_add_meal);
        setListData(R.id.diary_snack_header, R.id.diary_snack_footer, R.id.diary_snack, dailyFood.getSnackList() , dailyFood.getSnackCalorie(), R.string.diary_meal_snacks, R.string.diary_add_meal);
        setListData(R.id.diary_exercise_header, R.id.diary_exercise_footer, R.id.diary_exercise, dailyFood.getExerciseList() , dailyFood.getExerciseCalorie(), R.string.diary_exercise , R.string.diary_add_exercise);

    }

    public static PersonalData getPersonalData() {
        return personalData;
    }

}
