package com.lizhi.demo.tab_viewpager.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHeaderView;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.BaseViewPgerAdapter;
import com.lizhi.demo.baseadapter.MyBaseAdapter;
import com.lizhi.demo.chaoshidemo.view.MyViewPager;
import com.lizhi.demo.tab_viewpager.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.dragtoplayout.DragTopLayout;

/**
 * Created by Administrator on 2016/4/20.
 */
public class TabViewPagerActivity extends BaseActivity {
    ViewPager vp_content;
//    TabLayout tabLayout;
//    MyViewPager vp_header;
//    List<String> datas;


    MaterialViewPager materialViewPager;
//    DragTopLayout dragTopLayout;

    MaterialViewPagerHeaderView mvhv_pager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tab_viewpager);
        vp_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        vp_content = materialViewPager.getViewPager();
//        vp_header = (MyViewPager) findViewById(R.id.vp_header);
        vp_content.setAdapter(new MyViewPagerFragmentAdapter(getSupportFragmentManager()));


//        tabLayout.setupWithViewPager(vp_content);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        initHeaderViewPager();

    }


    public void initHeaderViewPager() {
//        datas = new ArrayList<>();
//        datas.add("1");
//        datas.add("2");
//        datas.add("3");
//        datas.add("4");
//        vp_header.setAdapter(new BaseViewPgerAdapter<String>(datas, R.layout.item_home_ad) {
//            @Override
//            public void getView(View view, String item, int position) {
//
//            }
//        });
    }

    public class MyViewPagerFragmentAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"a", "b", "c", "d"};

        public MyViewPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TabFragment.getInstance(tabTitles[position]);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
