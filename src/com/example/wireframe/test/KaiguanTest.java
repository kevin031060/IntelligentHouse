package com.example.wireframe.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wireframe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class KaiguanTest extends Activity {

    public boolean switchCondition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaiguan_test);


        findViewById(R.id.elecStatics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KaiguanTest.this, MainActivity.class));
            }
        });


        final ImageView switchOn = (ImageView) findViewById(R.id.kaiguan);
        final TextView txtSwitch = (TextView) findViewById(R.id.txtSwitch);
        //查询开关状态
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
                    if(Integer.parseInt(s)==1){
                        switchCondition=true;
                    }else {
                        switchCondition=false;
                    }
                }
            }
        }.execute();

        if(switchCondition){
            txtSwitch.setText("已开启");
        }else {
            txtSwitch.setText("已关闭");
        }


        switchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCondition){
                    //关闭开关
                    txtSwitch.setText("已关闭");

                    switchCondition=false;
                }else {
                    txtSwitch.setText("已开启");
                    //
                    switchCondition=true;
                }


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kaiguan_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
