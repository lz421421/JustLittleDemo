package com.lizhi.demo.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/27.
 */
public class MyRecycleViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;

    public MyRecycleViewHolder(View itemView, Context context) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<View>();
        mConvertView.setTag(this);
    }
}
