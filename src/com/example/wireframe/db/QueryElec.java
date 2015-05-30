package com.example.wireframe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by likaiwen on 15/4/29.
 */
public class QueryElec {
    private DatabaseHelper database_helper;
    private  Context context;
    private  double data;
    public QueryElec(Context context){

        this.context=context;
    }

    public void InsertData(){
        //时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String dateString = formatter.format(currentTime);
        String[] dT = dateString.split("-");
        System.out.println(dT[2]);

        //数据
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    InputStream in = new URL("http://api.yeelink.net/v1.0/device/18788/sensor/32639/datapoints").openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line =null;
                    StringBuffer content=new StringBuffer();
                    while((line=reader.readLine())!=null){
                        content.append(line);
                    }
                    reader.close();
                    System.out.println(content.toString());
                    return JsonP(content.toString(),"value");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null){
                    data = Double.parseDouble(s);
                }
            }
        }.execute();
        ContentValues values = new ContentValues();
        values.put("month",dT[1]);
        values.put("day",dT[2]);
        values.put("time",dT[3]+"-"+dT[4]);

        //
        data = 20.9954;
        values.put("data", data);
        database_helper = new DatabaseHelper(this.context);
        SQLiteDatabase db = database_helper.getWritableDatabase();//这里是获得可写的数据库
        db.insert("Energy", null, values);


    }

    public List<bean> QueryDataByMonth(int month){
        List<bean> beanList = new LinkedList<>();

        String monthStr = Integer.toString(month);
        database_helper = new DatabaseHelper(this.context);
        SQLiteDatabase db = database_helper.getWritableDatabase();//这里是获得可写的数据库
        Cursor c = db.rawQuery("SELECT * FROM Energy where month=?", new String[]{monthStr});
        while (c.moveToNext()) {
            bean b = new bean();
            int _id = c.getInt(c.getColumnIndex("id"));
            String day = c.getString(c.getColumnIndex("day"));
            double data = c.getDouble(c.getColumnIndex("data"));
            b.setData(data);
            b.setDay(day);
            beanList.add(b);
            System.out.println(_id+": "+day+" "+data);
        }
        c.close();

        return beanList;

    }
    public List<bean> QueryDataByDay(int month){
        List<bean> beanList = new LinkedList<>();

        String monthStr = Integer.toString(month);
        database_helper = new DatabaseHelper(this.context);
        SQLiteDatabase db = database_helper.getWritableDatabase();//这里是获得可写的数据库
        Cursor c = db.rawQuery("SELECT * FROM Energy where day=?", new String[]{monthStr});
        while (c.moveToNext()) {
            bean b = new bean();
            int _id = c.getInt(c.getColumnIndex("id"));
            String time = c.getString(c.getColumnIndex("time"));
            double data = c.getDouble(c.getColumnIndex("data"));
            b.setData(data);
            b.setTime(time);
            beanList.add(b);
            System.out.println(_id+": "+time+" "+data);
        }
        c.close();

        return beanList;
    }

    public String JsonP(String s, String index){

        String jp;
        try{
            if (index.equals("value")) {
                JSONObject jsonObject = new JSONObject(s);
                jp = jsonObject.getString("value");


            }else{
                JSONObject jsonObject = new JSONObject(s);
                jp = jsonObject.getString("timestamp");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            jp=null;
        }

        return jp;
    }

    public void AddDevice(String name){
        ContentValues values = new ContentValues();
        values.put("name", name);
        database_helper = new DatabaseHelper(this.context);
        SQLiteDatabase db = database_helper.getWritableDatabase();//这里是获得可写的数据库
        db.insert("Devices", null, values);
        System.out.println(name);


    }

    public Cursor GetDevices(){
        database_helper = new DatabaseHelper(this.context);
        SQLiteDatabase db = database_helper.getWritableDatabase();//这里是获得可写的数据库

        Cursor c = db.rawQuery("SELECT name FROM Devices", null);

        return c;
    }


    public void dropAllTables(){

        database_helper = new DatabaseHelper(this.context);
        SQLiteDatabase db = database_helper.getWritableDatabase();
        db.execSQL("drop table if exists Devices");
        db.execSQL("drop table if exists Energy");
    }

}
