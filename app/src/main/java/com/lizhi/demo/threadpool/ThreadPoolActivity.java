package com.lizhi.demo.threadpool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import proxy.Business;
import proxy.BusinessProxy;
import proxy.Sell;

public class ThreadPoolActivity extends BaseActivity {
    TextView tv_text;

    @Override
    public void setContentView() {
        setContentView(R.layout.view_acrtextview);
//        tv_text = (TextView) findViewById(R.id.tv_text);
//        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
//
//        bottomNavigationBar
//                .addItem(new BottomNavigationItem(R.mipmap.tab_cloud_normal, "云盘"))
//                .addItem(new BottomNavigationItem(R.mipmap.tab_im_normal, "聊天"))
//                .addItem(new BottomNavigationItem(R.mipmap.tab_discovery_normal, "发现"))
//                .addItem(new BottomNavigationItem(R.mipmap.tab_mine_normal, "我的"))
//                .initialise();
//        bottomNavigationBar.setBackgroundColor(0xfff0f0f0);
//        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
//
//        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(int position) {
//                showToast("" + position);
//            }
//
//            @Override
//            public void onTabUnselected(int position) {
//
//            }
//
//            @Override
//            public void onTabReselected(int position) {
//            }
//        });
//
//        //静态代理
////        Business business = new Business();
////        BusinessProxy businessProxy = new BusinessProxy(business);
////        businessProxy.sell("你猜我想卖什么？");
////        businessProxy.noSell("我什么也不想卖了！！");
//
//        //动态代理
//        final Business business = new Business();
//        Sell sell = (Sell) Proxy.newProxyInstance(business.getClass().getClassLoader(), business.getClass().getInterfaces(), new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println("-----before-----");
//                Object object = method.invoke(business, args);
//                System.out.println("-----end-----");
//                return object;
//            }
//        });
//        sell.sell("你猜我想卖什么？");
//        sell.noSell("我什么也不想卖了！！");
//        business.getClass().getConstructor()
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
