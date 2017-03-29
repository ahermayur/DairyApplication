package com.example.mayur.dairyapplication.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;

import org.json.JSONObject;

import java.util.ArrayList;

public class Medi extends Fragment {

    EditText et_medicine;
    Button btn_add;
    TextView tv_save;
    Context context;
    private ProgressDialog progress;
    ArrayList<String> medicine_name;
    LinearLayout container3;
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context=getActivity();

        progress= new ProgressDialog(context);
        progress.setIndeterminate(true);

        View view= inflater.inflate(R.layout.fragment_medi, container, false);
        et_medicine=(EditText)view.findViewById(R.id.et_medicine);
        container3 = (LinearLayout)view.findViewById(R.id.container3);

        btn_add=(Button)view.findViewById(R.id.btn_Medicine);

        tv_save=(TextView)view.findViewById(R.id.tv_set_Medicine_save);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((et_medicine.getText().toString()).length()==0) {

                    et_medicine.setError("Please Enter Medicine Name.!");

                }else {

                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.cust_medicine, null);
                final ImageView btnDelete = (ImageView) addView.findViewById(R.id.btn_cust_medicine_delete);

                final TextView tv_medicine_name = (TextView) addView.findViewById(R.id.tv_cus_medicine_name);
                tv_medicine_name.setText(et_medicine.getText().toString());

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) addView.getParent()).removeView(addView);
                    }
                });
                container3.addView(addView);
                et_medicine.setText("");

            }

            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int childCount = container3.getChildCount();

                medicine_name=new ArrayList<String>();

                for (int c = 0; c < childCount; c++) {
                    View childView = container3.getChildAt(c);
                    TextView child_tv_dies_name = (TextView) (childView.findViewById(R.id.tv_cus_medicine_name));
                    medicine_name.add(c, child_tv_dies_name.getText().toString());

                }
                new Medi.AsyncInsertMedicine().execute();

            }
        });

        return view;
    }
    private class AsyncInsertMedicine extends AsyncTask<Void,Void,Void> {
        RestAPI api=new RestAPI();
        String msg;
        boolean result=false;
        @Override
        protected void onPreExecute() {
            progress.setMessage("Saving Medicine Details...");
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject object = new JSONObject();


            try {
                object = api.InsertMedicin(Disease.Diseaseid,medicine_name);
                result=object.getBoolean("Value");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            container3.removeAllViews();
            if (result==false)
            {
                alert.showAlertDialog1(getActivity(),"Failed..!","Failed to insert new Medicine please try again....!",false);
            }
            else
            {
                alert.showAlertDialog1(getActivity(),"Success..!","Medicine Added Successfully....!",true);

                /*FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.cointner, new Precaution());
                transaction.commit();*/
            }
        }
    }



}
