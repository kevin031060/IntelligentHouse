package com.example.wireframe.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wireframe.R;
import com.example.wireframe.config.UrlConfig;
import com.example.wireframe.utils.HttpUtils;
import com.example.wireframe.utils.SwitchUtils;

public class KaiguanTest extends Activity {

    public boolean switchCondition = false;
    private TextView txtSwitch;
    private ImageView switchOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaiguan_test);
        switchOn = (ImageView) findViewById(R.id.kaiguan);
        txtSwitch = (TextView) findViewById(R.id.txtSwitch);

        //switch execution
        SwitchUtils sU = new SwitchUtils(switchCondition, txtSwitch, switchOn, this);
        sU.getSwitchState();
        sU.startSwitch();

        //activity change
        prepareData();
        activityJump();


    }


    private String wendu;
    private  String shidu;
    public void activityJump(){
        findViewById(R.id.elecStatics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KaiguanTest.this, MainActivity.class));
            }
        });

        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KaiguanTest.this,SlidingTest.class );
                intent.putExtra("wendu",wendu);
                intent.putExtra("shidu",shidu);
                startActivity(intent);
            }
        });
    }

    public void prepareData(){
        //wendu
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return HttpUtils.doGet(UrlConfig.URL_STATICS_WENDU, "value");
            }

            @Override
            protected void onPostExecute(String s) {

                wendu = s ;

            }
        }.execute();
        // shidu
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return HttpUtils.doGet(UrlConfig.URL_STATICS_SHIDU,"value");
            }

            @Override
            protected void onPostExecute(String s) {

                shidu = s;

            }
        }.execute();
    }


}
