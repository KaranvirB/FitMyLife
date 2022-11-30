package com.example.fml_app;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    int[] pictures;
    String[] exerciseTypes;

    private LayoutInflater inflater;

    CustomAdapter(Context context,String[]exerciseTypes, int[]pictures) {
        this.context = context;
        this.exerciseTypes=exerciseTypes;
        this.pictures=pictures;
    }

    @Override
    public int getCount() {
        return exerciseTypes.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sample_view,parent,false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewID);
        TextView textView = (TextView)convertView.findViewById(R.id.textViewID);

        imageView.setImageResource(pictures[position]);
        textView.setText(exerciseTypes[position]);

        return convertView;
    }
}
