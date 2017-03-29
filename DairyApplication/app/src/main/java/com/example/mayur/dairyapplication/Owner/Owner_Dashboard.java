package com.example.mayur.dairyapplication.Owner;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mayur.dairyapplication.Admin.Watch_Video;
import com.example.mayur.dairyapplication.FarmerRegister;
import com.example.mayur.dairyapplication.Login_Activity;
import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RegisterFarmer;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SetMilkRate;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;
import com.example.mayur.dairyapplication.SharePreferances.SessionManager;
import com.example.mayur.dairyapplication.TodaysCollection;
import com.example.mayur.dairyapplication.UpdateMilkDetails;
import com.example.mayur.dairyapplication.ViewRateTable;

import org.json.JSONArray;
import org.json.JSONObject;

public class Owner_Dashboard extends AppCompatActivity {

    TextView tv_menu_owner_view_farmer,tv_menu_owner_register_farmer,menu_setRate,tv_menu_owner_purchase,tv_menu_update_milk,tv_menu_owner_view_rate,tv_menu_owner_daily_collection,tv_menu_owner_watch_video;
    Intent intent;
    public static Boolean israteset=false;
    NotificationManager manager;
    Notification myNotication;
    SessionManager session;
    private ProgressDialog progress;

    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress= new ProgressDialog(this);
        progress.setIndeterminate(true);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_owoner__dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        menu_setRate=(TextView) findViewById(R.id.tv_menu_owner_setrate);
        tv_menu_owner_view_farmer=(TextView) findViewById(R.id.tv_menu_owner_view_farmer);
        tv_menu_owner_purchase=(TextView) findViewById(R.id.tv_menu_owner_purchase);
        tv_menu_owner_daily_collection=(TextView) findViewById(R.id.tv_menu_owner_daily_collection);
        tv_menu_owner_view_rate=(TextView) findViewById(R.id.tv_menu_owner_view_rate);
        tv_menu_owner_watch_video=(TextView) findViewById(R.id.tv_menu_owner_watch_video);
        tv_menu_owner_register_farmer=(TextView) findViewById(R.id.tv_menu_owner_register_farmer);
        tv_menu_update_milk=(TextView) findViewById(R.id.tv_menu_update_milk);
        session = new SessionManager(getApplicationContext());
        callforrate();
        menu_setRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Owner_Dashboard.this, SetMilkRate.class);
                startActivity(intent);
                manager.cancel(1);
            }
        });
        tv_menu_owner_watch_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Owner_Dashboard.this, Watch_Video.class);
                startActivity(intent);
            }
        });
        tv_menu_owner_view_farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Owner_Dashboard.this, View_Farmer_List.class);
                startActivity(intent);
            }
        });

        tv_menu_owner_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Owner_Dashboard.this, Fragment_MilkPurchase.class);
                startActivity(intent);
            }
        });
        tv_menu_owner_register_farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Owner_Dashboard.this, FarmerRegister.class);
                startActivity(intent);
            }
        });
        tv_menu_owner_daily_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Owner_Dashboard.this, TodaysCollection.class);
                startActivity(intent);
            }
        });
        tv_menu_owner_view_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Owner_Dashboard.this, ViewRateTable.class);
                startActivity(intent);
            }
        });
        tv_menu_update_milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Owner_Dashboard.this, UpdateMilkDetails.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sendNotification();
    }

    private void callforrate() {
        if(session.getMilkRate()==0)
        {
            new AsyncRate().execute();
        }
    }

    private class AsyncRate extends AsyncTask<Void,Void,Void> {
        Boolean result=false;
        JSONObject jsonObject=null;
        RestAPI api=new RestAPI();
        @Override
        protected void onPreExecute() {
            progress.setMessage("Getting Current Milk Rate");
            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                jsonObject=api.CurrentRate();
                result=jsonObject.getBoolean("Successful");
                JSONArray jsonArray=jsonObject.getJSONArray("Value");
                JSONObject jsonObj=null;
                if(jsonArray.length()==0)
                {
                    israteset=false;
                    result=false;
                }
                else
                {
                    israteset=true;
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        jsonObj=jsonArray.getJSONObject(i);
                        session.setMilkRate((float) jsonObj.getDouble("MilkRate"));
                        Log.d("Share Preferance","Information added");
                    }
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

                sendNotification();
                alert.showAlertDialog1(Owner_Dashboard.this,"Failed..!","Failed to access current Milk Rate....!",false);
                disableMenu(false);
            }
            else
            {
                disableMenu(true);
            }
        }
    }

    private void sendNotification() {
 /*       Intent intent = new Intent(Owner_Dashboard.this, SetMilkRate.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(Owner_Dashboard.this, 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(Owner_Dashboard.this);
        builder.setAutoCancel(true);
        builder.setContentTitle("Milk Rate Not Avilable..");
        builder.setContentText("Please Set New Milk Rate...");
        builder.setSmallIcon(R.drawable.rupees);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setNumber(100);
        builder.build();
        myNotication = builder.getNotification();
        manager.notify(1, myNotication);*/
    }

    public void disableMenu(boolean b) {
        tv_menu_owner_purchase.setEnabled(b);
        tv_menu_update_milk.setEnabled(b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                finish();
                finishAffinity();
                new SessionManager(getApplicationContext()).logoutUser();
                finishAffinity();
                finish();
                Intent intent=new Intent(Owner_Dashboard.this,Login_Activity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(Owner_Dashboard.this);
        builder.setMessage("Do you want to exit ?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.setIcon(R.drawable.warning);
        alertDialog.setTitle("Exit");
        alertDialog.show();
    }
}
