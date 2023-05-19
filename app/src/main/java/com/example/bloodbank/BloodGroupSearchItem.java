package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BloodGroupSearchItem extends AppCompatActivity {
    GridView grid;
    TextView txtPopupBloodGroupName;

    int[] distance = {10, 5, 90,200,100}, daysAgo = {80, 6, 10, 12 , 15};
    Button btnPopupCancel, btnCall;
    String textKey;
    ImageView btnDonor,btnExplore, btnProfile;
    ImageButton imgBtnBack;
    List<DataModel> dataModel,searchedDataModel;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_group_search_item);
        searchedDataModel = new ArrayList<>();
        //getting data from SqLiteDatabase
        MySqliteHelper db = new MySqliteHelper(BloodGroupSearchItem.this);
        dataModel = db.getUserDataAll();

        // getting data which is sent through intent
        Intent intent = getIntent();
        textKey = intent.getStringExtra("KEY Group");

        Search(dataModel, textKey);
        txtPopupBloodGroupName = findViewById(R.id.txt_blood_group_bar);
        txtPopupBloodGroupName.setText( textKey);

        if(searchedDataModel.size() == 0){
            Toast.makeText(this, textKey + " type of blood not found in the BloodBank.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        grid = findViewById(R.id.blood_group_search_grid);
        GridViewAdapter adapter = new GridViewAdapter(this,searchedDataModel,daysAgo,distance);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                setDataToDisplayView(searchedDataModel.get(i).name,searchedDataModel.get(i).donor_type,distance[i], searchedDataModel.get(i).blood_group, searchedDataModel.get(i).number, searchedDataModel.get(i).country, searchedDataModel.get(i).state, searchedDataModel.get(i).city,searchedDataModel.get(i).last_donation_date,
                        searchedDataModel.get(i).points, searchedDataModel.get(i).eye, searchedDataModel.get(i).habits, searchedDataModel.get(i).address, searchedDataModel.get(i).member_since);
                number = dataModel.get(i).number;

            }
        });

        // re directing to other page by buttom click
        btnDonor = findViewById(R.id.btn_donor);
        btnDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodGroupSearchItem.this,Donor.class);
                startActivity(intent);
            }
        });

        btnExplore = findViewById(R.id.btn_explore);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodGroupSearchItem.this,BloodBank.class);
                startActivity(intent);
            }
        });
        btnProfile = findViewById(R.id.btn_profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Profile.class);
                startActivity(intent);
            }
        });




        btnPopupCancel = findViewById(R.id.popup_cancel_btn);

        btnPopupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View linearLayout = findViewById(R.id.popup_view_display);
                linearLayout.setVisibility(View.GONE);
                linearLayout.setClickable(false);

            }
        });


        btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:0"+number.trim()));
                    startActivity(call);
                } catch (Exception e) {
                    Toast.makeText(BloodGroupSearchItem.this, "Calling permission error!, You need to give Calling permission first.", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgBtnBack = findViewById(R.id.back_img_btn);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    private void setDataToDisplayView(String name,String donorType, int distance, String bloodGroup, String number, String country, String state, String city, String lastDonationDate,
                                      int points, int profileViews, String habits, String address, String memberSince){

        // making popup linear layout visible or clickable who describes donor profile
        View linearLayout = findViewById(R.id.popup_view_display);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setClickable(true);
// setting value to the textview
        TextView txtName = findViewById(R.id.popup_name);
        txtName.setText(name);
        TextView txtType = findViewById(R.id.display_donor_type);
        txtType.setText(donorType);
        TextView txtDis = findViewById(R.id.display_donor_distance);
        txtDis.setText(Integer.toString(distance)+" KM");
        TextView txtBGroup = findViewById(R.id.display_donor_bloodgroup);
        txtBGroup.setText(bloodGroup);
        TextView txtNumber = findViewById(R.id.display_donor_number);
        txtNumber.setText(number);
        TextView txtCountry = findViewById(R.id.display_donor_country);
        txtCountry.setText(country);
        TextView txtState = findViewById(R.id.display_donor_state);
        txtState.setText(state);
        TextView txtCity = findViewById(R.id.display_donor_city);
        txtCity.setText(city);
        TextView txtLDDate = findViewById(R.id.display_donor_last_donation_date);
        txtLDDate.setText(lastDonationDate);
        TextView txtPoints = findViewById(R.id.display_donor_points);
        txtPoints.setText(Integer.toString(points));
        TextView txtPView = findViewById(R.id.display_donor_views);
        txtPView.setText(Integer.toString(profileViews));
        TextView txtHabit = findViewById(R.id.display_donor_habits);
        txtHabit.setText(habits);
        TextView txtAddress = findViewById(R.id.display_donor_address);
        txtAddress.setText(address);
        TextView txtMemberSince = findViewById(R.id.display_donor_member_since);
        txtMemberSince.setText(memberSince);

    }

    private void Search(List<DataModel>  dataModel, String key){
        int len = dataModel.size();

        for(int i=0;i<len;i++){

            if(key.equals(dataModel.get(i).blood_group)){

                searchedDataModel.add(dataModel.get(i));
            }
        }
    }


}




















