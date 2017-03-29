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


public class Precaution extends Fragment {
    EditText et_precaution;
    Button btn_add;
    TextView tv_save;
    Context context;
    private ProgressDialog progress;
    AlertDialogManager alert = new AlertDialogManager();
    ArrayList<String> precaution_name;
       LinearLayout container1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();

        progress= new ProgressDialog(context);
        progress.setIndeterminate(true);
        View view= inflater.inflate(R.layout.fragment_precaution, container, false);

        et_precaution=(EditText)view.findViewById(R.id.et_precaution);
        container1 = (LinearLayout)view.findViewById(R.id.container1);

        btn_add=(Button)view.findViewById(R.id.btn_precaution);

        tv_save=(TextView)view.findViewById(R.id.tv_set_diease_save);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if((et_precaution.getText().toString()).length()==0) {

                    et_precaution.setError("Please Enter Prevention.!");

                }else{


                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.custom_precution, null);
                    final ImageView btnDelete = (ImageView) addView.findViewById(R.id.btn_cust_precaution_delete);

                    final TextView tv_precaution_name = (TextView) addView.findViewById(R.id.tv_cus_precaution_name);

                    tv_precaution_name.setText(et_precaution.getText().toString());


                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout) addView.getParent()).removeView(addView);
                        }
                    });
                    container1.addView(addView);
                    et_precaution.setText("");
                }

            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int childCount = container1.getChildCount();

                precaution_name=new ArrayList<String>();

                    for (int c = 0; c < childCount; c++) {
                        View childView = container1.getChildAt(c);
                        TextView child_tv_dies_name = (TextView) (childView.findViewById(R.id.tv_cus_precaution_name));
                        precaution_name.add(c, child_tv_dies_name.getText().toString());

                    }
                    new AsyncInsertDiease().execute();

            }
        });

               return view;
    }
    private class AsyncInsertDiease extends AsyncTask<Void,Void,Void> {
        RestAPI api=new RestAPI();
        boolean result=false;
        JSONObject jsonObject=null;
        String msg;
        @Override
        protected void onPreExecute() {
            progress.setMessage("Saving Medicine Details...");
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject object = new JSONObject();


            try {
                object = api.InsertPrecaution(Disease.Diseaseid,precaution_name);
                result=object.getBoolean("Value");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if (result==false)
            {
                alert.showAlertDialog1(getActivity(),"Failed..!","Failed to insert new Prevention please try again....!",false);
            }
            else
            {
                alert.showAlertDialog1(getActivity(),"Success..!","Prevention Added Successfully....!",true);

                /*FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.cointner, new Precaution());
                transaction.commit();*/
            }
        }
    }

}
