package com.lizhi.demo.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ViewDragHelperView extends LinearLayout {
    int state = 0;
    private ViewDragHelper mDragHelper;
    Map<View, Point> poionts = new HashMap<>();


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (state == 0) {
            getParent().requestDisallowInterceptTouchEvent(false);
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    public ViewDragHelperView(Context context) {
        this(context, null);
    }

    public ViewDragHelperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }


    public class DragHelperCallback extends ViewDragHelper.Callback {


        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            Toast.makeText(getContext(), "edgeTouched", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            ViewDragHelperView.this.state = state;
            LogUtil.log("---------state-------->" + state);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            LogUtil.log("---------left-------->" + left);
//            LogUtil.log("---------dx-------->" + dx);
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - child.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
        }


        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
//            LogUtil.log("---------left-------->" + child);
//            LogUtil.log("---------dx-------->" + child);
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - child.getHeight();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }

        //释放 回到原处
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //松手的时候 判断如果是这个view 就让他回到起始位置
            //这边代码你跟进去去看会发现最终调用的是startScroll这个方法 所以我们就明白还要在computeScroll方法里刷新
            Point point = poionts.get(releasedChild);
            mDragHelper.settleCapturedViewAt(point.x, point.y);
            invalidate();
        }



        //当被拖拽的控件是 clickable =true 或者是button时候 这个方法必须重写 防止事件冲突 控件不能点击
        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    //获取原始位置  记录下来  为后来回到原处 保存数据
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //布局完成的时候就记录一下位置
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int x = childView.getLeft();
            int y = childView.getTop();
            Point point = new Point(x, y);
            poionts.put(childView, point);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        return true;
    }
}
