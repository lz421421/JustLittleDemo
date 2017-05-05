package com.lizhi.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.lizhi.demo.R;

/**
 * Created by 39157 on 2017/5/4.
 */

public class SquareView extends ImageView {

    private final int WIDTH = 300;
    Paint mPaint;
    private int _DEFAULT_COLOR = 0xffff0000;
    boolean isSTROKE = true;
    int stroke = 10;

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareView);
        _DEFAULT_COLOR = typedArray.getColor(R.styleable.SquareView_squareColor, _DEFAULT_COLOR);
        isSTROKE = typedArray.getBoolean(R.styleable.SquareView_isStoke, isSTROKE);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(_DEFAULT_COLOR);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(stroke);
        mPaint.setStyle(isSTROKE ? Paint.Style.STROKE : Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0, height = 0, widthMode, heightMode, widthSize, heightSize;
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY: {
                width = widthSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                width = WIDTH;
                break;
            }
            case MeasureSpec.UNSPECIFIED: {
                width = WIDTH;
                break;
            }
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY: {
                height = heightSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                height = WIDTH;
                break;
            }
            case MeasureSpec.UNSPECIFIED: {
                height = WIDTH;
                break;
            }
        }

        setMeasuredDimension(isSTROKE ? width - stroke : width, isSTROKE ? height - stroke : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRect(rect, mPaint);
    }
}
