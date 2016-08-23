package com.lizhi.demo.chaoshidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.chaoshidemo.utils.AnimationUtils;
import com.lizhi.demo.utils.LogUtil;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 2015/12/22.
 */
public class HomeView extends ScrollView {
    float downY;
    LinearLayout ll_header;
    View view_footer;
    LinearLayout.LayoutParams lp_header;
    int header_topMagin;
    LinearLayout innnerLayout;

    Scroller scroller;

    TextView tv_header;
    protected final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    protected final static float OPEN_RADIO = 1.5f; // support iOS like pull

    public HomeView(Context context) {
        this(context, null);
    }

    public HomeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ll_header = (LinearLayout) findViewById(R.id.ll_header);
        lp_header = (LinearLayout.LayoutParams) ll_header.getLayoutParams();
        header_topMagin = lp_header.topMargin = -lp_header.height;
        innnerLayout = (LinearLayout) getChildAt(0);
        tv_header = (TextView) findViewById(R.id.tv_header);
        view_footer = findViewById(R.id.view_footer);
//        ll_header.setLayoutParams(lp_header);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getRawY();
                float deltaY = nowY - downY;
                downY = nowY;
                int scrollY = getScrollY();
                int offset = innnerLayout.getMeasuredHeight() - getHeight();
                if (scrollY == 0 && deltaY > 0) {
                    int nowHeight = ((LinearLayout.LayoutParams) ll_header.getLayoutParams()).topMargin;
                    float changeMargin = deltaY / OFFSET_RADIO;
                    int afterMargin = ((LinearLayout.LayoutParams) ll_header.getLayoutParams()).topMargin = (int) (nowHeight + changeMargin);
                    ll_header.setLayoutParams(ll_header.getLayoutParams());
                    if (afterMargin >= 0) {
                        tv_header.setText("松开刷新");
                    } else {
                        tv_header.setText("下拉刷新");
                    }
                } else if (deltaY < 0) {
                    int nowHeight = ((LinearLayout.LayoutParams) ll_header.getLayoutParams()).topMargin;
                    if (nowHeight > header_topMagin) {
                        float changeMargin = deltaY;
                        int afterMargin = ((LinearLayout.LayoutParams) ll_header.getLayoutParams()).topMargin = (int) (nowHeight + changeMargin);
                        ll_header.setLayoutParams(ll_header.getLayoutParams());
                        if (afterMargin < 0) {
                            tv_header.setText("下拉刷新");
                        } else {
                            tv_header.setText("松开刷新");
                        }

                    }
                }
                if (scrollY == offset && deltaY < 0) {
                    int nowBottomMargin = view_footer.getLayoutParams().height;
                    float changeMargin = deltaY / OFFSET_RADIO;
                    int afterMargin = view_footer.getLayoutParams().height = (int) (nowBottomMargin - changeMargin);
                    view_footer.setLayoutParams(view_footer.getLayoutParams());
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                int afterMargin = ((LinearLayout.LayoutParams) ll_header.getLayoutParams()).topMargin;
                if (afterMargin >= 0) {
                    closeView(ll_header, afterMargin, 0);
                } else if (afterMargin < 0) {
                    closeView(ll_header, afterMargin, header_topMagin);
                }
                if (view_footer.getHeight() > 0) {
                    AnimationUtils.closeView(view_footer, view_footer.getHeight(), 0, 300);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnHomeFlashListener(OnHomeFlashListener onHomeFlashListener) {
        this.onHomeFlashListener = onHomeFlashListener;
    }

    OnHomeFlashListener onHomeFlashListener;

    public interface OnHomeFlashListener {
        public void onFlash();
    }


    public void closeView(final View view, final float from, final float to) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float values = (float) valueAnimator.getAnimatedValue();
                ((LinearLayout.LayoutParams) view.getLayoutParams()).topMargin = (int) (values);
                view.setLayoutParams(view.getLayoutParams());
                if (values == to && view == ll_header && from >= 0) {
                    if (onHomeFlashListener != null) {
                        tv_header.setText("正在刷新");
                        onHomeFlashListener.onFlash();
                    }
                }
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    public void setFlashComplete() {
        tv_header.setText("刷新结束");
        closeViewTo0(ll_header, 0, header_topMagin);
    }


    public void closeViewTo0(final View view, float from, float to) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float values = (float) valueAnimator.getAnimatedValue();
                ((LinearLayout.LayoutParams) view.getLayoutParams()).topMargin = (int) (values);
                view.setLayoutParams(view.getLayoutParams());
            }
        });
        animator.setDuration(300);
        animator.start();
    }


    /**
     * 滑动到顶部
     */
    public void scrollToTop() {
        int scrollY = getScrollY();
        int deltaY = 0 - scrollY;
        scroller.startScroll(0, scrollY, 0, deltaY, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {//没有滑完
            int y = scroller.getCurrY();
            scrollTo(0, y);
            postInvalidate();
        }
        super.computeScroll();
    }
}
