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
public class BaseViewPagerAdapter <T extends  View> extends PagerAdapter {

    List<T> datas;
    @SuppressWarnings({"unchecked", "varargs"})
    public BaseViewPagerAdapter(List<T> datas) {
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item = datas.get(position);
        container.addView(item);
        return item;
    }

    public ImageView setImageViewRec(View view, int imgId, int imageRec) {
        ImageView img = (ImageView) view.findViewById(imgId);
        img.setImageResource(imageRec);
        return img;
    }
}
