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

import org.json.JSONArray;
import org.json.JSONObject;

public class Owner_Registration extends AppCompatActivity {
    Button btn_register_owner;
    EditText et_ownername, et_owneraddress, et_ownermobno, et_owneremailid, et_username, et_password;
    AlertDialogManager alert = new AlertDialogManager();
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__registration);
        progress = new ProgressDialog(this);
        progress.setIndeterminate(true);
        btn_register_owner = (Button) findViewById(R.id.btn_register_owner);
        et_ownername = (EditText) findViewById(R.id.et_ownername);
        et_owneraddress = (EditText) findViewById(R.id.et_owneraddress);
        et_ownermobno = (EditText) findViewById(R.id.et_ownercontact);
        et_owneremailid = (EditText) findViewById(R.id.et_owneremail);
        et_username = (EditText) findViewById(R.id.et_ownerusername);
        et_password = (EditText) findViewById(R.id.et_ownerpassword);

        btn_register_owner.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String owner_name = et_ownername.getText().toString();
                String owner_add = et_owneraddress.getText().toString();
                String owner_contact = et_ownermobno.getText().toString();
                String owner_mail = et_owneremailid.getText().toString();
                String owner_username = et_username.getText().toString();
                String owner_pass = et_password.getText().toString();

                if (owner_name.length() <= 1) {
                    et_ownername.setError("Please Enter User Name");
                } else if (owner_add.length() <= 3) {
                    et_owneraddress.setError("Please Enter Valid Address");
                } else if (owner_contact.length()!=10) {
                    et_ownermobno.setError("Please Enter 10 Digit Contact Number");

                }else if (owner_username.length() < 3) {

                    et_username.setError("Please Enter Valid User Name");

                } else if (owner_pass.length() <= 3) {

                    et_password.setError("Please Enter Valid Password, Greater Than 3 Character");

                } else {

                    RegisterOwner registerowner = new RegisterOwner(owner_name, owner_add ,owner_contact, owner_mail, owner_username,owner_pass,1);
                    new AsyncOwnerRegistration().execute(registerowner);

                }
            }
        });

    }
    private class AsyncOwnerRegistration extends AsyncTask<RegisterOwner, Void, Void> {
        RestAPI api = new RestAPI();
        Boolean result = false;
        String user;
        @Override
        protected void onPreExecute() {
            progress.setMessage("Please Wait a Moment...");
            progress.show();
        }
        @Override
        protected Void doInBackground(RegisterOwner... params) {
            try {

                JSONObject jsonObject = api.InsertDairyOwner(params[0].getOwner_name(),params[0].getOwner_add(),params[0].getOwner_contact(),params[0].getOwner_mail(),params[0].getOwner_username(),params[0].getOwner_pass());
                result = jsonObject.getBoolean("Successful");
                 } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if (result == true) {
                alert.showAlertDialog1(Owner_Registration.this,"Successful", "Owner Register Successfull", true);

                Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(intent);
            }
            else
            {
                alert.showAlertDialog1(Owner_Registration.this,"Failed", "Owner Registeration Failed Plz Try Again", true);
            }
        }
    }
}
