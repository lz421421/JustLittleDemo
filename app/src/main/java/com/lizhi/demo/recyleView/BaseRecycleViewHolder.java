package com.lizhi.demo.recyleView;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/27.
 */
public class BaseRecycleViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;
    private int viewType;

    public BaseRecycleViewHolder(View itemView, int viewType) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
        this.viewType = viewType;
    }


    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings({"unchecked", "varargs"})
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseRecycleViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param imgRes
     * @return
     */
    public BaseRecycleViewHolder setImageRes(int viewId, int imgRes) {
        ImageView view = getView(viewId);
        view.setImageResource(imgRes);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }


}
