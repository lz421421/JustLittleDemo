package com.lizhi.demo.scrollview_header_stop;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.scrollview_header_stop.view.MyScrollView;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/1/3.
 */
public class ScrollHeaderActivity extends AppCompatActivity implements MyScrollView.OnScrollListener {
    MyScrollView myScrollView;
    LinearLayout ll_buy;//按钮的布局
    TextView et_search;


    public LinearLayout headerView;

    LinearLayout ll_title;
    int title_height;
    ImageView img_header;
    int img_height;
    RelativeLayout rl_title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_header);
        myScrollView = (MyScrollView) findViewById(R.id.scrollView);
        ll_buy = (LinearLayout) findViewById(R.id.buy);
        headerView = (LinearLayout) findViewById(R.id.buy_header);
        myScrollView.setOnScrollListener(this);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        et_search = (TextView) findViewById(R.id.et_search);
        img_header = (ImageView) findViewById(R.id.img_header);
        //当布局的状态或者控件的可见性发生改变回调的接口
        findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                title_height = ll_title.getHeight();
                img_height = img_header.getHeight();
                //这一步很重要，使得上面的购买布局和下面的购买布局重合
                onScrollY(myScrollView.getScrollY(), MyScrollView.TYPE_normalScroll);
//                myScrollView.initHeaderHeight(ScrollHeaderActivity.this);
            }
        });
    }

    @Override
    public void onScrollY(int scrollY, int type) {
        if (type == MyScrollView.TYPE_normalScroll) {
            changeTitle(scrollY);
            stopHeader(scrollY);
        } else if (type == MyScrollView.TYPE_falshFlag) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    myScrollView.reflashComplete();
                }
            }, 1000);
        } else if (type == MyScrollView.TYPE_hide) {
            rl_title.setAlpha(0);
        } else if (type == MyScrollView.TYPE_visiable) {
            rl_title.setAlpha(1);
        }
    }

    public void changeTitle(int scrollY) {
        float alpha = 1f / img_height;
        ll_title.setAlpha(scrollY * alpha);

        float textalpha = 0.5f / img_height;
        et_search.setAlpha(0.5f + scrollY * textalpha);
    }


    public void stopHeader(int scrollY) {
        int mBuyLayout2ParentTop = 0;
        final int ll_top = ll_buy.getTop();
        if (scrollY >= ll_top - title_height) {
            //表示已经滑动到顶部
            mBuyLayout2ParentTop = scrollY + title_height;
        } else {
            //没有滑动到顶部
            mBuyLayout2ParentTop = ll_top;
        }
        headerView.layout(0, mBuyLayout2ParentTop, headerView.getWidth(), mBuyLayout2ParentTop + headerView.getHeight());
    }

}
