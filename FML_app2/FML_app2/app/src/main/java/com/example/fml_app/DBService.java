package com.example.fml_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBService extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LoseIt.db";


    private static final int DATABASE_VERSION = 1;


    public DBService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_NUTRITION_TABLE = "CREATE TABLE "
                + FMLData.DietInfoTable.TABLE_NAME + " ("
                + FMLData.DietInfoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FMLData.DietInfoTable.COLUMN_TITLE + " TEXT NOT NULL, "
                + FMLData.DietInfoTable.COLUMN_CALORIE_PER_UNIT + " TEXT NOT NULL, "
                + FMLData.DietInfoTable.COLUMN_UNIT_NUMBER + " TEXT NOT NULL, "
                + FMLData.DietInfoTable.COLUMN_UNIT_NAME + " TEXT NOT NULL" + ")";

        // Execute table creation
        db.execSQL(SQL_CREATE_NUTRITION_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FMLData.DietInfoTable.TABLE_NAME);
        onCreate(db);
    }


    public void insertAll(List<DailyDietItem> listMeal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < listMeal.size(); i++) {
            contentValues.put(FMLData.DietInfoTable.COLUMN_TITLE, listMeal.get(i).getTitle());
            contentValues.put(FMLData.DietInfoTable.COLUMN_CALORIE_PER_UNIT, listMeal.get(i).getCaloriePerUnit());
            contentValues.put(FMLData.DietInfoTable.COLUMN_UNIT_NUMBER, listMeal.get(i).getUnitNumber());
            contentValues.put(FMLData.DietInfoTable.COLUMN_UNIT_NAME, listMeal.get(i).getUnitName());
            db.insert(FMLData.DietInfoTable.TABLE_NAME, null, contentValues);
            contentValues.clear();
        }
    }


    public void insertElement(String title, int caloriePerUnit, double unitNumber, String unitName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FMLData.DietInfoTable.COLUMN_TITLE, title);
        contentValues.put(FMLData.DietInfoTable.COLUMN_CALORIE_PER_UNIT, caloriePerUnit);
        contentValues.put(FMLData.DietInfoTable.COLUMN_UNIT_NUMBER, unitNumber);
        contentValues.put(FMLData.DietInfoTable.COLUMN_UNIT_NAME, unitName);
        db.insert(FMLData.DietInfoTable.TABLE_NAME, null, contentValues);
        contentValues.clear();
    }


    public ArrayList<DailyDietItem> getAllMeals() {
        ArrayList<DailyDietItem> mealList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from nutrition", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            mealList.add(new DailyDietItem(
                    res.getString(1),
                    Integer.parseInt(res.getString(2)),
                    Double.parseDouble(res.getString(3)),
                    res.getString(4)
            ));
            res.moveToNext();
        }
        res.close();
        return mealList;
    }


    public ArrayList<DailyDietItem> searchMeals(String searchTitle) {
        ArrayList<DailyDietItem> mealList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM nutrition WHERE title LIKE '%" + searchTitle + "%'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            mealList.add(new DailyDietItem(
                    res.getString(1),
                    Integer.parseInt(res.getString(2)),
                    Double.parseDouble(res.getString(3)),
                    res.getString(4)
            ));
            res.moveToNext();
        }
        res.close();
        return mealList;
    }

    public int getElementID(String title) {
        Integer result;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT _id FROM nutrition WHERE title = '%" + title + "%'", null);
        res.moveToFirst();
        result = res.getInt(0);
        res.close();
        return result;
    }

    public ArrayList<DailyDietItem> getDistinctMeals() {
        ArrayList<DailyDietItem> mealList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select distinct * from nutrition", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            mealList.add(new DailyDietItem(
                    res.getString(1),
                    Integer.parseInt(res.getString(2)),
                    Double.parseDouble(res.getString(3)),
                    res.getString(4)
            ));
            res.moveToNext();
        }
        res.close();
        return mealList;
    }


    public void optimizeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DailyDietItem> list = getDistinctMeals();
        db.execSQL("DROP TABLE IF EXISTS " + FMLData.DietInfoTable.TABLE_NAME);
        onCreate(db);
        insertAll(list);
    }


    public void deleteElement(DailyDietItem toDelete) {
        SQLiteDatabase db = this.getReadableDatabase();
        String title = toDelete.getTitle();
        Integer calPerUnit = toDelete.getCaloriePerUnit();
        Double unitNumber = toDelete.getUnitNumber();
        String unitName = toDelete.getUnitName();
        String DELETE_ELEMENT = "DELETE FROM " + FMLData.DietInfoTable.TABLE_NAME
                + " WHERE title = " + title + " AND caloriePerUnit = " + calPerUnit
                + " AND unitNumber = " + unitNumber + " AND unitName = " + unitName;
        db.execSQL(DELETE_ELEMENT);
    }

}
