package com.example.mayur.dairyapplication.SharePreferances;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.mayur.dairyapplication.Login_Activity;


/**
 * Created by mayur on 2/3/17.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Dairy_System";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //commonn data
    public static final String LOGINUSER = "owner";
    public static final int ID=0;
    public static final String NAME = "name";
    public static final String EmailID="email";
    public static final String Address="address";
    public static final String MobileNo="0000000000";
    public static final String UserName="username";
    public static final float MilkRate= 0;

    public int getID() {
        return pref.getInt(String.valueOf(ID),0);
    }

    public float getMilkRate() {
        return pref.getFloat(String.valueOf(MilkRate),0);
    }

    public String getNAME() {
        return pref.getString(NAME, null);
    }

    public String getEmailID() {
        return pref.getString(EmailID, null);
    }

    public String getAddress() {
        return pref.getString(Address, null);
    }

    public String getMobileNo() {
        return pref.getString(MobileNo, null);
    }
    public void setMilkRate(float milkrate)
    {
        editor.putFloat(String.valueOf(MilkRate),milkrate);
        editor.commit();
    }
    public  void setLoginuser(String name)
    {
        editor.putString(LOGINUSER,"admin");
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public String getLoginuser() {
        return pref.getString(LOGINUSER, null);
    }

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(int id,String name, String email,String mobileno,String address,String username){
        // Storing login value as TRUE
        editor.putString(LOGINUSER,"owner");
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(NAME, name);
        editor.putInt(String.valueOf(ID),id);
        editor.putString(Address, address);
        editor.putString(EmailID, email);
        editor.putString(MobileNo, mobileno);
        editor.putString(UserName, username);
        editor.commit();
    }
    public void createLoginSession(int id,String name, String email,String mobileno,String address){
        // Storing login value as TRUE
        editor.putString(LOGINUSER,"farmer");
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(NAME, name);
        editor.putInt(String.valueOf(ID),id);
        editor.putString(Address, address);
        editor.putString(EmailID, email);
        editor.putString(MobileNo, mobileno);
        editor.commit();
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user1 is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login_Activity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
