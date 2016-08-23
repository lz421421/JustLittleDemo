package com.lizhi.demo.coordinatorlayout_behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2015/12/20.
 */
public class MyTextView extends TextView {
    Scroller scroller;
    int height = -1;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }


    public void scrollWithAnimation(int x, int y, int time) {
        int startX = getScrollX();
        int startY = getScrollY();
        int deltaX = x - startX;
        int deltaY = x - startX;
        scroller.startScroll(startX, startY, deltaX, 0, time);
        invalidate();
    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int y = scroller.getCurrY();
            setTop(y);
            setBottom(y + height);
            postInvalidate();

        }
    }

    float downY = -1;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getRawY();
                if (height == -1) {
                    height = getHeight();
                }
                LogUtil.log("---onTouchEvent--" + height);
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = event.getRawY();
                float deltaY = nowY - downY;
                downY = nowY;
                LogUtil.log("---deltaY---:" + deltaY);
                LogUtil.log("---getTop---:" + getTop());
                int nowTop = getTop() + (int) deltaY;
                setTop(nowTop);
                setBottom(nowTop + height);
                break;
            case MotionEvent.ACTION_UP:
                scrollWithAnimation(0, getTop(), 1000);
                break;
        }
        return super.onTouchEvent(event);
    }

}
