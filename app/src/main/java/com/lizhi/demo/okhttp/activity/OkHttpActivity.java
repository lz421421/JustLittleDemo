package com.lizhi.demo.okhttp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.baseadapter.MyBaseAdapter;
import com.lizhi.demo.baseadapter.MyViewHolder;
import com.lizhi.demo.chaoshidemo.utils.AnimationUtils;
import com.lizhi.demo.okhttp.OkHttpManager;
import com.lizhi.demo.okhttp.callback.SimpleImpOnHttpCallBack;
import com.lizhi.demo.okhttp.request.BaseRequest;
import com.lizhi.demo.okhttp.request.RequestMaker;
import com.lizhi.demo.okhttp.response.LoginResponse;
import com.lizhi.demo.utils.CrcUtil;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.ImageViewUtils;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.ProgressDialog_GrayBG;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/3/22.
 */
public class OkHttpActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    ListView listView;
    RelativeLayout rl_title;
    int screenHeight;
    ImageView imageView;
    TextView tv_title;
    ProgressDialog_GrayBG dialog;
    public static String imgUrl = "http://a.hiphotos.baidu.com/image/pic/item/08f790529822720eac324afa79cb0a46f21fab36.jpg";

    int listViewPaddingTop;//listView 到顶部的距离
    int listViewToTop;//listView到顶部的距离
    int img_height;//图片的高度
    int img_width;//图片的宽度
    int curHeight;//动的时候 listView 到上面的距离 临时存储的  计算listView滑动的距离

    @Override
    public void setContentView() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_okhttp);

        dialog = new ProgressDialog_GrayBG(this);

        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        tv_title = (TextView) findViewById(R.id.tv_title);

        //透明状态栏
        screenHeight = DensityUtil.getWidth(this);

        listView = (ListView) findViewById(R.id.listView);
        imageView = (ImageView) findViewById(R.id.img_header);

        listViewPaddingTop = curHeight = listViewToTop = img_height = /*lp.height =*/ (int) (DensityUtil.getHeight(this) / 2.3);
        listView.setPadding(0, listViewPaddingTop, 0, 0);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("--哈哈->" + i);
        }
        listView.setAdapter(new MyBaseAdapter<String>(this, datas, R.layout.item_normal) {
            @Override
            public void convert(MyViewHolder helper, String item, int position) {
                helper.setText(R.id.tv_left, item);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                changeTitle(getScrollY());
            }
        });
        findViewById(R.id.btn_login).setOnClickListener(this);
        okHttpGet(imgUrl);
        listView.setOnTouchListener(this);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.goods_sample);
        imageView.setImageBitmap(ImageViewUtils.scaleImageView(bmp, DensityUtil.getWidth(OkHttpActivity.this)));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                dialog.show("正在登录...");
                String psw = CrcUtil.getSHA256("123123");
                String userName = "lxj123";
                BaseRequest loginRequest = RequestMaker.getInstance().getLoginRequest(userName, psw);
                getNetWorkHttp(loginRequest, new SimpleImpOnHttpCallBack<LoginResponse>(dialog) {
                    @Override
                    public void onSuccess(LoginResponse loginResponse, String jsonStr, int code) {
                        dialog.dissMiss();
                        showToast("登录成功");
                    }
                });
                break;
        }
    }

    private float scaleWidth = 1;
    private float scaleHeight = 1;

    public void okHttpGet(final String imgUrl) {
        dialog.show("正在获取图片...");
        BaseRequest baseRequest = RequestMaker.getInstance().getDownImgDataRequest(imgUrl);
        getNetWorkDownLoad(baseRequest, new SimpleImpOnHttpCallBack<byte[]>(dialog) {
            @Override
            public void onSuccess(byte[] bytes, String jsonStr, int code) {
                dialog.dismiss();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(ImageViewUtils.scaleImageView(bitmap, DensityUtil.getWidth(OkHttpActivity.this)));

                /****************/

               /* Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                int bmpWidth = bmp.getWidth();
                int bmpHeight = bmp.getHeight();
    *//* 设定图片放大的比例 *//*
                double scale = DensityUtil.getWidth(OkHttpActivity.this) * 1.0f / bmpWidth;
                LogUtil.log("-------scale----->" + scale);
    *//* 计算这次要放大的比例 *//*
                scaleWidth = (float) (scaleWidth * scale);
                scaleHeight = (float) (scaleHeight * scale);

    *//* 产生reSize后的Bitmap对象 *//*
                Matrix matrix = new Matrix();
                matrix.setScale(scaleWidth, scaleHeight);
                Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
                imageView.setImageBitmap(resizeBmp);*/

            }
        });
    }


    float downY = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float mDownY = event.getRawY();
                float my = mDownY - downY;
                downY = mDownY;
                if (imageView.getScrollY() == 0 && my > 0 && Math.abs(getScrollY()) >= listViewToTop) {
                    int nowPadding = listView.getListPaddingTop();
                    nowPadding += my;
                    listView.setPadding(0, nowPadding, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
//                AnimationUtils.closeView(imageView, imageView.getHeight(), img_height, 300);
//                AnimationUtils.closeViewWidth(imageView, imageView.getWidth(), img_width, 300);
                int upPadding = listView.getListPaddingTop();
                AnimationUtils.closePadding(listView, upPadding, listViewPaddingTop, 300);
                break;
        }

        return false;
    }

    public int getScrollY() {
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }


    public void changeTitle(int scrollY) {
        if (scrollY < 0) {
            int height = listViewToTop - DensityUtil.dip2px(this, 70);
            float alpha = 1f / height;

            float a = 1 - (Math.abs(scrollY) - DensityUtil.dip2px(this, 70)) * alpha;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                rl_title.setAlpha(a);
            }


            if (a < 1 && Math.abs(scrollY) != curHeight && a > 0) {
                int mY = curHeight - Math.abs(scrollY);
                curHeight = Math.abs(scrollY);
//                LogUtil.log("-------mY------>" + mY);
//                LogUtil.log("-------------->" + imageView.getScrollY());
                if (imageView.getScrollY() < 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        imageView.setScrollY(0);
                    }
                } else {
                    //防止抖动
                    if (imageView.getScrollY() == 0 && mY < 0) {
                        mY = 0;
                    }
                    imageView.scrollBy(0, mY / 4);
                }
            }
            if (Math.abs(scrollY) == listViewToTop) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    imageView.setScrollY(0);
                }
            }
        }

    }


}
