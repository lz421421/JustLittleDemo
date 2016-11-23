package com.lizhi.demo.recyleView.activity;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.XRecycleView.XRecycleView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 39157 on 2016/11/23.
 */
@SuppressWarnings("unchecked")
public class XRecycleViewActivity extends BaseActivity {

    XRecycleView xRecycleView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activirt_xrecycleview);
        xRecycleView = (XRecycleView) findViewById(R.id.xrcv_xrlv);
        initRecycleView();
    }

    public void initRecycleView() {
        xRecycleView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecycleView.setLayoutManager(linearLayoutManager);
        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_recycleheader, null, false);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_recycleheader, null, false);
        View view3 = LayoutInflater.from(this).inflate(R.layout.layout_recycleheader, null, false);
        xRecycleView.addHeader(view1);
        xRecycleView.addHeader(view2);
        xRecycleView.addHeader(view3);
        ABCAdapter abcAdapter = new ABCAdapter();
        List<String> mDatas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            mDatas.add("你猜这是第几条Item" + i);
        }
        abcAdapter.setmDatas(mDatas);
        xRecycleView.setAdapter(abcAdapter);
        xRecycleView.setOnXRecycleListener(new XRecycleView.OnXRecycleListener() {
            @Override
            public void onFlash() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRecycleView.completeFlashOrLoad();
                    }
                }, 10000);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    public class ABCAdapter extends XRecycleView.XRecycleViewAdapter<ABCAdapter.MyViewHolder, String> {

        @Override
        public void setHeaderData(View headerView, int position) {
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

        @Override
        public MyViewHolder createHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void setViewData(MyViewHolder holder, String item, int position) {
            holder.textView.setText(item);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.tv_content);
            }
        }
    }
}
