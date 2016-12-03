package com.lizhi.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lizhi.demo.utils.LogUtil;

/**
 * Created by 39157 on 2016/12/3.
 */

public class NumberPswEditText extends EditText {


    /**
     * 间隔
     */
    private final int PWD_SPACING = 5;
    /**
     * 密码大小
     */
    private final int PWD_SIZE = 40;
    /**
     * 密码长度
     */
    private final int PWD_LENGTH = 6;
    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;

    /**
     * 密码画笔
     */
    private Paint mPwdPaint;

    /**
     * 没有输入密码时候的 密码占位符
     */
    private Paint mNoPwdPaint;

    /**
     * 输入的密码长度
     */
    private int mInputLength;

    /**
     * 输入结束监听
     */
    private OnInputFinishListener mOnInputFinishListener;

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public NumberPswEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化密码画笔
        mPwdPaint = new Paint();
        mPwdPaint.setColor(Color.BLACK);
        mPwdPaint.setStyle(Paint.Style.FILL);
        mPwdPaint.setAntiAlias(true);

        setTextColor(Color.TRANSPARENT);
        setHighlightColor(Color.TRANSPARENT);
        setBackgroundColor(Color.TRANSPARENT);
        setCursorVisible(false);
        setLongClickable(false);

        mNoPwdPaint = new Paint();
        mNoPwdPaint.setColor(Color.parseColor("#666666"));
        mNoPwdPaint.setStyle(Paint.Style.STROKE);
        mNoPwdPaint.setAntiAlias(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = getText().toString().length();
                setSelection(length);
            }
        });
    }

    int rectWidth;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        rectWidth = (mWidth - PWD_SPACING * (PWD_LENGTH - 1)) / PWD_LENGTH;
        drawNoPSW(canvas);
        drawPSW(canvas);
    }

    /**
     * 绘制没有密码的时候的圆圈
     *
     * @param canvas
     */
    public void drawNoPSW(Canvas canvas) {
        for (int i = 0; i < 6; i++) {
            int cx = rectWidth / 2 + (rectWidth + PWD_SPACING) * i;
            int cy = mHeight / 2;
            canvas.drawCircle(cx, cy, PWD_SIZE, mNoPwdPaint);
        }
    }

    /**
     * 绘制输入密码的圆圈
     *
     * @param canvas
     */
    public void drawPSW(Canvas canvas) {
        for (int i = 0; i < mInputLength; i++) {
            int cx = rectWidth / 2 + (rectWidth + PWD_SPACING) * i;
            int cy = mHeight / 2;
            canvas.drawCircle(cx, cy, PWD_SIZE, mPwdPaint);
        }
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        int length = getText().toString().length();
        setSelection(length);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.mInputLength = text.toString().length();
        invalidate();
        if (mInputLength == PWD_LENGTH && mOnInputFinishListener != null) {
            hideSoftInput(getWindowToken());
            mOnInputFinishListener.onInputFinish(text.toString());
        }
        setSelection(mInputLength);
    }


    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public interface OnInputFinishListener {
        /**
         * 密码输入结束监听
         *
         * @param password
         */
        void onInputFinish(String password);
    }

    /**
     * 设置输入完成监听
     *
     * @param onInputFinishListener
     */
    public void setOnInputFinishListener(
            OnInputFinishListener onInputFinishListener) {
        this.mOnInputFinishListener = onInputFinishListener;
    }
}
