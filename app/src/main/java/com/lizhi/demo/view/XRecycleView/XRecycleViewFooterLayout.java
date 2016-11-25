package com.lizhi.demo.view.XRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.view.CircleRotateView;
import com.nineoldandroids.animation.ValueAnimator;

import static com.lizhi.demo.view.XRecycleView.XRecycleViewFooterLayout.State.CAN_FLASH;
import static com.lizhi.demo.view.XRecycleView.XRecycleViewFooterLayout.State.FLASHING;
import static com.lizhi.demo.view.XRecycleView.XRecycleViewFooterLayout.State.NO_CAN_FLASH;

/**
 * Created by 39157 on 2016/11/23.
 */

public class XRecycleViewFooterLayout extends LinearLayout {
    LinearLayout ll_footer;
    //原始高度 初始化高度 1px
    private int originalHeigt = 0;
    //达到刷新高度的临界值
    int flashHeight;
    CircleRotateView crv_foot_load;


    public XRecycleViewFooterLayout(Context context) {
        super(context);
        flashHeight = DensityUtil.dip2px(context, 30);
        initView();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_recycle_footer, this);
        ll_footer = (LinearLayout) findViewById(R.id.ll_footer);
        originalHeigt = getNowHeight();
        crv_foot_load = (CircleRotateView) findViewById(R.id.crv_foot_load);
    }

    public int getNowHeight() {
        LinearLayout.LayoutParams lp = (LayoutParams) ll_footer.getLayoutParams();
        return lp.height;
    }

    public void setHeightAdd(int addSize) {
        LayoutParams lp = (LayoutParams) ll_footer.getLayoutParams();
        lp.height += addSize;
        if (lp.height < originalHeigt) {
            lp.height = originalHeigt;
        }
        if (getState() != XRecycleViewFooterLayout.State.FLASHING) {
            if (getNowHeight() >= flashHeight) {
                setState(CAN_FLASH);
            } else {
                setState(NO_CAN_FLASH);
            }
        }
        ll_footer.setLayoutParams(lp);
    }

    public void start(boolean isStart) {
        if (isStart){
            setState(FLASHING);
        }else {
            setState(XRecycleViewFooterLayout.State.FLASH_COMPLETE);
        }
        crv_foot_load.isStart(isStart);
    }
    public void setProgress(int progress) {
        crv_foot_load.setProgress(progress);
    }
    public void setNowHeight(int height) {
        LayoutParams lp = (LayoutParams) ll_footer.getLayoutParams();
        lp.height = height;
        ll_footer.setLayoutParams(lp);
    }

    public void complete(){
        start(false);
        closeTo(null);
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
                            xRecycleListener.onLoadMore();
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

    XRecycleViewFooterLayout.State state = NO_CAN_FLASH;


    public void setState(XRecycleViewFooterLayout.State state) {
        this.state = state;

    }

    public XRecycleViewFooterLayout.State getState() {
        return state;
    }

    public enum State {
        //还不可以刷新
        NO_CAN_FLASH(-1, "加载更多"),
        //达到刷新的条件
        CAN_FLASH(0, "松开加载"),
        //正在刷新
        FLASHING(1, "正在加载"),
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
