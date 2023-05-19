package com.example.bloodbank;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MySqliteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "MyBloodBank",
            TB_NAME = "DonorData", TB_NAME_LOGIN = "loginCredentials",
            KEY_NAME = "name",
            KEY_NUMBER = "number",
            KEY_ADDRESS = "address",
            KEY_STATE = "state",
            KEY_CITY = "city",
            KEY_PIN_CODE = "pinCode",
            KEY_DoB = "DoB",
            KEY_LAST_DONATION_DATE = "lastDonation_Date",
            KEY_HABITS = "habits",
            KEY_COUNTRY = "country",
            KEY_BLOOD_GROUP = "bloodGroup",
            KEY_DONOR_TYPE = "donorType",
            KEY_MEMBERSHIP_DATE = "membershipDate",
            KEY_POINTS = "points",
            KEY_SEEN = "seen",
            KEY_PASSWORD ="password",
            KEY_LONGITUDE = "longitude",
            KEY_LATITUDE = "latitude";
    public MySqliteHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase db) {

        String query = "create table " + TB_NAME + " ( " + KEY_NAME + " TEXT NOT NULL, " + KEY_NUMBER + " TEXT PRIMARY KEY, " + KEY_ADDRESS + " TEXT, " +
                KEY_STATE + " TEXT NOT NULL, " + KEY_CITY + " TEXT NOT NULL, " + KEY_PIN_CODE + " TEXT NOT NULL, " + KEY_DoB+ " TEXT NOT NULL, " +
                KEY_LAST_DONATION_DATE + " TEXT NOT NULL, " + KEY_HABITS + " TEXT NOT NULL, " + KEY_COUNTRY + " TEXT NOT NULL, " + KEY_BLOOD_GROUP + " TEXT NOT NULL, " +
                KEY_DONOR_TYPE + " TEXT NOT NULL, " + KEY_MEMBERSHIP_DATE + " TEXT NOT NULL, " + KEY_POINTS + " INTEGER NOT NULL, "+KEY_SEEN +" INTEGER NOT NULL)";
        db.execSQL(query);

        query = "create table " + TB_NAME_LOGIN + " ( " +KEY_NUMBER + " TEXT PRIMARY KEY, " + KEY_PASSWORD + " TEXT NOT NULL, "+ KEY_LONGITUDE + " TEXT, " + KEY_LATITUDE + "TEXT, FOREIGN KEY ( "+ KEY_NUMBER+
                " ) REFERENCES " + TB_NAME + " ( " + KEY_NUMBER + " )) ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    void drop(String tableName){

        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("drop table " + tableName);
        db.close();

    }


    public int insertDonor(String name, String number, String address, String state, String city, String country, String pinCode, String Dob, String last_donation_date, String habits, String blood_group, String donor_type){
        int point = 0, seen=0;
        // getting current date and time
        String membership_Date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        membership_Date = sdf.format(calendar.getTime());

        // setting content value
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_NUMBER,number);
        cv.put(KEY_ADDRESS,address);
        cv.put(KEY_STATE,state);
        cv.put(KEY_CITY,city);
        cv.put(KEY_PIN_CODE,pinCode);
        cv.put(KEY_DoB,Dob);
        cv.put(KEY_LAST_DONATION_DATE,last_donation_date);
        cv.put(KEY_HABITS,habits);
        cv.put(KEY_COUNTRY,country);
        cv.put(KEY_BLOOD_GROUP,blood_group);
        cv.put(KEY_DONOR_TYPE,donor_type);
        cv.put(KEY_MEMBERSHIP_DATE,membership_Date);
        cv.put(KEY_POINTS,point);
        cv.put(KEY_SEEN,seen);

        // executing sqLite query
        SQLiteDatabase sql = this.getWritableDatabase();
        sql.insert(TB_NAME,null,cv);
        sql.close();

        return 1;
    }



    List<DataModel> getUserDataAll(){
        List<DataModel> userList = new ArrayList<>();
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from "+TB_NAME,null);

        while (cursor.moveToNext()){
            userList.add(new DataModel(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(9),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getInt(13),
                    cursor.getInt(14)

            ));

        }
        cursor.close();
        sql.close();
        return userList;
    }

    public DataModel getUserData(String number){
        DataModel  userData;
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from "+TB_NAME + " where " + KEY_NUMBER +"  = " + number ,null);

        try {
                    cursor.moveToFirst();
                    userData = new DataModel(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(9),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getInt(13),
                    cursor.getInt(14)

                    );
                }catch (Exception e){
                    userData = null;
                }

        cursor.close();
        sql.close();
        return userData;
    }

    public void removeData(String number){
        SQLiteDatabase sql = this.getReadableDatabase();
        try{

            sql.delete(TB_NAME,number,null);
            sql.close();
        }catch (Exception e){
            sql.close();
        }
    }

    public void updateDonor(String number, String name, String type, String lastDonationDate){
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_DONOR_TYPE,type);
        cv.put(KEY_LAST_DONATION_DATE,lastDonationDate);
        SQLiteDatabase sql  = this.getWritableDatabase();
        sql.update(TB_NAME,cv,number,null);
    }
    public int insertLogInCredential(String number, String password){
        ContentValues cv = new ContentValues();
        cv.put(KEY_NUMBER,number);
        cv.put(KEY_PASSWORD,password);
        SQLiteDatabase sql = this.getWritableDatabase();
        int flag;
        try{

            sql.insert(TB_NAME_LOGIN,null,cv);
            flag = 1;

        }catch (Exception e){
            flag = 0;
        }
        sql.close();
        return 1;
    }

    public String getLogInCredential(String number){
        String password = "12345" ;
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select "+KEY_PASSWORD +" from "+TB_NAME_LOGIN +" where " + KEY_NUMBER +" = "+number,null);

        try {

            cursor.moveToFirst();
            password = cursor.getString(0);

        }
        catch (Exception e){
            password = "";
        }
        cursor.close();
        sql.close();
        return password;
    }



}
