package com.lizhi.demo.scrollview_header_stop.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.gif.view.GifView;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;
import com.nineoldandroids.animation.ValueAnimator;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/1/5.
 */
public class HeaderView extends LinearLayout {
    Context mContext;
    View view_content;
    TextView tv_text;
    GifView gifView;
    int reflashHeight = 0;
    int screenW;
    int gifH;

    public int getReflashHeight() {
        return reflashHeight;
    }


    public HeaderView(Context context) {
        this(context, null);

    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initHeaderView(context);
    }

    public void initHeaderView(Context context) {
        view_content = LayoutInflater.from(context).inflate(R.layout.scrollview_header_view, null);
        addView(view_content);
        setBackgroundColor(Color.parseColor("#eeeeee"));

        tv_text = (TextView) findViewById(R.id.tv_text);
        gifView = (GifView) findViewById(R.id.gif);
        gifView.showCover();
        initHeight(context);
    }

    public void initHeight(Context context) {
        LinearLayout.LayoutParams lp = (LayoutParams) view_content.getLayoutParams();
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        view_content.setLayoutParams(lp);
//
//        screenW = DensityUtil.getWidth(context);
//        float offset = screenW * 1.0f / gifView.getGifDecoderWidth();
//        gifH = (int) (gifView.getGifDecoderHdight() * offset);
//        if (gifH == 1 || gifH == 0) {
//            gifH = 180;
//        }
//        gifView.setShowDimension(screenW, gifH);
//        reflashHeight = gifH - DensityUtil.dip2px(context, 20) / 2;
//        LogUtil.log("---initHeight-->");
        initGifHeight();
    }


    public void initGifHeight() {
        if (gifView.getShowHeight() != gifH) {
            screenW = DensityUtil.getWidth(getContext());
            float offset = screenW * 1.0f / gifView.getGifDecoderWidth();
            gifH = (int) (gifView.getGifDecoderHdight() * offset);
            if (gifH == 1 || gifH == 0) {
                gifH = (int) (180 * offset);
            }
            gifView.setShowDimension(screenW, gifH);
            reflashHeight = gifH - DensityUtil.dip2px(getContext(), 20) / 2;
        }
    }

    /**
     * 设置 header的高度 是累加的高度
     *
     * @param height
     */
    public void setHeight(int height) {
        LinearLayout.LayoutParams lp = (LayoutParams) getLayoutParams();
        lp.height += height;
        if (lp.height < 0) {
            lp.height = 0;
        }
        if (lp.height >= reflashHeight) {
            setText("松开立即刷新");
            gifView.showAnimation();
        } else {
            setText("下拉刷新");
            gifView.showCover();
        }
        setLayoutParams(lp);
    }

    /**
     * 设置真正的高度
     *
     * @param height
     */
    public void setHeightReally(int height) {
        LinearLayout.LayoutParams lp = (LayoutParams) getLayoutParams();
        lp.height = height;
        setLayoutParams(lp);
    }

    public int getHeaderHeight() {
        LinearLayout.LayoutParams lp = (LayoutParams) getLayoutParams();
        int height = lp.height;
        return height;
    }

    public void closeTo(int from, int to) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float values = (float) valueAnimator.getAnimatedValue();
                setHeightReally((int) values);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tv_text.setText(text);
        }
    }
}
