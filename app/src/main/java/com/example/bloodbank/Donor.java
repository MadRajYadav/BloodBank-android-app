package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class Donor extends AppCompatActivity {
    String[] bloodGroup = {"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    int[] distance = {10, 5, 90,200,100},daysAgo = {80, 6, 10, 12 , 15};
    GridView resentGrid, nearbyGrid, popularGrid, bloodGroupGrid;
    ImageButton btnHome,btnExplore,btnProfile;
    Button btnAddDonor, btnPopupCancel, btnCall;
    ImageButton imgBtnBack;
    List<DataModel> dataModel;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        //getting data from SqLiteDatabase
        MySqliteHelper db = new MySqliteHelper(Donor.this);
        dataModel = db.getUserDataAll();

        bloodGroupGrid = findViewById(R.id.blood_group_gridview);
        GridViewAdapterBloodGroup adapterBloodGroup =new GridViewAdapterBloodGroup(bloodGroup,Donor.this);
        // giving width to the grid layout
        View view0 = findViewById(R.id.blood_group_gridview);
        ViewGroup.LayoutParams layoutParams0 = view0.getLayoutParams();
        layoutParams0.width = 65* bloodGroup.length*2;
        view0.setLayoutParams(layoutParams0);
        //giving number of colum to the gridview
        bloodGroupGrid.setNumColumns(bloodGroup.length);
        bloodGroupGrid.setAdapter(adapterBloodGroup);

        bloodGroupGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),BloodGroupSearchItem.class);
                intent.putExtra("KEY Group",bloodGroup[i]);
                startActivity(intent);
            }
        });


        resentGrid = findViewById(R.id.resent_blood_donation);
        // making adapter
        GridViewAdapter adapter = new GridViewAdapter(this,dataModel,daysAgo,distance);

        // giving width to the grid layout
        View view = findViewById(R.id.resent_blood_donation);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = 170* dataModel.size()*2;
        view.setLayoutParams(layoutParams);

        //giving number of colum to the gridview
        resentGrid.setNumColumns(dataModel.size());
        // setting adapter to the gridview
        resentGrid.setAdapter(adapter);

        resentGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                setDataToDisplayView(dataModel.get(i).name,dataModel.get(i).donor_type,distance[i], dataModel.get(i).blood_group, dataModel.get(i).number, dataModel.get(i).country, dataModel.get(i).state, dataModel.get(i).city,dataModel.get(i).last_donation_date,
                        dataModel.get(i).points, dataModel.get(i).eye, dataModel.get(i).habits, dataModel.get(i).address, dataModel.get(i).member_since);
                number = dataModel.get(i).number;
            }
        });

        // same repeated
        popularGrid = findViewById(R.id.popular_blood_donation);
        View view1 = findViewById(R.id.popular_blood_donation);
        ViewGroup.LayoutParams layoutParams1 = view1.getLayoutParams();
        layoutParams1.width = 170* dataModel.size()*2;
        view1.setLayoutParams(layoutParams1);
        popularGrid.setNumColumns(dataModel.size());
        popularGrid.setAdapter(adapter);
        popularGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                setDataToDisplayView(dataModel.get(i).name,dataModel.get(i).donor_type,distance[i], dataModel.get(i).blood_group, dataModel.get(i).number, dataModel.get(i).country, dataModel.get(i).state, dataModel.get(i).city,dataModel.get(i).last_donation_date,
                        dataModel.get(i).points, dataModel.get(i).eye, dataModel.get(i).habits, dataModel.get(i).address, dataModel.get(i).member_since);
                number = dataModel.get(i).number;
            }
        });

        // same repeated
        nearbyGrid = findViewById(R.id.nearby_blood_donation);
        View view2 = findViewById(R.id.nearby_blood_donation);
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        layoutParams2.width = 170* dataModel.size()*2;
        view2.setLayoutParams(layoutParams2);
        nearbyGrid.setNumColumns(dataModel.size());
        nearbyGrid.setAdapter(adapter);
        nearbyGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                setDataToDisplayView(dataModel.get(i).name,dataModel.get(i).donor_type,distance[i], dataModel.get(i).blood_group, dataModel.get(i).number, dataModel.get(i).country, dataModel.get(i).state, dataModel.get(i).city,dataModel.get(i).last_donation_date,
                        dataModel.get(i).points, dataModel.get(i).eye, dataModel.get(i).habits, dataModel.get(i).address, dataModel.get(i).member_since);
                number = dataModel.get(i).number;
            }
        });



        // re directing to other page by buttom click
        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Donor.this, Home.class);
                startActivity(intent);
            }
        });

        btnExplore = findViewById(R.id.btn_explore);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Donor.this,BloodBank.class);
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

        btnAddDonor = findViewById(R.id.btn_add_donor);
        btnAddDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Donor.this,addDonor.class);
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
                    Toast.makeText(Donor.this, e.toString(), Toast.LENGTH_LONG).show();
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


}