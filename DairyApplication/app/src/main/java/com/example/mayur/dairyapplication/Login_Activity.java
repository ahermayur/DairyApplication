package com.example.mayur.dairyapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayur.dairyapplication.Admin.AdminDashboard;
import com.example.mayur.dairyapplication.Farmer.Farmer_Dashboard;
import com.example.mayur.dairyapplication.Owner.Owner_Dashboard;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;
import com.example.mayur.dairyapplication.SharePreferances.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login_Activity extends AppCompatActivity {

    Button btn_login,btn_ip;
    EditText et_username,et_password,et_ip;
    TextView tv_register_owner;
    String username,password;
    SessionManager session;
    private ProgressDialog progress;
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()==true)
        {
            String name=session.getLoginuser();
            Intent i = null;
            if(name.equalsIgnoreCase("owner"))
            {
                i=new Intent(Login_Activity.this,Owner_Dashboard.class);
            }
            if(name.equalsIgnoreCase("farmer"))
            {
                i=new Intent(Login_Activity.this,Farmer_Dashboard.class);
            }
            if(name.equalsIgnoreCase("admin"))
            {
                i=new Intent(Login_Activity.this,AdminDashboard.class);
            }
            startActivity(i);
            finish();
        }


        btn_login=(Button)findViewById(R.id.btn_login);
        btn_ip=(Button)findViewById(R.id.btn_Ip);
        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        et_ip=(EditText)findViewById(R.id.et_ip);

        tv_register_owner=(TextView)findViewById(R.id.tv_register_owner);
        progress= new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setMessage("Verifying User...");
        //login customer
        btn_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_ip.getVisibility()==View.INVISIBLE) {
                    et_ip.setVisibility(View.VISIBLE);
                    et_ip.setText(RestAPI.ip);
                }
                else {
                    RestAPI.ip = et_ip.getText().toString();
                    Toast.makeText(getApplicationContext(), "IP change Successfully....", Toast.LENGTH_LONG).show();
                    et_ip.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()==true) {
                    new AsyncFarmerLogin().execute();
                }
           }
        });

        btn_login.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(validate()==true) {
                    if (username.equalsIgnoreCase("Admin") && password.equalsIgnoreCase("Admin")) {

                        Intent intent = new Intent(Login_Activity.this, AdminDashboard.class);
                        startActivity(intent);
                        session.setLoginuser("admin");
                        finish();
                    } else {

                        new AsyncLogin().execute();
                    }
                }
                    return false;
            }
        });
        //register customer

        //register customer
        tv_register_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login_Activity.this,Owner_Registration.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate() {
        username=et_username.getText().toString();
        password=et_password.getText().toString();
        if(username.length()<4)
        {
            et_username.setError("Please Enter Valid User Name");
            return false;
        }else if(password.length()<4)
        {
            et_password.setError("Please enter Valid Password");
            return false;
        }
        return true;
    }

    private class AsyncLogin extends AsyncTask<Void,Void,Void>{
        Boolean result=false;
        JSONObject jsonObject=null;
        RestAPI api=new RestAPI();
        @Override
        protected void onPreExecute() {
            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                jsonObject=api.OwnerLogin(username,password);
                result=jsonObject.getBoolean("Successful");

                JSONArray jsonArray=jsonObject.getJSONArray("Value");
                JSONObject jsonObj=null;
                if(jsonArray.length()==0)
                {
                    result=false;
                }
                for(int i=0;i<jsonArray.length();i++)
                {
                    jsonObj=jsonArray.getJSONObject(i);
                    session.createLoginSession(jsonObj.getInt("Owner_ID"),jsonObj.getString("Owner_Name"),jsonObj.getString("Owner_Address"),jsonObj.getString("Owner_MobNo"),jsonObj.getString("Owner_EmailID"),jsonObj.getString("Owner_UserName"));
                    Log.d("Share Preferance","Information added");
                }
                try {
                    String error=jsonObject.getString("ErrorMessage");
                    result=false;
                }
                catch (Exception e)
                {

                }
            }
            catch(Exception e)
            {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if (result==false)
            {
                alert.showAlertDialog1(Login_Activity.this,"Failed..!","Login failed please try again....!",false);
            }
            else
            {
                Intent intent = new Intent(Login_Activity.this, Owner_Dashboard.class);
                startActivity(intent);

            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Login_Activity.this);
        builder.setMessage("Do you want to exit ?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.setIcon(R.drawable.warning);
        alertDialog.setTitle("Exit");
        alertDialog.show();
    }
    private class AsyncFarmerLogin extends AsyncTask<Void,Void,Void> {
        Boolean result=false;
        JSONObject jsonObject=null;
        RestAPI api=new RestAPI();
        @Override
        protected void onPreExecute() {
            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                jsonObject=api.FarmerLogin(username,password);
                result=jsonObject.getBoolean("Successful");

                JSONArray jsonArray=jsonObject.getJSONArray("Value");
                if(jsonArray.length()<=0)
                {
                    result=false;
                }
                JSONObject jsonObj=null;
                for(int i=0;i<jsonArray.length();i++)
                {
                    jsonObj=jsonArray.getJSONObject(i);
                 //   session.createLoginSession(jsonObj.getInt("Owner_ID"),jsonObj.getString("Owner_Name"),jsonObj.getString("Owner_Address"),jsonObj.getString("Owner_MobNo"),jsonObj.getString("Owner_EmailID"),jsonObj.getString("Owner_UserName"));
                    Log.d("Share Preferance","Information added");
                }
                try {
                    String error=jsonObject.getString("ErrorMessage");
                    result=false;
                }
                catch (Exception e)
                {

                }

/*
                jsonObject=api.Login(username,password);
                result=jsonObject.getBoolean("Successful");
                JSONArray jsonArray=jsonObject.getJSONArray("Value");
                if(jsonArray.length()<=0)
                {
                    result=false;
                }
                JSONObject jsonObj=null;
                for(int i=0;i<jsonArray.length();i++)
                {
                    jsonObj=jsonArray.getJSONObject(i);
                    //   session.createLoginSession(jsonObj.getInt("Owner_ID"),jsonObj.getString("Owner_Name"),jsonObj.getString("Owner_Address"),jsonObj.getString("Owner_MobNo"),jsonObj.getString("Owner_EmailID"),jsonObj.getString("Owner_UserName"));
                    Log.d("Share Preferance","Information added");
                }
                try {
                    String error=jsonObject.getString("ErrorMessage");
                    result=false;
                }
                catch (Exception e)
                {

                }*/


            }
            catch(Exception e)
            {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if (result==false)
            {
                alert.showAlertDialog1(Login_Activity.this,"Failed..!","Login failed please try again....!",false);
            }
            else
            {
                Intent intent=new Intent(Login_Activity.this, Farmer_Dashboard.class);
                startActivity(intent);
                finish();
            }
        }
    }
}