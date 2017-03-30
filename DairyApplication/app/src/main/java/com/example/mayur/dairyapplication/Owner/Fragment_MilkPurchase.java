package com.example.mayur.dairyapplication.Owner;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;
import com.example.mayur.dairyapplication.SharePreferances.SessionManager;
import com.example.mayur.dairyapplication.UpdateMilkDetails;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Fragment_MilkPurchase extends AppCompatActivity {

    TextView tv_date;
    EditText et_fid,et_quantity,et_fat,et_deg,et_snf,et_rate,et_total;
    Button btn_save;
    SessionManager session;

    AlertDialogManager alert = new AlertDialogManager();
    Calendar cal = Calendar.getInstance();
    final String today = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fragment__milk_purchase);
        session = new SessionManager(getApplicationContext());


        tv_date = (TextView) findViewById(R.id.datepicker);

        tv_date.setText(today);

        et_fid = (EditText) findViewById(R.id.et_Fid);

        et_quantity = (EditText) findViewById(R.id.et_quantity);

        et_snf = (EditText) findViewById(R.id.et_snf);

        et_deg = (EditText) findViewById(R.id.et_deg);

        et_rate = (EditText) findViewById(R.id.et_rate);

        et_total = (EditText) findViewById(R.id.et_total);

        et_fat = (EditText) findViewById(R.id.et_fat);

        btn_save = (Button) findViewById(R.id.btn_save);

        et_deg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {

                        float deg = Float.valueOf(et_deg.getText().toString());
                        float fat = Float.valueOf(et_fat.getText().toString());
                        double snf = (deg / 4) + 0.21 * fat + 0.36;

                        String str = (snf + "").substring(1, 4);
                     //   et_snf.setText("" + snf);
                        et_snf.setText(new DecimalFormat("##.#").format(snf));
                        et_rate.setText(getRate(fat,snf));
                        et_total.setText("" + (Float.parseFloat(et_quantity.getText().toString()) * Float.parseFloat(et_rate.getText().toString())));
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //EditText et_fid,et_quantity,et_fat,et_deg,et_snf,et_rate,et_total;

                if (et_fid.getText().toString().length() == 0) {
                    et_fid.setError("Please Enter Farmer ID");
                } else if (et_quantity.getText().toString().length() == 0) {
                    et_quantity.setError("Please Enter Quantity ");
                }else if(et_fat.getText().toString().length()==0)
                {
                    et_fat.setError("Please Enter Fat value");

                }else if(et_deg.getText().toString().length()==0)
                {
                    et_deg.setError("Please Enter Degree value");

                }else {

                    Date date1= null;
                    try {
                        date1 = new SimpleDateFormat("yyyy-mm-dd").parse(today);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    PurchaseMilk_Cons purchaseMilk_cons = new PurchaseMilk_Cons(session.getID(), Integer.parseInt(et_fid.getText().toString()), date1, Float.parseFloat(et_quantity.getText().toString()),
                            Float.parseFloat(et_fat.getText().toString()), Float.parseFloat(et_deg.getText().toString()), Float.parseFloat(et_snf.getText().toString()),
                            Float.parseFloat(et_rate.getText().toString()), Float.parseFloat(et_total.getText().toString()));

                    new AsyncPurchase().execute(purchaseMilk_cons);
                }

            }
        });
    }
    public String getRate(float fat, double snf) {
        snf = Math.round(snf * 10);
        snf = snf/10;
        float rate=session.getMilkRate();
        String cal_rate=""+rate;

        for(double i=2;i<=fat;i= (i+0.1))
        {
            i = Math.round(i * 10);
            i = i/10;
            float rate1 = rate;
            for(double j=5;j<=snf;j= (j+0.1))
            {
                j = Math.round(j * 10);
                j = j/10;
                rate1= (float) (rate1+0.20);
                rate1 = Math.round(rate1 * 100);
                rate1 = rate1/100;
                cal_rate=rate1+"";
            }
            rate= (float) (rate+0.30);
            rate = Math.round(rate * 100);
            rate = rate/100;
        }
        return cal_rate;
    }

    private class AsyncPurchase extends AsyncTask<PurchaseMilk_Cons,Void,Void> {
        String result;
        JSONObject jsonObject=null;
        RestAPI api=new RestAPI();
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress= new ProgressDialog(Fragment_MilkPurchase.this);
            progress.setIndeterminate(true);
            progress.setMessage("Inserting Records...");
            progress.show();
        }

        @Override
        protected Void doInBackground(PurchaseMilk_Cons... params) {
            RestAPI api=new RestAPI();
            try
            {
                Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(today);
                jsonObject=api.MilkPurchase(params[0].getOid(),params[0].getFid(),date1,params[0].getQuantity(),params[0].getFat(),params[0].getDeg(),params[0].getSnf(),params[0].getRate(),params[0].getTotal());
                result=jsonObject.getString("Value");
            }
            catch (Exception e)
            {
                Log.d("Purchase Milk",e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.hide();
            if(result.contains("Already exists")==true)
            {
                alert.showAlertDialog1(Fragment_MilkPurchase.this,"Failed..!","Milk Already Collected....!",false);
                et_fid.setText("");
                et_quantity.setText("");
                et_snf.setText("");
                et_deg.setText("");
                et_rate.setText("");
                et_fat.setText("");
                et_total.setText("");
            }
            else {
                if (result.contains("success") == true) {
                    Toast.makeText(getApplicationContext(), "Milk Collected", Toast.LENGTH_LONG).show();
                    et_fid.setText("");
                    et_quantity.setText("");
                    et_snf.setText("");
                    et_deg.setText("");
                    et_rate.setText("");
                    et_fat.setText("");
                    et_total.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), " Error Occoured plz try again", Toast.LENGTH_LONG).show();
                }
            }

        }
    }


}