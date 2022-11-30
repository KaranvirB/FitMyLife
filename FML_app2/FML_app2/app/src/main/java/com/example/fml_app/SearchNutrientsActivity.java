package com.example.fml_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SearchNutrientsActivity extends AppCompatActivity {


    public static final int CREATE_FOOD_IN_SEARCH = 21;

    public static final int CONNECT_TIMEOUT = 5000;

    private static final int CONNECT_ERROR = -1;

    private DBService dbService;


    String meal;

    String currentDateString;

    String similarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbService = new DBService(this);
        setContentView(R.layout.activity_search_nutrients);
        meal = getIntent().getStringExtra("whichMeal");
        currentDateString = getIntent().getStringExtra("currentDateString");
        Toolbar toolbar = findViewById(R.id.toolbar_search_meal);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout linearLayout = findViewById(R.id.search_food_result_from_internet);
        View createFoodButton = getLayoutInflater().inflate(R.layout.create_food_bt, null);
        linearLayout.addView(createFoodButton);

        (createFoodButton.findViewById(R.id.create_food_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(SearchNutrientsActivity.this,
                                AddFoodActivity.class), CREATE_FOOD_IN_SEARCH);
                    }
                });

        SearchView searchView = findViewById(R.id.search_meal_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchNutrientsAsyncTask SearchNutrientsAsyncTask = new SearchNutrientsAsyncTask();
                SearchNutrientsAsyncTask.execute(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        EditText textView = searchView.findViewById(
                androidx.appcompat.R.id.search_src_text);
        textView.setHintTextColor(ContextCompat.getColor(SearchNutrientsActivity.this,
                R.color.colorGrey));
        textView.setTextColor(ContextCompat.getColor(SearchNutrientsActivity.this,
                R.color.colorLightText));

        ImageView icon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        icon.setColorFilter(SearchNutrientsActivity.this.getResources().getColor(R.color.colorGrey));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FOOD_IN_SEARCH && resultCode == RESULT_OK) {
            DailyDietItem food = (DailyDietItem) data.getSerializableExtra("Food");
            DailyDiet diary = MainActivity2.getPersonalData().getDiary(currentDateString);
            switch (meal){
                case "breakfast":
                    diary.addBreakfastList(food);
                    break;
                case "lunch":
                    diary.addLunchList(food);
                    break;
                case "dinner":
                    diary.addDinnerList(food);
                    break;
                case "snack":
                    diary.addSnackList(food);
                    break;
            }
            finish();
        }
    }

    private class SearchNutrientsAsyncTask extends AsyncTask<String, Integer, List<DailyDietItem>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LinearLayout linearLayout = findViewById(R.id.search_food_result_from_internet);
            linearLayout.removeAllViews();
            linearLayout.addView(getLayoutInflater().inflate(R.layout.search_progress_circle, null));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == CONNECT_ERROR){
                Toast.makeText(SearchNutrientsActivity.this,
                        R.string.internet_connection_error, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected List<DailyDietItem> doInBackground(String... strings) {
            similarTitle = strings[0];
            String appId = "d185bfb4";
            String appKey = "357f1fb8996ecfeecade8c8f9b9b178c";
            String initialPart = "https://api.nutritionix.com/v1_1/search/";
            String query = strings[0].replace(" ", "%20");
            String resultsNum = "?results=0:20&";
            String fields = "fields=item_name,brand_name,nf_calories,nf_serving_size_qty,nf_serving_size_unit&";
            String idAndKey = "appId=" + appId + "&appKey=" + appKey;
            String URLString = initialPart + query + resultsNum + fields + idAndKey;

            try {
                URL url = new URL(URLString);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.connect();

                int responseCode = httpsURLConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK){
                    InputStreamReader inputStreamReader = new InputStreamReader(httpsURLConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line);
                    }
                    String resultString = stringBuilder.toString();
                    Gson gson = new Gson();
                    NutrientsSearchResult results = gson.fromJson(resultString, NutrientsSearchResult.class);
                    List<DailyDietItem> foodList = new ArrayList<>();
                    for (NutrientsSearchResult.NutrientsGoal hit :results.hits){
                        NutrientsSearchResult.NutrientsGoal.NutrientsItem item = hit.fields;
                        foodList.add(new DailyDietItem(item.item_name + ", " + item.brand_name,
                                (int) item.nf_calories, item.nf_serving_size_qty,
                                item.nf_serving_size_unit));
                    }
                    return foodList;

                }else{
                    publishProgress(CONNECT_ERROR);
                }

            } catch (MalformedURLException e) {
                publishProgress(CONNECT_ERROR);
                e.printStackTrace();

            } catch (IOException e) {
                publishProgress(CONNECT_ERROR);
                e.printStackTrace();
            }

            return new ArrayList<>();
        }
        @Override
        protected void onPostExecute(final List<DailyDietItem> foods) {
            if(!foods.isEmpty())
                super.onPostExecute(foods);
            else
                super.onPostExecute(dbService.getAllMeals());
            dbService.insertAll(foods);
            LinearLayout linearLayout = findViewById(R.id.search_food_result_from_internet);
            linearLayout.removeAllViews();
            // hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            View createFoodButton;

            if (foods.isEmpty()) {
                linearLayout.addView(getLayoutInflater().inflate(R.layout.activity_nutrients_result, null));
                ListView listView = findViewById(R.id.search_meal_result_list);
                listView.setAdapter(new ListSearcFoodAdapter(dbService.searchMeals(similarTitle), SearchNutrientsActivity.this));
                createFoodButton = getLayoutInflater().inflate(R.layout.create_food_bt, null);
                listView.addFooterView(createFoodButton);
                listView.setDivider(null);
            } else {
                linearLayout.addView(getLayoutInflater().inflate(R.layout.activity_nutrients_result, null));
                ListView listView = findViewById(R.id.search_meal_result_list);
                listView.setAdapter(new ListSearcFoodAdapter(foods, SearchNutrientsActivity.this));
                createFoodButton = getLayoutInflater().inflate(R.layout.create_food_bt, null);
                listView.addFooterView(createFoodButton);
                listView.setDivider(null);
            }
            (createFoodButton.findViewById(R.id.create_food_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchNutrientsActivity.this, AddFoodActivity.class);
                    startActivityForResult(intent, CREATE_FOOD_IN_SEARCH);
                }
            });
        }
    }
}
