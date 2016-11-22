package com.lizhi.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/7/22.
 * 自己旋转的View
 */
public class CircleRotateView extends View {

    Paint mPaint;
    float strokeWidth = 10;
    int color = 0x666666;
    boolean isStart = false;

    public CircleRotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleRotateView);
        if (typedArray != null) {
            strokeWidth = typedArray.getDimension(R.styleable.CircleRotateView_strokeWidth, strokeWidth);
            color = typedArray.getColor(R.styleable.CircleRotateView_strokeColor, color);
            typedArray.recycle();
        }

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth);
    }

    public CircleRotateView(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultResult = 0;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);

        int width = 0;
        int height = 0;

        //内容包裹
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
//            MeasureSpec.UNSPECIFIED
            defaultResult = 300;
        }
        //内容包裹
        if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(widthSize, defaultResult);
        }

        //内容包裹
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
//            MeasureSpec.UNSPECIFIED
            defaultResult = 300;
        }
        //内容包裹
        if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(heightSize, defaultResult);
        }
        setMeasuredDimension(width, height);
    }

    int i = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float cWidth = (float) (width * 0.6);
        float cHeight = (float) (height * 0.6);
        RectF rectF = new RectF((width - cWidth) / 2, (height - cHeight) / 2, (width + cWidth) / 2, (height + cHeight) / 2);
        canvas.drawArc(rectF, 270 + i, (float) 240, false, mPaint);
        if (isStart) {
            i += 5;
            postInvalidate();
        }
    }

    public void isStart(boolean isStart) {
        this.isStart = isStart;
        postInvalidate();
    }

    public void setProgress(int progress) {
        i += progress;
        postInvalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.log("-------MotionEvent.ACTION_DOWN------->");
                SpringConfig config = SpringConfig.fromOrigamiTensionAndFriction(50, 1);
                SpringSystem springSystem = SpringSystem.create();
                Spring spring = springSystem.createSpring();
                spring.setSpringConfig(config);
                // Add a listener to observe the motion of the spring.
                spring.addListener(new SimpleSpringListener() {

                    @Override
                    public void onSpringUpdate(Spring spring) {
                        // You can observe the updates in the spring
                        // state by asking its current value in onSpringUpdate.
                        float value = (float) spring.getCurrentValue();
//                        LogUtil.log("--------value----->" + value);
                        setScaleX(value);
                        setScaleY(value);
                    }
                });
                spring.setCurrentValue(1.0);
                spring.setEndValue(0.8);
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.log("-------MotionEvent.ACTION_CANCEL------->");
            case MotionEvent.ACTION_UP:
                LogUtil.log("-------MotionEvent.ACTION_UP------->");
                SpringConfig config_up = SpringConfig.fromOrigamiTensionAndFriction(50, 1);
                SpringSystem springSystem_up = SpringSystem.create();
                Spring spring_up = springSystem_up.createSpring();
                spring_up.setSpringConfig(config_up);
                // Add a listener to observe the motion of the spring.
                spring_up.addListener(new SimpleSpringListener() {

                    @Override
                    public void onSpringUpdate(Spring spring) {
                        // You can observe the updates in the spring
                        // state by asking its current value in onSpringUpdate.
                        float value = (float) spring.getCurrentValue();
//                        LogUtil.log("--------value----->" + value);
                        setScaleX(value);
                        setScaleY(value);
                    }
                });
                spring_up.setCurrentValue(0.8);
                spring_up.setEndValue(1.0);
                break;
        }
        return super.onTouchEvent(event);
    }
}
