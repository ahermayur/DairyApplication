
package com.example.mayur.dairyapplication.Admin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddVideo extends AppCompatActivity {

    EditText et_video_title,et_video_id;
    TextView videoid;
    Button btn_save;
    String videotitle,video_id;
    private ProgressDialog progress;
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        progress= new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setMessage("Adding Video...");
        et_video_title=(EditText)findViewById(R.id.et_video_title);
        et_video_id=(EditText)findViewById(R.id.et_video_id);
        videoid=(TextView)findViewById(R.id.videoid);
        btn_save=(Button)findViewById(R.id.btn_save);
        et_video_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String str=et_video_id.getText().toString();
                if(hasFocus==false)
                {

                    if(str.length()<32)
                    {
                        et_video_title.setError("Please Enter Valid Video Url");
                    }
                    else {
                        videoid.setText(str.substring(32));
                    }
                }
                else
                {
                    if(str.length()<32)
                    {

                    }
                    else {
                        videoid.setText(str.substring(32));
                    }
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videotitle=et_video_title.getText().toString();
                video_id=videoid.getText().toString();
                new AsyncAddVideo().execute();
                et_video_id.setText("https://www.youtube.com/watch?v=");
                et_video_title.setText("");
            }
        });
    }

    private class AsyncAddVideo extends AsyncTask<Void,Void,Void>{

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
                jsonObject=api.InsertVideo(video_id,videotitle);
                result=jsonObject.getBoolean("Successful");
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
                alert.showAlertDialog1(AddVideo.this,"Failed..!","Video Add failed please try again....!",false);
            }
            else
            {
                alert.showAlertDialog1(AddVideo.this,"Success..!","Video Added Successful",false);
            }
        }
    }
}
