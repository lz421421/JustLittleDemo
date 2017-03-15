package com.lizhi.demo.viewDragHelper;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.ViewDragHelperView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 39157 on 2016/12/21.
 */

public class SwipeItemView extends ScrollView {

    private ViewDragHelper mDragHelper;
    Point point;
    public static final int INVALID_POINTER = -1;
    private int mActivePointerId = INVALID_POINTER;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private int mPointersDown;

    DragHelperCallback mCallBack = new DragHelperCallback();
    private ScrollerCompat mScroller;
    /**
     * Interpolator defining the animation curve for mScroller
     */
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    public SwipeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, mCallBack);
        LogUtil.log("---------height---->" + DensityUtil.getHeight(context));
        mScroller = ScrollerCompat.create(context, sInterpolator);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int sy = getScrollY();
//        LogUtil.log("---------onInterceptTouchEvent---->" + sy);
//        super.onInterceptTouchEvent(ev);
//        return mDragHelper.shouldInterceptTouchEvent(ev);
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        final int actionMasked = action & MotionEvent.ACTION_MASK;
        LogUtil.log("----------dispatchTouchEvent------------>"+actionMasked);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mDragHelper.processTouchEvent(event);
        final int action = event.getAction();
        final int actionMasked = action & MotionEvent.ACTION_MASK;
        LogUtil.log("----------onTouchEvent------------>"+actionMasked);
        return true;
    }





    boolean tryCaptureViewForDrag(int pointerId) {
        mActivePointerId = pointerId;
        return true;
    }


    //    @Override
