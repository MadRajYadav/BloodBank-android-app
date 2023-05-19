package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class Home extends AppCompatActivity {
    GridView grid;
    ImageView btnDonor,btnExplore, btnProfile;

    String[] strings = {"Donor", "Donated Blood","Donated Requests This Month","Donated Requests So Far"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CALL_PHONE},1);
        }

        MySqliteHelper db = new MySqliteHelper(Home.this);
        List<DataModel> dataModel = db.getUserDataAll();
        int[] value = {dataModel.size(),0, 0, 0};
        grid = findViewById(R.id.home_grid);
        HomeGridAdapter homeGridAdapter = new HomeGridAdapter(this,strings,value);
        grid.setAdapter(homeGridAdapter);


        // re directing to other page by buttom click
        btnDonor = findViewById(R.id.btn_donor);
        btnDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Donor.class);
                startActivity(intent);
            }
        });

        btnExplore = findViewById(R.id.btn_explore);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,BloodBank.class);
                intent.putExtra("activity","Home.class");
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

    }
}