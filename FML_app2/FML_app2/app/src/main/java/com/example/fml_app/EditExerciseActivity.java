package com.example.fml_app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditExerciseActivity extends AppCompatActivity {


    public static final int DELETE_RESULT_OK = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        // process data passed in
        DailyDietItem exercise = (DailyDietItem) getIntent().getSerializableExtra("Exercise");
        final int position = getIntent().getIntExtra("position", -1);
        final String dateString = getIntent().getStringExtra("currentDateString");
        if (exercise != null){
            ((EditText) findViewById(R.id.diary_content_edit_title_edit)).setText(exercise.getTitle());
            ((EditText) findViewById(R.id.diary_content_edit_calory_per_unit_edit)).setText(Integer.toString(exercise.getCaloriePerUnit()));
            ((EditText) findViewById(R.id.diary_content_edit_amount_edit)).setText(Double.toString(exercise.getUnitNumber()));
            ((EditText) findViewById(R.id.diary_content_edit_unit_name_edit)).setText(exercise.getUnitName());
        }

        //toolbar related
        Toolbar toolbar = findViewById(R.id.toolbar_edit_exercise);
        toolbar.inflateMenu(R.menu.edit);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.toolbar_edit_delete:
                        showDeleteAlertDialog(position);
                        break;
                    case R.id.toolbar_edit_done:
                        String titleText = ((EditText) findViewById(R.id.diary_content_edit_title_edit)).getText().toString();
                        String calPerUnitText = ((EditText) findViewById(R.id.diary_content_edit_calory_per_unit_edit)).getText().toString();
                        String amountText = ((EditText) findViewById(R.id.diary_content_edit_amount_edit)).getText().toString();
                        String unitNameText = ((EditText) findViewById(R.id.diary_content_edit_unit_name_edit)).getText().toString();
                        if (titleText.equals("") || calPerUnitText.equals("") || amountText.equals("") || unitNameText.equals("")) {
                            showErrorDialog();
                        } else {
                            int caloryPerUnit = (int) Double.parseDouble(calPerUnitText);
                            double unitNumber = Double.parseDouble(amountText);
                            DailyDietItem exercise = new DailyDietItem(titleText, caloryPerUnit, unitNumber, unitNameText);
                            String dateString = getIntent().getStringExtra("currentDateString");
                            MainActivity2.getPersonalData().getDiary(dateString).editExerciseList(position, exercise);
                            finish();
                        }
                        return true;
                }
                return false;
            }
        });
    }


    private void showErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.general_alert));
        alertDialog.setMessage(getString(R.string.required_item_empty_error));
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.dismiss), (DialogInterface.OnClickListener) null);
        alertDialog.show();
    }

    private void showDeleteAlertDialog(final int position){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.general_alert));
        alertDialog.setMessage(getString(R.string.delete_item_alert));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String dateString = getIntent().getStringExtra("currentDateString");
                MainActivity2.getPersonalData().getDiary(dateString).removeExerciseList(position);
                finish();
            }
        });
        alertDialog.show();
    }
}
