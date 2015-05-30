package com.example.wireframe.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wireframe.R;
import com.example.wireframe.model.Devices;
import com.example.wireframe.test.EsptouchDemoActivity;
import com.example.wireframe.test.KaiguanTest;

import java.util.List;

/**
 * Created by likaiwen on 15/5/20.
 */
public class BtnAdapter extends BaseAdapter {
    private Context context;
    private List<Devices> devices;
    public BtnAdapter(Context context, List<Devices> devices){

        this.context = context;
        this.devices = devices;
    }
    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item,null);
        }
        ImageButton imageBtn = (ImageButton) convertView.findViewById(R.id.image_item);
        TextView textView = (TextView) convertView.findViewById(R.id.text_item);

        Devices device = devices.get(position);

        imageBtn.setImageResource(device.getImage());
        textView.setText(device.getName());

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==0){
                    Intent intent = new Intent(context, EsptouchDemoActivity.class);
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, KaiguanTest.class);
                    context.startActivity(intent);
                }

            }
        });

        return convertView;
    }
}
