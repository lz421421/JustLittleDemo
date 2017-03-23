package com.lizhi.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by 39157 on 2017/3/23.
 */

public class AcrTextView extends View {

    // 设置外边框圆的边框粗细
    private int stroke = 4;
    int progressColor = 0xffff0000;//百分比的颜色
    int progressSize = 0;//百分比的大小


    int arcColor = 0xffcccccc;//半圆的颜色
    int defaultResult = 300;
    Paint mPaint;//外接圆的画笔
    Paint mProgressPaint;//进度的 画笔
    String progress = "9.69";
    Paint _Paint;//百分号的画笔


    String extraTextText = "近半年收益";
    int extraTextColor = 0xffcccccc;//上面的  textView颜色
    Paint extra_Paint;//百分号的画笔

    public AcrTextView(Context context) {
        super(context);
    }

    public AcrTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setProgress(String progress) {
        this.progress = progress;
        invalidate();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setColor(arcColor);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(stroke);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setAntiAlias(true);//抗锯齿
        mProgressPaint.setStyle(Paint.Style.STROKE);

        _Paint = new Paint();
        _Paint.setColor(progressColor);
        _Paint.setAntiAlias(true);//抗锯齿
        _Paint.setStyle(Paint.Style.STROKE);

        extra_Paint = new Paint();
        extra_Paint.setColor(extraTextColor);
        extra_Paint.setAntiAlias(true);//抗锯齿
        extra_Paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        RectF rectF = new RectF(2, 2, width - 2, height * 2);
        canvas.drawArc(rectF, 180, 180, false, mPaint);

        //文字
        progressSize = height / 2;
        mProgressPaint.setTextSize(progressSize);
        _Paint.setTextSize(progressSize / 2);
        //文字的大小
        float pSize = mProgressPaint.measureText(progress);//进度的 size
        float unitSize = mProgressPaint.measureText("%");//单位的size
        float totalSize = pSize + unitSize / 3;//总长度
        float start = width / 2 - totalSize / 2;

        canvas.drawText(progress, start, height - 2, mProgressPaint);
        canvas.drawText("%", start + pSize, height - 2, _Paint);

        extra_Paint.setTextSize(progressSize / 2 - 5);
        float extraSize = extra_Paint.measureText(extraTextText);//进度的 size
        canvas.drawText(extraTextText, width / 2 - extraSize / 2, height / 2 - 2, extra_Paint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {//固定大小 或者是 matchParent
            width = widthSize;
        } else {
            width = Math.min(widthSize, defaultResult);
        }

//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (heightMode == MeasureSpec.EXACTLY) {//固定大小 或者是 matchParent
//            height = heightSize;
//        } else {
//            height = Math.min(heightSize, defaultResult);
//        }
        int height = width / 2;
        setMeasuredDimension(width, height);
    }


}
