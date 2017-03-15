package com.lizhi.demo.CanvasPath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by 39157 on 2016/12/19.
 */

public class DrawBitmap extends ScrollView {

    int mWidth, mHeight;
    Bitmap mBitmap;
    int mBWidth, mBHeight;

    public DrawBitmap(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBitmap();
    }


    public void initBitmap() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goods_sample);
        mBWidth = mBitmap.getWidth();
        mBHeight = mBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode, heightMode, width, height;
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.EXACTLY://具体大小
                break;
            case MeasureSpec.AT_MOST:
                width = mBWidth;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = mBWidth;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY://具体大小
                break;
            case MeasureSpec.AT_MOST:
                height = mBHeight;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = mBHeight;
                break;
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }


    int srcW = 0;
    int srcH = 0;
    boolean isAuto = true;

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.translate(mWidth / 2, mHeight / 2);
        Rect src = new Rect(0, 0, srcW, srcH);
        Rect dst = new Rect(0, 0, srcW, srcH);
        canvas.drawBitmap(mBitmap, src, dst, new Paint());
        if (srcW <= mWidth && isAuto) {
            srcW += 5;
            invalidate();
        }
        if (srcH <= mHeight && isAuto) {
            srcH += 5;
            invalidate();
        }
    }

    public void reset() {
        srcW = 0;
    }

    public void add() {
        isAuto = false;
        srcW += 5;
        invalidate();
    }
}
