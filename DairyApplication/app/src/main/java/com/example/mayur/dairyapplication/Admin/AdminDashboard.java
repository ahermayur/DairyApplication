package com.example.mayur.dairyapplication.Admin;

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

import com.example.mayur.dairyapplication.Login_Activity;
import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.SetMilkRate;
import com.example.mayur.dairyapplication.SharePreferances.SessionManager;
import com.example.mayur.dairyapplication.ViewRateTable;

public class AdminDashboard extends AppCompatActivity {
    SessionManager session;
    TextView tv_menu_addvideo,tv_menu_Watchvideo,tv_menu_admin_currentrate,tv_menu_admin_add_dis,tv_menu_admin_view_dis,tv_menu_admin_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        session = new SessionManager(getApplicationContext());
        tv_menu_addvideo=(TextView)findViewById(R.id.tv_menu_addvideo);
       // tv_menu_admin_currentrate=(TextView)findViewById(R.id.tv_menu_admin_currentrate);
        tv_menu_admin_add_dis=(TextView)findViewById(R.id.tv_menu_admin_add_dis);
        tv_menu_admin_view_dis=(TextView)findViewById(R.id.tv_menu_admin_view_dis);
        tv_menu_Watchvideo=(TextView)findViewById(R.id.tv_menu_Watchvideo);
      //  tv_menu_admin_logout=(TextView)findViewById(R.id.tv_menu_admin_logout);
        tv_menu_addvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,AddVideo.class);
                startActivity(intent);
            }
        });
        tv_menu_admin_view_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,View_Disease.class);
                startActivity(intent);
            }
        });

        tv_menu_Watchvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,Watch_Video.class);
                startActivity(intent);
            }
        });
        tv_menu_admin_add_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this,Admin_Dashboard.class);
                startActivity(intent);
            }
        });
     /*   tv_menu_admin_currentrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this, ViewRateTable.class);
                startActivity(intent);
            }
        });
        tv_menu_admin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminDashboard.this, Login_Activity.class);
                startActivity(intent);
                finish();
                session.logoutUser();
            }
        });*/
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
                finishAffinity();
                Intent intent=new Intent(AdminDashboard.this,Login_Activity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
            AlertDialog.Builder builder=new AlertDialog.Builder(AdminDashboard.this);
            builder.setMessage("Do you want to exit ?");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    finishAffinity();
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
