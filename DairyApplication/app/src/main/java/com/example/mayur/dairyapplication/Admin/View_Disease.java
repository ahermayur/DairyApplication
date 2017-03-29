package com.example.mayur.dairyapplication.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_Disease extends AppCompatActivity {
    ListView lv_disease;
    private ProgressDialog progress;
    AlertDialogManager alert = new AlertDialogManager();
    Context context;
    String[] Title;
    String[] Description;
    String[] Season;
    int[] did;
    View_Disease_Info info=new View_Disease_Info();
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_disease);
        context=getApplicationContext();
        progress= new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setMessage("Getting Disease List...");
        lv_disease=(ListView)findViewById(R.id.lv_view_disease);
        lv_disease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View_Disease_Info.des_title=Title[position];
                View_Disease_Info.des_desc=Description[position];
                View_Disease_Info.desc_season=Season[position];
                View_Disease_Info.desid=did[position];


                Intent intent=new Intent(View_Disease.this,View_Disease_Info.class);
                startActivity(intent);
            }
        });
        new Async_View().execute();
    }

    private class Async_View extends AsyncTask<Void,Void,Void>{
        Boolean result=false;
        JSONObject jsonObject=null;
        RestAPI api=new RestAPI();
        @Override
        protected void onPreExecute() {
            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            RestAPI api=new RestAPI();
            try {
                jsonObject=api.GetDetailsDisease();
                result=jsonObject.getBoolean("Successful");
                JSONArray jsonArray=jsonObject.getJSONArray("Value");
                JSONObject jsonObj=null;
                size=jsonArray.length();
                did=new int[jsonArray.length()];
                Title=new String[jsonArray.length()];
                Description=new String[jsonArray.length()];
                Season=new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObj = jsonArray.getJSONObject(i);
                    did[i] = jsonObj.getInt("Did");

                    Title[i] = jsonObj.getString("Dname");
                    Description[i] = jsonObj.getString("Descripion");
                    try{
                        Season[i] = jsonObj.getString("Season");
                    }
                    catch (Exception e)
                    {
                        Season[i]="Null";
                    }

                }
            } catch (Exception e) {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if (result==false)
            {
                alert.showAlertDialog1(View_Disease.this,"Failed..!","Loading failed please try again....!",false);
            }
            else
            {
                setadapter();
            }

        }
    }
    private void setadapter() {
        lv_disease.setAdapter(new ArrayAdapter<String>(View_Disease.this,android.R.layout.simple_list_item_1,Title));
    }
}
