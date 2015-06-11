package com.example.wireframe.test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wireframe.R;
import com.example.wireframe.utils.HttpUtils;

import java.util.Date;

public class testHttp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http);

        TextView tw2 = (TextView)findViewById(R.id.textView7);
        //HttpUtils.doGet("http://192.168.0.110:8080/CeshiKaiguan/xianshishuju?id={\"value\":90}","value");
        //tw2.setText(HttpUtils.doGet("http://192.168.0.110:8080/CeshiKaiguan/xianshishuju?id={\"value\":90}","value"));

        findViewById(R.id.testBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tw =(TextView)findViewById(R.id.textView5);
                tw.setText(HttpUtils.doGet("http://api.yeelink.net/v1.0/device/19043/sensor/38510/datapoints","value"));
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Date s = new Date();
                       // HttpUtils.doPost("http://192.168.0.110:8080/CeshiKaiguan/xianshishuju","09","28");
                        HttpUtils.doPostJson2("http://api.yeelink.net/v1.0/device/19043/sensor/38510/datapoints","1");

                        return null;
                    }
                }.execute();

            }
        });
        //WebView webView = (WebView) findViewById(R.id.webView);
        //webView.loadUrl("http://192.168.0.110:8080/CeshiKaiguan/xianshishuju?id={\"value\":90}");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_http, menu);
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
}
