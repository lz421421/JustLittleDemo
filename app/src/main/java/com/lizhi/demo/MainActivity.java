package com.lizhi.demo;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.lizhi.demo.chaoshidemo.HomeFragment;
import com.lizhi.demo.coordinatorlayout_behavior.MyTextView;
import com.lizhi.demo.fresco.activity.FrescoActivity;
import com.lizhi.demo.okhttp.activity.OkHttpActivity;
import com.lizhi.demo.personal.PersonalActivity;
import com.lizhi.demo.recyleView.activity.RecycleViewActivity;
import com.lizhi.demo.recyleView.activity.XRecycleViewActivity;
import com.lizhi.demo.rxjava.RxJavaActivity;
import com.lizhi.demo.tantan.activity.TanTanMainActivity;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.RectProgressView;

public class MainActivity extends BaseActivity {
    MyTextView first;

    RectProgressView rectProgressView;

    @Override
    public void setContentView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_fragment, null);
        setContentView(contentView);
//        MainActivityHelper.initMainActivity(this, contentView);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();

        ft.add(R.id.fl_content, homeFragment);
        ft.commit();
        findViewById(R.id.tv_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.tv_心情).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("心情");
            }
        });
        rectProgressView = (RectProgressView) findViewById(R.id.rpv_progress);
        rectProgressView.setMaxLenth(100);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (upData <= 100) {
                    upData += 5;
                    try {

                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rectProgressView.upDataHeight(upData);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (upData == 100) {
                        break;
                    }
                }
            }
        }).start();
    }

    int upData = 0;

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


     /*   Intent localIntent = new Intent(this, TanTanMainActivity.class);
        ActivityOptionsCompat localActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, findViewById(R.id.img_open), TanTanMainActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(this, localIntent, localActivityOptionsCompat.toBundle());
            return;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            localIllegalArgumentException.printStackTrace();
            startActivity(localIntent);
        }*/

//        startActivity(ObservActivity.class);
//        startActivity(RxJavaActivity.class);


//切换Activity动画
  /*      Intent localIntent = new Intent(this, FrescoActivity.class);
        ActivityOptionsCompat localActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, findViewById(R.id.img_open), FrescoActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(this, localIntent, localActivityOptionsCompat.toBundle());
            return;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            localIllegalArgumentException.printStackTrace();
            startActivity(localIntent);
        }*/


//        startActivity(RecycleViewActivity.class);
        startActivity(XRecycleViewActivity.class);


    }

    public void startActivity(Class mClass) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        startActivity(intent);
    }
}
