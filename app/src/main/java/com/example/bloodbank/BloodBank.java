package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BloodBank extends AppCompatActivity {
//    String[] bloodGroup = {"A+","B+","O-","A+","B-"}, name = {"Bipin","Rahul","Vishnu","Raj Yadav","Aabha Yadav"}, type = {"Paid","Free","Paid","Free","Free"}, city = {"Patna","Bhagalpur","Danapur","Asarganj","Patna"},
//            state = {"Bihar","Bihar","Bihar","Bihar","Bihar"}, country = {"India","India","India","India","India"},number ={"8051379968","8051379968","8051379968","8051379968","8051379968"},lastDonationDate = {"12/1/2022","30/1/2023","18/7/2022","21/5/2021","25/7/2022"}
//            , habits = {"Cricket","Football","Reading", "Writing","Singing"}, address = {"Patna","Bhagalpur","Danapur","Asarganj","Patna"}, memberSince = {"12/1/2022","30/1/2023","18/7/2022","21/5/2021","25/7/2022"};
    int[] distance = {10, 5, 90,200,100}, daysAgo = {80, 6, 10, 12 , 15}, points = {6, 8, 4, 10, 4}, eye = {10, 17, 19, 40,98};
//    int count = 5;
    List<DataModel> dataModel;
    ImageButton btnHome, btnDonor, imgBtnBack, btnProfile;
    GridView resentGrid, nearbyGrid, popularGrid;
    Button btnAddDonor, btnPopupCancel, btnCall;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloodbank);

        //getting data from SqLiteDatabase
        MySqliteHelper db = new MySqliteHelper(BloodBank.this);
        dataModel = db.getUserDataAll();



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
                Intent intent = new Intent(BloodBank.this, Home.class);
                startActivity(intent);
            }
        });
        btnDonor = findViewById(R.id.btn_donor);
        btnDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodBank.this,Donor.class);
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
                Intent intent = new Intent(BloodBank.this,addDonor.class);
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
                    Toast.makeText(BloodBank.this, "Calling permission error!, You need to give Calling permission first.", Toast.LENGTH_LONG).show();
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


//
//    String[] bloodGroup =new String[6], name = new String[6], type = new String[6], city = new String[6],
//            state =new String[6], country = new String[6],number =new String[6],lastDonationDate = new String[6],
//            habits = new String[6], address = new String[6], memberSince = new String[6];
//    int[] distance = new int[6], daysAgo = new int[6], points = new int[6], eye = new int[6];
//    int i =4;
//        System.arraycopy(this.bloodGroup, 0,bloodGroup,0,4);
//                System.arraycopy(this.name, 0,name,0,4);
//                System.arraycopy(this.type, 0,type,0,4);
//                System.arraycopy(this.city, 0,city,0,4);
//                System.arraycopy(this.state, 0,state,0,4);
//                System.arraycopy(this.country, 0,country,0,4);
//                System.arraycopy(this.number, 0,number,0,4);
//                System.arraycopy(this.lastDonationDate, 0,lastDonationDate,0,4);
//                System.arraycopy(this.habits, 0,habits,0,4);
//                System.arraycopy(this.address, 0,address,0,4);
//                System.arraycopy(this.memberSince, 0,memberSince,0,4);
//                System.arraycopy(this.distance, 0,distance,0,4);
//                System.arraycopy(this.daysAgo, 0,daysAgo,0,4);
//                System.arraycopy(this.points, 0,points,0,4);
//                System.arraycopy(this.eye, 0,eye,0,4);
//                data adddonor = new data();
//                addDonor adddonor1 = new addDonor();
//                name[i] =adddonor.name;
//                type[i] = adddonor1.donorTypes[adddonor.donorTypeLoc];
//                distance[i] = 15;
//                bloodGroup[i] = adddonor1.bloodGroup[adddonor.bloodGroupLoc];
//                number[i] = adddonor.phone;
//                country[i] = adddonor1.countries[adddonor.countryLoc];
//                state[i] = adddonor.state;
//                city[i] = adddonor.city;
//                lastDonationDate[i] = adddonor.lastDate;
//                points[i] = 8;
//                eye[i] = 0;
//                habits[i] = adddonor.habits;
//                address[i] = adddonor.address;
//                Date c = Calendar.getInstance().getTime();
//                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//                memberSince[i] = df.format(c);
