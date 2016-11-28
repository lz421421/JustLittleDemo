package com.lizhi.demo.baseadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 39157 on 2016/11/23.
 */

public class MyBaseRecycleAdapter<T> extends RecyclerView.Adapter<MyRecycleViewHolder> {
    public List<T> mDatas;

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyRecycleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }
}
