package com.lizhi.demo;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.lizhi.demo.chaoshidemo.HomeFragment;
import com.lizhi.demo.coordinatorlayout_behavior.MyTextView;
import com.lizhi.demo.h5.activity.NormalH5Activity;
import com.lizhi.demo.okhttp.activity.OkHttpActivity;
import com.lizhi.demo.simple_demo.ElevationActivity;
import com.lizhi.demo.tab_viewpager.activity.TabViewPagerActivity;
import com.lizhi.demo.tantan.activity.StackViewDemoActivity;
import com.lizhi.demo.tantan.activity.TanTanMainActivity;

public class MainActivity extends BaseActivity {
    MyTextView first;

    @Override
    public void setContentView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_fragment, null);
        setContentView(contentView);
//        MainActivityHelper.initMainActivity(this, contentView);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();

        ft.add(R.id.fl_content, homeFragment);
        ft.commit();
    }


    public void button(View view) {
//        startActivity(IpcActivity.class);
//        startActivity(ScrollHeaderActivity.class);
//        startActivity(SwipeBackActivity.class);
//        startActivity(GifActivity.class);
//        startActivity(PersonalActivity.class);

//        startActivity(TimeTextViewActivity.class);
//        startActivity(OkHttpActivity.class);
//        startActivity(NormalH5Activity.class);
//        startActivity(TabViewPagerActivity.class);

//        startActivity(ElevationActivity.class);
//        startActivity(StackViewDemoActivity.class);
//        startActivity(TanTanMainActivity.class);


        Intent localIntent = new Intent(this, TanTanMainActivity.class);
        ActivityOptionsCompat localActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, findViewById(R.id.img_open), TanTanMainActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(this, localIntent, localActivityOptionsCompat.toBundle());
            return;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            localIllegalArgumentException.printStackTrace();
            startActivity(localIntent);
        }

    }

    public void startActivity(Class mClass) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        startActivity(intent);
    }
}
