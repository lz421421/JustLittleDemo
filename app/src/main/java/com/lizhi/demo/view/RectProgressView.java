package com.lizhi.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by 39157 on 2016/11/9.
 */

public class RectProgressView extends View {


    public RectProgressView(Context context) {
        super(context);
    }

    public RectProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RectProgressView);
        if (array != null) {
            //获取我们在xml中设置的各个自定义属性
            sweepColor = array.getColor(R.styleable.RectProgressView_sweepBgColor, sweepColor);
            maxLenth = array.getInteger(R.styleable.RectProgressView_maxLenth, 100);
            upDataHeight = array.getInteger(R.styleable.RectProgressView_upDataHeight, 0);
            array.recycle();
        }
        LogUtil.log("-----maxLenth-->" + maxLenth + "---?" + upDataHeight);
    }

    private int sweepColor = Color.parseColor("#40000000");//

    private static final int DEFAULT_WIDTH = 100;

    private static final int DEFAULT_HEIGHT = 100;

    int maxLenth;//上传的文件的高度
    int upDataHeight = 0;//上传的高度

    /**
     * 设置文件总长度
     *
     * @param maxLenth
     */
    public void setMaxLenth(int maxLenth) {
        this.maxLenth = maxLenth;
    }

    //上传的总进度
    public void upDataHeight(int updata) {
        upDataHeight = getHeight() * updata / maxLenth;
        invalidate();

    }

    /**
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true); //设置抗锯齿
        mPaint.setColor(sweepColor);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight() - upDataHeight);
        canvas.drawRect(rectF, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int hMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int wSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int hSize = View.MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode) {
            case View.MeasureSpec.AT_MOST://
                float density = getResources().getDisplayMetrics().density;
                wSize = (int) (DEFAULT_WIDTH * density);
                hSize = (int) (DEFAULT_HEIGHT * density);
                break;
            case View.MeasureSpec.EXACTLY:
                wSize = hSize = Math.min(wSize, hSize);
                break;
        }
        setMeasuredDimension(wSize, hSize);
    }

}
