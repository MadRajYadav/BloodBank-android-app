package com.example.bloodbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;

public class GridViewAdapterBloodGroup extends BaseAdapter {
    String[] bloodGroup;
    Context context;

    public GridViewAdapterBloodGroup(String[] bloodGroup, Context context) {
        this.bloodGroup = bloodGroup;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bloodGroup.length;
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
            grid = lf.inflate(R.layout.grid_view_adapter_blood_group,null);
            TextView txt = grid.findViewById(R.id.blood_group_grid);
            txt.setText(bloodGroup[position]);
        }
        else {
            grid = (View) convertView;
        }
        return  grid;
    }
}
