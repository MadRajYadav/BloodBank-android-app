package com.example.bloodbank;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    List<DataModel> dataModel;
    int[] daysAgo, distance;
    public GridViewAdapter(Context context, List<DataModel> dataModel, int[] daysAgo, int[] distance) {
        this.context = context;
       this.dataModel = dataModel;
       this.daysAgo = daysAgo;
       this.distance = distance;
    }

    @Override
    public int getCount() {
        return dataModel.size();
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
    public View getView(int i, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            grid = new View(context);
            grid = lf.inflate(R.layout.grid_view_adapter,null);
            // setting values to the View
            TextView txtBloodGroup = grid.findViewById(R.id.txt_blood_group);
            txtBloodGroup.setText(dataModel.get(i).blood_group);

            TextView txtName = grid.findViewById(R.id.txt_name);
            txtName.setText(dataModel.get(i).name);

            TextView txtNumber= grid.findViewById(R.id.txt_number);
            txtNumber.setText(dataModel.get(i).number);

            TextView txtType = grid.findViewById(R.id.txt_donor_type);
            txtType.setText(dataModel.get(i).donor_type);

            TextView txtStateCity = grid.findViewById(R.id.txt_state_city);
            txtStateCity.setText(dataModel.get(i).state+", "+dataModel.get(i).city);

            TextView txtCountry= grid.findViewById(R.id.txt_country);
            txtCountry.setText(dataModel.get(i).country);

            TextView txtDistance= grid.findViewById(R.id.txt_distance1);
            txtDistance.setText(Integer.toString(distance[i])+" KM");

            TextView txtDaysAgo= grid.findViewById(R.id.txt_day_ego);
            txtDaysAgo.setText(Integer.toString(daysAgo[i])+" Days Ago");

            TextView txtPoints= grid.findViewById(R.id.txt_points);
            txtPoints.setText(Integer.toString(dataModel.get(i).points)+" Points");

            TextView txtSeen= grid.findViewById(R.id.txt_seen);
            txtSeen.setText(Integer.toString(dataModel.get(i).eye));

        }
        else {
            grid = (View) convertView;
        }
        return grid;
    }


}
