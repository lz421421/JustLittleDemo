package com.lizhi.demo.threadpool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;

public class ThreadPoolActivity extends BaseActivity {
    TextView tv_text;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_thread_pool);
        tv_text = (TextView) findViewById(R.id.tv_text);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.tab_cloud_normal, "云盘"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_im_normal, "聊天"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_discovery_normal, "发现"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_mine_normal, "我的"))
                .initialise();
        bottomNavigationBar.setBackgroundColor(0xfff0f0f0);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showToast("" + position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }


    public void start(View view) {
        if (BackUpThreadPool.getBackAlbumPool().isPaused()) {
            BackUpThreadPool.getBackAlbumPool().resume();
        } else {
            BackUpThreadPool.getBackAlbumPool().stopAll();
            for (int i = 0; i < 100; i++) {
                final int j = i;
                BackUpThreadPool.getBackAlbumPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_text.setText("" + j);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void pause(View view) {
        BackUpThreadPool.getBackAlbumPool().stopAll();
    }

}
