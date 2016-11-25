package com.lizhi.demo.view.XRecycleView;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lizhi.demo.R;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by 39157 on 2016/11/23.
 */
@SuppressWarnings("unchecked")
public class XRecycleView extends RecyclerView {
    private final String TAG = "XRecycleView";

    private static List<View> headerView;
    LinearLayoutManager mLayoutManager;
    private static final float DRAG_RATE = 2.5f;
    private static final float DRAG_RATE_FOOTER = 2.5f;
    static XRecycleViewHeaderLayout headerFlashView;
    static XRecycleViewFooterLayout footerView;
    private float mLastY = -1; //记录的Y坐标

    public XRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public static int getHeaderViewSize() {
        return headerView == null ? 0 : headerView.size();
    }

    public List<View> getHeaderViews() {
        return headerView;
    }

    public <T extends View> void addHeader(T view) {
        if (view == null) {
            throw new IllegalArgumentException("HeaderView 空");
        } else if (view.getParent() != null) {
            throw new IllegalArgumentException("HeaderView 的只能有一个爸爸");
        }
        if (headerView == null) {
            headerView = new ArrayList<>();
        }
        headerView.add(view);
    }

    @Override
    protected void onDetachedFromWindow() {
        headerView = null;
        super.onDetachedFromWindow();
    }

