package com.lizhi.demo.fresco.activity;

import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;

/**
 * Created by Administrator on 2016/5/4.
 */
public class FrescoActivity extends BaseActivity {
    SimpleDraweeView img_icon ;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_fresco);
        img_icon = (SimpleDraweeView) findViewById(R.id.img_icon);
        Uri uri = Uri.parse("http://a.hiphotos.baidu.com/image/pic/item/08f790529822720eac324afa79cb0a46f21fab36.jpg");
        img_icon.setImageURI(uri);

    }
}
