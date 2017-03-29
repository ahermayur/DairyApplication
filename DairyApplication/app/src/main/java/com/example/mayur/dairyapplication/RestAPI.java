 /* JSON API for android appliation */
package com.example.mayur.dairyapplication;

 import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

 public class RestAPI {
     public static String ip="192.168.1.100";
     private final String urlString = "http://"+ip+"/Dairy%20System/Dairy%20System/Handler1.ashx";

     private static String convertStreamToUTF8String(InputStream stream) throws IOException {
         String result = "";
         StringBuilder sb = new StringBuilder();
         try {
             InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
             char[] buffer = new char[4096];
             int readedChars = 0;
             while (readedChars != -1) {
                 readedChars = reader.read(buffer);
                 if (readedChars > 0)
                     sb.append(buffer, 0, readedChars);
             }
             result = sb.toString();
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         return result;
     }


     private String load(String contents) throws IOException {
         URL url = new URL(urlString);
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setRequestMethod("POST");
         conn.setConnectTimeout(60000);
         conn.setDoOutput(true);
         conn.setDoInput(true);
         OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream());
         w.write(contents);
         w.flush();
         InputStream istream = conn.getInputStream();
         String result = convertStreamToUTF8String(istream);
         return result;
     }


     private Object mapObject(Object o) {
         Object finalValue = null;
         if (o.getClass() == String.class) {
             finalValue = o;
         }
         else if (Number.class.isInstance(o)) {
             finalValue = String.valueOf(o);
         } else if (Date.class.isInstance(o)) {
             SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", new Locale("en", "USA"));
             finalValue = sdf.format((Date)o);
         }
         else if (Collection.class.isInstance(o)) {
             Collection<?> col = (Collection<?>) o;
             JSONArray jarray = new JSONArray();
             for (Object item : col) {
                 jarray.put(mapObject(item));
             }
             finalValue = jarray;
         } else {
             Map<String, Object> map = new HashMap<String, Object>();
             Method[] methods = o.getClass().getMethods();
             for (Method method : methods) {
                 if (method.getDeclaringClass() == o.getClass()
                         && method.getModifiers() == Modifier.PUBLIC
                         && method.getName().startsWith("get")) {
                     String key = method.getName().substring(3);
                     try {
                         Object obj = method.invoke(o, null);
                         Object value = mapObject(obj);
                         map.put(key, value);
                         finalValue = new JSONObject(map);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
             }

         }
         return finalValue;
     }

     public JSONObject InsertDairyOwner(String ownername,String owneraddress,String ownermobno,String owneremailid,String username,String password) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "InsertDairyOwner");
         p.put("ownername",mapObject(ownername));
         p.put("owneraddress",mapObject(owneraddress));
         p.put("ownermobno",mapObject(ownermobno));
         p.put("owneremailid",mapObject(owneremailid));
         p.put("username",mapObject(username));
         p.put("password",mapObject(password));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject ShowOwnerId() throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "ShowOwnerId");
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject CurrentRate() throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "CurrentRate");
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject OwnerLogin(String username,String passsword) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "OwnerLogin");
         p.put("username",mapObject(username));
         p.put("passsword",mapObject(passsword));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject DairyOwnerLogin(String username,String passsword) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "DairyOwnerLogin");
         p.put("username",mapObject(username));
         p.put("passsword",mapObject(passsword));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject InsertFarmerDetails(String farmername,String farmeraddress,String farmermobno,String username,String password,int id) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "InsertFarmerDetails");
         p.put("farmername",mapObject(farmername));
         p.put("farmeraddress",mapObject(farmeraddress));
         p.put("farmermobno",mapObject(farmermobno));
         p.put("username",mapObject(username));
         p.put("password",mapObject(password));
         p.put("id",mapObject(id));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject FarmerDetails(int userid) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "FarmerDetails");
         p.put("userid",mapObject(userid));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject FarmerLogin(String username,String passsword) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "FarmerLogin");
         p.put("username",mapObject(username));
         p.put("passsword",mapObject(passsword));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject Login(String username,String password) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "Login");
         p.put("username",mapObject(username));
         p.put("password",mapObject(password));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject UpdateFarmerDetails(int id,String farmername,String farmeraddress,String farmermobno,String username,String password) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "UpdateFarmerDetails");
         p.put("id",mapObject(id));
         p.put("farmername",mapObject(farmername));
         p.put("farmeraddress",mapObject(farmeraddress));
         p.put("farmermobno",mapObject(farmermobno));
         p.put("username",mapObject(username));
         p.put("password",mapObject(password));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject MilkPurchase(int ownerid,int farmerid,Date date,float quantity,float fat,float deg,float snf,float milkrate,float total) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "MilkPurchase");
         p.put("ownerid",mapObject(ownerid));
         p.put("farmerid",mapObject(farmerid));
         p.put("date",mapObject(date));
         p.put("quantity",mapObject(quantity));
         p.put("fat",mapObject(fat));
         p.put("deg",mapObject(deg));
         p.put("snf",mapObject(snf));
         p.put("milkrate",mapObject(milkrate));
         p.put("total",mapObject(total));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject UpdateMilkPurchase(int farmerid,String date,float quantity,float fat,float deg,float snf,float milkrate,float total) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "UpdateMilkPurchase");
         p.put("farmerid",mapObject(farmerid));
         p.put("date",mapObject(date));
         p.put("quantity",mapObject(quantity));
         p.put("fat",mapObject(fat));
         p.put("deg",mapObject(deg));
         p.put("snf",mapObject(snf));
         p.put("milkrate",mapObject(milkrate));
         p.put("total",mapObject(total));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject GetMilkStockbyDate(String date) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "GetMilkStockbyDate");
         p.put("date",mapObject(date));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject SelectMilkPurchaseDetails(int farmerid,String date) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "SelectMilkPurchaseDetails");
         p.put("farmerid",mapObject(farmerid));
         p.put("date",mapObject(date));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject SetMilkRate(float fat,float snf,float milkrate,String date,int owner_id) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "SetMilkRate");
         p.put("fat",mapObject(fat));
         p.put("snf",mapObject(snf));
         p.put("milkrate",mapObject(milkrate));
         p.put("date",mapObject(date));
         p.put("owner_id",mapObject(owner_id));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject UpdateMilkRate(float milkrate,String date,int ownerid,int id) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "UpdateMilkRate");
         p.put("milkrate",mapObject(milkrate));
         p.put("date",mapObject(date));
         p.put("ownerid",mapObject(ownerid));
         p.put("id",mapObject(id));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject ViewMilkRate() throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "ViewMilkRate");
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject ViewTodaysCollection(int oid,String date) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "ViewTodaysCollection");
         p.put("oid",mapObject(oid));
         p.put("date",mapObject(date));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject InsertDisease(int Did,String Dname,String Descripion,String Season) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "InsertDisease");
         p.put("Did",mapObject(Did));
         p.put("Dname",mapObject(Dname));
         p.put("Descripion",mapObject(Descripion));
         p.put("Season",mapObject(Season));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject InsertPrecaution(int die_id,ArrayList<String> Descripion) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "InsertPrecaution");
         p.put("die_id",mapObject(die_id));
         p.put("Descripion",mapObject(Descripion));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject GetAllPrecaution(int id) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "GetAllPrecaution");
         p.put("id",mapObject(id));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject InsertSymptoms(int die_id,ArrayList<String> Descripion) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "InsertSymptoms");
         p.put("die_id",mapObject(die_id));
         p.put("Descripion",mapObject(Descripion));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject GetAllSymptom(int id) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "GetAllSymptom");
         p.put("id",mapObject(id));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject InsertMedicin(int die_id,ArrayList<String> Descripion) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "InsertMedicin");
         p.put("die_id",mapObject(die_id));
         p.put("Descripion",mapObject(Descripion));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject GetAllMedicine(int id) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "GetAllMedicine");
         p.put("id",mapObject(id));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject GetDetailsDisease() throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "GetDetailsDisease");
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject InsertVideo(String videoid,String videotitle) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "InsertVideo");
         p.put("videoid",mapObject(videoid));
         p.put("videotitle",mapObject(videotitle));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject WatchVideo() throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "WatchVideo");
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

     public JSONObject ViewAllFarmer(int id) throws Exception {
         JSONObject result = null;
         JSONObject o = new JSONObject();
         JSONObject p = new JSONObject();
         o.put("interface","RestAPI");
         o.put("method", "ViewAllFarmer");
         p.put("id",mapObject(id));
         o.put("parameters", p);
         String s = o.toString();
         String r = load(s);
         result = new JSONObject(r);
         return result;
     }

 }