    public void setXAdapter(XRecycleView.XRecycleViewAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof XRecycleViewAdapter) {
            setXAdapter((XRecycleView.XRecycleViewAdapter) adapter);
        } else {
            throw new IllegalArgumentException("adapter 必须继承 XRecycleViewAdapter");
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLastY == -1) {
            mLastY = event.getRawY();
        }
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getRawY();
                float deltaY = y - mLastY;
                mLastY = y;
                if (headerFlashView.getState() != XRecycleViewHeaderLayout.State.FLASHING) {
                    headerFlashView.setProgress((int) deltaY);
                }
                //往下拉 增加高度 屏蔽事件 ==0 >=0  ==1 >=0
                if (isTopDrageDown(deltaY) || isTopDrageDown_(deltaY)) {
                    headerFlashView.setHeightAdd((int) (deltaY / DRAG_RATE));
                    return false;
                }
                //往上滑动 减少高度  屏蔽事件  ==1 <=0
                if (isTopDrageUp(deltaY)) {
                    if (headerFlashView.getNowHeight() > headerFlashView.getOriginalHeigt() && headerFlashView.getState() != XRecycleViewHeaderLayout.State.FLASHING) {
                        headerFlashView.setHeightAdd((int) (deltaY * 1.5 / DRAG_RATE));
                        return false;
                    } /*else {
                        return super.onTouchEvent(event);
                    }*/
                }
                if (footerView != null) {
                    if (isBottomDrageUp(deltaY)) {
                        footerView.setHeightAdd((int) (-deltaY / DRAG_RATE_FOOTER));
                    }
                    if (footerView.getState() != XRecycleViewFooterLayout.State.FLASHING) {
                        footerView.setProgress((int) -deltaY);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                headerFlashView.closeTo(onXRecycleListener);
                if (footerView != null) {
                    footerView.closeTo(onXRecycleListener);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public boolean isBottomDrageUp(float deltaY) {
        int lastCompletelyVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
        if (deltaY < 0 && (lastCompletelyVisibleItemPosition == getAdapter().getItemCount() - 1 || lastCompletelyVisibleItemPosition == getAdapter().getItemCount() - 2)) {
            return true;
        }
        return false;
    }

    public void completeFlashOrLoad() {
        if (headerFlashView.getState() == XRecycleViewHeaderLayout.State.FLASHING) {
            headerFlashView.complete();
        }

        if (footerView != null && footerView.getState() == XRecycleViewFooterLayout.State.FLASHING) {
            footerView.complete();
        }
    }

    /**
     * 是否是到达顶部 并且往下拉
     */
    public boolean isTopDrageDown(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition == 0 && deltaY >= 0) {
            //刷新布局全部展示出来下拉
            return true;
        }
        return false;
    }


    /**
     * 出现刷新布局往下拉 此时刷新布局 有一部分在屏幕外
     */
    public boolean isTopDrageDown_(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition == 1 && deltaY >= 0) {
            //刷新布局全部展示出来下拉
            return true;
        }
        return false;
    }

    /**
     * 往下拉 刷新布局 全部展示
     *
     * @param deltaY
     * @return
     */
    public boolean isTopDrageUp_(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition == 0 && deltaY <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 出现刷新布局 往上滑动 此时有一部分刷新布局已经跑到屏幕外编
     *
     * @param deltaY
     * @return
     */
    public boolean isTopDrageUp(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if ((firstCompletelyVisibleItemPosition == 1 || firstCompletelyVisibleItemPosition == 0) && deltaY <= 0) {
            return true;
        }
        return false;
    }


    public void setOnXRecycleListener(OnXRecycleListener onXRecycleListener) {
        this.onXRecycleListener = onXRecycleListener;
    }

    OnXRecycleListener onXRecycleListener;

    public interface OnXRecycleListener {
        public void onFlash();

        public void onLoadMore();
    }


    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof LinearLayoutManager) {
            if (layout instanceof GridLayoutManager) {
                final GridLayoutManager mGridLayoutManager = (GridLayoutManager) layout;
                mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int spanSize = ((XRecycleViewAdapter) getAdapter()).getItemColumnSpan(position);
                        if (spanSize == -1) {
                            return mGridLayoutManager.getSpanCount();
                        } else {
                            return spanSize;
                        }
                    }
                });
                super.setLayoutManager(mGridLayoutManager);
                this.mLayoutManager = mGridLayoutManager;
            } else {
                super.setLayoutManager(layout);
                this.mLayoutManager = (LinearLayoutManager) layout;
            }
        } else {
            throw new IllegalArgumentException("在下抱歉，目前只支持LinearLayoutManager和GridLlayoutManager");
        }
    }

    public static abstract class XRecycleViewAdapter<T extends RecyclerView.ViewHolder, M> extends RecyclerView.Adapter<T> {

        private final static int headerFlash = 1000;
        private final static int headerType = 1001;
        private final static int normal = 1002;
        private final static int footLoadMore = 1003;
        private int headerPosition = 0;

        public class XRecycleViewExtralHolder extends RecyclerView.ViewHolder {

            public XRecycleViewExtralHolder(View itemView) {
                super(itemView);
            }

        }

        final private int getItemColumnSpan(int position) {
            switch (getItemViewType(position)) {
                case headerFlash:
                case footLoadMore:
                case headerType:
                    return -1;
                default:
                    return 1;

            }
        }

        private List<M> mDatas;

        public void setmDatas(List<M> mDatas) {
            this.mDatas = mDatas;
        }

        public List<M> getmDatas() {
            return mDatas;
        }

        @Override
        public T onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case headerFlash:
                    headerFlashView = new XRecycleViewHeaderLayout(parent.getContext());
                    parent.addView(headerFlashView);
                    ViewGroup.LayoutParams headerFlashView_lp = headerFlashView.getLayoutParams();
                    headerFlashView_lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    headerFlashView.setLayoutParams(headerFlashView_lp);
                    return (T) new XRecycleViewExtralHolder(headerFlashView);
                case headerType:
                    View header_View = headerView.get(headerPosition++);
                    parent.addView(header_View);

                    ViewGroup.LayoutParams header_View_lp = header_View.getLayoutParams();
                    header_View_lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    header_View.setLayoutParams(header_View_lp);
                    return (T) new XRecycleViewExtralHolder(header_View);
                case footLoadMore:
                    footerView = new XRecycleViewFooterLayout(parent.getContext());
                    parent.addView(footerView);
                    ViewGroup.LayoutParams footerView_lp = footerView.getLayoutParams();
                    footerView_lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    footerView.setLayoutParams(footerView_lp);
                    return (T) new XRecycleViewExtralHolder(footerView);
                default:
                    return createHolder(parent, viewType);
            }
        }

        @Override
        final public void onBindViewHolder(T holder, int position) {
            switch (getItemViewType(position)) {
                case headerFlash:
                    break;
                case headerType:
                    setHeaderData(headerView.get(position - 1), position - 1);
                    break;
                case footLoadMore:
                    break;
                default:
                    bindHolder(holder, position - headerView.size() - 1);
                    break;
            }

        }

        public abstract void setHeaderData(View headerView, int position);

        public abstract T createHolder(ViewGroup parent, int viewType);

        public void bindHolder(T holder, int position) {
            setViewData(holder, mDatas.get(position), position);
        }

        public abstract void setViewData(T t, M m, int position);


        @Override
        final public int getItemViewType(int position) {
            if (position == 0) {
                return headerFlash;
            } else if (position == getItemCount() - 1) {
                return footLoadMore;
            }
            if (getHeaderViewSize() > 0 && position > 0 && position <= getHeaderViewSize()) {
                return headerType;
            }
            return getViewType(position - getHeaderViewSize() - 1);
        }

        public int getViewType(int position) {
            return 0;
        }

        public int getCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        @Override
        final public int getItemCount() {
            return getCount() + getHeaderViewSize() + 2;
        }

    }


}
