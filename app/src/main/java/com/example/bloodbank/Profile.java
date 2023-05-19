package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    ImageView btnDonor,btnExplore, btnHome;
    LinearLayout profileInfo, editProfileInfo;
    TextView txtError,txtName,txtNumber,txtAddress,txtGroupType, txtDoB, txtLastDate;
    EditText edtChange, edtName, edtLastDate;
    Spinner spinType;
    Button editPassword, logOut, removeDonor, submitChanges;
    DataModel dataModel;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        MySqliteHelper sql = new MySqliteHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("BloodBank", MODE_PRIVATE);
        number = sharedPreferences.getString("Number",null);


        if(number !=null){
            dataModel = sql.getUserData(number);
        }
        txtError = findViewById(R.id.error);

        if(dataModel == null){
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Sorry!, you are not a Donor.");

        }else {

                // setting visibility
                txtError.setVisibility(View.GONE);
                editProfileInfo = findViewById(R.id.editing_profile_info);
                editProfileInfo.setVisibility(View.GONE);
                profileInfo = findViewById(R.id.profile_info);
                profileInfo.setVisibility(View.VISIBLE);

    // getting instance through id
                txtName = findViewById(R.id.profile_name);
                txtNumber = findViewById(R.id.profile_number);
                txtAddress = findViewById(R.id.profile_address);
                txtGroupType = findViewById(R.id.profile_blood_group_donor_type);
                txtDoB = findViewById(R.id.profile_dob);
                txtLastDate = findViewById(R.id.profile_last_donation_date);

    // setting data to profile
                txtName.setText(dataModel.name);
                txtNumber.setText(dataModel.number);
                txtAddress.setText(dataModel.city + ", " + dataModel.state + ", " + dataModel.country + ", " + dataModel.pinCode);
                txtGroupType.setText(dataModel.blood_group + " (" + dataModel.donor_type + ")");
                txtDoB.setText("Born On " + dataModel.Dob);
                txtLastDate.setText("Last Donated On " + dataModel.last_donation_date);

                // getting instance to edit profile
                edtName = findViewById(R.id.edit_profile_name);
                edtLastDate = findViewById(R.id.edit_profile_last_donation_date);
                spinType = findViewById(R.id.edit_profile_donor_type);
                //setting value to these
                edtName.setText(dataModel.name);
                edtLastDate.setText(dataModel.last_donation_date);

                //adapter for spinner
                String[] type = new String[2];
                if (dataModel.donor_type.equals("Paid")) {
                    type[0] = "Paid";
                    type[1] = "Free";
                } else {
                    type[0] = "Free";
                    type[1] = "Paid";
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, type);
                spinType.setAdapter(adapter);


                edtChange = findViewById(R.id.edit_profile_info);
                edtChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        profileInfo.setVisibility(View.GONE);
                        editProfileInfo.setVisibility(View.VISIBLE);

                    }
                });
                removeDonor = findViewById(R.id.btn_delete_record);
                removeDonor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sql.removeData(number);
                        Toast.makeText(Profile.this, "you are removed from BloodBank.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);

                    }
                });


                submitChanges = findViewById(R.id.btn_changed_submit);
                submitChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtName.getText().toString().isEmpty()) {
                            Toast.makeText(Profile.this, "Enter your name first.", Toast.LENGTH_SHORT).show();
                        } else if (edtLastDate.getText().toString().isEmpty()) {
                            Toast.makeText(Profile.this, "Enter last donation date.", Toast.LENGTH_SHORT).show();
                        } else {
                            sql.updateDonor(number, edtName.getText().toString(), spinType.getSelectedItem().toString(), edtLastDate.getText().toString());
                            Toast.makeText(Profile.this, "Successfully updated your profile.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Profile.class);
                            startActivity(intent);

                        }
                    }
                });

        }
            logOut = findViewById(R.id.btn_log_out);
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Number",null);
                    editor.putBoolean("LogIn",false);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(),LogInCredentials.class);
                    startActivity(intent);
                }
            });

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
                Intent intent = new Intent(getApplicationContext(),BloodBank.class);
                intent.putExtra("activity","Home.class");
                startActivity(intent);
            }
        });

        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
    }
}