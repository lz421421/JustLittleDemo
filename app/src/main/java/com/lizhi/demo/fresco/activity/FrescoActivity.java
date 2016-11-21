package com.lizhi.demo.fresco.activity;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/5/4.
 */
@SuppressWarnings("unchecked")
public class FrescoActivity extends BaseActivity {
    SimpleDraweeView img_icon;
    DraweeController draweeController;
    public static final String TRANSIT_PIC = "picture";


    @Override
    public void setContentView() {
//     http://image.haha.mx/2014/02/02/middle/1115779_c221d1fc47b97bb1605cddc9c8aec0a7_1391347675.gif
        setContentView(R.layout.activity_fresco);
        img_icon = (SimpleDraweeView) findViewById(R.id.img_icon);
        Uri uri = Uri.parse("http://s1.dwstatic.com/group1/M00/B7/F7/40ede23b328757cea2cd14e0720b3275.gif");
//        img_icon.setImageURI(uri);//设置静态图

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable anim) {
                LogUtil.log("--------onFinalImageSet-------" + anim);
                if (anim != null) {
                    // 其他控制逻辑
                    anim.start();
                }
            }
        };

        draweeController =//设置GIF
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri).setControllerListener(controllerListener)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        img_icon.setController(draweeController);

        ImageView img = (ImageView) findViewById(R.id.img);
        img.setColorFilter(Color.parseColor("#77000000"));
//        img.setColorFilter(null);
        ViewCompat.setTransitionName(img, TRANSIT_PIC);
    }


    public void paly(View view) {
        Animatable animatable = draweeController.getAnimatable();//控释是否播放
        if (animatable != null) {
            //判断是否正在运行
            if (animatable.isRunning()) {
                //运行中，停止
                LogUtil.log("--------animatable.isRunning()-------");
                animatable.stop();
            } else {
                LogUtil.log("--------animatable.start()-------");
                //停止了，运行
                animatable.start();
            }
        } else {
            LogUtil.log("--------animatable.start()-------" + animatable);
            //
        }
    }
}
