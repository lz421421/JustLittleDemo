package com.lizhi.demo.chaoshidemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lizhi.demo.R;


/**
 * 自动轮播的viewpager
 *
 * @author lizhi
 */
public class MyViewPager extends ViewPager {

    long time = 2000;
    // private int STOP_MSG_WHAT = 1;
    boolean isLoop;
    private int currentPage = 0;
    private int START_MSG_WHAT = 0;
    /**
     * 按下点的X坐标
     */
    private int downX;
    /**
     * 按下点的Y坐标
     */
    private int downY;
    /**
     * 临时存储X坐标
     */
    private int tempX;

    /**
     * 临时存储Y坐标
     */
    private int tempY;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == START_MSG_WHAT) {
                handler.sendEmptyMessageDelayed(START_MSG_WHAT, time);
                MyViewPager.this.setCurrentItem(currentPage);
                currentPage++;
            } else {
                handler.removeMessages(START_MSG_WHAT);
            }
        }

    };

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs !=null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyViewPager);
            isLoop = a.getBoolean(R.styleable.MyViewPager_isLoop, false);
            a.recycle();
        }
        start();
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
        if (!loop) {
            stop();
        } else {
            stop();
            start();
        }
    }

    public void stop() {
        if (handler.hasMessages(START_MSG_WHAT)) {
            handler.removeMessages(START_MSG_WHAT);
        }
    }

    public void start() {
        if (isLoop) {
            if (!handler.hasMessages(START_MSG_WHAT)) {
                handler.sendEmptyMessageDelayed(START_MSG_WHAT, time);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            downX = tempX = (int) ev.getRawX();
            downY = tempY = (int) ev.getRawY();
            stop();
        } else if (action == MotionEvent.ACTION_UP) {
            currentPage = this.getCurrentItem() + 1;
            start();
        } else if (action == MotionEvent.ACTION_MOVE) {
            int moveX = (int) ev.getRawX();
            int moveY = (int) ev.getRawY();
            int deltaX = tempX - moveX;
            int deltaY = tempY - moveY;
            tempX = moveX;
            tempY = moveY;
            if (Math.abs(deltaY) > Math.abs(deltaX)) {
                getParent().requestDisallowInterceptTouchEvent(false);
                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
