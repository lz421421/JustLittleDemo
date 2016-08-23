package com.lizhi.demo.personal.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/1/8.
 */
public class NormalView extends LinearLayout {
    TextView tv_left;
    TextView tv_right;
    ImageView iv_left;
    ImageView iv_right;

    CharSequence left_text;
    CharSequence right_text;
    int left_img;
    int right_img;

    public NormalView(Context context) {
        this(context, null);
    }

    public NormalView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NormalView);
        left_text = a.getText(R.styleable.NormalView_leftText);
        right_text = a.getText(R.styleable.NormalView_rightText);
        left_img = a.getResourceId(R.styleable.NormalView_leftIcon, 0);
        right_img = a.getResourceId(R.styleable.NormalView_rightIcon, 0);
        a.recycle();

        LogUtil.log("--left_text->"+left_text);
        LogUtil.log("--right_text->"+right_text);
        LogUtil.log("--left_img->"+left_img);
        LogUtil.log("--right_img->"+right_img);
        initView();
        initText();

    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_normal, this);


        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);

        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);



    }

    public void initText(){
        if (TextUtils.isEmpty(left_text)) {
            tv_left.setVisibility(View.GONE);
        } else {
            tv_left.setVisibility(View.VISIBLE);
            tv_left.setText(left_text);
        }

        if (TextUtils.isEmpty(right_text)) {
            tv_right.setVisibility(View.GONE);
        } else {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(right_text);
        }

        if (left_img == 0) {
            iv_left.setVisibility(View.GONE);
        } else {
            iv_left.setVisibility(View.VISIBLE);
            iv_left.setImageResource(left_img);
        }

        if (right_img == 0) {
            iv_right.setVisibility(View.GONE);
        } else {
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setImageResource(right_img);
        }
    }


}
