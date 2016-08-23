package com.lizhi.demo.tantan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 *@author 吴毅博
 *@data 2016年7月6日上午9:41:23
 *@version
 *@Description StackView适配器。主要将图片或颜色添加进来
 */

public class ColorAdapter extends BaseAdapter
{
    private Context mContext;
    
    private int [] mColors;
    
    public ColorAdapter(Context context, int [] colors) {
        mContext = context;
        mColors = colors;
    }
    
    public int getCount() {
        return mColors == null ? 0 : mColors.length;
    }
    public Object getItem(int position) {
        return mColors == null ? null : mColors[position];
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View cacheView, ViewGroup parent) {
        LinearLayout.LayoutParams colorLayoutParams = new LinearLayout.LayoutParams(400, 400);
        LinearLayout colorLayout = new LinearLayout(mContext);
        //    colorLayout.setBackgroundColor(mColors[position]);//设置颜色
        colorLayout.setBackgroundResource(mColors[position]);//设置图片
        colorLayout.setLayoutParams(colorLayoutParams);
        
        return colorLayout;
    }
}
