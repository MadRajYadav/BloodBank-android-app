package com.example.bloodbank;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogInCredentials extends AppCompatActivity {

    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private Boolean isSmsPermissionGranted = false;
    private Boolean isCallPermissionGranted = false;
    private Boolean isLocationPermissionGranted = false;

    EditText edt_number, edt_password, edt_sign_up, edt_forget_password;
    Button btnLogIn;
    TextView txtError;
    String number, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_credentials);

        MySqliteHelper sql = new MySqliteHelper(LogInCredentials.this);

        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {

                if(result.get(Manifest.permission.SEND_SMS)!=null){
                    isSmsPermissionGranted = result.get(Manifest.permission.SEND_SMS);
                }
                if(result.get(Manifest.permission.CALL_PHONE)!=null){
                    isSmsPermissionGranted = result.get(Manifest.permission.CALL_PHONE);
                }
                if(result.get(Manifest.permission.ACCESS_FINE_LOCATION)!=null){
                    isSmsPermissionGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            }
        });

        requestPermission();





        edt_sign_up = findViewById(R.id.edt_sign_up);
        edt_forget_password = findViewById(R.id.edt_forget_password);
        btnLogIn = findViewById(R.id.btn_log_in);

        txtError = findViewById(R.id.log_in_error);
        edt_number = findViewById(R.id.log_in_number1);
        edt_password = findViewById(R.id.log_in_password);




        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                if(!(isSmsPermissionGranted && isCallPermissionGranted && isLocationPermissionGranted)){
                    Toast.makeText(LogInCredentials.this, "You need to give some permission", Toast.LENGTH_LONG).show();
                    return;
                }
                number = edt_number.getText().toString();
                password = edt_password.getText().toString();


                if(btnLogIn.getText().toString().equals("Send Password")){
                    number = edt_number.getText().toString();

                    if(number.isEmpty()){
                        Toast.makeText(LogInCredentials.this, "Enter your registered number.", Toast.LENGTH_LONG).show();
                        edt_number.requestFocus();
                    }
                    String password = sql.getLogInCredential(number);
                    if(password.isEmpty()){
                        Toast.makeText(LogInCredentials.this, "This number is not registered.", Toast.LENGTH_LONG).show();
                    } else{

                            SmsCall sms = new SmsCall(LogInCredentials.this);
                            String massage = "Your password is " + password;
                            String error = sms.sendTextMessage(number,massage);

                            if(error.isEmpty()){
                            Toast.makeText(LogInCredentials.this, "You can log in by using your password.", Toast.LENGTH_LONG).show();
                            View view = findViewById(R.id.rel_password);
                            view.setVisibility(View.VISIBLE);
                            View view1 = findViewById(R.id.lin_forget);
                            view1.setVisibility(View.VISIBLE);
                            btnLogIn.setText("Log In");
                            edt_password.requestFocus();

                        } else {
                                txtError.setVisibility(View.VISIBLE);
                                txtError.setText("SMS permission error!, You need to give SMS permission first.");
                            }
                    }
                }
                else{
                    if(number.isEmpty()){
                        Toast.makeText(LogInCredentials.this, "Enter your registered number.", Toast.LENGTH_LONG).show();
                        edt_number.requestFocus();
                    } else if (password.isEmpty()) {
                        Toast.makeText(LogInCredentials.this, "Enter your password.", Toast.LENGTH_LONG).show();
                        edt_password.requestFocus();
                    } else{

                        String sqlPassword = sql.getLogInCredential(number);
                        Log.e("password ", sqlPassword);
                        if (sqlPassword.isEmpty()){
                            Toast.makeText(LogInCredentials.this, "This number is not registered.", Toast.LENGTH_LONG).show();
                            edt_number.requestFocus();
                        }
                        else if(password.equals(sqlPassword)){
                            Toast.makeText(LogInCredentials.this, "Thank you for using BloodBank.", Toast.LENGTH_LONG).show();
                            //Storing number into sharePreference
                            // one time log in
                            SharedPreferences sharedPreferences = getSharedPreferences("BloodBank",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Number",number);
                            editor.putBoolean("LogIn",true);
                            editor.apply();


                            // redirecting to Home page
                            Intent intent = new Intent(LogInCredentials.this,Home.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LogInCredentials.this, "Password is wrong.", Toast.LENGTH_LONG).show();
                            edt_password.setText("");
                            edt_password.requestFocus();
                        }


                    }
                }
            }
        });

//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectActivityLeaks().penaltyLog().build());
//        StrictMode.enableDefaults();
        edt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                if(isSmsPermissionGranted && isCallPermissionGranted && isLocationPermissionGranted){
                    Intent intent = new Intent(LogInCredentials.this, SignUp.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LogInCredentials.this, "You need to give some permission", Toast.LENGTH_LONG).show();

                }

            }
        });

        edt_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.rel_password);
                view.setVisibility(View.GONE);
                View view1 = findViewById(R.id.lin_forget);
                view1.setVisibility(View.GONE);
                btnLogIn.setText("Send Password");
                edt_number.requestFocus();
                Toast.makeText(LogInCredentials.this, "Enter your registered number.", Toast.LENGTH_LONG).show();
            }
        });



    }


    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);

//        super.onBackPressed();



    }

    private  void requestPermission(){

        // checking the required permission are given or not and storing its result in variable.
        isSmsPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_DENIED;
        isCallPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_DENIED;
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED;

        // making arrayList
        List<String> permissionRequest = new ArrayList<String>();


        // checking permission granted or not
        if(!isLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!isSmsPermissionGranted){
            permissionRequest.add(Manifest.permission.SEND_SMS);
        }
        if(!isCallPermissionGranted){
            permissionRequest.add(Manifest.permission.CALL_PHONE);
        }


        if(!permissionRequest.isEmpty()){
            mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }

    }


}