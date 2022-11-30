package com.example.fml_app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PersonalData implements Serializable {

    private int goal;

    private Map<String, DailyDiet> stringDiaryHashMap;

    public PersonalData() {
        goal = 0;
        stringDiaryHashMap = new HashMap<>();
    }


    public DailyDiet getDiary(String dateString) {
        DailyDiet diary;
        if (stringDiaryHashMap.containsKey(dateString)) {
            diary = stringDiaryHashMap.get(dateString);
        } else {
            diary = new DailyDiet(dateString);
            stringDiaryHashMap.put(dateString, diary);
        }
        return diary;
    }

    public int getGoal() {
        return goal;
    }


    public void setGoal(int goal) {
        this.goal = goal;
    }
}
