package com.lizhi.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lizhi.demo.R;

/**
 * Created by Administrator on 2016/7/19.
 */
public class RoundTextView extends TextView {
    float radios;
    int roundBg;
    Paint mPaint = new Paint();
    RectF rectF;

    public RoundTextView(Context context) {
        super(context);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView);
        radios = array.getDimension(R.styleable.RoundTextView_radios, 0);
        roundBg = array.getColor(R.styleable.RoundTextView_roundBg, 0xffffffff);
        array.recycle();
        initBGPaint();
    }

    public void initBGPaint() {
        mPaint = new Paint();
        mPaint.setColor(roundBg);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, radios, radios, mPaint);
        super.onDraw(canvas);
    }

}
