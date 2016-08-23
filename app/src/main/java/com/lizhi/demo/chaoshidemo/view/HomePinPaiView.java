package com.lizhi.demo.chaoshidemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.MyBaseAdapter;
import com.lizhi.demo.baseadapter.MyViewHolder;
import com.lizhi.demo.chaoshidemo.bean.HomePinPaiBean;

import java.util.List;

/**
 * 首页掌合品牌
 * Created by Administrator on 2015/12/23.
 */
public class HomePinPaiView extends LinearLayout {
    TextView tv_title;
    CustomGridView cg_ghs;
    public Context context;
    public int counlNum;

    public HomePinPaiView(Context context) {
        this(context, null);
    }

    public HomePinPaiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public HomePinPaiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.home_gold_ghs, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        cg_ghs = (CustomGridView) findViewById(R.id.cg_ghs);
    }

    public void setText(String text) {
        tv_title.setText(text);
    }

    public void setGridViewModle(List<HomePinPaiBean> list, final int childHeight) {
        cg_ghs.setAdapter(new MyBaseAdapter<HomePinPaiBean>(context, list, R.layout.item_home_ghs) {
            @Override
            public void convert(MyViewHolder helper, HomePinPaiBean item,int position) {
                helper.setText(R.id.tv_title, getNotNullString(item.title));
                if (item.img == 0) {
                    helper.setImageResource(R.id.img_img, R.drawable.auth_bottom_bg);
                } else {
                    helper.setImageResource(R.id.img_img, item.img);
                }
                LinearLayout ll_content = (LinearLayout) helper.getConvertView().findViewById(R.id.ll_content);
                LayoutParams lp = (LayoutParams) ll_content.getLayoutParams();
                lp.height = childHeight;
                ll_content.setLayoutParams(lp);
            }
        });
    }

    public String getNotNullString(String text) {
        if (text == null) {
            return "";
        } else {
            return text;
        }

    }

}
