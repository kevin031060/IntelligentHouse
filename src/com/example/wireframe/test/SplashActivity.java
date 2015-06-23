package com.example.wireframe.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.wireframe.R;
import com.example.wireframe.utils.HttpUtils;

public class SplashActivity extends Activity {

    public static final String TAG = SplashActivity.class.getSimpleName();

    private ImageView mSplashItem_iv = null;

    private String wendu;
    private String shidu;

    /*
     * (non-Javadoc)
     *
     * @see com.itau.tmall.ui.base.BaseActivity#findViewById()
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.activity_splash);

        prepareData();
        onLoading();
    }

    private  void  onLoading()
    {
        Thread  thread = new  Thread()
        {
            public  void  run()
            {
                try
                {
                    Thread.sleep( 2000 );
                    Intent  intent = new  Intent(SplashActivity.this,SlidingTest.class );
                    intent.putExtra("wendu",wendu);
                    intent.putExtra("shidu",shidu);
                    startActivity(intent);
                }
                catch( InterruptedException  erro )
                {
                    erro.printStackTrace();
                };
            }
        };

        thread.start();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.itau.jingdong.ui.base.BaseActivity#initView()
     */

    public void prepareData(){
        //wendu
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return HttpUtils.doGet("http://api.yeelink.net/v1.0/device/18788/sensor/32639/datapoints", "value");
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
                return HttpUtils.doGet("http://api.yeelink.net/v1.0/device/18788/sensor/32640/datapoints","value");
            }

            @Override
            protected void onPostExecute(String s) {

                shidu = s;

            }
        }.execute();
    }


}
