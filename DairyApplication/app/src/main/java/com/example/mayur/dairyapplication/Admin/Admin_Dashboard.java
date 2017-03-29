package com.example.mayur.dairyapplication.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.mayur.dairyapplication.R;

public class Admin_Dashboard extends AppCompatActivity {


TextView tv_diease,tv_symptoms,tv_precaution,tv_medicine;
    int count=0;
    boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__dashboard);
      tv_diease=(TextView)findViewById(R.id.tv_diease);
        tv_precaution=(TextView)findViewById(R.id.tv_precausion);
        tv_symptoms=(TextView)findViewById(R.id.tv_symptoms);
        tv_medicine=(TextView)findViewById(R.id.tv_medicine);

        tv_diease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = true;
                tv_diease.setBackgroundColor(Color.MAGENTA);
                tv_precaution.setBackgroundColor(View.INVISIBLE);
                tv_medicine.setBackgroundColor(View.INVISIBLE);
                tv_symptoms.setBackgroundColor(View.INVISIBLE);

                FragmentManager manager = getSupportFragmentManager();

                FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.cointner, new Disease());
                    // transaction.addToBackStack(null);
                    transaction.commit();
            }
        });
        tv_precaution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = true;
                tv_precaution.setBackgroundColor(Color.MAGENTA);
                tv_diease.setBackgroundColor(View.INVISIBLE);
                tv_medicine.setBackgroundColor(View.INVISIBLE);
                tv_symptoms.setBackgroundColor(View.INVISIBLE);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.cointner, new Precaution());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        tv_symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = true;
                tv_symptoms.setBackgroundColor(Color.MAGENTA);
                tv_diease.setBackgroundColor(View.INVISIBLE);
                tv_precaution.setBackgroundColor(View.INVISIBLE);
                tv_medicine.setBackgroundColor(View.INVISIBLE);

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.cointner, new Symptoms());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        tv_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_medicine.setBackgroundColor(Color.MAGENTA);
                tv_precaution.setBackgroundColor(View.INVISIBLE);
                tv_diease.setBackgroundColor(View.INVISIBLE);
                tv_symptoms.setBackgroundColor(View.INVISIBLE);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.cointner, new Medi());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
