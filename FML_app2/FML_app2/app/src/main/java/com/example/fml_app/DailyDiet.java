package com.example.fml_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DailyDiet implements Serializable {


    private List<DailyDietItem> breakfastList;
    private List<DailyDietItem> lunchList;
    private List<DailyDietItem> dinnerList;
    private List<DailyDietItem> snackList;
    private List<DailyDietItem> exerciseList;
    private String date;


    public DailyDiet(String date) {
        this.date = date;
        breakfastList = new ArrayList<>();
        lunchList = new ArrayList<>();
        dinnerList = new ArrayList<>();
        snackList = new ArrayList<>();
        exerciseList = new ArrayList<>();
    }

    public int getBreakfastCalorie() {
        int res = 0;
        for (DailyDietItem foodItem : breakfastList) {
            res += foodItem.getTotalCalorie();
        }
        return res;
    }

    public int getLunchCalorie() {
        int res = 0;
        for (DailyDietItem foodItem : lunchList) {
            res += foodItem.getTotalCalorie();
        }
        return res;
    }

    public int getDinnerCalorie() {
        int res = 0;
        for (DailyDietItem foodItem : dinnerList) {
            res += foodItem.getTotalCalorie();
        }
        return res;
    }


    public int getSnackCalorie() {
        int res = 0;
        for (DailyDietItem foodItem : snackList) {
            res += foodItem.getTotalCalorie();
        }
        return res;
    }


    public int getExerciseCalorie() {
        int res = 0;
        for (DailyDietItem exerciseItem : exerciseList) {
            res += exerciseItem.getTotalCalorie();
        }
        return res;
    }

    public int getGainedCalorie() {
        return getBreakfastCalorie() + getDinnerCalorie() + getLunchCalorie() + getSnackCalorie();
    }


    public int getBurnedCalorie() {
        return getExerciseCalorie();
    }


    public int getRemainingCalorie(int goal) {
        return goal - getGainedCalorie() + getBurnedCalorie();
    }


    public List<DailyDietItem> getBreakfastList() {
        return breakfastList;
    }

    public void setBreakfastList(List<DailyDietItem> breakfastList) {
        this.breakfastList = breakfastList;
    }


    public List<DailyDietItem> getLunchList() {
        return lunchList;
    }


    public void setLunchList(List<DailyDietItem> lunchList) {
        this.lunchList = lunchList;
    }


    public List<DailyDietItem> getDinnerList() {
        return dinnerList;
    }


    public void setDinnerList(List<DailyDietItem> dinnerList) {
        this.dinnerList = dinnerList;
    }


    public List<DailyDietItem> getSnackList() {
        return snackList;
    }


    public void setSnackList(List<DailyDietItem> snackList) {
        this.snackList = snackList;
    }


    public List<DailyDietItem> getExerciseList() {
        return exerciseList;
    }


    public void setExerciseList(List<DailyDietItem> exerciseList) {
        this.exerciseList = exerciseList;
    }


    public void addBreakfastList(DailyDietItem food) {
        breakfastList.add(food);
    }


    public void addLunchList(DailyDietItem food) {
        lunchList.add(food);
    }


    public void addDinnerList(DailyDietItem food) {
        dinnerList.add(food);
    }


    public void addSnackList(DailyDietItem food) {
        snackList.add(food);
    }


    public void addExerciseList(DailyDietItem exercise) {
        exerciseList.add(exercise);
    }


    public void removeBreakfastList(int position) {
        if (position < breakfastList.size() && position >= 0) {
            breakfastList.remove(position);
        }
    }

    public void removeLunchList(int position) {
        if (position < lunchList.size() && position >= 0) {
            lunchList.remove(position);
        }
    }


    public void removeDinnerList(int position) {
        if (position < dinnerList.size() && position >= 0) {
            dinnerList.remove(position);
        }
    }

    public void removeSnackList(int position) {
        if (position < snackList.size() && position >= 0) {
            snackList.remove(position);
        }
    }


    public void removeExerciseList(int position) {
        if (position < exerciseList.size() && position >= 0) {
            exerciseList.remove(position);
        }
    }


    public void editBreakfastList(int position, DailyDietItem food) {
        if (position < breakfastList.size() && position >= 0) {
            breakfastList.set(position, food);
        }
    }


    public void editLunchList(int position, DailyDietItem food) {
        if (position < lunchList.size() && position >= 0) {
            lunchList.set(position, food);
        }
    }


    public void editDinnerList(int position, DailyDietItem food) {
        if (position < dinnerList.size() && position >= 0) {
            dinnerList.set(position, food);
        }
    }


    public void editSnackList(int position, DailyDietItem food) {
        if (position < snackList.size() && position >= 0) {
            snackList.set(position, food);
        }
    }


    public void editExerciseList(int position, DailyDietItem exercise) {
        if (position < exerciseList.size() && position >= 0) {
            exerciseList.set(position, exercise);
        }
    }

}
