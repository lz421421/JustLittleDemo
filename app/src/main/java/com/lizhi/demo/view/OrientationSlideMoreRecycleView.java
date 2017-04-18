package com.lizhi.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.lizhi.demo.utils.LogUtil;

/**
 * Created by 39157 on 2017/4/1.
 * 横向滑动更多
 */

public class OrientationSlideMoreRecycleView extends RecyclerView {


    public OrientationSlideMoreRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        View childView = getLayoutManager().findViewByPosition(3);
        LogUtil.log("--------onScrollChanged----view-->" + childView);

//        View childView = getChildAt(3);
        if (childView !=null){
            childView.layout(0,0,100,100);
        }
//        int count = getChildCount();
//        LogUtil.log("--------onScrollChanged----count-->" + count);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
//        LogUtil.log("--------onScrolled----L-->" + dx);
//        LogUtil.log("--------onScrolled----T-->" + dy);
    }
}
