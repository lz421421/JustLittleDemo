package com.lizhi.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.lizhi.demo.R;

/**
 * Created by 39157 on 2016/11/9.
 */

public class ProgressView extends View {


    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        if (array != null) {
            //获取我们在xml中设置的各个自定义属性
            sweepStep = array.getInteger(R.styleable.ProgressView_sweepStep, sweepStep);
            padding = array.getInteger(R.styleable.ProgressView_padding, padding);
            circleColor = array.getColor(R.styleable.ProgressView_circleColor, circleColor);
            sweepColor = array.getColor(R.styleable.ProgressView_sweepColor, sweepColor);
            startAngle = array.getInteger(R.styleable.ProgressView_startAngle, startAngle); //回收TypeArray资源
            array.recycle();
        }
    }

    private int sweepStep = 10;//扇形变换的步长(就是角度)
    private int padding = 0;//外边框距离扇形的距离 填充
    private int circleColor = Color.WHITE;//边框的颜色
    private int sweepColor = Color.parseColor("#20000000");//扇形颜色
    private int startAngle = 90;//起始角度
    // 设置外边框圆的边框粗细
    private int storke = 0;
    private int sweepAngle = 0;//扫过的角度

    private static final int DEFAULT_WIDTH = 100;

    private static final int DEFAULT_HEIGHT = 100;

    int maxLenth;

    /**
     * 设置文件总长度
     *
     * @param maxLenth
     */
    public void setMaxLenth(int maxLenth) {
        this.maxLenth = maxLenth;
    }

    //设置每次更新的进度
    public void upDataHeighr(int updata) {
        int height = getHeight() * (maxLenth - updata) / maxLenth;
        invalidate();

    }

    /**
     * 先绘制外边框 －－内部扇形
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        Paint mPaint = new Paint();
//        mPaint.setAntiAlias(true); //设置抗锯齿
//        mPaint.setColor(sweepColor);
//        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
//        canvas.drawRect(rectF, mPaint);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true); //设置抗锯齿
        mPaint.setColor(sweepColor);
//         绘制外层的圆框
        mPaint.setColor(circleColor);
        mPaint.setStrokeWidth(storke);
        mPaint.setStyle(Paint.Style.STROKE);//设置圆形为空心的圆  /
        // /这里我们得到控件的Height和Width，根据Heigh和Width来确定圆心的位置，来绘制外层圆
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - storke / 2, mPaint);
        invalidate();//请求重新绘制view
        // 绘制内部的扇形
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(sweepColor);
        RectF rectF = new RectF(padding + storke, padding + storke, getWidth() - padding - storke, getWidth() - padding - storke);
        canvas.drawArc(rectF, startAngle, sweepAngle, true, mPaint);
        sweepAngle += sweepStep;//根据步长更新扫过的角度
        sweepAngle = sweepAngle > 360 ? 0 : sweepAngle;
        invalidate();//重绘view
    }

    //因为我们是继承的View来自定义的View，所以onMeasure方法要重写
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        //因为绘制的是圆，所以判断一下高度或者宽度中的一个就可以。
        switch (wMode) {
            case MeasureSpec.AT_MOST://android:layout_width="warp_content"  //获取屏幕像素
                float density = getResources().getDisplayMetrics().density;
                wSize = (int) (DEFAULT_WIDTH * density);
                hSize = (int) (DEFAULT_HEIGHT * density);
                break;
            //当在xml中指定控件的宽高为match_parent或者指定数值的宽高时，回调以下代码
            case MeasureSpec.EXACTLY://android:layout_width="match_parent" android:layout_width="40dp"
                wSize = hSize = Math.min(wSize, hSize);
                break;
        }  //只要重写onMeasure方法，一定要调用以下方法，不然会报错
        setMeasuredDimension(wSize, hSize);
    }

}
