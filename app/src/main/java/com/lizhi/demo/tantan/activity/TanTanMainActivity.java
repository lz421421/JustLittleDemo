package com.lizhi.demo.tantan.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.BaseViewPgerAdapter;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.ImageViewUtils;
import com.lizhi.demo.utils.LogUtil;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class TanTanMainActivity extends BaseActivity implements View.OnClickListener {
    ImageView img_title;
    ViewPager vp_header;
    RelativeLayout rl_main;
    LinearLayout ll_img;

    SlidingPaneLayout slidingPaneLayout;
    public static final String TRANSIT_PIC = "picture";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tantanhome);
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        ViewCompat.setTransitionName(findViewById(R.id.img_open_target), TRANSIT_PIC);

    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                showToast("--tv_1->");
                break;
            case R.id.tv_2:
                showToast("--tv_2->");
/*                SpringConfig config = SpringConfig.fromOrigamiTensionAndFriction(50, 3);
                // Create a system to run the physics loop for a set of springs.
                SpringSystem springSystem = SpringSystem.create();

// Add a spring to the system.
                Spring spring = springSystem.createSpring();

// Add a listener to observe the motion of the spring.
                spring.addListener(new SimpleSpringListener() {

                    @Override
                    public void onSpringUpdate(Spring spring) {
                        // You can observe the updates in the spring
                        // state by asking its current value in onSpringUpdate.
                        float value = (float) spring.getCurrentValue();
                        LogUtil.log("--------value----->" + value);
                        v.setScaleX(value);
                        v.setScaleY(value);
                    }
                });

// Set the spring in motion; moving from 0 to 1
                spring.setCurrentValue(0.8);
                spring.setEndValue(1);*/
                break;
     /*       case R.id.vp_imgs:
            case R.id.view_place:
                int margin = DensityUtil.dip2px(this, 20);
                if (rl_main.getVisibility() == View.VISIBLE) {
                    //关闭
                    rl_main.setVisibility(View.GONE);
                    openVp_imgs(0, margin, 150);
                } else {
                    //打开
                    openVp_imgs(margin, 0, 150);
                }

                break;*/
        }
    }

    public void openVp_imgs(final int from, final int to, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int margin = (int) valueAnimator.getAnimatedValue();
                ll_img.setPadding(margin, margin, margin, margin);
//                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) vp_imgs.getLayoutParams();
//                lp.bottomMargin = margin;
//                lp.topMargin = margin;
//                lp.leftMargin = margin;
//                lp.rightMargin = margin;
//                vp_imgs.setLayoutParams(lp);

                if (from != 0 && margin == 0) {
                    rl_main.setVisibility(View.VISIBLE);
                }

            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
}
