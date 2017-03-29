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


public class Symptoms extends Fragment {
    EditText et_symptoms;
    Button btn_add;
    TextView tv_save;
    Context context;
    private ProgressDialog progress;
    ArrayList<String> symptoms_name;
    LinearLayout container2;
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context=getActivity();

        progress= new ProgressDialog(context);
        progress.setIndeterminate(true);

        View view= inflater.inflate(R.layout.fragment_symptoms, container, false);
        et_symptoms=(EditText)view.findViewById(R.id.et_symptoms);
        container2 = (LinearLayout)view.findViewById(R.id.container2);

        btn_add=(Button)view.findViewById(R.id.btn_symptoms);

        tv_save=(TextView)view.findViewById(R.id.tv_set_symptoms_save);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if((et_symptoms.getText().toString()).length()==0) {

                    et_symptoms.setError("Please Enter Symptoms.!");

                }else {


                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.cust_symptoms, null);
                    final ImageView btnDelete = (ImageView) addView.findViewById(R.id.btn_cust_symptoms_delete);

                    final TextView tv_symptoms_name = (TextView) addView.findViewById(R.id.tv_cus_symptoms_name);
                    tv_symptoms_name.setText(et_symptoms.getText().toString());

                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout) addView.getParent()).removeView(addView);
                        }
                    });
                    container2.addView(addView);
                    et_symptoms.setText("");

                }

            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int childCount = container2.getChildCount();

                symptoms_name=new ArrayList<String>();

                for (int c = 0; c < childCount; c++) {
                    View childView = container2.getChildAt(c);
                    TextView child_tv_dies_name = (TextView) (childView.findViewById(R.id.tv_cus_symptoms_name));
                    symptoms_name.add(c, child_tv_dies_name.getText().toString());

                }
                new Symptoms.AsyncInsertSymptoms().execute();

            }
        });

        return view;
    }
    private class AsyncInsertSymptoms extends AsyncTask<Void,Void,Void> {
        RestAPI api=new RestAPI();
        String msg;
        boolean result=false;
        @Override
        protected void onPreExecute() {
            progress.setMessage("Saving Symptoms Details...");
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject object = new JSONObject();


            try {
                object = api.InsertSymptoms(Disease.Diseaseid,symptoms_name );
               result=object.getBoolean("Value");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            container2.removeAllViews();
            progress.dismiss();
            if (result==false)
            {
                alert.showAlertDialog1(getActivity(),"Failed..!","Failed to insert new Symptoms please try again....!",false);
            }
            else
            {
                alert.showAlertDialog1(getActivity(),"Success..!","Symptoms Added Successfully....!",true);

                /*FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.cointner, new Precaution());
                transaction.commit();*/
            }
        }
    }

    }

