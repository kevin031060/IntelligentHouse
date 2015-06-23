package com.example.wireframe.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wireframe.config.UrlConfig;

/**
 * Created by likaiwen on 15/6/23.
 */
public class SwitchUtils {

    private boolean switchCondition;
    private TextView txtSwitch;
    private ImageView switchOn;
    private  Context context;

    public SwitchUtils(boolean switchCondition, TextView txtSwitch, ImageView switchOn, Context context) {
        this.switchCondition = switchCondition;
        this.txtSwitch = txtSwitch;
        this.switchOn = switchOn;
        this.context = context;
    }

    public void getSwitchState(){
        //查询开关状态
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return HttpUtils.doGet(UrlConfig.URL_SWITCH_38510, "value");
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
    }


    public void startSwitch(){
        switchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCondition){
                    //关闭开关

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            HttpUtils.doPostJson2(UrlConfig.URL_SWITCH_38510,"0");

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast toast=Toast.makeText(context, "开关已关闭", Toast.LENGTH_SHORT);
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
                            HttpUtils.doPostJson2(UrlConfig.URL_SWITCH_38510,"1");

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast toast=Toast.makeText(context, "开关已开启", Toast.LENGTH_SHORT);
                            toast.show();
                            txtSwitch.setText("已开启");

                            switchCondition=true;
                        }
                    }.execute();

                }


            }
        });

    }


}
