package com.lizhi.demo.simple_demo;

import android.content.ClipData;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.MyBaseAdapter;
import com.lizhi.demo.baseadapter.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
public class ElevationActivity extends BaseActivity {
    GridView gv;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_elevation_demo);

        gv = (GridView) findViewById(R.id.gv);
        ArrayList<Item> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new Item());
        }

        gv.setAdapter(new MyBaseAdapter<Item>(this, datas, R.layout.item_guide) {
            @Override
            public void convert(MyViewHolder helper, Item item, int position) {
//                TextView textView = helper.getView(R.id.tv_);
//                textView.setText(item.text);
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public class Item {

    }
}
