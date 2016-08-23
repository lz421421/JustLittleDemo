package com.lizhi.demo.chaoshidemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lizhi.demo.MainActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.chaoshidemo.bean.HomeGHSBean;
import com.lizhi.demo.chaoshidemo.bean.ServiceStationBean;
import com.lizhi.demo.chaoshidemo.utils.AnimationUtils;
import com.lizhi.demo.chaoshidemo.view.CustomListView;
import com.lizhi.demo.chaoshidemo.view.HomeFenLeiLayout;
import com.lizhi.demo.chaoshidemo.view.HomeGlodGHSView;
import com.lizhi.demo.chaoshidemo.view.HomePinPaiView;
import com.lizhi.demo.chaoshidemo.view.HomeView;
import com.lizhi.demo.chaoshidemo.view.MyViewPager;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, HomeView.OnHomeFlashListener {
    MainActivity mainActivity;
    HomeFragmentHelper helper;
    HomeView homeView;
    /**
     * 是否是游客模式
     */
    public boolean isVisitor = true;
    /**
     * 服务站对象
     */
    public ServiceStationBean serviceStationBean;
    LinearLayout ll_title_service;
    public static final int ll_title_service_Height = 48;//单位DP
    TextView tv_serviceStation;

    LinearLayout ll_content;//内容布局
    int screenHeight;//屏幕高度

    MyViewPager bannerPager;//banner
    HomeFenLeiLayout homeFenLeiLayout;//一级分类
    HomeGlodGHSView homeGlodGHSView;//金牌供货商
    CustomListView adListView;//广告的listview
    HomePinPaiView homePinPaiView;//掌合好品牌


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_home, null);
        homeView = (HomeView) view.findViewById(R.id.homeView);
        homeView.setOnHomeFlashListener(this);
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        ll_title_service = (LinearLayout) view.findViewById(R.id.ll_title_service);
        ll_title_service.setOnClickListener(this);
        tv_serviceStation = (TextView) view.findViewById(R.id.tv_serviceStation);
        screenHeight = DensityUtil.getHeight(mainActivity);
        openServiceTitle();
        initServiceStation();
        initViewData();
        return view;
    }

    public void initViewData() {
        helper = new HomeFragmentHelper(mainActivity, ll_content);
        bannerPager = helper.initBanner((int) (screenHeight / 3.5), 1);//banner
        helper.initGirdGuide(screenHeight / 6, 2);//四个tab
        homeFenLeiLayout = helper.initFenLeiLayout(screenHeight / 6, 7, 3);//一级分类
        helper.initTableThree(screenHeight / 4, 4);//三个引导tab 订货会 团购 满赠
        homeGlodGHSView = helper.initHomeGoldGHS(screenHeight / 5, 8, 5);//金牌供货商
        adListView = helper.initAd(screenHeight / 5, 3, 6);//三个广告业
        homePinPaiView = helper.initPinPai(screenHeight / 5, 12, 7);//掌合好品牌
    }


    public void update() {
//        ll_content.removeViewAt(1);
//        ll_content.removeViewAt(1);
//        ll_content.removeViewAt(1);
//        ll_content.removeViewAt(1);
//        ll_content.removeViewAt(1);
//        ll_content.removeViewAt(1);
//        ll_content.removeViewAt(1);
//        initViewData();
        homeView.scrollToTop();
    }


    public void setVisitor(boolean visitor) {
        isVisitor = visitor;
        openServiceTitle();
    }

    /**
     * 打开选择服务站的title
     */
    public void openServiceTitle() {
        if (ll_title_service != null && isVisitor && ll_title_service.getHeight() == 0) {
            AnimationUtils.openView(ll_title_service, 0, DensityUtil.dip2px(mainActivity, ll_title_service_Height), 300);
        } else if (!isVisitor) {
            AnimationUtils.closeView(ll_title_service, ll_title_service.getHeight(), 0, 300);
        }

    }

    /**
     * 设置服务站对象
     *
     * @param serviceStationBean
     */
    public void setServiceStationBean(ServiceStationBean serviceStationBean) {
        this.serviceStationBean = serviceStationBean;
        initServiceStation();
    }

    public void initServiceStation() {
        if (serviceStationBean != null && tv_serviceStation != null) {
            tv_serviceStation.setText(serviceStationBean.serviceName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_service:
                update();
                break;
        }
    }

    @Override
    public void onFlash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                homeView.setFlashComplete();
            }
        }, 1000);
    }
}
