package com.example.fml_app;

import static com.example.fml_app.SearchNutrientsActivity.CREATE_FOOD_IN_SEARCH;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class ListSearcFoodAdapter extends BaseAdapter {


    private List<DailyDietItem> foodList;

    private Context context;


    public ListSearcFoodAdapter(List<DailyDietItem> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return foodList.size();
    }


    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.food_content, null);
        }
        ((TextView) view.findViewById(R.id.diary_content_title)).setText(foodList.get(i).getTitle());
        ((TextView) view.findViewById(R.id.diary_content_value)).setText(Integer.toString(foodList.get(i).getCaloriePerUnit()));
        ((TextView) view.findViewById(R.id.diary_content_amount)).setText(Double.toString(foodList.get(i).getUnitNumber()) + " " + foodList.get(i).getUnitName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddFoodActivity.class);
                intent.putExtra("Food", foodList.get(i));
                ((SearchNutrientsActivity) context).startActivityForResult(intent, CREATE_FOOD_IN_SEARCH);
            }

        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle(context.getResources().getString(R.string.general_alert));
                alertDialog.setMessage(context.getResources().getString(R.string.delete_item_alert));
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getResources().getString(R.string.cancel), (DialogInterface.OnClickListener) null);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
                return false;
            }
        });
        return view;
    }


}
