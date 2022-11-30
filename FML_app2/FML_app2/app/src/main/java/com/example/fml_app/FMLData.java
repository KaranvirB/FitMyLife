package com.example.fml_app;

import android.provider.BaseColumns;


public class FMLData {

    private FMLData() {

    }


    public static final class DietInfoTable implements BaseColumns {

        public final static String TABLE_NAME = "nutrition";

        public final static String COLUMN_TITLE = "title";

        public final static String COLUMN_CALORIE_PER_UNIT = "caloriePerUnit";

        public final static String COLUMN_UNIT_NUMBER = "unitNumber";

        public final static String COLUMN_UNIT_NAME = "unitName";
    }
}
