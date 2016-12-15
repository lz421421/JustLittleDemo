package com.lizhi.demo.CanvasPath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.lizhi.demo.utils.LogUtil;

/**
 * Created by 39157 on 2016/12/9.
 */

public class CanvasPathView extends View {

    Paint mPaint;//画笔
    int mWidth, mHeight;//宽高

    public CanvasPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(60f);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    float[] floats = {10, 10};

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtil.log("---onSizeChanged--w-->" + w + "--h->" + h);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.translate(mWidth / 3, mHeight / 3);
        canvas.drawColor(Color.GREEN);

//        //画点
//        canvas.drawPoint(100, 100, mPaint);
//        canvas.drawPoints(new float[]{
//                100f, 200f,
//                100f, 300f,
//                100f, 400f}, mPaint);
//
//        //画线
//        canvas.drawLine(200, 200, 400, 200, mPaint);
//        canvas.drawLines(new float[]{
//                300, 300, 400, 300,
//                300, 400, 400, 400,
//                300, 500, 400, 500,
//                300, 600, 400, 600,
//        }, mPaint);
//
//        //画矩形
//        canvas.drawRect(333, 333, 333 + 200, 333 + 200, mPaint);
//        Rect rect = new Rect(333 + 300, 333 + 300, 333 + 600, 333 + 600);
//        canvas.drawRect(rect, mPaint);
//        RectF rectF = new RectF(0, 333 + 1000, 300, 333 + 1300);
//        canvas.drawRect(rectF, mPaint);
//
//        //画圆角矩形
//        RectF rectRound = new RectF(450, 333 + 1000, 750, 333 + 1300);
//        canvas.drawRoundRect(rectRound, 30, 30, mPaint);

        //扇形的 外接矩形
        canvas.translate(mWidth / 2, mHeight / 2);                // 将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.5);  // 饼状图半径
        RectF pieRectF = new RectF(-r, -r, r, r);
        mPaint.setStrokeWidth(0f);
        for (int i = 0; i < 6; i++) {
            mPaint.setColor(colors[i]);
            canvas.drawArc(pieRectF, i * 60, 60, true, mPaint);
        }
        canvas.rotate(60,r,0);

        for (int i = 0; i < 6; i++) {
            mPaint.setColor(colors[i]);
            canvas.drawArc(pieRectF, i * 60, 60, true, mPaint);
        }
//        invalidate();
    }

    int rotate = 0;


    int colors[] = {Color.CYAN, Color.GRAY, Color.BLUE, Color.parseColor("#f0f0f0"), Color.MAGENTA, Color.parseColor("#ff6600")};
}
