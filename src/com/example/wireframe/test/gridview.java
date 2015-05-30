package com.example.wireframe.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.wireframe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gridview extends Activity {

    private GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //准备要添加的数据条目
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        Map<String, Object> item0 = new HashMap<String, Object>();
        item0.put("imageItem", R.drawable.addbutton);//添加图像资源的ID
        item0.put("textItem", "添加新设备");//按序号添加ItemText
        items.add(item0);

        for (int i = 0; i < 1; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("imageItem", R.drawable.bulb);//添加图像资源的ID
            item.put("textItem", "icon" + i);//按序号添加ItemText
            items.add(item);
        }

        //实例化一个适配器
        SimpleAdapter adapter = new SimpleAdapter(this,
                items,
                R.layout.grid_item,
                new String[]{"imageItem", "textItem"},
                new int[]{R.id.image_item, R.id.text_item});

        //获得GridView实例
        gv = (GridView)findViewById(R.id.gridView);
        //为GridView设置适配器
        gv.setAdapter(adapter);


}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gridview, menu);
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
