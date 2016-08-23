package com.lizhi.demo.chaoshidemo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.BaseViewPagerAdapter;
import com.lizhi.demo.baseadapter.BaseViewPgerAdapter;
import com.lizhi.demo.baseadapter.MyBaseAdapter;
import com.lizhi.demo.baseadapter.MyViewHolder;
import com.lizhi.demo.chaoshidemo.bean.GuideBean;
import com.lizhi.demo.chaoshidemo.bean.HomeAdBean;
import com.lizhi.demo.chaoshidemo.bean.HomeGHSBean;
import com.lizhi.demo.chaoshidemo.bean.HomePinPaiBean;
import com.lizhi.demo.chaoshidemo.view.CustomGridView;
import com.lizhi.demo.chaoshidemo.view.CustomListView;
import com.lizhi.demo.chaoshidemo.view.HomeFenLeiLayout;
import com.lizhi.demo.chaoshidemo.view.HomeGlodGHSView;
import com.lizhi.demo.chaoshidemo.view.HomePinPaiView;
import com.lizhi.demo.chaoshidemo.view.MyViewPager;
import com.lizhi.demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class HomeFragmentHelper {
    volatile  Context mContext;
    LinearLayout ll_content;
    final String guides[] = new String[]{"订货会", "满赠", "团购", "供货商"};//title
    final int guidesImg[] = new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
    final int guidePage = 4;//每页显示几个


    public HomeFragmentHelper(Context mContext, LinearLayout ll_content) {
        this.mContext = mContext;
        this.ll_content = ll_content;
    }

    /**
     * 初始化 banner
     *
     * @param height
     * @param index
     */
    public MyViewPager initBanner(int height, int index) {
        MyViewPager myViewPager = getViewPager(height, index);
        List<Integer> datas = new ArrayList<>(5);
        datas.add(R.drawable.goods_sample);
        datas.add(R.drawable.ic_launcher);
        datas.add(R.drawable.goods_sample);
        datas.add(R.drawable.ic_launcher);
        datas.add(R.drawable.goods_sample);
        BaseViewPgerAdapter adapter = new BaseViewPgerAdapter<Integer>(datas, R.layout.item_pager) {
            @Override
            public void getView(View view, Integer item, int position) {
                setImageViewRec(view, R.id.img_pager, item);
            }
        };
        myViewPager.setAdapter(adapter);
        myViewPager.setLoop(true);
        myViewPager.setBackgroundColor(Color.argb(0xff, 0x66, 0x66, 0x66));
        return myViewPager;
    }


    /**
     * 初始化四个导航
     *
     * @param height
     * @param index
     */
    @SuppressWarnings({"unchecked", "varargs"})
    public void initGirdGuide(final int height, int index) {
        MyViewPager viewPager = getViewPager(height, index);
        List<GuideBean> beanList = new ArrayList<>();
        int len = guides.length;
        for (int i = 0; i < len; i++) {
            GuideBean bean = new GuideBean(guides[i], guidesImg[i]);
            beanList.add(bean);
        }
        List<CustomGridView> views = getGustomGridView(beanList, height);
        viewPager.setAdapter(new BaseViewPagerAdapter(views));

    }

    /**
     * 首页四个 tab
     *
     * @param dates
     * @param height
     * @return
     */
    public List<CustomGridView> getGustomGridView(List<GuideBean> dates, final int height) {
        final int pagerSize = dates.size() % guidePage == 0 ? dates.size() / guidePage : dates.size() / guidePage + 1;
        LogUtil.log("--pagerSize->" + pagerSize);
        List<CustomGridView> views = new ArrayList<>(pagerSize);
        ViewGroup.LayoutParams lp;
        CustomGridView customGridView;
        for (int i = 0; i < pagerSize; i++) {
            customGridView = new CustomGridView(mContext);
            customGridView.setNumColumns(4);
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            customGridView.setLayoutParams(lp);
            List<GuideBean> gridViewDatas;
            int startIndex = i * 4;//截取的开始位置
            int end = i == pagerSize - 1 ? dates.size() : startIndex + 4;//截取的结束位置
            gridViewDatas = dates.subList(startIndex, end);
            customGridView.setAdapter(new MyBaseAdapter<GuideBean>(mContext, gridViewDatas, R.layout.item_guide) {
                @Override
                public void convert(MyViewHolder helper, GuideBean item,int position) {
                    helper.setText(R.id.tv_title, item.title);
                    helper.setImageResource(R.id.img_icon, item.imgIcon);

                    LinearLayout ll_content = (LinearLayout) helper.getConvertView().findViewById(R.id.ll_content);
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
                    lp.height = height;
                    ll_content.setLayoutParams(lp);
                }
            });
            views.add(customGridView);
        }
        return views;
    }


    public MyViewPager getViewPager(int height, int index) {
        MyViewPager myViewPager = new MyViewPager(mContext);
        ll_content.addView(myViewPager, index);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) myViewPager.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = height;
        myViewPager.setLayoutParams(lp);
        return myViewPager;
    }


    /**
     * 首页一级分类
     *
     * @param childSize   子控件的个数
     * @param childHeight 一个item的高度
     * @param index       在父空间的位置
     */
    public HomeFenLeiLayout initFenLeiLayout(int childHeight, int childSize, int index) {
        HomeFenLeiLayout homeFenLeiLayout = new HomeFenLeiLayout(mContext, childSize, childHeight);
        ll_content.addView(homeFenLeiLayout, index);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) homeFenLeiLayout.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        homeFenLeiLayout.setLayoutParams(lp);
        homeFenLeiLayout.setBackgroundColor(Color.argb(0xff, 0xcc, 0xcc, 0xcc));
        ImageView imageView;
        for (int i = 0; i < childSize; i++) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams img_lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, childHeight);
            imageView.setLayoutParams(img_lp);
            if (i % 2 == 0) {
                imageView.setImageResource(R.drawable.tm_mui_bike);
            } else {
                imageView.setImageResource(R.drawable.goods_sample);
            }
            imageView.setBackgroundResource(R.drawable.imag_bg);
            homeFenLeiLayout.addView(imageView);
        }
        return homeFenLeiLayout;
    }


    /**
     * 添加三个tab引导页
     *
     * @param height
     * @param index
     */
    public void initTableThree(int height, int index) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_three_guide, null);
        ll_content.addView(view, index);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = height;


    }

    /**
     * 金牌供货商
     *
     * @param childHeight
     * @param size
     * @param index
     * @return
     */
    public HomeGlodGHSView initHomeGoldGHS(int childHeight, int size, int index) {
        HomeGlodGHSView homeGlodGHSView = new HomeGlodGHSView(mContext);
        ll_content.addView(homeGlodGHSView, index);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) homeGlodGHSView.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        lp.height = (size % 2 == 0 ? size / 2 : size / 2 + 1) * childHeight;
        homeGlodGHSView.setLayoutParams(lp);
        homeGlodGHSView.setText("金牌供货商");
        List<HomeGHSBean> list = new ArrayList<>(size);
        HomeGHSBean bean = null;
        for (int i = 0; i < size; i++) {
            bean = new HomeGHSBean();
            list.add(bean);
        }
        homeGlodGHSView.setGridViewModle(list, childHeight);
        return homeGlodGHSView;
    }

    /**
     * 添加广告
     *
     * @param childHeight
     * @param size
     * @param index
     */
    public CustomListView initAd(final int childHeight, int size, int index) {
        final CustomListView customListView = new CustomListView(mContext);
        ll_content.addView(customListView, index);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) customListView.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        customListView.setLayoutParams(lp);
        List<HomeAdBean> list = new ArrayList<>(size);
        HomeAdBean bean = null;
        for (int i = 0; i < size; i++) {
            bean = new HomeAdBean();
            list.add(bean);
        }
        customListView.setAdapter(new MyBaseAdapter<HomeAdBean>(mContext, list, R.layout.item_home_ad) {
            @Override
            public void convert(MyViewHolder helper, HomeAdBean item,int position) {
                LinearLayout ll_content = (LinearLayout) helper.getConvertView().findViewById(R.id.ll_content);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
                lp.height = childHeight;
                ll_content.setLayoutParams(lp);
                if (item.img != 0) {
                    helper.setImageResource(R.id.img_icon, item.img);
                } else {
                    helper.setImageResource(R.id.img_icon, R.drawable.auth_bottom_bg);
                }
            }
        });
        return customListView;
    }

    /**
     * 金牌供货商
     *
     * @param childHeight
     * @param size
     * @param index
     * @return
     */
    public HomePinPaiView initPinPai(int childHeight, int size, int index) {
        HomePinPaiView homePinPaiView = new HomePinPaiView(mContext);
        ll_content.addView(homePinPaiView, index);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) homePinPaiView.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        lp.height = (size % 2 == 0 ? size / 2 : size / 2 + 1) * childHeight;
        homePinPaiView.setLayoutParams(lp);
        homePinPaiView.setText("掌合好品牌");
        List<HomePinPaiBean> list = new ArrayList<>(size);
        HomePinPaiBean bean = null;
        for (int i = 0; i < size; i++) {
            bean = new HomePinPaiBean();
            list.add(bean);
        }
        homePinPaiView.setGridViewModle(list, childHeight);
        return homePinPaiView;
    }


}
