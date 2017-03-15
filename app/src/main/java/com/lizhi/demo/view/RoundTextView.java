package com.lizhi.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.DensityUtil;

/**
 * Created by Administrator on 2016/7/19.
 */
public class RoundTextView extends TextView {
    float radios;
    int roundBg;
    Paint mPaint = new Paint();
    RectF rectF;

    boolean isStroke = false;

    public RoundTextView(Context context) {
        super(context);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView);
        radios = array.getDimension(R.styleable.RoundTextView_radios, 0);
        roundBg = array.getColor(R.styleable.RoundTextView_roundBg, 0xffffffff);
        isStroke = array.getBoolean(R.styleable.RoundTextView_isStroke, false);
        array.recycle();
        initBGPaint();
    }

    public void initBGPaint() {
        mPaint = new Paint();
        mPaint.setColor(roundBg);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(isStroke ? Paint.Style.STROKE : Paint.Style.FILL);
        if (isStroke) {
            mPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 1));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        rectF = new RectF(8, 8, getWidth() - 8, getHeight() - 8);
        canvas.drawRoundRect(rectF, radios, radios, mPaint);
        super.onDraw(canvas);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!enabled) {
            mPaint.setColor(0xff999999);
            setTextColor(0xff999999);
        }
        invalidate();
        super.setEnabled(enabled);
    }
}
