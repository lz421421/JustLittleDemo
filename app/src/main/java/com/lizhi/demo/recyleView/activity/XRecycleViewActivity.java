package com.lizhi.demo.recyleView.activity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.XRecycleView.XRecycleView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 39157 on 2016/11/23.
 */
@SuppressWarnings("unchecked")
public class XRecycleViewActivity extends BaseActivity {

    public int j = 0;
    XRecycleView xRecycleView;
    ABCAdapter abcAdapter;

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
        abcAdapter = new ABCAdapter(R.layout.item_recycleview);
        List<String> mDatas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mDatas.add("你猜这是第几条Item" + i);
        }
        abcAdapter.setData(mDatas);
//        xRecycleView.setEmputyView(LayoutInflater.from(this).inflate(R.layout.layout_recycleview_emputy, null));
        xRecycleView.setAdapter(abcAdapter);
        xRecycleView.setOnXRecycleListener(new XRecycleView.OnXRecycleListener() {
            @Override
            public void onFlash() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> addData = new ArrayList<String>();
                        for (int i = 0; i < 2; i++) {
                            addData.add("这是第" + j + "次新增加的" + i);
                        }
                        abcAdapter.notifyItemRangeInserted(0, addData);
                        xRecycleView.completeFlashOrLoad();
                        j++;
                        if (j == 5) {
                            abcAdapter.notifyItemChange(2, "12333");
                        }
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> loadData = new ArrayList<String>();
                        for (int i = 0; i < 3; i++) {
                            loadData.add("这是上拉加载更多的" + i);
                        }
                        abcAdapter.getData().addAll(loadData);
                        abcAdapter.notifyDataSetChanged();
                        xRecycleView.completeFlashOrLoad();

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
                abcAdapter.notifyItemRangeRemove(2, 4);
            }
        });
    }

    public void initHeader(MyRecycleViewHolder holder, View headerView, int position) {
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

    public class ABCAdapter extends XRecycleView.XRecycleViewAdapter<String> {

        public ABCAdapter(int layoutId) {
            super(layoutId);
        }

        @Override
        public void setHeaderData(MyRecycleViewHolder holder, View headerView, int position) {
            initHeader(holder, headerView, position);
        }

        @Override
        public void setViewData(MyRecycleViewHolder holder, String item, int position) {
            holder.setText(R.id.tv_content, item);
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.log("---------hHAHAH---》");
                }
            });
        }
    }
}
