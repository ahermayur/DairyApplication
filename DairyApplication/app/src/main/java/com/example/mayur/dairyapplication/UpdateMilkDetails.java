package com.example.mayur.dairyapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.mayur.dairyapplication.Owner.Fragment_MilkPurchase;
import com.example.mayur.dairyapplication.Owner.PurchaseMilk_Cons;
import com.example.mayur.dairyapplication.Owner.UpdateMilk;
import com.example.mayur.dairyapplication.SharePreferances.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateMilkDetails extends AppCompatActivity {
    public static EditText et_up_date,fid,et_up_quantity,et_up_fat,et_up_deg,et_up_snf,et_up_rate,et_up_total;
    String fat,snf,rate,deg,quantity,total;
    ImageButton datepicker;
    Button btn_update;
    Calendar cal2;
    int day = 0;
    int month = 0;
    int year = 0;
    SessionManager session;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_milk_details);
        session = new SessionManager(getApplicationContext());
        datepicker=(ImageButton)findViewById(R.id.btn_datepicker);
        cal2 = Calendar.getInstance();
        cal2.set(2, 2, 1999);
        day = cal2.get(Calendar.DAY_OF_MONTH);
        month = cal2.get(Calendar.MONTH);
        year = cal2.get(Calendar.YEAR);

        fat=snf=deg=rate=total="";
        btn_update=(Button)findViewById(R.id.btn_update);
        et_up_date=(EditText)findViewById(R.id.et_date);
        fid=(EditText)findViewById(R.id.et_up_Fid);
        et_up_quantity=(EditText)findViewById(R.id.et_up_quantity);
        et_up_fat=(EditText)findViewById(R.id.et_up_fat);
        et_up_deg=(EditText)findViewById(R.id.et_up_deg);
        et_up_snf=(EditText)findViewById(R.id.et_up_snf);
        et_up_rate=(EditText)findViewById(R.id.et_up_rate);
        et_up_total=(EditText)findViewById(R.id.et_up_total);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(UpdateMilkDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_up_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        fid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    try {
                        int farmer_id = Integer.parseInt(fid.getText().toString());
                        UpdateMilk_Cons updateMilk_cons = new UpdateMilk_Cons(farmer_id, et_up_date.getText().toString());
                        new AsyncUpdateMilkDetails().execute(updateMilk_cons);
                    }
                    catch(Exception e)
                    {
                        Log.d("Update Milk Error : ",e.getMessage());
                    }
                }
            }
        });
        et_up_fat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        float deg = Float.valueOf(et_up_deg.getText().toString());
                        float fat = Float.valueOf(et_up_fat.getText().toString());
                        double snf = (deg / 4) + 0.21 * fat + 0.36;
                        et_up_snf.setText(new DecimalFormat("##.#").format(snf));
                        et_up_rate.setText(getRate(fat,snf));
                        et_up_total.setText("" + (Float.parseFloat(et_up_quantity.getText().toString()) * Float.parseFloat(et_up_rate.getText().toString())));
                    }
                    catch(Exception e){}
                }
            }
        });
        et_up_quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        float deg = Float.valueOf(et_up_deg.getText().toString());
                        float fat = Float.valueOf(et_up_fat.getText().toString());
                        double snf = (deg / 4) + 0.21 * fat + 0.36;
                        et_up_snf.setText(new DecimalFormat("##.#").format(snf));
                        et_up_rate.setText(getRate(fat,snf));
                        et_up_total.setText("" + (Float.parseFloat(et_up_quantity.getText().toString()) * Float.parseFloat(et_up_rate.getText().toString())));
                    }
                    catch(Exception e){}
                }
            }
        });
        et_up_snf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        float deg = Float.valueOf(et_up_deg.getText().toString());
                        float fat = Float.valueOf(et_up_fat.getText().toString());
                        double snf = (deg / 4) + 0.21 * fat + 0.36;
                        et_up_snf.setText(new DecimalFormat("##.#").format(snf));
                       // et_up_rate.setText("23.5");
                        et_up_rate.setText(getRate(fat,snf));
                        et_up_total.setText("" + (Float.parseFloat(et_up_quantity.getText().toString()) * Float.parseFloat(et_up_rate.getText().toString())));
                    }
                    catch(Exception e){}
                }
            }
        });
        et_up_deg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    try {
                        float deg = Float.valueOf(et_up_deg.getText().toString());
                        float fat = Float.valueOf(et_up_fat.getText().toString());
                        double snf = (deg / 4) + 0.21 * fat + 0.36;
                        et_up_snf.setText(new DecimalFormat("##.#").format(snf));
                        et_up_rate.setText(getRate(fat,snf));
                        et_up_total.setText("" + (Float.parseFloat(et_up_quantity.getText().toString()) * Float.parseFloat(et_up_rate.getText().toString())));
                    }
                    catch(Exception e){}
                }
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_up_date.length()==0)
                {
                    et_up_date.setError("Enter a Date");
                }
                if(fid.length()==0)
                {
                    fid.setError("Enetr a Farmer ID");
                }
                else if(et_up_quantity.length()==0)
                {
                    et_up_quantity.setError("Enetr a Quantity");
                }
                else if(et_up_fat.length()==0)
                {
                    et_up_fat.setError("Enetr a Fat");
                }
                else if(et_up_deg.length()==0)
                {
                    et_up_deg.setError("Enetr a Degree");
                }
                else {

                    Date date1= null;
                    try {
                        date1 = new SimpleDateFormat("yyyy-mm-dd").parse(et_up_date.getText().toString());
                        date1=date1;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    UpdateMilk UpdateMilk = new UpdateMilk(Integer.parseInt(fid.getText().toString()), et_up_date.getText().toString(), Float.parseFloat(et_up_quantity.getText().toString()),
                            Float.parseFloat(et_up_fat.getText().toString()), Float.parseFloat(et_up_deg.getText().toString()), Float.parseFloat(et_up_snf.getText().toString()),
                            Float.parseFloat(et_up_rate.getText().toString()), Float.parseFloat(et_up_total.getText().toString()));
                    new AsyncSetUpdate().execute(UpdateMilk);
                }
            }
        });
    }

    public class AsyncSetUpdate extends AsyncTask<UpdateMilk,Void,Void>{

        ProgressDialog progress;
        String result="";
        JSONObject jsonObject=null;
        RestAPI api=new RestAPI();
        @Override
        protected void onPreExecute() {
            progress= new ProgressDialog(UpdateMilkDetails.this);
            progress.setIndeterminate(true);
            progress.setMessage("Inserting Records...");
            progress.show();
        }
        @Override
        protected Void doInBackground(UpdateMilk... params) {

            JSONObject object = new JSONObject();
            try
            {
             //
                jsonObject=api.UpdateMilkPurchase(params[0].getFid(),params[0].getDate(),params[0].getQuantity(),params[0].getFat(),params[0].getDeg(),params[0].getSnf(),params[0].getRate(),params[0].getTotal());

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
                if (result.contains("success") == true) {
                    Toast.makeText(getApplicationContext(), "Milk Collected", Toast.LENGTH_LONG).show();
                    new UpdateMilkDetails().clearFields();
                } else {
                    Toast.makeText(getApplicationContext(), " Error Occoured plz try again", Toast.LENGTH_LONG).show();
                }

        }

    }

    public String getRate(float fat, double snf) {
        snf = Math.round(snf * 10);
        snf = snf/10;
        float rate=session.getMilkRate();
        String cal_rate=""+rate;
        for(float i=2;i<=fat;i= (float) (i+0.1))
        {
            i = Math.round(i * 10);
            i = i/10;
            for(float j=5;j<=snf;j= (float) (j+0.1))
            {
                j = Math.round(j * 10);
                j = j/10;
                rate= (float) (rate+0.20);
                rate = Math.round(rate * 100);
                rate = rate/100;
                    cal_rate=rate+"";

            }
            rate= (float) (rate+0.30);
            rate = Math.round(rate * 100);
            rate = rate/100;
        }
        return cal_rate;
    }

    public static void clearFields()
    {
        et_up_date.setText("");
        fid.setText("");
        et_up_quantity.setText("");
        et_up_snf.setText("");
        et_up_deg.setText("");
        et_up_rate.setText("");
        et_up_fat.setText("");
        et_up_total.setText("");
    }
    private class AsyncUpdateMilkDetails extends AsyncTask<UpdateMilk_Cons,Void,Void>{

        @Override
        protected Void doInBackground(UpdateMilk_Cons... params) {
            RestAPI api=new RestAPI();
            try
            {
                JSONObject jsonObject=api.SelectMilkPurchaseDetails(params[0].getFid(),params[0].getDate());
                JSONArray jsonArray=jsonObject.getJSONArray("Value");

                JSONObject jsonObj=null;
                for(int i=0;i<jsonArray.length();i++)
                {
                    jsonObj=jsonArray.getJSONObject(i);

//                    arrayList.add(new InsertHolidays(jsonObj.get("HolidayName").toString(), jsonObj.get("Date").toString(), jsonObj.get("Image").toString()));

                    quantity=jsonObj.get("Milk_Quantity").toString();
                    fat=jsonObj.get("Fat").toString();
                    deg=jsonObj.get("deg").toString();
                    snf=jsonObj.get("snf").toString();
                    rate=jsonObj.get("MilkRate").toString();
                    total=jsonObj.get("Total").toString();
                }
            }
            catch (Exception e)
            {
                Log.d("Update Milk Details Error : ",e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setValue();
        }
    }

    private void setValue() {
        et_up_fat.setText(fat);
        et_up_rate.setText(rate);
        et_up_deg.setText(deg);
        et_up_snf.setText(snf);
        et_up_quantity.setText(quantity);
        et_up_total.setText(total);
    }
    private class UpdateMilk_Cons {
        int fid;
        String date;

        public UpdateMilk_Cons(int fid, String date) {
            this.fid = fid;
            this.date = date;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
