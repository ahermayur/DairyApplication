package com.example.mayur.dairyapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONParser {

    public JSONParser()
    {
        super();
    }
    public Boolean parseUserAuth(JSONObject object)
    {    Boolean userAuth=null;
        try {
            userAuth= object.getBoolean("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
          //  Log.d("JSONParser => parseUserAuth", e.getMessage());
        }

        return userAuth;
    }

    public ArrayList<String> parseOwnerName(JSONObject object)
    {
        ArrayList<String> ownername = new ArrayList<String>();
        try {
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = (JSONObject) array.get(i);
                ownername.add((String) object1.get("Owner_Name"));
                //and so on
            }
        }
        catch (Exception e)
        {
            Log.d("Owner Name Parser",e.getMessage());
        }

        return ownername;
    }

    public String parseUserType(JSONObject object)
    {
        String usertype="";
       //GetUserType getUserType2=new GetUserType();
        try {
            JSONObject jsonObj=object.getJSONArray("Value").getJSONObject(0);

            usertype=jsonObj.getString("UserType");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
  //          Log.d("JSONParser => parseUserDetails", e.getMessage());
        }

        return usertype;

    }

    public String[] parseTimeTable(JSONObject object)
    {

        String[] lect=new String[9];
        try {
            JSONObject jsonObj=object.getJSONArray("Value").getJSONObject(0);
            lect[0]=jsonObj.getString("Id");
            lect[1]=jsonObj.getString("lecture1");
            lect[2]=jsonObj.getString("lecture2");
            lect[3]=jsonObj.getString("lecture3");
            lect[4]=jsonObj.getString("lecture4");
            lect[5]=jsonObj.getString("lecture5");
            lect[6]=jsonObj.getString("lecture6");
            lect[7]=jsonObj.getString("lecture7");
            lect[8]=jsonObj.getString("lecture8");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser Time Table",e.getMessage());
        }
        return lect;
    }
/*
    public UserDetails parseUserDetails(JSONObject object)
    {
        UserDetails userDetail=new UserDetails();

        try {
            JSONObject jsonObj=object.getJSONArray("Value").getJSONObject(0);

            userDetail.setFullname(jsonObj.getString("FullName"));
            userDetail.setEmailid(jsonObj.getString("Email"));
            userDetail.setMobileno(jsonObj.getString("MobileNo"));
            userDetail.setUsername(jsonObj.getString("UserName"));
            userDetail.setPassword(jsonObj.getString("Password"));
            userDetail.setusertype(jsonObj.getString("UserType"));
            userDetail.setSclass(jsonObj.getString("class"));
            userDetail.setImage(jsonObj.getString("Image"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseUserDetails", e.getMessage());
        }

        return userDetail;

    }



    public ShowUserType parseusertype2(JSONObject object)
    {
        ShowUserType showUserType=new ShowUserType();

        try {
            JSONObject jsonObj=object.getJSONArray("Value").getJSONObject(0);
            showUserType.setUserType(jsonObj.getString("UserType"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseUserDetails", e.getMessage());
        }

        return showUserType;

    }

    public Emailid parseemailid(JSONObject object)
    {
        Emailid emailid=new Emailid();

        try {
            JSONObject jsonObj=object.getJSONArray("Value").getJSONObject(0);
            emailid.setEmailid(jsonObj.getString("Email"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseUserDetails", e.getMessage());
        }

        return emailid;

    }


    public ArrayList<InsertHolidays> parseInsertHolidays(JSONObject object)
    {
        ArrayList<InsertHolidays> arrayList=new ArrayList<InsertHolidays>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                // arrayList.add(new InsertIntoNotice(jsonObj.getString("sendto")));
                arrayList.add(new InsertHolidays(jsonObj.get("HolidayName").toString(), jsonObj.get("Date").toString(), jsonObj.get("Image").toString()));
                //Log.i("Jshgdfh ", jsonObj.get("Notice_Subject").toString());
               // Log.i("Jsshsdhdh  ", jsonObj.get("Notice_Description").toString());
               // Log.i("jh",jsonObj.get("attachment").toString());

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            //  Log.d("JSONParser => parseDepartment", e.getMessage());
        }
        return arrayList;
    }

*/

/*
    public ArrayList<StaffDocument> parseDocumentDetails(JSONObject object)
    {
        ArrayList<StaffDocument> arrayList=new ArrayList<StaffDocument>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
              // arrayList.add(new StaffDocument(jsonObj.getString("sendto")));
                arrayList.add(new StaffDocument(jsonObj.get("Doc_Subject").toString(), jsonObj.get("Doc_Description").toString(), jsonObj.get("Doc_DateTime").toString(), jsonObj.get("Doc_Attachment").toString(), jsonObj.get("Doc_SendBy").toString(), jsonObj.get("Doc_SendTo").toString()));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            //  Log.d("JSONParser => parseDepartment", e.getMessage());
        }
        return arrayList;
    }


    public ArrayList<InsertNotice> parseInsertNotice(JSONObject object)
    {
        ArrayList<InsertNotice> arrayList=new ArrayList<InsertNotice>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
               // arrayList.add(new InsertIntoNotice(jsonObj.getString("sendto")));
                arrayList.add(new InsertNotice(jsonObj.get("Notice_Subject").toString(), jsonObj.get("Notice_Description").toString(), jsonObj.get("Notice_Date").toString(), jsonObj.get("Send_By").toString(), jsonObj.get("Send_To").toString(), jsonObj.get("Attachment").toString()));
               // Log.i("Jshgdfh ", jsonObj.get("Notice_Subject").toString());
              //  Log.i("Jsshsdhdh  ", jsonObj.get("Notice_Description").toString());
              //  Log.i("jh",jsonObj.get("attachment").toString());

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            //  Log.d("JSONParser => parseDepartment", e.getMessage());
        }
        return arrayList;
    }


    public ArrayList<InsertIntoStudentDetails>parseInsertIntoStudentDetails (JSONObject object)
    {
        ArrayList<InsertIntoStudentDetails> arrayList=new ArrayList<InsertIntoStudentDetails>();
        try{
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new InsertIntoStudentDetails(jsonObj.get("studentname").toString(),jsonObj.get("studentemail").toString(),jsonObj.get("studentpassword").toString(),jsonObj.get("studentmobileno").toString(),jsonObj.get("yearname").toString(),
                        jsonObj.get("studentaddress").toString(),jsonObj.get("studentdob").toString(),jsonObj.get("studentsem").toString()));
                Log.i("Jshgdfh ", jsonObj.get("studentname").toString());
                Log.i("Jsshsdhdh  ", jsonObj.get("studentemail").toString());


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }*/

}