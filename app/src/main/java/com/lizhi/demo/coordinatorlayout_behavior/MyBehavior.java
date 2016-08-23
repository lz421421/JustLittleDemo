package com.lizhi.demo.coordinatorlayout_behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2015/12/20.
 */
public class MyBehavior extends CoordinatorLayout.Behavior {
    private int targetId;

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Follow);
//        for (int i = 0; i < a.getIndexCount(); i++) {
//            int attr = a.getIndex(i);
//            if (a.getIndex(i) == R.styleable.Follow_target) {
//                targetId = a.getResourceId(attr, -1);
//            }
//        }
//        a.recycle();
//        LogUtil.log("--------MyBehavior----");
    }

    /**
     * 确定以来的View
     *
     * @param parent
     * @param child      执行操作的view  就是添加属性的layout_behavior的View
     * @param dependency 依赖的View  根据这个View 改变 child的状态
     * @return
     */
//    @Override
//    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        LogUtil.log("--------layoutDependsOn----");
//        return dependency.getId() == targetId;
//    }

    /**
     * 当依赖的View  发生变化时候调用
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
//    @Override
//    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
////        LogUtil.log("--------onDependentViewChanged----");
////        child.setY(dependency.getHeight() + dependency.getY());
//        return true;
//    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        offset(child,dyConsumed);
    }

    boolean scrolling = false;
    int offsetTotal = 0;
    public void offset(View child,int dy){
        int old = offsetTotal;
        int top = offsetTotal - dy;
        top = Math.max(top, -child.getHeight());
        top = Math.min(top, 0);
        offsetTotal = top;
        if (old == offsetTotal){
            scrolling = false;
            return;
        }
        int delta = offsetTotal-old;
        child.offsetTopAndBottom(delta);
        scrolling = true;
    }
}
