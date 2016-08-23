package com.lizhi.demo.tab_viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lizhi.demo.BaseFragment;
import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.MyBaseAdapter;
import com.lizhi.demo.baseadapter.MyViewHolder;
import com.lizhi.demo.tab_viewpager.MyListView;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class TabFragment extends BaseFragment {
    //    TextView tv_content;
    MyListView lv_listView;
    String text;
    List<String> datas;
    final int[] location = new int[2];

    public static BaseFragment getInstance(String text) {
        LogUtil.log("----------getInstance------>");
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.log("----------onCreate------>");
        super.onCreate(savedInstanceState);
        text = getArguments().getString("text");
        datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("--->" + i);
        }

    }

    @Override
    public View setContentView(LayoutInflater inflater) {
        LogUtil.log("------setContentView----->");
        View view = inflater.inflate(R.layout.fragment_tab, null);
//        tv_content = (TextView) view.findViewById(R.id.tv_content);
//        tv_content.setText(text);


        lv_listView = (MyListView) view.findViewById(R.id.lv_listView);
        lv_listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
              /*  ll_header_codtion.getLocationOnScreen(location);
                int y = location[1];//头布局 到屏幕上面的距离
                ll_search.getLocationOnScreen(location);
                int ll_search_y = location[1];//固定布局 到屏幕上面的距离
                if (y <= ll_search_y) {
                    ll_search.setVisibility(View.VISIBLE);
                } else {
                    ll_search.setVisibility(View.GONE);
                }
                if (y - ll_search_y <= DensityUtil.dip2px(activity, 48)) {
                    activity.scrollTabView(false);
                } else {
                    activity.scrollTabView(true);
                }*/
            }
        });


      /*  ImageView  emputyHeaderView = new ImageView(mActivity);
        emputyHeaderView.setImageResource(R.drawable.goods_sample);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(mActivity,250));
        emputyHeaderView.setLayoutParams(lp);
        lv_listView.addHeaderView(emputyHeaderView);*/

        lv_listView.setAdapter(new MyBaseAdapter<String>(mActivity, datas, R.layout.item_dem) {
            @Override
            public void convert(MyViewHolder helper, String item, int position) {
                helper.setText(R.id.tv_item, item);
            }
        });
        return view;
    }
}
