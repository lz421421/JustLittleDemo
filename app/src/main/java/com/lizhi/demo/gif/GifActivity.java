package com.lizhi.demo.gif;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import com.lizhi.demo.R;
import com.lizhi.demo.gif.view.GifView;
import com.lizhi.demo.scrollview_header_stop.view.MyScrollView;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/1/7.
 */
public class GifActivity extends AppCompatActivity {
    GifView gifView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
//        LogUtil.log("--getNumber-->" + getNumber());

        gifView = (GifView) findViewById(R.id.gif);
//        gifView.showCover();
        gifView.showAnimation();
        gifView.setGifImage(R.drawable.home_reflash);

        findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
//                int screenW = DensityUtil.getWidth(GifActivity.this);
//                float offset = screenW * 1.0f / gifView.getGifDecoderWidth();
//                int gifH = gifView.getGifDecoderHdight();
//                gifView.setShowDimension(screenW, (int) (gifH * offset));
                int height = gifView.getHeight();
                LogUtil.log("--height-->" + height);
                float offset = height * 1.0f / gifView.getGifDecoderHdight();
                int width = (int) (gifView.getGifDecoderWidth() * offset);
                gifView.setShowDimension(width, height);
            }
        });


    }


    public int getNumber() {
        int num = 0;
        boolean isBreak = true;
        while (isBreak) {
            num += 7;
            if (num % 2 == 1 && num % 3 == 1 && num % 4 == 1 && num % 5 == 1 && num % 6 == 1 && num % 7 == 0) {
                isBreak = false;
            } else {
                isBreak = true;
            }
        }
        return num;
    }
}
