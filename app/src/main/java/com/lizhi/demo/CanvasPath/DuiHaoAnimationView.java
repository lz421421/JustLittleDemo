package com.lizhi.demo.CanvasPath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.lizhi.demo.utils.DensityUtil;

/**
 * Created by 39157 on 2016/12/17.
 */

public class DuiHaoAnimationView extends View {
    Picture mPicture;
    Picture mDuiDaoPicture;

    public DuiHaoAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPicture();
    }

    public void initPicture() {
        mPicture = new Picture();
        Canvas canvas = mPicture.beginRecording(DensityUtil.dip2px(getContext(), 30), DensityUtil.dip2px(getContext(), 30));
        canvas.translate(mPicture.getWidth() / 2, mPicture.getHeight() / 2);
        //画圆圈背景
        Paint bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(4);
        bgPaint.setColor(0xffff6600);
        bgPaint.setAntiAlias(true);
        float r = mPicture.getWidth() / 2 - 3;
        canvas.drawCircle(0, 0, r, bgPaint);
        mPicture.endRecording();

        //画对号
        mDuiDaoPicture = new Picture();
        Canvas mDuiHaoCanvas = mDuiDaoPicture.beginRecording(DensityUtil.dip2px(getContext(), 30), DensityUtil.dip2px(getContext(), 30));
        mDuiHaoCanvas.translate(mPicture.getWidth() / 2, mPicture.getHeight() / 2);
        bgPaint.setStrokeWidth(4);
        Path path = new Path();
        path.moveTo(-mPicture.getWidth() / 4 + 2, 0);
        path.lineTo(2, mPicture.getHeight() / 4 - 4);
        path.lineTo(r * 3 / 4, -r / 2-2);
        path.offset(-r / 4, 0);
        bgPaint.setColor(0xffcccccc);
        mDuiHaoCanvas.drawPath(path, bgPaint);
        mDuiDaoPicture.endRecording();

    }

    int width = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        PictureDrawable pictureDrawable = new PictureDrawable(mPicture);
        pictureDrawable.setBounds(0, 0, mPicture.getWidth(), mPicture.getHeight());
        pictureDrawable.draw(canvas);


        PictureDrawable duiHaoPictureDrawable = new PictureDrawable(mDuiDaoPicture);
        if (width >= mDuiDaoPicture.getWidth()) {
            width = mDuiDaoPicture.getWidth();
        } else {
            width += 2;
            invalidate();
        }
        duiHaoPictureDrawable.setBounds(0, 0, width, mDuiDaoPicture.getHeight());
        duiHaoPictureDrawable.draw(canvas);
    }
}
