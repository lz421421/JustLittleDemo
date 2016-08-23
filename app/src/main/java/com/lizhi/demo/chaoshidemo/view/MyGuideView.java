package com.lizhi.demo.chaoshidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lizhi.demo.chaoshidemo.bean.GuideBean;
import com.lizhi.demo.utils.DensityUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/22.
 */
public class MyGuideView extends LinearLayout {

    public MyGuideView(Context context) {
        this(context,null);
    }

    public MyGuideView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public  void createView(GuideBean guideBean){

    }

}
