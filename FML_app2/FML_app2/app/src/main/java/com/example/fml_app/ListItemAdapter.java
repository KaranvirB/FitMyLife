package com.example.fml_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private List<DailyDietItem> itemList = new ArrayList<>();

    public ListItemAdapter(List<DailyDietItem> itemList) {
        this.itemList = itemList;
    }


    private OnItemClickListner onItemClickListner;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (!itemList.isEmpty()) {

            holder.title.setText((itemList.get(position)).getTitle());
            holder.value.setText(Integer.toString((itemList.get(position)).getTotalCalorie()));
            holder.amount.setText(Double.toString((itemList.get(position)).getUnitNumber()) + " " + ((DailyDietItem) itemList.get(position)).getUnitName());
            holder.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClick(position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView value;
        TextView amount;

        RelativeLayout all;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.diary_content_title);
            value = itemView.findViewById(R.id.diary_content_value);
            amount = itemView.findViewById(R.id.diary_content_amount);
            all = itemView.findViewById(R.id.diary_item_layout);
        }
    }

    public interface OnItemClickListner {
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;


    }
}