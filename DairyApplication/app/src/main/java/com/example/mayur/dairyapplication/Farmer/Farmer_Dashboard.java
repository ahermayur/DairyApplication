package com.example.mayur.dairyapplication.Farmer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mayur.dairyapplication.Admin.AdminDashboard;
import com.example.mayur.dairyapplication.Admin.View_Disease;
import com.example.mayur.dairyapplication.Admin.View_Disease_Info;
import com.example.mayur.dairyapplication.Admin.Watch_Video;
import com.example.mayur.dairyapplication.Login_Activity;
import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.SharePreferances.SessionManager;
import com.example.mayur.dairyapplication.ViewRateTable;

public class Farmer_Dashboard extends AppCompatActivity {
    TextView tv_farmer_Watchvideo,tv_farmer_see_details,tv_farmer_see_dis,tv_farmer_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer__dashboard);
        tv_farmer_Watchvideo=(TextView)findViewById(R.id.tv_menu_farmer_Watchvideo);
        tv_farmer_see_details=(TextView)findViewById(R.id.tv_Farmer_see_currentrate);
        tv_farmer_see_dis=(TextView)findViewById(R.id.tv_farmer_see_dies);
        tv_farmer_logout=(TextView)findViewById(R.id.tv_farmer_loguot);

        tv_farmer_Watchvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Farmer_Dashboard.this,Watch_Video.class);
                startActivity(i);
            }
        });

        tv_farmer_see_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Farmer_Dashboard.this,ViewRateTable.class);
                startActivity(i);
            }
        });

        tv_farmer_see_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Farmer_Dashboard.this,View_Disease.class);
                startActivity(intent);
            }
        });

        tv_farmer_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Farmer_Dashboard.this,Login_Activity.class);
                startActivity(i);

            }
        });
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
                new SessionManager(getApplicationContext()).logoutUser();
                Intent intent=new Intent(Farmer_Dashboard.this,Login_Activity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Farmer_Dashboard.this);
        builder.setMessage("Do you want to exit ?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                finish();
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
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
