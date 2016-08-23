package com.lizhi.demo.scrollview_header_stop.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/1/3.
 */
public class MyScrollView extends ScrollView {

    OnScrollListener onScrollListener;
    int lastY;
    HeaderView headerView;
    LinearLayout ll_content;
    boolean isFirstTouch = true;

    //    int reflashHeight = 65;//刷新的时候停留的高度 单位dp
    int reflashHeightPX = 0;//刷新的时候停留的高度 单位px

    boolean isFlashing = false;

    public final static int TYPE_falshFlag = 1;
    public final static int TYPE_normalScroll = 0;
    public final static int TYPE_hide = 2;
    public final static int TYPE_visiable = 3;
    protected final static float OFFSET_RADIO = 1.8f; //


    public interface OnScrollListener {
        /**
         * @param scrollY
         * @param type    0 普通滑动 1  刷新 2下拉
         */
        public void onScrollY(int scrollY, int type);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        headerView = new HeaderView(getContext());
        ll_content.addView(headerView, 0);
        headerView.setHeightReally(0);
        reflashHeightPX = headerView.getReflashHeight();
    }




    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int y = (int) ev.getRawY();
        int deltaY = y - lastY;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int scrollY = getScrollY();
                if (isFirstTouch) {
                    deltaY = 0;
                    isFirstTouch = false;
                }
//                LogUtil.log("-scrollY-->" + scrollY + "-deltaY->" + deltaY + "-headerView.getHeaderHeight()-->" + headerView.getHeaderHeight());
                if (scrollY <= 0 && deltaY > 0) {
                    //下拉
                    headerView.setHeight((int) (deltaY / OFFSET_RADIO));
                    if (onScrollListener != null) {
                        onScrollListener.onScrollY(0, TYPE_hide);
                    }
                } else if (deltaY < 0 && headerView.getHeaderHeight() > 0 && !isFlashing) {
                    //上滑
                    headerView.setHeight((int) (deltaY * 1.5));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isFirstTouch = true;
                int height = headerView.getHeaderHeight();
                if (height >= reflashHeightPX) {
                    headerView.closeTo(headerView.getHeaderHeight(), reflashHeightPX);
                    if (onScrollListener != null && !isFlashing) {
                        headerView.setText("正在刷新...");
                        onScrollListener.onScrollY(0, TYPE_falshFlag);
                    }
                    isFlashing = true;
                } else {
                    isFlashing = false;
                    headerView.closeTo(height, 0);
                    if (onScrollListener != null) {
                        onScrollListener.onScrollY(0, TYPE_visiable);
                    }
                }
                break;
        }
        lastY = y;
        if (headerView.getHeaderHeight() == 0 || isFlashing) {
            return super.onTouchEvent(ev);
        }
        return true;
    }

    public void reflashComplete() {
        headerView.closeTo(headerView.getHeaderHeight(), 0);
        isFlashing = false;
        headerView.setText("松开刷新");
        if (onScrollListener != null) {
            onScrollListener.onScrollY(0, TYPE_visiable);
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScrollY(t, TYPE_normalScroll);
        }
    }
}
