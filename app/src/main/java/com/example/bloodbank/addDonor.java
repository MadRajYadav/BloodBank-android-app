package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class addDonor extends AppCompatActivity {
    Spinner donorSpin, bloodGroupSpin, countrySpin;
    TextView txtName, txtPhone, txtAddress, txtState, txtCity, txtPinCode, txtDoB, txtLastDate, txtHabits;
    Button btn_submit;
    ImageButton imgBtnBack;
    String[] donorTypes = {"Donor Type","Paid","Free"};
    String[] bloodGroup = {"Blood Group","O+","O-","A+","A-","B+","B-","AB+","AB-"};
    String[] countries = {"Select Country","Armenia","Australia","Canada","China","Germany","India","Japan","Pakistan","Sweden","Switzerland", "United Kingdom","United States"};

    int countryLoc, bloodGroupLoc, donorTypeLoc;
    String name, phone, address, state, city, pinCode, DoB, lastDate, habits;
    MySqliteHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        database = new MySqliteHelper(addDonor.this);

        // making instance of submit button
        btn_submit = findViewById(R.id.btn_submit);


        // making adapter for these Spinner
        donorSpin = findViewById(R.id.donor_type_spin);
        bloodGroupSpin = findViewById(R.id.blood_group_spin);
        countrySpin = findViewById(R.id.country_spin);

        ArrayAdapter<String> adapterCountry =new ArrayAdapter<>(this, R.layout.simple_spinner_item,countries);
        countrySpin.setAdapter(adapterCountry);

        ArrayAdapter<String> adapterDonorType =new ArrayAdapter<>(this, R.layout.simple_spinner_item,donorTypes);
        donorSpin.setAdapter(adapterDonorType);

        ArrayAdapter<String> adapterBloodGroup =new ArrayAdapter<>(this, R.layout.simple_spinner_item,bloodGroup);
        bloodGroupSpin.setAdapter(adapterBloodGroup);


        // getting selected element from spinner

        countrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryLoc = position;
                if(position != 0) {
                    Toast.makeText(addDonor.this, "You have selected " + countries[position], Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        donorSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                donorTypeLoc = position;
                if (position != 0) {
                    Toast.makeText(addDonor.this, "You have selected " + donorTypes[position], Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bloodGroupSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroupLoc = position;
                if(position != 0) {
                Toast.makeText(addDonor.this, "You have selected "+bloodGroup[position], Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // making instances of textView
        txtName = findViewById(R.id.txt_name);
        txtPhone = findViewById(R.id.num_number);
        txtAddress = findViewById(R.id.txt_address);
        txtState = findViewById(R.id.txt_state);
        txtCity = findViewById(R.id.txt_city);
        txtPinCode = findViewById(R.id.txt_pincode);
        txtDoB = findViewById(R.id.txt_dob);
        txtLastDate = findViewById(R.id.txt_lastdate);
        txtHabits = findViewById(R.id.txt_habits);

        // doing some task on onClick method of submit button

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ge ting value from these textView
                name = txtName.getText().toString();
                phone = txtPhone.getText().toString();
                address = txtAddress.getText().toString();
                state = txtState.getText().toString();
                city = txtCity.getText().toString();
                pinCode = txtPinCode.getText().toString();
                DoB = txtDoB.getText().toString();
//                Date DoB1 = convertDate(DoB); // calling covertDate function for conversion
                lastDate = txtLastDate.getText().toString();
//                Date lastDate1 = convertDate(lastDate);// calling covertDate function for conversion
                habits = txtHabits.getText().toString();



                // checking that value is entered or not.

                if(name.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter your full name.", Toast.LENGTH_SHORT).show();
                }
                else if(phone.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter your phone number.", Toast.LENGTH_SHORT).show();
                }
                else if(address.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter your address.", Toast.LENGTH_SHORT).show();
                }
                else if(countryLoc == 0){
                    Toast.makeText(addDonor.this, "Please select your country.", Toast.LENGTH_SHORT).show();
                }
                else if(state.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter your state.", Toast.LENGTH_SHORT).show();
                }
                else if(city.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter your city.", Toast.LENGTH_SHORT).show();
                }
                else if(pinCode.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter your postal code.", Toast.LENGTH_SHORT).show();
                }
                else if(donorTypeLoc == 0){
                    Toast.makeText(addDonor.this, "Please select donor type.", Toast.LENGTH_SHORT).show();
                }
                else if(bloodGroupLoc == 0){
                    Toast.makeText(addDonor.this, "Please select your blood group.", Toast.LENGTH_SHORT).show();
                }
                else if(DoB.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter your date of birth.", Toast.LENGTH_SHORT).show();
                }
                else if(lastDate.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter last donation date.", Toast.LENGTH_SHORT).show();
                }
                else if(habits.isEmpty()){
                    Toast.makeText(addDonor.this, "Please Enter your habits.", Toast.LENGTH_SHORT).show();
                }
                else{
                    txtName.setText("");
                    txtPhone.setText("");
                    txtAddress.setText("");
                    txtState.setText("");
                    txtCity.setText("");
                    txtPinCode.setText("");
                    txtDoB.setText("");
                    txtLastDate.setText("");
                    txtHabits.setText("");
                    donorSpin.setAdapter(adapterDonorType);
                    bloodGroupSpin.setAdapter(adapterBloodGroup);
                    countrySpin.setAdapter(adapterCountry);

                    // inserting data into SQLITE database
                    int flag = database.insertDonor(name,phone,address,state,city,countries[countryLoc],pinCode,DoB,lastDate,habits,bloodGroup[bloodGroupLoc],donorTypes[donorTypeLoc] );
                    if(flag == 1)
                    {
                        Toast.makeText(addDonor.this, "you are recorded into The BloodBank.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),BloodBank.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(addDonor.this, "Something went Wrong!, please try again.", Toast.LENGTH_LONG).show();

                    }

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



    Date stringToDate(String date){
        // converting string type date to date type date
        Date DobDate = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            DobDate = format.parse(date);
        } catch (ParseException e) {
            Toast.makeText(addDonor.this, "Date is Wrong.", Toast.LENGTH_SHORT).show();
        }
        return DobDate;
    }



//    public static void setDataPrefs(String strName, String str, Context mContext) {
//        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
//        SharedPreferences.Editor editor = prefs.edit();
//        int count = prefs.getInt("count",0);
//        Log.e("count", Integer.toString(count));
//        editor.putInt("count",count+1);
//        editor.putString(strName+Integer.toString(count),str);
//    }
//    public static String getDataPrefs(String arrayName, Context mContext) {
//        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
//        return prefs.getString(arrayName+"0","");
//
//    }

}
