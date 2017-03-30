package com.example.mayur.dairyapplication;

import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayur.dairyapplication.SharePreferances.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewRateTable extends AppCompatActivity
{
    Context context;
    ListView  lv_rate;
    int length=0;
    String[] fat;
    String[] snf;
    String[] rate;
    SessionManager session;
    private ProgressDialog progress;
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_rate_table);
    session = new SessionManager(getApplicationContext());
        context=getApplicationContext();
        lv_rate=(ListView)findViewById(R.id.lv_rate);
   //     new AsyncViewRateTable().execute();
        Object obj[]=calulateRate();
    fat= (String[]) obj[0];
    snf= (String[]) obj[1];
    rate= (String[]) obj[2];
    setadapter();
    }

    public Object[] calulateRate() {


        length=0;
        for(double i=2;i<=5.1;i=  (i+0.1))
        {
            i = Math.round(i * 10);
            i = i/10;
            for(double j=5;j<10.1;j= (j+0.1))
            {
                j = Math.round(j * 10);
                j = j/10;
                length++;
            }
        }
        String fat[]=new String[length];
        String snf[]=new String[length];
        String rates[]=new String[length];
        float rate=session.getMilkRate();
        length=0;
        for(double i=2;i<=5.1;i=  (i+0.1))
        {
            i = Math.round(i * 10);
            i = i/10;
            float rate1 = rate;
            for(double j=5;j<10.1;j= (j+0.1))
            {
                j = Math.round(j * 10);
                j = j/10;
                rate1= (float) (rate1+0.20);
                fat[length]=i+"";
                snf[length]=j+"";
                rate1 = Math.round(rate1 * 100);
                rate1 = rate1/100;
                rates[length]=rate1+"";
                length++;
            }
            rate= (float) (rate+0.30);
            rate = Math.round(rate * 100);
            rate = rate/100;
        }
        return new Object[]{fat,snf,rates};
    }


    private class AsyncViewRateTable extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            RestAPI api=new RestAPI();
            try
            {
                JSONObject object=api.ViewMilkRate();
                JSONArray jsonArray=object.getJSONArray("Value");
                fat=new String[jsonArray.length()];
                snf=new String[jsonArray.length()];
                rate=new String[jsonArray.length()];
                JSONObject jsonObj=null;
                for(int i=0;i<jsonArray.length();i++)
                {
                    jsonObj=jsonArray.getJSONObject(i);
                    fat[length]=jsonObj.get("Fat").toString();
                    snf[length]=jsonObj.get("SNF").toString();
                    rate[length]=jsonObj.get("MilkRate").toString();
                    length++;
                }
            }
            catch (Exception e)
            {
                Log.d("View Rate Table : ",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            setadapter();
        }
    }

    private void setadapter() {
        lv_rate.setAdapter(new CAdapter(context,fat,snf,rate));
    }

    private class CAdapter extends BaseAdapter {
        Context context;
        String[] fat;
        String[] snf;
        String[] rate;
        LayoutInflater inflater=null;

        public CAdapter(Context context, String[] fat, String[] snf, String[] rate) {
            this.context = context;
            this.fat = fat;
            this.snf = snf;
            this.rate = rate;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class Holder
        {
            TextView tv_fat;
            TextView tv_snf;
            TextView tv_rate;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View rows = inflater.inflate(R.layout.single_row_rate, null);
            Holder holder=new Holder();
            holder.tv_fat = (TextView) rows.findViewById(R.id.tv_rate_fat);
            holder.tv_snf = (TextView) rows.findViewById(R.id.tv_rate_snf);
            holder.tv_rate = (TextView) rows.findViewById(R.id.tv_rate_rate);

            holder.tv_fat.setText(fat[position]);
            holder.tv_snf.setText(snf[position]);
            holder.tv_rate.setText(rate[position]);
            rows.setTag(rows);
            return rows;
        }
    }
}