//    public void computeScroll() {
//        if (continueSettling() && isDrag) {
//            invalidate();
//        }
//    }
    public boolean continueSettling() {


//        boolean keepGoing = mScroller.computeScrollOffset();
//        LogUtil.log("---------continueSettling---->"+keepGoing);
        final int x = mScroller.getCurrX();
        final int y = mScroller.getCurrY();
        final int dx = x - childView.getLeft();
        final int dy = y - childView.getTop();

        if (dx != 0) {
            ViewCompat.offsetLeftAndRight(childView, dx);
        }
        if (dy != 0) {
            ViewCompat.offsetTopAndBottom(childView, dy);
        }
//
//        if (keepGoing && x == mScroller.getFinalX() && y == mScroller.getFinalY()) {
//            // Close enough. The interpolator/scroller might think we're still moving
//            // but the user sure doesn't.
//            mScroller.abortAnimation();
//            keepGoing = false;
//        }
//        return keepGoing;
//        ValueAnimator animator = ValueAnimator.ofFloat(y ,)
        return true;
    }

    /**
     * 释放
     *
     * @param xvel
     * @param yvel
     */
    private void dispatchViewReleased(float xvel, float yvel) {
        final int startLeft = childView.getLeft();
        final int startTop = childView.getTop() == point.y ? childView.getBottom() : childView.getTop();
        final int dx = point.x - startLeft;
        final int dy = startTop - point.y;
        LogUtil.log("---------startTop-------->" + startTop);
        LogUtil.log("---------point.y-------->" + point.y);
        if (dx == 0 && dy == 0) {
            return;
        }
        final int duration = 300;
        ValueAnimator animator = ValueAnimator.ofFloat(dy, 0);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float vY = (float) animation.getAnimatedValue();
                LogUtil.log("---------dispatchViewReleased-------->" + vY);
                childView.setTop((int) vY);
//                ViewCompat.offsetTopAndBottom(childView, (int) vY);
            }
        });
        animator.start();
    }

    private void dragTo(int left, int top, int dx, int dy) {
        int clampedX = left;
        int clampedY = top;
        final int oldLeft = childView.getLeft();
        final int oldTop = childView.getTop();
        if (dx != 0) {
            clampedX = mCallBack.clampViewPositionHorizontal(childView, left, dx);
            ViewCompat.offsetLeftAndRight(childView, clampedX - oldLeft);
        }
        if (dy != 0) {
            clampedY = mCallBack.clampViewPositionVertical(childView, top, dy);
            ViewCompat.offsetTopAndBottom(childView, clampedY - oldTop);
        }
        if (dx != 0 || dy != 0) {
            final int clampedDx = clampedX - oldLeft;
            final int clampedDy = clampedY - oldTop;
        }
    }

    public void cancel() {
        mActivePointerId = INVALID_POINTER;
        clearMotionHistory();
    }


    private void clearMotionHistory() {
        if (mInitialMotionX == null) {
            return;
        }
        Arrays.fill(mInitialMotionX, 0);
        Arrays.fill(mInitialMotionY, 0);
        Arrays.fill(mLastMotionX, 0);
        Arrays.fill(mLastMotionY, 0);
        mPointersDown = 0;
    }

    private void clearMotionHistory(int pointerId) {
        if (mInitialMotionX == null) {
            return;
        }
        mInitialMotionX[pointerId] = 0;
        mInitialMotionY[pointerId] = 0;
        mLastMotionX[pointerId] = 0;
        mLastMotionY[pointerId] = 0;
        mPointersDown &= ~(1 << pointerId);
    }

    private void saveLastMotion(MotionEvent ev) {
        final int pointerCount = MotionEventCompat.getPointerCount(ev);
        for (int i = 0; i < pointerCount; i++) {
            final int pointerId = MotionEventCompat.getPointerId(ev, i);
            final float x = MotionEventCompat.getX(ev, i);
            final float y = MotionEventCompat.getY(ev, i);
            mLastMotionX[pointerId] = x;
            mLastMotionY[pointerId] = y;
        }
    }


    private void saveInitialMotion(float x, float y, int pointerId) {
        ensureMotionHistorySizeForId(pointerId);
        mInitialMotionX[pointerId] = mLastMotionX[pointerId] = x;
        mInitialMotionY[pointerId] = mLastMotionY[pointerId] = y;
        mPointersDown |= 1 << pointerId;
    }

    private void ensureMotionHistorySizeForId(int pointerId) {
        if (mInitialMotionX == null || mInitialMotionX.length <= pointerId) {
            float[] imx = new float[pointerId + 1];
            float[] imy = new float[pointerId + 1];
            float[] lmx = new float[pointerId + 1];
            float[] lmy = new float[pointerId + 1];
            if (mInitialMotionX != null) {
                System.arraycopy(mInitialMotionX, 0, imx, 0, mInitialMotionX.length);
                System.arraycopy(mInitialMotionY, 0, imy, 0, mInitialMotionY.length);
                System.arraycopy(mLastMotionX, 0, lmx, 0, mLastMotionX.length);
                System.arraycopy(mLastMotionY, 0, lmy, 0, mLastMotionY.length);
            }
            mInitialMotionX = imx;
            mInitialMotionY = imy;
            mLastMotionX = lmx;
            mLastMotionY = lmy;
        }
    }


    public class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LogUtil.log("--------clampViewPositionHorizontal------>" + left + "----->" + dx);
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - child.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            LogUtil.log("--------clampViewPositionHorizontal---return--->" + newLeft);
            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
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
            mDragHelper.settleCapturedViewAt(point.x, point.y);
            invalidate();
        }


        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            Toast.makeText(getContext(), "edgeTouched", Toast.LENGTH_SHORT).show();
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

        @Override
        public void onViewDragStateChanged(int state) {
//            ViewDragHelperView.this.state = state;
            LogUtil.log("---------state-------->" + state);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

    }


    //获取原始位置  记录下来  为后来回到原处 保存数据
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //布局完成的时候就记录一下位置
        childView = getChildAt(0);
        int x = childView.getLeft();
        int y = childView.getTop();
        point = new Point(x, y);
        LogUtil.log("---onLayout----->" + x + "---->" + y);
    }

    public boolean isNeedMove() {
        int offset = childView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
//		Log.e("jj", "scrolly=" + scrollY);
        // 0是顶部，后面那个是底部
        return scrollY == 0 || scrollY == offset;
    }

    View childView;
}
