package com.example.bloodbank;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Context context= MainActivity.this;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting data from sharePreference
        SharedPreferences getSharedPreferences = getSharedPreferences("BloodBank",MODE_PRIVATE);

        // To wait for 3 second before calling next activity
            // re directing to Home page
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {



                    //checking if log in
                    Intent intent;
                    if(getSharedPreferences.getBoolean("LogIn",false))
                        intent = new Intent(MainActivity.this, Home.class);
                    else
                        intent = new Intent(MainActivity.this, LogInCredentials.class);

                    startActivity(intent);
                }

            }, 3000);   //3 seconds


    }



}


































