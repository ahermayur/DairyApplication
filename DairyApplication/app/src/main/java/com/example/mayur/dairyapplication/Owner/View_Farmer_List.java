package com.example.mayur.dairyapplication.Owner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayur.dairyapplication.Farmer.Farmer_Payment;
import com.example.mayur.dairyapplication.R;
import com.example.mayur.dairyapplication.RestAPI;
import com.example.mayur.dairyapplication.SharePreferances.AlertDialogManager;
import com.example.mayur.dairyapplication.SharePreferances.SessionManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class View_Farmer_List extends AppCompatActivity {
    ListView lv_farmer_list;
    int listsize=0;
    Context context;
    private ProgressDialog progress;
    SessionManager session;
    AlertDialogManager alert = new AlertDialogManager();
    public ArrayList<Initiator> Collection1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__farmer__list);
        lv_farmer_list=(ListView)findViewById(R.id.lv_farmer_list);

        lv_farmer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                final PopupMenu popup = new PopupMenu(View_Farmer_List.this, lv_farmer_list);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_farmer_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent=new Intent(View_Farmer_List.this, Farmer_Payment.class);
                        switch (item.getItemId())
                        {
                            case R.id.popup_15days:
                                intent.putExtra("day",15);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.popup_30days:
                                intent.putExtra("day",15);
                                startActivity(intent);
                                finish();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        session = new SessionManager(getApplicationContext());
        progress= new ProgressDialog(this);
        context=getApplicationContext();
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");
        new AsyncViewFarmerList().execute();
    }

    private class AsyncViewFarmerList extends AsyncTask<Void,Void,Void> {
        boolean result=false;
        RestAPI api=new RestAPI();

        @Override
        protected void onPreExecute() {
            progress.setMessage("Retriving Farmer List...");
            progress.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject object = api.ViewAllFarmer(session.getID());
                result=object.getBoolean("Successful");
                JSONArray jsonArray = object.getJSONArray("Value");

                Collection1 = new ArrayList<>();
                JSONObject jsonObj = null;
                listsize = jsonArray.length();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String temp;
                    jsonObj = jsonArray.getJSONObject(i);
                    Collection1.add(new Initiator(jsonObj.getInt("Farmer_ID"), jsonObj.getString("Farmer_Name"), jsonObj.getString("Farmer_Address"), jsonObj.getString("Farmer_MobNo"), jsonObj.getString("Farmer_UserName")));
                }
            }
            catch (Exception e)
            {
                e.getMessage();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();
            if(result==false) {
                alert.showAlertDialog1(View_Farmer_List.this,"Network Error","Please try again",true);
            }
            else
            {
                setAdapter();
            }
        }
    }

    public class CAdapter extends BaseAdapter implements ListAdapter {
        Context context;
        public List<Initiator> Collection2=null;
        LayoutInflater inflater=null;
        public CAdapter(Context context, ArrayList<Initiator> collection1) {
            this.context = context;
            this.Collection2=collection1;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return listsize;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            TextView tv_dr_fid,tv_dr_name,tv_dr_address,tv_dr_mobno,tv_dr_username;
            View rows = inflater.inflate(R.layout.custom_farmer_list, null);
            try {
                tv_dr_fid = (TextView) rows.findViewById(R.id.tv_r_fid);
                tv_dr_name = (TextView) rows.findViewById(R.id.tv_r_name);
                tv_dr_address = (TextView) rows.findViewById(R.id.tv_r_address);
                tv_dr_mobno = (TextView) rows.findViewById(R.id.tv_r_MobNo);
                tv_dr_username = (TextView) rows.findViewById(R.id.tv_r_username);
                tv_dr_fid.setText(Collection2.get(position).getFarmer_id()+"");
                tv_dr_name.setText(Collection2.get(position).getFarmer_name());
                tv_dr_address.setText(Collection2.get(position).getAddress());
                tv_dr_mobno.setText(Collection2.get(position).getMobno());
                tv_dr_username.setText(Collection2.get(position).getUsername());
            }
            catch(Exception e)
            {
                e.getMessage();
            }
            rows.setTag(rows);
            return rows;
        }
    }

    private void setAdapter() {
        CAdapter adapter=new CAdapter(context,Collection1);
        lv_farmer_list.setAdapter(adapter);
    }

    public static class Initiator {
        int farmer_id;
        String farmer_name;
        String address;
        String mobno;
        String username;

        public int getFarmer_id() {
            return farmer_id;
        }

        public void setFarmer_id(int farmer_id) {
            this.farmer_id = farmer_id;
        }

        public String getFarmer_name() {
            return farmer_name;
        }

        public void setFarmer_name(String farmer_name) {
            this.farmer_name = farmer_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobno() {
            return mobno;
        }

        public void setMobno(String mobno) {
            this.mobno = mobno;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Initiator(int farmer_id, String farmer_name, String address, String mobno, String username) {
            this.farmer_id = farmer_id;
            this.farmer_name = farmer_name;
            this.address = address;
            this.mobno = mobno;
            this.username = username;
        }
    }
}
