package com.lizhi.demo.baseadapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/12/18.
 */
public abstract class BaseViewPgerAdapter<T> extends PagerAdapter {

    List<T> data;
    int layoutId;

    public BaseViewPgerAdapter(List<T> data, int layoutId) {
        this.data = data;
        this.layoutId = layoutId;
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size() == 1 ? 1 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public abstract void getView(View view, T item, int position);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(layoutId, null);
        T item = data.get(position % data.size());
        getView(view, item, position);
        container.addView(view);
        return view;
    }

    public ImageView setImageViewRec(View view, int imgId, int imageRec) {
        ImageView img = (ImageView) view.findViewById(imgId);
        img.setImageResource(imageRec);
        return img;
    }
}
