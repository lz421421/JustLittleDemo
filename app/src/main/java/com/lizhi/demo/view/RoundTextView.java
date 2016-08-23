package com.lizhi.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/7/19.
 */
public class RoundTextView extends TextView {
    private int inColor;

    public RoundTextView(Context context) {
        this(context, null);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundView);
        inColor = array.getColor(R.styleable.RoundView_inColor, 0);
        array.recycle();
    }


    public void setInColor(int color) {
        this.inColor = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        if (inColor != 0) {
            paint.setColor(inColor);
        }
        //在空的画布上画一个矩形
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                5, 5, paint);

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFilterBitmap(true);
        textPaint.setColor(getCurrentTextColor());

        String text = getText().toString();
        float textSize = getTextSize();
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);//设置文字水平居中
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        Log.i("LXH", "--canvas.getHeight()--" + canvas.getHeight() + "--textPaint.descent()--" + textPaint.descent() + "--textPaint.ascent()--" + textPaint.ascent());
        canvas.drawText(text, getWidth() / 2, yPos, textPaint);

    }
}
