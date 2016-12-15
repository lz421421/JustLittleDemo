package com.lizhi.demo.CanvasPath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 39157 on 2016/12/9.
 */

public class BezierView extends View {
    Paint mPaint;
    int mWidth, mHeight;
    PointF startP, controlP, endP;
    int mPointColor = Color.parseColor("#cccccc");

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        startP = new PointF();
        controlP = new PointF();
        endP = new PointF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        startP.x = w / 4;
        startP.y = h / 2;

        endP.x = w * 3 / 4;
        endP.y = h / 2;

        controlP.x = w / 2;
        controlP.y = h / 4;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                controlP.x = event.getX();
                controlP.y = event.getY();
                invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setColor(mPointColor);
        mPaint.setStrokeWidth(15);
        //画三个点 起点
        canvas.drawPoint(startP.x, startP.y, mPaint);
        canvas.drawText("A", startP.x, startP.y, mPaint);
        //画三个点 控制点
        canvas.drawPoint(controlP.x, controlP.y, mPaint);
        canvas.drawText("C", controlP.x, controlP.y, mPaint);
        //画三个点 终点
        canvas.drawPoint(endP.x, endP.y, mPaint);
        canvas.drawText("B", endP.x, endP.y, mPaint);


        Path path = new Path();
        mPaint.setColor(Color.parseColor("#ff6600"));
        path.moveTo(startP.x, startP.y);
        path.quadTo(controlP.x, controlP.y, endP.x, endP.y);
        canvas.drawPath(path, mPaint);
    }

}
