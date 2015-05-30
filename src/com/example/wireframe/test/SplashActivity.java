package com.example.wireframe.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.wireframe.R;

public class SplashActivity extends Activity {

    public static final String TAG = SplashActivity.class.getSimpleName();

    private ImageView mSplashItem_iv = null;

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
                    Thread.sleep( 3000 );
                    Intent  intent = new  Intent(SplashActivity.this,SlidingTest.class );
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



}
