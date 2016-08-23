package com.lizhi.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/4/20.
 */
public class TabScrollView extends ScrollView {
    public TabScrollView(Context context) {
        this(context,null);
    }

    public TabScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.log("-----------dispatchTouchEvent------->");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.log("-----------onInterceptTouchEvent------->");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtil.log("-----------onTouchEvent------->");
        return super.onTouchEvent(ev);
    }
}
