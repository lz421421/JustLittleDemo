package com.lizhi.demo.chaoshidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class HomeFenLeiLayout extends LinearLayout {


    public int childSize;
    public int childHeight;
    List<View> childs;
    int screenWidth;

    public HomeFenLeiLayout(Context context, int childSize, int childHeight) {
        super(context);
        this.childSize = childSize;
        this.childHeight = childHeight;
        screenWidth = DensityUtil.getWidth(context);
    }

    public HomeFenLeiLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeFenLeiLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = (2 * (childSize - childSize % 3) / 3) * childHeight + childHeight * (childSize % 3);
        setMeasuredDimension(screenWidth, height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        int left = 0;
        int top = 0;
        int right = screenWidth;
        int bottom = 0;
        for (int i = 0; i < childCount; i++) {
            View chidView = getChildAt(i);
            bottom = (2 * ((i + 1) - (i + 1) % 3) / 3) * childHeight + childHeight * ((i + 1) % 3);
            top = bottom - childHeight;
            if (i % 3 == 0) {
                left = 0;
                right = screenWidth;
            } else if (i % 3 == 1) {
                left = 0;
                right = screenWidth / 2;
            } else if (i % 3 == 2) {
                left = screenWidth / 2;
                right = screenWidth;
            }
            chidView.layout(left, top, right, bottom);
        }
    }
}
