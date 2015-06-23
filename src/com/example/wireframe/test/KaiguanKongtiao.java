package com.example.wireframe.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wireframe.R;
import com.example.wireframe.config.UrlConfig;
import com.example.wireframe.utils.HttpUtils;
import com.example.wireframe.utils.SwitchUtils;

public class KaiguanKongtiao extends Activity {


    private static final String[] m_Countries = { "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };   //定义数组
    private ArrayAdapter<String> adapter;                                                                //存放数据
    private Spinner spinnerCardNumber;                                                                  //下拉框
    private String temperature_selected;
    private String wendu;
    private  String shidu;
    public boolean switchCondition = false;
    private TextView txtSwitch;
    private ImageView switchOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kongtiao_kaiguan);

        txtSwitch = (TextView)findViewById(R.id.txtSwitch);
        switchOn = (ImageView)findViewById(R.id.kaiguan);

        //switch execution
        SwitchUtils sU = new SwitchUtils(switchCondition, txtSwitch, switchOn, this);
        sU.getSwitchState();
        sU.startSwitch();

        //activity change
        prepareData();

        initBtn();


        spinnerCardNumber = (Spinner)findViewById(R.id.spinTemper);
        //将可选内容与ArrayAdapter连接，
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_Countries);
        //将adapter添加到m_Spinner中
        spinnerCardNumber.setAdapter(adapter);
        //到这里，就完成了下拉框的绑定数据，下拉框中已经有我们想要选择的值了。下面获取选择的值。
        //添加Spinner事件监听
        spinnerCardNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                temperature_selected = m_Countries[position];

                System.out.println(temperature_selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private void initBtn(){
        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        Intent intent = new Intent(KaiguanKongtiao.this,SlidingTest.class );
                        intent.putExtra("wendu",wendu);
                        intent.putExtra("shidu",shidu);
                        startActivity(intent);


            }
        });

        findViewById(R.id.elecStatics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KaiguanKongtiao.this, MainActivity.class));
            }
        });

        findViewById(R.id.btn_changeTem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(temperature_selected);
                //向Yeelink发送改变温度的请求
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        HttpUtils.doPostJson2(UrlConfig.URL_SWITCH_38510,temperature_selected);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast toast=Toast.makeText(getApplicationContext(), "设置完成", Toast.LENGTH_SHORT);
                        //显示toast信息
                        toast.show();
                    }
                }.execute();
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
