package com.example.wireframe.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.wireframe.R;
import com.example.wireframe.utils.HomeArc;

public class Index extends Activity {

    private GridView gv;
    LinearLayout arc, arc1, arc2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        arc = (LinearLayout) findViewById(R.id.layout_temp);//圆弧计分
        arc1 = (LinearLayout) findViewById(R.id.layout_shidu);
        arc2 = (LinearLayout) findViewById(R.id.layout_yongdian);
        arc.addView(new HomeArc(this, 22));
        arc1.addView(new HomeArc(this, 52));
        arc2.addView(new HomeArc(this, 82));

//        findViewById(R.id.lightBulb).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Index.this, KaiguanTest.class));
//            }
//        });

//        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
//        for (int i = 0; i < 9; i++) {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("imageItem", R.drawable.ic_launcher);//添加图像资源的ID
//            item.put("textItem", "icon" + i);//按序号添加ItemText
//            items.add(item);
//        }
//
//        //实例化一个适配器
//        SimpleAdapter adapter = new SimpleAdapter(this,
//                items,
//                R.layout.grid_item,
//                new String[]{"imageItem", "textItem"},
//                new int[]{R.id.image_item, R.id.text_item});
//
//        //获得GridView实例
//        gv = (GridView)findViewById(R.id.gridView);
//        //为GridView设置适配器
//        gv.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
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
