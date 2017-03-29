package com.example.mayur.dairyapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayur.dairyapplication.Owner.Owner_Dashboard;
import com.example.mayur.dairyapplication.SharePreferances.*;

import org.json.JSONObject;

import java.util.Calendar;

import static com.example.mayur.dairyapplication.R.layout.fragment_set_milk_rate;


public class SetMilkRate extends AppCompatActivity {

    EditText et_setrate;
    TextView et_setsnf,et_fat,tv_menu_owner_purchase;
    Button btn_setrate;

    SessionManager session;
    com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager alert = new com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_milk_rate);
        session = new SessionManager(getApplicationContext());
        et_setsnf=(TextView) findViewById(R.id.et_setsnf);
        et_fat=(TextView) findViewById(R.id.et_set_Fat);
        et_setrate=(EditText) findViewById(R.id.et_setrate);

        btn_setrate=(Button)findViewById(R.id.btnsetrate);
        btn_setrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal= Calendar.getInstance();
                String today = cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);

                if(et_setrate.getText().toString().length()==0)
                {
                    et_setrate.setError("Please Set Rate For Today");
                }
                else {

                    float newrate= Float.parseFloat(et_setrate.getText().toString());

                    SetRate_Cons setRateCons = new SetRate_Cons(Float.parseFloat(et_fat.getText().toString()), Float.parseFloat(et_setsnf.getText().toString()), newrate, today, session.getID());
                    new AsyncSetRate().execute(setRateCons);
                }
            }
        });
    }

    public class SetRate_Cons {
        float fat,snf;
        float newrate;
        String today;
        int i;
        public SetRate_Cons(float fat, float snf, float newrate, String today, int i) {
            this.fat = fat;
            this.snf = snf;
            this.newrate = newrate;
            this.today = today;
            this.i = i;
        }

        public float getFat() {
            return fat;
        }

        public void setFat(float fat) {
            this.fat = fat;
        }



        public float getSnf() {
            return snf;
        }

        public void setSnf(float snf) {
            this.snf = snf;
        }
        public float getNewrate() {
            return newrate;
        }

        public void setNewrate(float newrate) {
            this.newrate = newrate;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

    }

    private class AsyncSetRate extends AsyncTask<SetRate_Cons,Void,Void> {
        boolean result=false;
        RestAPI api=new RestAPI();
        ProgressDialog progress;
        @Override
        protected Void doInBackground(SetRate_Cons[] params) {

            try
            {
                RestAPI api=new RestAPI();
                JSONObject object=api.SetMilkRate(params[0].getFat(),params[0].getSnf(),params[0].getNewrate(),params[0].getToday(),params[0].getI());
                result=object.getBoolean("Successful");
            }
            catch(Exception e)
            {
                Log.d("Set Milk Rate",e.getMessage());
            }
            return null;
        }

        protected  void onPreExecute()
        {
            super.onPreExecute();
            Intent i=new Intent(getApplicationContext(),Owner_Dashboard.class);
            startActivity(i);
            finish();
            progress= new ProgressDialog(SetMilkRate.this);
            progress.setMessage("Please Wait...");
            progress.show();
            progress.setIndeterminate(true);
            progress.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
            if(result==false) {
                alert.showAlertDialog1(SetMilkRate.this,"Failed","Please try again",true);
            }
            else
            {
                alert.showAlertDialog1(SetMilkRate.this,"Success","Milk Rate set Successfully....",true);
                session.setMilkRate(Float.parseFloat(et_setrate.getText().toString()));
            }
        }
    }
}