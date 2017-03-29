package com.example.mayur.dairyapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayur.dairyapplication.SharePreferances.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TodaysCollection extends AppCompatActivity {

    Context context;
    EditText et_date;
    Button btn_view;
    Calendar cal2;
    int day = 0;
    int month = 0;
    int year = 0;
    SessionManager session;
    private DatePickerDialog datePickerDialog;
    int listsize=0;
    ImageButton imgbtn_datepicker;
    public ArrayList<Initiator> Collection1;
    //  public List<String> fid,fname,quantity,fat,deg,snf,rate,total;
    ListView lv_collection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_todays_collection);
        context=getApplicationContext();
        session = new SessionManager(getApplicationContext());
        Calendar cal= Calendar.getInstance();
        final String today = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH);
        lv_collection=(ListView)findViewById(R.id.lv_daily_collection);
        et_date=(EditText)findViewById(R.id.et_date_collection);
        btn_view=(Button)findViewById(R.id.btn_view_collection);
        et_date.setText(today);
        imgbtn_datepicker=(ImageButton)findViewById(R.id.imageButton1);

        cal2 = Calendar.getInstance();
        cal2.set(2, 2, 1999);

        day = cal2.get(Calendar.DAY_OF_MONTH);
        month = cal2.get(Calendar.MONTH);
        year = cal2.get(Calendar.YEAR);

        imgbtn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(TodaysCollection.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncViewCollection().execute(et_date.getText().toString());
            }
        });
        new AsyncViewCollection().execute(today);

    }

    private class AsyncViewCollection extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String... params) {
            RestAPI api=new RestAPI();
            try
            {
                JSONObject object=api.ViewTodaysCollection(session.getID(),params[0]);
                JSONArray jsonArray=object.getJSONArray("Value");
                Collection1=new ArrayList<>();
                JSONObject jsonObj=null;
                listsize=jsonArray.length();
                for(int i=0;i<jsonArray.length();i++)
                {
                    String temp;
                    jsonObj=jsonArray.getJSONObject(i);
                    Collection1.add(new Initiator(jsonObj.get("Farmer_ID").toString(),jsonObj.get("Farmer_Name").toString(),jsonObj.get("Milk_Quantity").toString(),jsonObj.get("Fat").toString(),jsonObj.get("deg").toString(),jsonObj.get("snf").toString(),jsonObj.get("MilkRate").toString(),jsonObj.get("Total").toString()));
            /*        fid.add(jsonObj.get("Farmer_ID").toString());
                    fname.add(jsonObj.get("Farmer_Name").toString());
                    quantity.add(jsonObj.get("Milk_Quantity").toString());
                    fat.add(jsonObj.get("Fat").toString());
                    deg.add(jsonObj.get("deg").toString());
                    snf.add(jsonObj.get("SNF").toString());
                    rate.add(jsonObj.get("MilkRate").toString());
                    total.add(jsonObj.get("Total").toString());
            */
                }
            }
            catch (Exception ex)
            {
                Log.d("Todays Collection Error : ",ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setadapter();
        }
    }



    private void setadapter() {
        CAdapter adapter=new CAdapter(context,Collection1);
        lv_collection.setAdapter(adapter);

                //new CAdapter(context,fid,fname,quantity,fat,deg,snf,rate,total));

    }

    public class CAdapter extends BaseAdapter implements ListAdapter {
        Context context;
        public List<String> fid,fname,quantity,fat,deg,snf,rate,total;
        public List<Initiator> Collection2=null;
        LayoutInflater inflater=null;
        public CAdapter(Context context, List<String> fid, List<String> fname, List<String> quantity, List<String> fat, List<String> deg, List<String> snf, List<String> rate, List<String> total) {
            this.context = context;
            this.fid = fid;
            this.fname = fname;
            this.quantity = quantity;
            this.fat = fat;
            this.deg = deg;
            this.snf = snf;
            this.rate = rate;
            this.total = total;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public CAdapter(Context context, ArrayList<Initiator> collection1) {
            this.context = context;
            this.Collection2=collection1;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listsize;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            TextView tv_dr_fid,tv_dr_name,tv_dr_quantity,tv_dr_fat,tv_dr_snf,tv_dr_rate,tv_dr_total,tv_dr_deg;
            View rows = inflater.inflate(R.layout.single_row_dailycollection, null);
           try {
               tv_dr_fid = (TextView) rows.findViewById(R.id.tv_dr_fid);
               tv_dr_name = (TextView) rows.findViewById(R.id.tv_dr_name);
               tv_dr_quantity = (TextView) rows.findViewById(R.id.tv_dr_quantity);
               tv_dr_fat = (TextView) rows.findViewById(R.id.tv_dr_fat);
               tv_dr_deg = (TextView) rows.findViewById(R.id.tv_dr_deg);
               tv_dr_snf = (TextView) rows.findViewById(R.id.tv_dr_snf);
               tv_dr_rate = (TextView) rows.findViewById(R.id.tv_dr_rate);
               tv_dr_total = (TextView) rows.findViewById(R.id.tv_dr_total);

               tv_dr_fid.setText(Collection2.get(position).getFarmer_id());
               tv_dr_name.setText(Collection2.get(position).getFarmer_name());
               tv_dr_quantity.setText(Collection2.get(position).getMilk_quantity());
               tv_dr_fat.setText(Collection2.get(position).getFat());
               tv_dr_deg.setText(Collection2.get(position).getDeg());
               tv_dr_snf.setText(Collection2.get(position).getSnf());
               tv_dr_rate.setText(Collection2.get(position).getMilkRate());
               tv_dr_total.setText(Collection2.get(position).getTotal());
           }
           catch(Exception e)
           {}

            rows.setTag(rows);
            return rows;
        }
    }

    public static class Initiator {
        String farmer_id, farmer_name, milk_quantity, fat, deg, snf, milkRate,total;

        public String getFarmer_id() {
            return farmer_id;
        }

        public void setFarmer_id(String farmer_id) {
            this.farmer_id = farmer_id;
        }

        public String getFarmer_name() {
            return farmer_name;
        }

        public void setFarmer_name(String farmer_name) {
            this.farmer_name = farmer_name;
        }

        public String getMilk_quantity() {
            return milk_quantity;
        }

        public void setMilk_quantity(String milk_quantity) {
            this.milk_quantity = milk_quantity;
        }

        public String getFat() {
            return fat;
        }

        public void setFat(String fat) {
            this.fat = fat;
        }

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }

        public String getSnf() {
            return snf;
        }

        public void setSnf(String snf) {
            this.snf = snf;
        }

        public String getMilkRate() {
            return milkRate;
        }

        public void setMilkRate(String milkRate) {
            this.milkRate = milkRate;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public Initiator(String farmer_id, String farmer_name, String milk_quantity, String fat, String deg, String snf, String milkRate, String total) {
            this.farmer_id = farmer_id;
            this.farmer_name = farmer_name;

            this.milk_quantity = milk_quantity;
            this.fat = fat;
            this.deg = deg;
            this.snf = snf;
            this.milkRate = milkRate;
            this.total = total;
        }
    }
}
