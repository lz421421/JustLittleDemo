package com.lizhi.demo.chaoshidemo.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 2015/12/23.
 */
public class AnimationUtils {

    public static void openView(final View view, int from, int to, long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int) valueAnimator.getAnimatedValue();
                view.getLayoutParams().height = height;
                view.setLayoutParams(view.getLayoutParams());
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void closeView(final View view, int from, int to, long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int) valueAnimator.getAnimatedValue();
                view.getLayoutParams().height = height;
                view.setLayoutParams(view.getLayoutParams());
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void closeViewWidth(final View view, int from, int to, long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int) valueAnimator.getAnimatedValue();
                view.getLayoutParams().width = height;
                view.setLayoutParams(view.getLayoutParams());
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void closeMargin(final View view, int from, int to, long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams vgLp = view.getLayoutParams();
                if (vgLp instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) vgLp).topMargin = height;
                } else if (vgLp instanceof RelativeLayout.LayoutParams) {
                    ((RelativeLayout.LayoutParams) vgLp).topMargin = height;
                }
                view.setLayoutParams(vgLp);
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    public static void closePadding(final View view, int from, int to, long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int) valueAnimator.getAnimatedValue();
                view.setPadding(0, height, 0, 0);
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
}
