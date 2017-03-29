package com.example.mayur.dairyapplication.Admin;

import android.app.ProgressDialog;
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

public class Watch_Video extends AppCompatActivity {
    ListView lv_video;
    AlertDialogManager alert = new AlertDialogManager();
    private ProgressDialog progress;
    String video_title[];
    String video_id[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video);
        lv_video=(ListView)findViewById(R.id.lv_video);
        progress= new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setMessage("Loading Video...");
        new AsyncWatchVideo().execute();
        lv_video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BasicPlayerActivity.VIDEO_ID=video_id[position];
                Intent intent=new Intent(Watch_Video.this,BasicPlayerActivity.class);
                startActivity(intent);
            }
        });
    }

    private class AsyncWatchVideo extends AsyncTask<Void,Void,Void>{
        Boolean result=false;
        JSONObject jsonObject=null;
        RestAPI api=new RestAPI();
        @Override
        protected void onPreExecute() {

            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                jsonObject=api.WatchVideo();
                result=jsonObject.getBoolean("Successful");
                JSONArray jsonArray=jsonObject.getJSONArray("Value");
                JSONObject jsonObj=null;
                video_id=new String[jsonArray.length()];
                video_title=new String[jsonArray.length()];
                for(int i=0;i<jsonArray.length();i++)
                {
                    jsonObj=jsonArray.getJSONObject(i);
                    video_id[i]=jsonObj.getString("video_id");
                    video_title[i]=jsonObj.getString("video_title");
                }
                try {
                    String error=jsonObject.getString("ErrorMessage");
                    result=false;
                }
                catch (Exception e)
                {

                }
            }
            catch(Exception e)
            {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if (result==false)
            {
                alert.showAlertDialog1(Watch_Video.this,"Failed..!","Failed to Load Video please try again....!",false);
            }
            else
            {
                lv_video.setAdapter(new ArrayAdapter<String>(Watch_Video.this,android.R.layout.simple_list_item_1,video_title));
            }
        }
    }
}
