package com.lizhi.demo.viewDragHelper;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.lizhi.demo.R;
import com.lizhi.demo.view.XRecycleView.XRecycleViewHeaderLayout;

/**
 * Created by 39157 on 2016/12/29.
 */

public class MyRecycleView extends RecyclerView {
    View headerLayout;

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (headerLayout !=null){
            measureChildWithMargins(headerLayout, widthSpec, 0, heightSpec, 0);
            int height = MeasureSpec.getSize(heightSpec);
            int childHeight = headerLayout.getMeasuredHeight();
            height = height + childHeight;
            int mode = MeasureSpec.getMode(heightSpec);
            heightSpec = MeasureSpec.makeMeasureSpec(height, mode);
        }
        super.onMeasure(widthSpec, heightSpec);
    }





    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (headerLayout !=null){
            headerLayout.layout(0, 0, headerLayout.getMeasuredWidth(), headerLayout.getMeasuredHeight());
        }
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        headerLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_header_flash,this,false);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (headerLayout !=null){
            drawChild(canvas, headerLayout, getDrawingTime());
        }
    }
}
