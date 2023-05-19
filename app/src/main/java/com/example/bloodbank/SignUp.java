package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class SignUp extends AppCompatActivity {

    Button btnSignUp;
    EditText edtNumber, edtOTP, edtPassword1, edtPassword2, edtLogIn;
    TextView txtVerify;
    ImageView imgEye;
    String number, password1, password2, otp, randomNumber ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        txtVerify = findViewById(R.id.txt_verify);
        imgEye = findViewById(R.id.btn_show_password);

        btnSignUp = findViewById(R.id.btn_sign_up);
        edtLogIn = findViewById(R.id.edt_log_in);

        edtNumber = findViewById(R.id.sign_up_number);
        edtOTP = findViewById(R.id.sign_up_otp);
        edtPassword1 = findViewById(R.id.sign_up_password1);
        edtPassword2 = findViewById(R.id.sign_up_password2);
        edtLogIn = findViewById(R.id.edt_log_in);
        


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = edtNumber.getText().toString();
                password1 = edtPassword1.getText().toString();
                password2 = edtPassword2.getText().toString();

                if(number.isEmpty()){
                    Toast.makeText(SignUp.this, "Number can not be empty", Toast.LENGTH_SHORT).show();
                }
                else if (password1.isEmpty()){
                    Toast.makeText(SignUp.this, "Password can not be empty.", Toast.LENGTH_SHORT).show();
                }
                else if (!password1.equals(password2)){
                    Toast.makeText(SignUp.this, "Password did not matched.", Toast.LENGTH_SHORT).show();
                }
                else{
                    MySqliteHelper db = new MySqliteHelper(SignUp.this);
                    int flag = db.insertLogInCredential(number,password1);
                    if (flag == 1){
                        Toast.makeText(SignUp.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this,LogInCredentials.class);
                        startActivity(intent);
                    }
                }
            }
        });

        edtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,LogInCredentials.class);
                startActivity(intent);
            }
        });

        imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPassword2.setInputType(InputType.TYPE_CLASS_TEXT);

            }
        });



    }

}