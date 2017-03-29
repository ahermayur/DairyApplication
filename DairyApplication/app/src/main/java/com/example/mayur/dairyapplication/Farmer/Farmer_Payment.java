package com.example.mayur.dairyapplication.Farmer;

import android.content.Context;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.round;

public class Farmer_Payment extends AppCompatActivity {
    ListView lv_farmer_payment;
    int listsize=0;
    public ArrayList<Initiator> Collection1;
    Context context;
    TextView tv_payment_total_milk,tv_payment_avg_fat,tv_payment_avg_snf,tv_payment_avg_rate;
    CountAnimationTextView tv_payment_total_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer__payment);
        lv_farmer_payment=(ListView)findViewById(R.id.lv_farmer_payment);
        context=getApplicationContext();
        Collection1 = new ArrayList<>();
        Collection1.add(new Initiator("1-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("2-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("3-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("4-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("5-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("6-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("7-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("8-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("9-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("10-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("11-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("12-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("13-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("14-3",14.2,3.5,8.5,23.7));
        Collection1.add(new Initiator("15-3",14.2,3.5,8.5,23.7));
        listsize=Collection1.size();
        setAdapter();
        tv_payment_total_milk=(TextView)findViewById(R.id.tv_payment_total_milk);
        tv_payment_avg_fat=(TextView)findViewById(R.id.tv_payment_avg_fat);
        tv_payment_avg_snf=(TextView)findViewById(R.id.tv_payment_avg_snf);
        tv_payment_avg_rate=(TextView)findViewById(R.id.tv_payment_avg_rate);
        tv_payment_total_payment=(CountAnimationTextView)findViewById(R.id.tv_payment_total_payment);

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
        avg_fat=roundTwoDecimals((avg_fat/15));
        avg_rate=roundTwoDecimals((avg_rate/15));
        avg_snf=roundTwoDecimals((avg_snf/15));
        total_payment=roundTwoDecimals(total_payment);
        tv_payment_total_milk.setText(total_milk+"");
        tv_payment_avg_fat.setText(avg_fat+"");
        tv_payment_avg_snf.setText(avg_snf+"");
        tv_payment_avg_rate.setText(avg_rate+"");
        tv_payment_total_payment
                .setAnimationDuration(5000)
                // .setDecimalFormat(new DecimalFormat(".##"))
                .countAnimation(0, (int) round(total_payment));
        // tv_payment_total_payment.setText(total_payment+"");
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
    }
}
