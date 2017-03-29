package com.example.mayur.dairyapplication.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;

import org.json.JSONObject;

import java.util.Random;

//import com.example.mayur.dairyadmins.Fragnents_Diesase.Frag_dies_show;


public class Disease extends Fragment {
  RadioButton rb_rain,rb_summer,rb_winter;
FrameLayout container;
    Button btn_insert,btn_clear;
    EditText et_title,et_decription;
    public static int Diseaseid;
    private ProgressDialog progress;
    AlertDialogManager alert = new AlertDialogManager();
    String season;
LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_disease, container, false);

        et_title=(EditText)view.findViewById(R.id.et__dies_title);
        et_decription=(EditText)view.findViewById(R.id.et__dies_description);
        btn_insert=(Button)view.findViewById(R.id.btn_insert_dies);
        btn_clear=(Button)view.findViewById(R.id.btn_clear);
        rb_summer=(RadioButton)view.findViewById(R.id.rbtn_summer);
        rb_rain=(RadioButton)view.findViewById(R.id.rbtn_rain);
        rb_winter=(RadioButton)view.findViewById(R.id.rbtn_winter);

        progress= new ProgressDialog(getActivity());
        progress.setIndeterminate(true);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Dname=et_title.getText().toString();
                String Descripion=et_decription.getText().toString();

                if(et_title.length()==0)
                {
                    et_title.setError("Please Enter Title");
                }
                else if(et_decription.length()==0)
                {
                    et_decription.setError("Please Enter Description");
                }
                else
                {

                    if(rb_rain.isChecked()==true) {

                        season ="Rainy";
                    }
                    else if (rb_winter.isChecked()==true)
                    {
                       season="winter";
                    }
                    else if (rb_summer.isChecked()==true)
                    {
                        season="Summer";
                    }
                    else
                    {
                        season="all season";
                    }
                    Constructor_insert constructor_insert=new Constructor_insert(Dname,Descripion);
                    new Disease.AsyncInsertdiease().execute(constructor_insert);
                    clear();
                }

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        return  view;
    }
    public void clear()
    {
        et_decription.setText("");
        et_title.setText("");
    }

    private class AsyncInsertdiease extends AsyncTask<Constructor_insert,Void,Void> {
        boolean result=false;
        JSONObject jsonObject=null;
        @Override
        protected void onPreExecute() {
            progress.setMessage("Please Wait a Moment...");
            progress.show();
        }

        @Override
        protected Void doInBackground(Constructor_insert... params) {
            RestAPI restAPI=new RestAPI();
            try
            {
                Diseaseid=new Random().nextInt(99999);
                jsonObject=restAPI.InsertDisease(Diseaseid,params[0].getDname(),params[0].getDescripion(),season);
                result=jsonObject.getBoolean("Successful");
                try
                {
                    jsonObject.getString("ErrorMessage");
                    result=false;
                }
                catch (Exception e)
                {

                }
            }
            catch (Exception e)
            { e.printStackTrace();}
            return null;


        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if (result==false)
            {
                alert.showAlertDialog1(getActivity(),"Failed..!","Failed to insert new Disease please try again....!",false);
            }
            else
            {
                alert.showAlertDialog1(getActivity(),"Success..!","Disease Added Successfully....!",true);

                /*FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.cointner, new Precaution());
                transaction.commit();*/
            }

        }
    }
}
