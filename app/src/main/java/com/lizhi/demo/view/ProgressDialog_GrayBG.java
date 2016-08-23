package com.lizhi.demo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lizhi.demo.R;


/**
 * Created by Administrator on 2015/11/9.
 */
public class ProgressDialog_GrayBG extends Dialog {
    private static ProgressDialog_GrayBG progressDialog;
    ImageView img_progress;
    Animation animation;
    TextView textView;
    private Context mContext;

    public ProgressDialog_GrayBG(Context context) {
        super(context, R.style.Theme_dialog_no_bg);
        this.mContext = context;
        initView();
    }

    public ProgressDialog_GrayBG(Context context, int themeResId) {
        super(context, R.style.Theme_dialog_no_bg);
        initView();
    }


    public void initView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress_graybg, null);
        setContentView(contentView);
        img_progress = (ImageView) contentView.findViewById(R.id.img_progress);
        textView = (TextView) contentView.findViewById(R.id.tv_msg);
        animation = AnimationUtils.loadAnimation(mContext, R.anim.rote);

        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);


        Window window = getWindow();
//        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.verticalMargin = 200;
//        lp.width = DensityUtil.getWidth(mContext) / 3;
//        lp.height = lp.width;
//        lp.alpha = 0xffffff;
//        window.setAttributes(lp);
    }

    public void show(String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
        img_progress.setAnimation(animation);
        img_progress.startAnimation(animation);
        setCanceledOnTouchOutside(false);
//        setCancelable(false);
        super.show();
    }

    /**
     * dialog正在显示时 设置Text
     */
    public void setShowingText(String text) {
        if (!isShowing()) {
            show(text);
        } else {
            if (!TextUtils.isEmpty(text)) {
                textView.setText(text);
            }
        }
    }

    public void dissMiss() {
        try {
            if (mContext instanceof Activity) {
                Activity activity = (Activity) mContext;
                if (activity != null && !activity.isFinishing()) {
                    animation.cancel();
                    img_progress.clearAnimation();
                    super.dismiss();
                }
            } else {
                animation.cancel();
                img_progress.clearAnimation();
                super.dismiss();
            }

        } catch (Exception e) {

        }

    }


}
