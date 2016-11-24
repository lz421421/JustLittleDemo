package com.lizhi.demo.view.XRecycleView;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.CircleRotateView;
import com.nineoldandroids.animation.ValueAnimator;

import static com.lizhi.demo.view.XRecycleView.XRecycleViewHeaderLayout.State.CAN_FLASH;
import static com.lizhi.demo.view.XRecycleView.XRecycleViewHeaderLayout.State.NO_CAN_FLASH;
import static com.lizhi.demo.view.XRecycleView.XRecycleViewHeaderLayout.State.FLASHING;
import static com.lizhi.demo.view.XRecycleView.XRecycleViewHeaderLayout.State.FLASH_COMPLETE;

/**
 * Created by 39157 on 2016/11/23.
 */

public class XRecycleViewHeaderLayout extends LinearLayout {
    LinearLayout ll_header_content;
    //原始高度 初始化高度 1px
    private int originalHeigt = 0;
    //达到刷新高度的临界值
    int flashHeight;
    TextView tv_header_flash, tv_header_flash_point;
    CircleRotateView crv_header_falsh;


    public XRecycleViewHeaderLayout(Context context) {
        super(context);
        flashHeight = DensityUtil.dip2px(context, 80);
        initView();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_header_flash, this);
        ll_header_content = (LinearLayout) findViewById(R.id.ll_header_content);
        tv_header_flash = (TextView) findViewById(R.id.tv_header_flash);
        tv_header_flash_point = (TextView) findViewById(R.id.tv_header_flash_point);
        originalHeigt = getNowHeight();
        crv_header_falsh = (CircleRotateView) findViewById(R.id.crv_header_falsh);
    }

    public int getOriginalHeigt() {
        return originalHeigt;
    }

    public int getNowHeight() {
        LinearLayout.LayoutParams lp = (LayoutParams) ll_header_content.getLayoutParams();
        return lp.topMargin;
    }

    public void setHeightAdd(int addSize) {
        LayoutParams lp = (LayoutParams) ll_header_content.getLayoutParams();
        lp.topMargin += addSize;
        if (lp.topMargin < originalHeigt) {
            lp.topMargin = originalHeigt;
        }
        if (getState() != State.FLASHING) {
            if (getNowHeight() >= flashHeight) {
                setState(CAN_FLASH);
            } else {
                setState(NO_CAN_FLASH);
            }
        }
        ll_header_content.setLayoutParams(lp);
    }

    /**
     * 开始刷新
     * @param isStart
     */
    public void start(boolean isStart) {
        if (isStart){
            setState(FLASHING);
        }else {
            setState(XRecycleViewHeaderLayout.State.FLASH_COMPLETE);
        }
        crv_header_falsh.isStart(isStart);
        tv_header_flash.setText(state.text);
        if (isStart) {
            setPointText();
        } else {
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
        }
    }


    Handler handler;
    Runnable runnable;

    public void setPointText() {
        final StringBuilder stringBuilder = new StringBuilder(".");
        if (handler == null) {
            handler = new Handler();
        }
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (stringBuilder.length() == 3) {
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                    stringBuilder.append(".");
                    tv_header_flash_point.setText(stringBuilder.toString());
                    handler.postDelayed(runnable, 500);
                }
            };
        }
        handler.postDelayed(runnable, 500);
    }

    public void setProgress(int progress) {
        crv_header_falsh.setProgress(progress);
    }

    public void complete(){
        start(false);
        closeTo(null);
    }

    public void setNowHeight(int height) {
        LayoutParams lp = (LayoutParams) ll_header_content.getLayoutParams();
        lp.topMargin = height;
        ll_header_content.setLayoutParams(lp);
    }

    public void closeTo(final XRecycleView.OnXRecycleListener xRecycleListener) {
        int from = getNowHeight();
        int to = originalHeigt;
        switch (state) {
            case CAN_FLASH:
                to = flashHeight;
                break;
            case FLASHING:
                to = flashHeight;
                break;
            case NO_CAN_FLASH:
                to = originalHeigt;
                break;
            case FLASH_COMPLETE:
                to = originalHeigt;
                break;
        }
        if (from <= to) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        final int finalTo = to;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int values = (int) valueAnimator.getAnimatedValue();
                setNowHeight(values);
                if (values == finalTo) {
                    switch (state) {
                        case CAN_FLASH:
                            start(true);
                            xRecycleListener.onFlash();
                            break;
                        case FLASH_COMPLETE:

                            break;
                    }
                }
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    public State getState() {
        return state;
    }

    State state = NO_CAN_FLASH;

    public void setState(State state) {
        this.state = state;
        tv_header_flash.setText(state.text);

    }

    public enum State {
        //还不可以刷新
        NO_CAN_FLASH(-1, "下拉刷新"),
        //达到刷新的条件
        CAN_FLASH(0, "松开刷新"),
        //正在刷新
        FLASHING(1, "正在刷新"),
        //刷新结束
        FLASH_COMPLETE(2, "刷新完成");
        public int state;
        String text;
        //各自对应的高度

        State(int state, String text) {
            this.state = state;
            this.text = text;
        }
    }
}
