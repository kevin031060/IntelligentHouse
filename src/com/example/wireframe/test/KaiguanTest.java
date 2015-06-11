package com.example.wireframe.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wireframe.R;
import com.example.wireframe.utils.HttpUtils;

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
                return HttpUtils.doGet("http://api.yeelink.net/v1.0/device/19043/sensor/38510/datapoints", "value");
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
                if(switchCondition){
                    txtSwitch.setText("已开启");
                }else {
                    txtSwitch.setText("已关闭");
                }
            }
        }.execute();




        switchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCondition){
                    //关闭开关

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            HttpUtils.doPostJson2("http://api.yeelink.net/v1.0/device/19043/sensor/38510/datapoints","0");

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast toast=Toast.makeText(getApplicationContext(), "开关已关闭", Toast.LENGTH_SHORT);
                            //显示toast信息
                            toast.show();
                            txtSwitch.setText("已关闭");

                            switchCondition=false;
                        }
                    }.execute();


                }else {
                    //打开开关
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            HttpUtils.doPostJson2("http://api.yeelink.net/v1.0/device/19043/sensor/38510/datapoints","0");

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast toast=Toast.makeText(getApplicationContext(), "开关已开启", Toast.LENGTH_SHORT);
                            toast.show();
                            txtSwitch.setText("已开启");

                            switchCondition=true;
                        }
                    }.execute();

                }


            }
        });

        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KaiguanTest.this,SlidingTest.class ));
            }
        });

    }


}
