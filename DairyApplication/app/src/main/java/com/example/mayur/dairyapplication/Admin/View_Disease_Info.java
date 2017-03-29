package com.example.mayur.dairyapplication.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_Disease_Info extends AppCompatActivity {
    public static String des_title,des_desc,desc_season;
    public static int desid;
    TextView tv_d_title,tv_d_season,tv_d_Descripion;
    Context context;
    String data_list[],data_list2[],data_list3[];
    LinearLayout container;

    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_disease_info);
        context=getApplicationContext();
        tv_d_title=(TextView)findViewById(R.id.tv_d_title);
        tv_d_season=(TextView)findViewById(R.id.tv_d_season);
        tv_d_Descripion=(TextView)findViewById(R.id.tv_d_Descripion);
        container = (LinearLayout)findViewById(R.id.ll_container);
        tv_d_title.setText(des_title+"");
        tv_d_season.setText("Season : "+desc_season+"");
        tv_d_Descripion.setText(des_desc+"");
        new AsyncGetPrecaution().execute();
    }

    private void setData(String[] data_list, String s) {
        LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView1 = layoutInflater.inflate(R.layout.custom_v_titles, null);
        final TextView tv_d_c_tit = (TextView) addView1.findViewById(R.id.tv_d_c_tit);
        tv_d_c_tit.setText(s);
        container.addView(addView1);
        for (int i=0;i<data_list.length;i++) {
            final View addView = layoutInflater.inflate(R.layout.custom_v_precaution, null);
            final TextView tv_d_c_pre = (TextView) addView.findViewById(R.id.tv_d_c_pre);
            tv_d_c_pre.setText(data_list[i]);
            container.addView(addView);
        }
    }

    private class AsyncGetPrecaution extends AsyncTask<Void,Void,Void> {
        RestAPI api=new RestAPI();
        boolean result=false;
        JSONObject jsonObject=null;
        String msg;
        @Override
        protected void onPreExecute() {

//            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                jsonObject=api.GetAllPrecaution(desid);
                result=jsonObject.getBoolean("Successful");
            JSONArray jsonArray=jsonObject.getJSONArray("Value");
                data_list=new String[jsonArray.length()];
            JSONObject jsonObj=null;
                if(jsonArray.length()<=0)
                {
                    result=false;
                }
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);

                data_list[i]=jsonObj.getString("Description");
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {

            if(result==true) {
                setData(data_list, " Prevention ");
            }
            new AsyncGetSymptoms().execute();
        }
    }

    private class AsyncGetSymptoms extends AsyncTask<Void,Void,Void> {
        RestAPI api=new RestAPI();
        boolean result=false;
        JSONObject jsonObject=null;
        String msg;
        @Override
        protected void onPreExecute() {

//            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                jsonObject=api.GetAllSymptom(desid);
                result=jsonObject.getBoolean("Successful");
                JSONArray jsonArray=jsonObject.getJSONArray("Value");
                data_list2=new String[jsonArray.length()];
                JSONObject jsonObj=null;
                if(jsonArray.length()<=0)
                {
                    result=false;
                }
                for(int i=0;i<jsonArray.length();i++)
                {
                    jsonObj=jsonArray.getJSONObject(i);

                    data_list2[i]=jsonObj.getString("description");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
         //   progress.hide();
            if(result==true) {
                setData(data_list2, " Symptoms ");
            }
            new AsyncGetMedicine().execute();
        }
    }

    private class AsyncGetMedicine  extends AsyncTask<Void,Void,Void> {
        RestAPI api=new RestAPI();
        boolean result=false;
        JSONObject jsonObject=null;
        String msg;
        @Override
        protected void onPreExecute() {

     //       progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                jsonObject=api.GetAllMedicine(desid);
                result=jsonObject.getBoolean("Successful");
                JSONArray jsonArray=jsonObject.getJSONArray("Value");
                data_list3=new String[jsonArray.length()];
                JSONObject jsonObj=null;
                if(jsonArray.length()<=0)
                {
                    result=false;
                }
                for(int i=0;i<jsonArray.length();i++)
                {
                    jsonObj=jsonArray.getJSONObject(i);

                    data_list3[i]=jsonObj.getString("Description");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
         //   progress.dismiss();
            if(result==true) {
                setData(data_list3, " Medicine ");
            }
        }
    }
}
