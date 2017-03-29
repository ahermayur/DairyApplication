package com.example.mayur.dairyapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mayur.dairyapplication.Owner.Owner_Dashboard;
import com.example.mayur.dairyapplication.SharePreferances.SessionManager;
import com.google.android.gms.appindexing.AppIndex;

import org.json.JSONArray;
import org.json.JSONObject;

public class FarmerRegister extends AppCompatActivity {

    Button btn_register_farmer;
    EditText et_farmername, et_farmeraddress, et_farmercontact, et_farmeremail, et_farmerusername, et_farmerpassword;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private ProgressDialog progress;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        session = new SessionManager(getApplicationContext());
        progress= new ProgressDialog(this);
        progress.setIndeterminate(true);
        btn_register_farmer = (Button) findViewById(R.id.btn_register_farmer);
        et_farmername = (EditText) findViewById(R.id.et_farmername);
        et_farmeraddress = (EditText) findViewById(R.id.et_farmeraddress);
        et_farmercontact = (EditText) findViewById(R.id.et_farmercontact);
        et_farmeremail = (EditText) findViewById(R.id.et_farmeremail);
        et_farmerusername = (EditText) findViewById(R.id.et_farmerusername);
        et_farmerpassword = (EditText) findViewById(R.id.et_farmerpassword);

        btn_register_farmer.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String farmer_name = et_farmername.getText().toString();
                String farmer_add = et_farmeraddress.getText().toString();
                String farmer_contact = et_farmercontact.getText().toString();
                String farmer_mail = et_farmeremail.getText().toString();
                String farmer_username = et_farmerusername.getText().toString();
                String farmer_pass = et_farmerpassword.getText().toString();
                if (farmer_name.length() <= 3) {
                    et_farmername.setError("Please Enter User Name");
                } else if (farmer_add.length() <= 3) {
                    et_farmeraddress.setError("Please Enter Valid Address");
                } else if (farmer_contact.length()!=10) {
                    et_farmercontact.setError("Please Enter 10 Digit Contact Number");
                } else if (farmer_username.length() < 3) {
                    et_farmerusername.setError("Please Enter Valid User Name");
                } else if (farmer_pass.length() <= 3) {
                    et_farmerpassword.setError("Please Enter Valid Password, Greater Than 3 Character");
                } else {
                    RegisterFarmer registerFarmer = new RegisterFarmer(farmer_name, farmer_add, farmer_contact, farmer_mail, farmer_username, farmer_pass,session.getID());
                    new AsyncCreateUser().execute(registerFarmer);
                }
            }
        });
    }


    private class AsyncCreateUser extends AsyncTask<RegisterFarmer, Void, Void> {
        Boolean result=false;
        JSONObject jsonObject=null;
        RestAPI api=new RestAPI();
        @Override
        protected void onPreExecute() {
            progress.setMessage("Registering New Farmer....Please Wait");
            progress.show();
        }
        @Override
        protected Void doInBackground(RegisterFarmer... params) {
            try {
                jsonObject=api.InsertFarmerDetails(params[0].getFarmer_name(),params[0].getFarmer_add(),params[0].getFarmer_contact(),params[0].getFarmer_username(),params[0].getFarmer_pass(),params[0].getOwner_id());
                result = jsonObject.getBoolean("Successful");


            } catch (Exception e) {

                e.printStackTrace();
            }


            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if (result==false)
            {
                alert.showAlertDialog1(FarmerRegister.this,"Failed..!","Login failed please try again....!",false);
            }
            else
            {

                Intent intent = new Intent(FarmerRegister.this, Owner_Dashboard.class);
                startActivity(intent);
                finish();
                Toast.makeText(FarmerRegister.this,"Farmer Added successfully ...!",Toast.LENGTH_LONG).show();
            }
        }
    }
}
