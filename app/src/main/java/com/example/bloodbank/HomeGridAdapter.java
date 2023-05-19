package com.example.bloodbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeGridAdapter extends BaseAdapter {
    Context context;
    String[] text;
    int[] value1;

    public HomeGridAdapter(Context context, String[] text, int[] value1) {
        this.context = context;
        this.text = text;
        this.value1 = value1;
    }
    @Override
    public int getCount() {
        return text.length;
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
        View grid;
        LayoutInflater lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            grid = lf.inflate(R.layout.home_grid_adqpter,null);
            TextView txtNum = grid.findViewById(R.id.txt_number_grid);
            txtNum.setText(Integer.toString(value1[position]));
            TextView txtText = grid.findViewById(R.id.txt_text_grid);
            txtText.setText(text[position]);
        }
        else {
            grid = (View) convertView;
        }
        return grid;
    }
}
