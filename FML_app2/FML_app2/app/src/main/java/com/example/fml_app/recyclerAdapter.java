package com.example.fml_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<item> itemsList;

    public recyclerAdapter(ArrayList<item> itemsList){
        this.itemsList = itemsList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;
        private TextView daysLeft;

        public MyViewHolder(final View view){
            super(view);
            nameTxt = view.findViewById(R.id.goal_name);
            daysLeft = view.findViewById(R.id.daysLeft);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = itemsList.get(position).get_goal_name();
        holder.nameTxt.setText(name);
        String days = itemsList.get(position).get_days_left();
        holder.daysLeft.setText(days + " days left!");

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
