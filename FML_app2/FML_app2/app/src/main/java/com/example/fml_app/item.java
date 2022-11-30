package com.example.fml_app;

public class item {
    private String goal_name;
    private int days_left;

    public item(String goal_name, int days_left){
        this.goal_name = goal_name;
        this.days_left = days_left;
    }

    public String get_goal_name(){
        return goal_name;
    }

    public String get_days_left(){
        return Integer.toString(days_left);
    }

    public void set_goal_name(String goal_name){
        this.goal_name = goal_name;
    }




}
