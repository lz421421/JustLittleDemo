package com.lizhi.demo.viewDragHelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.MyBaseRecycleAdapter;
import com.lizhi.demo.recyleView.BaseRecycleViewHolder;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.XRecycleView.XRecycleView;

import java.util.Arrays;
import java.util.List;

public class ViewDragHelperActivity extends BaseActivity {
    MyRecycleView myRecycleView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_view_drag_helpe);
        myRecycleView = (MyRecycleView) findViewById(R.id.myRecycleView);
        List<String> data = Arrays.asList(new String[]{"1", "2", "3", "4","1", "2", "3", "4","1", "2", "3", "4","1", "2", "3", "4","1", "2", "3", "4","1", "2", "3", "4"});
        myRecycleView.setLayoutManager(new LinearLayoutManager(this));
        myRecycleView.setAdapter(new XRecycleView.XRecycleViewAdapter<String>(R.layout.item_recycleview, data) {
            @Override
            public void setViewData(BaseRecycleViewHolder t, String item, int position) {

            }

        });
    }

}
