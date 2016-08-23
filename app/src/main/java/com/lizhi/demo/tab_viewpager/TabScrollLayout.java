package com.lizhi.demo.tab_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/4/22.
 */
public class TabScrollLayout extends LinearLayout {

    public TabScrollLayout(Context context) {
        this(context, null);
    }

    public TabScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSouldIntrept(boolean souldIntrept) {
        isSouldIntrept = souldIntrept;
    }

    public boolean isSouldIntrept = true;
    View view_header;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isShoud = isSouldIntrept(ev);
        if (isSouldIntrept && isShoud) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    int headerHeight;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        headerHeight = getHeaderHeight();
//        view_header = findViewById(R.id.view_header);
        LogUtil.log("----." + view_header);
    }

    boolean isFirstTouch = true;
    int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                isFirstTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float temX = event.getRawX();
                float temY = event.getRawY();
                float delX = temX - downX;
                float delY = temY - downY;
                if (isFirstTouch) {
                    isFirstTouch = false;
                    delY = 0;
                }
                boolean isSetHeader = true;
                if (Math.abs(delY) >= Math.abs(delX)) {
                    LogUtil.log("-----delY---->" + delY);
                    if (getHeaderHeight() <= 0) {
                        if (delY > 0) {
                            isSetHeader = true;
                            isSouldIntrept = false;
                        } else {
                            isSetHeader = false;
                            isSouldIntrept = false;
                        }

                    }
                    if (getHeaderHeight() > DensityUtil.dip2px(getContext(), 150)) {
                        if (delY <= 0) {
                            isSetHeader = true;
                            isSouldIntrept = true;
                        } else {
                            isSetHeader = false;
                            isSouldIntrept = false;
                        }
                    }
                    if (isSetHeader) {
                        setView_header((int) delY);
                    }
                } else {
                }
                downX = temX;
                downY = temY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isFirstTouch = true;
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setView_header(int delY) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view_header.getLayoutParams();
        lp.height += delY;
        view_header.setLayoutParams(lp);
    }

    public int getHeaderHeight() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view_header.getLayoutParams();
        return lp.height;
    }

    float downX;
    float downY;

    public boolean isSouldIntrept(MotionEvent ev) {
        boolean isNeed = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                downX = ev.getX();
                downY = getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float temX = getX();
                float temY = getY();
                float delX = temX - downX;
                float delY = temY - downY;
                downX = temX;
                downY = temY;
                if (delY > delX) {
                    isNeed = true;
                } else {
                    isNeed = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return isNeed;
    }

}
