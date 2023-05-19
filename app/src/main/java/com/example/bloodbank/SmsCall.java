package com.example.bloodbank;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SmsCall extends AppCompatActivity {
    Context context;

    public SmsCall(Context context) {
        this.context = context;
    }

    public String sendTextMessage(String number, String massage){

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, massage, null, null);
            Toast.makeText(context, "Massage is sent", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            return e.toString();
        }
        return "";
    }
    public void call(String number){

            try {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:0"+number.trim()));
                startActivity(call);
            } catch (Exception e) {
                Toast.makeText(context, e.toString() , Toast.LENGTH_LONG).show();
            }

    }

}
