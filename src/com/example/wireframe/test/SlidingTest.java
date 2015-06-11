package com.example.wireframe.test;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.wireframe.R;
import com.example.wireframe.adapter.BtnAdapter;
import com.example.wireframe.db.QueryElec;
import com.example.wireframe.model.Devices;
import com.example.wireframe.utils.HomeArc;

import java.util.ArrayList;
import java.util.List;

public class SlidingTest extends Activity implements PanelSlideListener{

    private SlidingPaneLayout paneLayout = null;
    private static final String TAG = "ZiHao";

    private GridView gv;
    private List<Devices> devicesList;
    LinearLayout arc, arc1, arc2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingtest);
        initView();
        //圆弧计分
        arc = (LinearLayout) findViewById(R.id.layout_temp);//圆弧计分
        arc1 = (LinearLayout) findViewById(R.id.layout_shidu);
        arc2 = (LinearLayout) findViewById(R.id.layout_yongdian);
        arc.addView(new HomeArc(this, 22));
        arc1.addView(new HomeArc(this, 52));

        arc2.addView(new HomeArc(this,28));
        //滑动窗口
        findViewById(R.id.slideleft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paneLayout.isOpen()) {
                    paneLayout.closePane();
                } else {
                    paneLayout.openPane();
                }

            }
        });

        devicesList = getData();

        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(new BtnAdapter(this, devicesList));




    }
    /**
     * 初始化视图
     */
    private void initView() {
        paneLayout = (SlidingPaneLayout) findViewById(R.id.slidepanel);
        paneLayout.setPanelSlideListener(this);


    }

    /**
     * 面板完全关闭回调
     */
    @Override
    public void onPanelClosed(View view) {
        // TODO Auto-generated method stub
        Log.d(TAG, "菜单已关闭");
    }

    /**
     * 面板完全打开回调
     */
    @Override
    public void onPanelOpened(View view) {
        // TODO Auto-generated method stub
        Log.d(TAG, "菜单已打开");
    }

    /**
     * 面板发生改变时的回调--滑动过程中持续回调
     */
    @Override
    public void onPanelSlide(View view, float slideOffset) {
        // TODO Auto-generated method stub
        Log.d(TAG, "界面视图发生改变...");
    }

    public List<Devices> getData(){

        List<Devices> devices = new ArrayList<Devices>();
        //Add Devices Btton
        Devices device0 =new Devices();
        device0.setName("添加设备");
        device0.setImage(R.drawable.addbutton);
        devices.add(device0);
        //Bulb Button
        Devices device1 =new Devices();
        device1.setName("卧室灯");
        device1.setImage(R.drawable.bulb);
        devices.add(device1);
        //AirConditioner Button
        Devices device2 =new Devices();
        device2.setName("空调");
        device2.setImage(R.drawable.shortcuts_icon_promotion);
        devices.add(device2);


        // GridView 得到设备列表
//        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
//        Map<String, Object> item0 = new HashMap<String, Object>();
//        item0.put("imageItem", R.drawable.addbutton);//添加图像资源的ID
//        item0.put("textItem", "添加新设备");//按序号添加ItemText
//        items.add(item0);
        //调用数据库
        QueryElec qe = new QueryElec(this);
        Cursor c = qe.GetDevices();
        while (c.moveToNext()){

//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("imageItem", R.drawable.bulb);//添加图像资源的ID
//            item.put("textItem", c.getString(c.getColumnIndex("name")) );//按序号添加ItemText
//            items.add(item);
//            System.out.println("kkkkk"+c.getString(c.getColumnIndex("name")));

            //

            Devices device = new Devices();
            device.setName(c.getString(c.getColumnIndex("name")));
            device.setImage(R.drawable.bulb);
            devices.add(device);
        }


        c.close();
        return devices;

    }

}
