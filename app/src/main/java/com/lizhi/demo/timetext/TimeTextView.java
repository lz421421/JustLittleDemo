package com.lizhi.demo.timetext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

/**
 * 自定义倒计时文本控件
 *
 * @author Administrator
 */
public class TimeTextView extends TextView implements Runnable {
    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息
    private long times;
    private long mday, mhour, mmin, msecond;//天，小时，分钟，秒
    private boolean run = false; //是否启动了

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTextView);
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTextView);
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }

    public TimeTextView(Context context) {
        super(context);
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        removeCallbacks(this);
        run = false;
        this.times = times;
        mday = times / 86400;
        mhour = (times - (times / 86400) * 86400) / 3600;
        mmin = ((times - (times / 3600) * 3600) / 60);
        msecond = times % 60;
    }

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        msecond--;
        if (msecond < 0) {
            mmin--;
            msecond = 59;
            if (mmin < 0) {
                mmin = 59;
                mhour--;
                if (mhour < 0) {
                    // 倒计时结束
                    mhour = 59;
                    mday--;
                }
            }
        }
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        //标示已经启动
        run = true;
        ComputeTime();
        String strTime = "剩余" + mday + "天" + mhour + "时" + mmin + "分" + msecond + "秒";
        this.setText(strTime);
        postDelayed(this, 1000);
    }
}