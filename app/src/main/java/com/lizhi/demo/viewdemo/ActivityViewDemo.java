package com.lizhi.demo.viewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.OrientationSlideMoreRecycleView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityViewDemo extends BaseActivity implements View.OnTouchListener {

    OrientationSlideMoreRecycleView orientationSlideMoreRecycleView;
    ImageView img;
    BitmapRegionDecoder mDecoder;
    Rect mRect;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_view_demo);
        orientationSlideMoreRecycleView = (OrientationSlideMoreRecycleView) findViewById(R.id.orientationSlideMoreRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        orientationSlideMoreRecycleView.setLayoutManager(linearLayoutManager);
        orientationSlideMoreRecycleView.setAdapter(new MyAdapter());

        img = (ImageView) findViewById(R.id.img);
        img.setOnTouchListener(this);
        img.post(new Runnable() {
            @Override
            public void run() {
                int width = img.getWidth();
                int height = img.getHeight();

                LogUtil.log("----------ImageView------->" + width + "---ImageView-->" + height);
                try {
                    InputStream inputStream = getAssets().open("b.jpg");
                    //获取屏幕的宽高
                    BitmapFactory.Options op = new BitmapFactory.Options();
//                    op.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStream, null, op);
                    int bWidth = op.outWidth;
                    int bHeight = op.outHeight;
                    LogUtil.log("---------Options-------->" + bWidth + "---Options-->" + bHeight);

                    //设置显示图片的 中心区域
                    mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);

                    mRect = new Rect(bWidth / 2 - width / 2, bHeight / 2 - height / 2, width + bWidth / 2 - width / 2, height + bHeight / 2 - height / 2);
//                    Rect rect = new Rect(0, 0,bWidth, bHeight);
                    Bitmap bitmap = mDecoder.decodeRegion(mRect, op);
                    img.setImageBitmap(bitmap);

//                    img.setImageResource(R.mipmap.b);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        ArrayList<String> list;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setImageRegion(x, y);
                break;
        }
        return true;
    }


    private void setImageRegion(int left, int top) {
//        BitmapFactory.Options opts = new BitmapFactory.Options();
        final int width = img.getWidth();
        final int height = img.getHeight();

        final int imgWidth = mDecoder.getWidth();
        final int imgHeight = mDecoder.getHeight();

        int right = left + width;
        int bottom = top + height;
        if (right > imgWidth) right = imgWidth;
        if (bottom > imgHeight) bottom = imgHeight;
        if (left < 0) left = 0;
        if (top < 0) top = 0;

        mRect.set(left, top, right, bottom);
        Bitmap bm = mDecoder.decodeRegion(mRect, null);
        img.setImageBitmap(bm);
    }


    public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ActivityViewDemo.this).inflate(R.layout.item_guide, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_item.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_item = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}
