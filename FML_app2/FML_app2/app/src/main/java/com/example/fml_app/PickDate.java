package com.example.fml_app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PickDate {


    public static String millisecondTimeToString(long millisecond){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resultDate = new Date(millisecond);
        return sdf.format(resultDate);
    }


    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date stringToDate(String dateString){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
