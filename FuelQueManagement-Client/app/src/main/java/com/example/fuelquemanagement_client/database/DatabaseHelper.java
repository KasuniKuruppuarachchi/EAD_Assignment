package com.example.fuelquemanagement_client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.models.StationOwner;
import com.example.fuelquemanagement_client.models.User;
import com.example.fuelquemanagement_client.models.VehicleOwner;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String ROLE = "ROLE";
    public static final String ID = "ID";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String LOCATION = "LOCATION";
    public static final String STATION_NAME = "STATION_NAME";

//    public static final String VEHICLE_OWNER_TABLE = "VEHCILE_OWNER_TABLE";
//    public static final String STATION_OWNER_TABLE = "STATION_OWNER_TABLE";


    public DatabaseHelper(@Nullable Context context) {
        //name --> Database Name
        super(context, "fuel_queue", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String createTableStmntVehicle = "CREATE TABLE " + VEHICLE_OWNER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FULL_NAME + ", " + USERNAME + ", " + PASSWORD + ")";
//        sqLiteDatabase.execSQL(createTableStmntVehicle);
//
//        String createTableStmntStation = "CREATE TABLE " + STATION_OWNER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FULL_NAME + ", " + USERNAME + ", " + PASSWORD + ", " + LOCATION + ", " + STATION_NAME + ")";
//        sqLiteDatabase.execSQL(createTableStmntStation);

        String createTableStmntUser = "CREATE TABLE " + USER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FULL_NAME + ", " + USERNAME + ", " + PASSWORD + ", " + LOCATION + ", " + STATION_NAME + ", " + ROLE + ")";
        sqLiteDatabase.execSQL(createTableStmntUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

//    public boolean addVehicleOwnerRecord(VehicleOwner vehicleOwner) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(FULL_NAME, vehicleOwner.getfName());
//        cv.put(USERNAME, vehicleOwner.getUsername());
//        cv.put(PASSWORD, vehicleOwner.getPassword());
//
//        long insertSuccess = db.insert(VEHICLE_OWNER_TABLE, null, cv);
//        if(insertSuccess == -1) {
//            return false;
//        }
//        return true;
//    }

//    public boolean addStationOwnerRecord(StationOwner stationOwner) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(FULL_NAME, stationOwner.getfName());
//        cv.put(USERNAME, stationOwner.getUsername());
//        cv.put(PASSWORD, stationOwner.getPassword());
//        cv.put(LOCATION, stationOwner.getLocation());
//        cv.put(STATION_NAME, stationOwner.getStationName());
//
//        long insertSuccess = db.insert(STATION_OWNER_TABLE, null, cv);
//        if(insertSuccess == -1) {
//            return false;
//        }
//        return true;
//    }

    // Add User Record to the database
    public boolean addUserRecord(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FULL_NAME, user.getfName());
        cv.put(USERNAME, user.getUsername());
        cv.put(PASSWORD, user.getPassword());
        cv.put(LOCATION, user.getLocation());
        cv.put(STATION_NAME, user.getStationName());
        cv.put(ROLE, user.getRole());

        long insertSuccess = db.insert(USER_TABLE, null, cv);
        if(insertSuccess == -1) {
            return false;
        }
        return true;
    }

    //Validate the Login
    public User loginValidate(String username, String password) {
        String[] selectionArgs = new String[]{username, password};
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();
        try
        {
            c = db.rawQuery("select * from "+ USER_TABLE + " where username=? and password=?", selectionArgs);
            if(c.moveToFirst()){
                do {
                    user.setId(c.getInt(0));
                    user.setfName(c.getString(1));
                    user.setUsername(c.getString(2));
                    user.setPassword(c.getString(3));
                    user.setLocation(c.getString(4));
                    user.setStationName(c.getString(5));
                    user.setRole(c.getString(6));

                } while (c.moveToNext());
            }else{
                user = null;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public String getLastStationOwnerId(){
        String[] selectionArgs = new String[]{ROLE,Constants.STATION};
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + USER_TABLE ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        int id = cursor.getInt(0);

        return String.valueOf(id);

    }

}
