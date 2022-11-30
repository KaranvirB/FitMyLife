package com.example.fml_app;

import java.util.List;

public class NutrientsSearchResult {

    public int total_hits;

    public double max_score;

    public List<NutrientsGoal> hits;


    public class NutrientsGoal {

        public String _index;

        public String _type;

        public String _id;

        public double _score;

        public NutrientsItem fields;

        public class NutrientsItem {


            public String item_name;
            public String brand_name;

            public double nf_calories;

            public double nf_serving_size_qty;

            public String nf_serving_size_unit;

        }
    }
}
