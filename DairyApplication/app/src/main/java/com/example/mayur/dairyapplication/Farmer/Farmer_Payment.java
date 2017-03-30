package com.example.mayur.dairyapplication.Farmer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daasuu.cat.CountAnimationTextView;
import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.StrictMath.round;

public class Farmer_Payment extends AppCompatActivity {
    ListView lv_farmer_payment;
    int listsize=0;
    public ArrayList<Initiator> Collection1;
    Context context;
    int day_count=15;
    int farmer_id;
    TextView tv_payment_total_milk,tv_payment_avg_fat,tv_payment_avg_snf,tv_payment_avg_rate,farmer_name,next,previous,payment_date;
    CountAnimationTextView tv_payment_total_payment;
    private ProgressDialog progress;
    AlertDialogManager alert = new AlertDialogManager();
    public static Calendar cal = Calendar.getInstance();
    final String today = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    String start_date,end_date;
    int current_v_year=cal.get(Calendar.YEAR);
    int current_v_month=(cal.get(Calendar.MONTH) + 1);
    int first_day;
    int last_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer__payment);

        lv_farmer_payment=(ListView)findViewById(R.id.lv_farmer_payment);
        tv_payment_total_milk=(TextView)findViewById(R.id.tv_payment_total_milk);
        tv_payment_avg_fat=(TextView)findViewById(R.id.tv_payment_avg_fat);
        tv_payment_avg_snf=(TextView)findViewById(R.id.tv_payment_avg_snf);
        tv_payment_avg_rate=(TextView)findViewById(R.id.tv_payment_avg_rate);
        tv_payment_total_payment=(CountAnimationTextView)findViewById(R.id.tv_payment_total_payment);
        farmer_name=(TextView)findViewById(R.id.tv_payment_farmer_name);
        next=(TextView)findViewById(R.id.tv_payment_next);
        previous=(TextView)findViewById(R.id.tv_payment_previous);
        payment_date=(TextView)findViewById(R.id.tv_payment_date);

        progress= new ProgressDialog(this);
        context=getApplicationContext();
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");
        Bundle bundle = getIntent().getExtras();
        farmer_id=bundle.getInt("id");
        farmer_name.setText(bundle.getString("name"));
        day_count=bundle.getInt("day");

        calculateday();
        payment_date.setText(" ( "+first_day+" to "+last_day+" ) / "+current_v_month+" / "+current_v_year);
        Collection1 = new ArrayList<>();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous();
            }
        });
    }

    public void calculateday() {
        if(day_count==15) {
            if (cal.get(Calendar.DAY_OF_MONTH) <= 15) {
                start_date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-01";
                end_date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-15";
                first_day=1;
                last_day=15;
            } else {
                start_date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-16";
                end_date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                first_day=16;
                last_day=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            day_count=last_day-first_day+1;
        }
        else {
            start_date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-01";
            end_date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            first_day=1;
            last_day=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            day_count=last_day;
        }
        new AsyncGetPayment().execute();
    }

    public void next()
    {
        if(day_count<=15)
        {
            if(first_day==1) {
                if(first_day==31)
                {
                    cal.add(Calendar.DAY_OF_MONTH, 16);
                }
                if(first_day==30)
                {
                    cal.add(Calendar.DAY_OF_MONTH, 15);
                }
                if(first_day==29)
                {
                    cal.add(Calendar.DAY_OF_MONTH, 14);
                }
                if(first_day==28)
                {
                    cal.add(Calendar.DAY_OF_MONTH, 13);
                }
            }
            if(first_day==16) {
                cal.add(Calendar.DAY_OF_MONTH, 15);
            }
        }
        else
        {
            cal.add(Calendar.MONTH, 1);
        }
        calculateday();
    }
    public void previous()
    {
        if(day_count<=15)
        {
            if(first_day==1) {
                if(first_day==31)
                {
                    cal.add(Calendar.DAY_OF_MONTH, -16);
                }
                if(first_day==30)
                {
                    cal.add(Calendar.DAY_OF_MONTH, -15);
                }
                if(first_day==29)
                {
                    cal.add(Calendar.DAY_OF_MONTH, -14);
                }
                if(first_day==28)
                {
                    cal.add(Calendar.DAY_OF_MONTH, -13);
                }
            }
            if(first_day==16) {
                cal.add(Calendar.DAY_OF_MONTH, -15);
            }
        }
        else
        {
            cal.add(Calendar.MONTH, -1);
        }
        calculateday();
    }



    public double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    public static class Initiator {
        String date;
        double milk;
        double fat;
        double snf;
        double rate;

        public Initiator(String date, double milk, double fat, double snf, double rate) {
            this.date = date;
            this.milk = milk;
            this.fat = fat;
            this.snf = snf;
            this.rate = rate;
        }

        public String getDate() {
            return date;
        }

        public double getMilk() {
            return milk;
        }

        public double getFat() {
            return fat;
        }

        public double getSnf() {
            return snf;
        }

        public double getRate() {
            return rate;
        }
    }
    public class CAdapter extends BaseAdapter implements ListAdapter {
        Context context;
        public List<Initiator> Collection2=null;
        LayoutInflater inflater=null;
        public CAdapter(Context context, ArrayList<Initiator> collection1) {
            this.context = context;
            this.Collection2=collection1;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return day_count;
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
            TextView tv_farmer_payment_date,tv_farmer_payment_milk,tv_farmer_payment_fat,tv_farmer_payment_snf,tv_farmer_payment_rate;
            View rows = inflater.inflate(R.layout.custom_farmer_payment, null);
            try {
                tv_farmer_payment_date = (TextView) rows.findViewById(R.id.cus_farmer_payment_date);
                tv_farmer_payment_milk = (TextView) rows.findViewById(R.id.cus_farmer_payment_milk);
                tv_farmer_payment_fat = (TextView) rows.findViewById(R.id.cus_farmer_payment_fat);
                tv_farmer_payment_snf = (TextView) rows.findViewById(R.id.cus_farmer_payment_snf);
                tv_farmer_payment_rate = (TextView) rows.findViewById(R.id.cus_farmer_payment_rate);
                tv_farmer_payment_date.setText(Collection2.get(position).getDate());
                tv_farmer_payment_milk.setText(Collection2.get(position).getMilk()+"");
                tv_farmer_payment_fat.setText(Collection2.get(position).getFat()+"");
                tv_farmer_payment_snf.setText(Collection2.get(position).getSnf()+"");
                tv_farmer_payment_rate.setText(Collection2.get(position).getRate()+"");
            }
            catch(Exception e)
            {
                e.getMessage();
            }
            rows.setTag(rows);
            return rows;
        }
    }

    private void setAdapter() {
        CAdapter adapter=new CAdapter(context,Collection1);
        lv_farmer_payment.setAdapter(adapter);

        double total_milk,avg_fat,avg_snf,avg_rate,total_payment;
        total_milk=0;
        avg_fat=0;
        avg_snf=0;
        avg_rate=0;
        total_payment=0;
        for(int i=0;i<Collection1.size();i++)
        {
            total_milk=total_milk+Collection1.get(i).getMilk();
            avg_fat=avg_fat+Collection1.get(i).getFat();
            avg_snf=avg_snf+Collection1.get(i).getSnf();
            avg_rate=avg_rate+Collection1.get(i).getRate();
            total_payment=total_payment+(Collection1.get(i).getMilk()*Collection1.get(i).getRate());
        }
        total_milk=roundTwoDecimals(total_milk);
        avg_fat=roundTwoDecimals((avg_fat/listsize));
        avg_rate=roundTwoDecimals((avg_rate/listsize));
        avg_snf=roundTwoDecimals((avg_snf/listsize));
        total_payment=roundTwoDecimals(total_payment);
        tv_payment_total_milk.setText(total_milk+"");
        tv_payment_avg_fat.setText(avg_fat+"");
        tv_payment_avg_snf.setText(avg_snf+"");
        tv_payment_avg_rate.setText(avg_rate+"");
        tv_payment_total_payment
                .setAnimationDuration(5000)
                .countAnimation(0, (int) round(total_payment));
    }

    private class AsyncGetPayment extends AsyncTask<Void,Void,Void>{

        boolean result=false;
        RestAPI api=new RestAPI();

        @Override
        protected void onPreExecute() {
            progress.setMessage("Please Wait...");
            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject object = api.GetPaymentDetails(farmer_id,start_date,end_date);
                result=object.getBoolean("Successful");
                JSONArray jsonArray = object.getJSONArray("Value");

                Collection1 = new ArrayList<>();
                JSONObject jsonObj = null;
                listsize = jsonArray.length();
                int temp=first_day;
                int day=1,month=1;
                for (int i = 0; i < jsonArray.length()+1; i++) {
                    try {
                        jsonObj = jsonArray.getJSONObject(i);
                        day = jsonObj.getInt("Day");
                        month = jsonObj.getInt("Month");
                    }
                    catch (Exception e)
                    {
                        while (last_day>temp)
                        {
                            temp++;
                            Collection1.add(new Initiator((temp)+"-"+month, 0.0, 0.0, 0.0, 0.0));
                        }
                    }
                    jsonObj = jsonArray.getJSONObject(i);
                    while (day>temp)
                    {
                        Collection1.add(new Initiator((temp)+"-"+month, 0.0, 0.0, 0.0, 0.0));
                        temp++;
                    }
                    Collection1.add(new Initiator(day+"-"+month, jsonObj.getDouble("Milk_Quantity"), jsonObj.getDouble("Fat"), jsonObj.getDouble("snf"), jsonObj.getDouble("MilkRate")));
                }
            }
            catch (Exception e)
            {
                e.getMessage();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if(result==false) {
                alert.showAlertDialog1(Farmer_Payment.this,"Failed","No Record found",true);
            }
            else
            {
                setAdapter();
            }
        }

    }
}
