package com.lizhi.demo.recyleView.activity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.MyRecycleViewHolder;
import com.lizhi.demo.recyleView.BaseRecycleViewHolder;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.LetterSortView;
import com.lizhi.demo.view.RoundTextView;
import com.lizhi.demo.view.XRecycleView.XRecycleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * Created by 39157 on 2016/11/23.
 */
@SuppressWarnings("unchecked")
public class XRecycleViewActivity extends BaseActivity {

    public int j = 0;
    XRecycleView xRecycleView;
    ABCAdapter abcAdapter;

    LetterSortView letterSortView;
    RoundTextView rtv_middle;

    @Override
    public void setContentView() {
        setContentView(R.layout.activirt_xrecycleview);
        xRecycleView = (XRecycleView) findViewById(R.id.xrcv_xrlv);
        initRecycleView();
    }

    public void initRecycleView() {
        xRecycleView.setItemAnimator(new DefaultItemAnimator());

//        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
//        xRecycleView.setLayoutManager(gridLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecycleView.setLayoutManager(linearLayoutManager);


        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_recycleheader, null, false);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_coordinator, null, false);
        View view3 = LayoutInflater.from(this).inflate(R.layout.layout_scroll_header, null, false);
        View view4 = LayoutInflater.from(this).inflate(R.layout.activity_tantanhome, null, false);
//        xRecycleView.addHeader(view1);
//        xRecycleView.addHeader(view2);
//        xRecycleView.addHeader(view3);
//        xRecycleView.addHeader(view4);
//        xRecycleView.setFlashEnable(false);
        abcAdapter = new ABCAdapter(R.layout.item_recycle_grid);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("--->" + j);
        }
        xRecycleView.setAdapter(abcAdapter);
        abcAdapter.setData(datas);
        xRecycleView.setOnXRecycleListener(new XRecycleView.OnXRecycleListener() {
            @Override
            public void onFlash() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRecycleView.completeFlashOrLoad();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRecycleView.setFlashEnable(true);
                        xRecycleView.setLoadMoreEnable(false);
                    }
                }, 1500);
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                abcAdapter.notifyItemRemove(2);
//                abcAdapter.notifyItemInserted(1, "1212");
//                abcAdapter.notifyItemRangeRemove(2, 4);
            }
        });

        rtv_middle = (RoundTextView) findViewById(R.id.rtv_middle);
        letterSortView = (LetterSortView) findViewById(R.id.letterSortView);
        letterSortView.setOnLetterTouchListener(new LetterSortView.OnLetterTouchListener() {
            @Override
            public void onTouch(String letter) {
                rtv_middle.setVisibility(View.VISIBLE);
                rtv_middle.setText(letter);
            }
        });
    }

    public class ABCAdapter extends XRecycleView.XRecycleViewAdapter<String> {
        public ABCAdapter(int layoutId) {
            super(layoutId);
        }

        @Override
        public void setHeaderData(BaseRecycleViewHolder holder, View headerView, int position) {
            initHeader(holder, headerView, position);
        }

        @Override
        public void setViewData(final BaseRecycleViewHolder holder, String item, int position) {
        }
    }


    public void initHeader(BaseRecycleViewHolder holder, View headerView, int position) {
        LogUtil.log("-----headerView----->" + headerView + "--position-->" + position);
        if (position == 0) {
            Uri uri = Uri.parse("http://s1.dwstatic.com/group1/M00/B7/F7/40ede23b328757cea2cd14e0720b3275.gif");
            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable anim) {
//                        LogUtil.log("--------onFinalImageSet-------" + anim);
                    if (anim != null) {
                        // 其他控制逻辑
                        anim.start();
                    }
                }
            };
            DraweeController draweeController =//设置GIF
                    Fresco.newDraweeControllerBuilder()
                            .setUri(uri).setControllerListener(controllerListener)
                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                            .build();
            SimpleDraweeView img_header = (SimpleDraweeView) headerView.findViewById(R.id.img_header);
            img_header.setController(draweeController);
            LogUtil.log("-----------setHeaderData------->" + img_header);
        }
    }
}
