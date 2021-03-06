package com.lizhi.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 39157 on 2016/12/15.
 */

public class LetterSortView extends View {
    public static final String[] letters = new String[]{
            "#",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"

    };

    int mHeight = 0;
    int mWidth;
    int mSingleTextSize = 0;
    String touchLetter;
    int letterSpace = 10;
    boolean isDrawBg = false;
    Paint bgPaint;//背景的画笔
    TextPaint textPaint;//字母的画笔
    OnLetterTouchListener onLetterTouchListener;

    public LetterSortView(Context context) {
        this(context, null);
    }


    public LetterSortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        letterSpace = DensityUtil.dip2px(context, 5);
        mSingleTextSize = DensityUtil.sp2px(context, 12);
        initPaint();
    }

    public void setOnLetterTouchListener(OnLetterTouchListener onLetterTouchListener) {
        this.onLetterTouchListener = onLetterTouchListener;
    }

    public void initPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(0xffbfbfbf);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(0xff666666);
        textPaint.setTextSize(mSingleTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST://匹配父窗口
                height = letters.length * mSingleTextSize + (letters.length - 1) * letterSpace;
                break;
            case MeasureSpec.EXACTLY://确定大小
                break;
            case MeasureSpec.UNSPECIFIED://有多大显示多大
                break;
        }

        switch (widthMode) {
            case MeasureSpec.AT_MOST://匹配父窗口
                width = mSingleTextSize + DensityUtil.dip2px(getContext(), 6);
                break;
            case MeasureSpec.EXACTLY://确定大小
                break;
            case MeasureSpec.UNSPECIFIED://有多大显示多大
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchY = event.getY();
        touchLetter = getLetter(touchY);
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                isDrawBg = true;
                if (onLetterTouchListener != null && touchLetter != null) {
                    onLetterTouchListener.onTouch(touchLetter.trim(), false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDrawBg = false;
                if (onLetterTouchListener != null) {
                    onLetterTouchListener.onTouch(null, true);
                }
                break;
        }
        invalidate();
        return true;
    }

    public String getLetter(float touchY) {
        int index = (int) (touchY / (mSingleTextSize + letterSpace));
        if (index >= 0 && index < letters.length) {
            return letters[index];
        } else {
            return null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDrawBg) {
            //画背景
            RectF bgRect = new RectF(0, 0, mWidth, mHeight);
            canvas.drawRect(bgRect, bgPaint);
        }
        for (int i = 0; i < letters.length; i++) {
            int x = mWidth - mSingleTextSize;
            if (i == 9 || i == letters.length - 1) {
                x = x + 3;
            }
            String text = letters[i];
            canvas.drawText(text, x, mSingleTextSize, textPaint);
            canvas.translate(0, letterSpace + mSingleTextSize);
        }
        /*****************/
//        draw(canvas);
    }
//
//    public void draw(Canvas canvas) {
//        if (isDrawBg) {
//            //画背景
//            RectF bgRect = new RectF(0, 0, mWidth, mHeight);
//            canvas.drawRect(bgRect, bgPaint);
//        }
//        mSingleTextSize = (mHeight - (letters.length - 1) * letterSpace) / letters.length;
//        textPaint.setTextSize(mSingleTextSize);
//        for (int i = 0; i < letters.length; i++) {
//            int x = mWidth / 2 - mSingleTextSize / 2 + 9;
//            String text = letters[i];
//            if (i == letters.length - 1) {
//                x = mWidth / 2 - mSingleTextSize / 2;
//            }
//            if (i == 13 || i == 23) {
//                x = mWidth / 2 - mSingleTextSize / 2 + 5;
//            }
//            canvas.drawText(text, x, (mSingleTextSize + letterSpace) * (i + 1), textPaint);
//        }
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = h - 5;
        mWidth = w;
    }

    public interface OnLetterTouchListener {
        void onTouch(String letter, boolean isUp);
    }
}
