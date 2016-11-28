package com.lizhi.demo.view.XRecycleView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lizhi.demo.baseadapter.MyRecycleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 上下拉刷新R
 * Created by 39157 on 2016/11/23.
 */
@SuppressWarnings("unchecked")
public class XRecycleView extends RecyclerView {
//    private final String TAG = "XRecycleView";

    private static final float DRAG_RATE = 2.5f;
    private static final float DRAG_RATE_FOOTER = 2.5f;
    OnXRecycleListener onXRecycleListener;
    private List<View> headerView;
    private LinearLayoutManager mLayoutManager;
    private XRecycleViewHeaderLayout headerFlashView;
    private XRecycleViewFooterLayout footerView;
    private float mLastY = -1; //记录的Y坐标
    private View mEmputyView;
    private boolean isFlashEnable = true;
    private boolean isLoadMoreEnable = true;

    public XRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFlashEnable(boolean flashEnable) {
        isFlashEnable = flashEnable;
        if (headerFlashView != null) {
            if (isFlashEnable) {
                headerFlashView.setState(XRecycleViewHeaderLayout.State.NO_CAN_FLASH);
            } else {
                headerFlashView.setState(XRecycleViewHeaderLayout.State.FLASH_ENABLE_FALSE);
            }
        }
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        isLoadMoreEnable = loadMoreEnable;
        if (footerView != null) {
            if (isLoadMoreEnable) {
                footerView.setState(XRecycleViewFooterLayout.State.NO_CAN_FLASH);
            } else {
                footerView.setState(XRecycleViewFooterLayout.State.FLASH_ENABLE_FALSE);
            }
        }
    }

    public View getEmputyView() {
        return mEmputyView;
    }

    public void setEmputyView(View mEmputyView) {
        this.mEmputyView = mEmputyView;
    }

    public XRecycleViewHeaderLayout getHeaderFlashView() {
        if (headerFlashView == null || headerFlashView.getParent() != null) {
            headerFlashView = new XRecycleViewHeaderLayout(getContext());
        }
        if (isFlashEnable) {
            headerFlashView.setState(XRecycleViewHeaderLayout.State.NO_CAN_FLASH);
        } else {
            headerFlashView.setState(XRecycleViewHeaderLayout.State.FLASH_ENABLE_FALSE);
        }
        return headerFlashView;
    }

    public XRecycleViewFooterLayout getFooterView() {
        if (footerView == null || footerView.getParent() != null) {
            footerView = new XRecycleViewFooterLayout(getContext());
        }
        if (isLoadMoreEnable) {
            footerView.setState(XRecycleViewFooterLayout.State.NO_CAN_FLASH);
        } else {
            footerView.setState(XRecycleViewFooterLayout.State.FLASH_ENABLE_FALSE);
        }
        return footerView;
    }

    public int getHeaderViewSize() {
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
        footerView = null;
        headerFlashView = null;
        super.onDetachedFromWindow();
    }

    public void setXAdapter(XRecycleView.XRecycleViewAdapter adapter) {
        adapter.setmXrecycleView(this);
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
        float y = event.getRawY();
        float deltaY = y - mLastY;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = y;
                if (headerFlashView.getState() != XRecycleViewHeaderLayout.State.FLASHING) {
                    headerFlashView.setProgress((int) deltaY);
                }
                //往下拉 增加高度 屏蔽事件 ==0 >=0  ==1 >=0
                if (isTopDragDown(deltaY) || isTopDragDown_(deltaY)) {
                    headerFlashView.setHeightAdd((int) (deltaY / DRAG_RATE));
                    return false;
                }
                //往上滑动 减少高度  屏蔽事件  ==1 <=0
                if (isTopDragUp(deltaY)) {
                    if (headerFlashView.getNowHeight() > headerFlashView.getOriginalHeigt() && headerFlashView.getState() != XRecycleViewHeaderLayout.State.FLASHING) {
                        headerFlashView.setHeightAdd((int) (deltaY * 1.5 / DRAG_RATE));
                        return false;
                    }
                }
                if (footerView != null) {
                    if (isBottomDragUp(deltaY)) {
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

    public boolean isBottomDragUp(float deltaY) {
        int lastCompletelyVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
        return deltaY < 0 && (lastCompletelyVisibleItemPosition == getAdapter().getItemCount() - 1 || lastCompletelyVisibleItemPosition == getAdapter().getItemCount() - 2);
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
    public boolean isTopDragDown(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        return firstCompletelyVisibleItemPosition == 0 && deltaY >= 0;
    }

    /**
     * 出现刷新布局往下拉 此时刷新布局 有一部分在屏幕外
     */
    public boolean isTopDragDown_(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        return firstCompletelyVisibleItemPosition == 1 && deltaY >= 0;
    }

    /**
     * 往下拉 刷新布局 全部展示
     *
     * @param deltaY 滑动距离
     * @return 刷新布局全部展示时候下拉  true
     */
    public boolean isTopDragUp_(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        return firstCompletelyVisibleItemPosition == 0 && deltaY <= 0;
    }

    /**
     * 出现刷新布局 往上滑动 此时有一部分刷新布局已经跑到屏幕外边
     *
     * @param deltaY 滑动距离
     * @return 出现刷新布局 往上滑动 此时有一部分刷新布局已经跑到屏幕外边 true
     */
    public boolean isTopDragUp(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        return (firstCompletelyVisibleItemPosition == 1 || firstCompletelyVisibleItemPosition == 0) && deltaY <= 0;
    }

    public void setOnXRecycleListener(OnXRecycleListener onXRecycleListener) {
        this.onXRecycleListener = onXRecycleListener;
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


    public interface OnXRecycleListener {
        void onFlash();

        void onLoadMore();
    }

    public static abstract class XRecycleViewAdapter<M> extends RecyclerView.Adapter<com.lizhi.demo.baseadapter.MyRecycleViewHolder> {

        private final static int headerFlash = 1000;
        private final static int headerType = 1001;
        private final static int normal = 1002;
        private final static int footLoadMore = 1003;
        private final static int emputy = 1004;
        //        private XRecycleViewHeaderLayout mHeaderFlashView;
//        private XRecycleViewFooterLayout mfooterView;
//        private View mEmputyView;
        int layoutId;
        private int headerPosition = 0;
        private XRecycleView mXrecycleView;
        private List<M> mData;

        public XRecycleViewAdapter(int layoutId) {
            this.layoutId = layoutId;
        }

        /**
         * 指定位置增加数据
         *
         * @param position 这个position是去掉headerSize开始算起的
         * @param m        添加的对象
         */
        final public void notifyItemInserted(int position, M m) {
            if (mData == null) {
                mData = new ArrayList<>();
            }
            mData.add(position, m);
            notifyItemInserted(position + 1 + mXrecycleView.getHeaderViewSize());
            notifyItemChange(position, m);
        }

        /**
         * 指定位置增加 数据
         *
         * @param start    指定位置 这个position是去掉headerSize开始算起的
         * @param addItems 被添加的数据
         */
        final public void notifyItemRangeInserted(int start, List<M> addItems) {
            if (addItems == null || addItems.size() == 0) {
                return;
            }
            if (mData == null) {
                mData = new ArrayList<>();
            }
            if (start > mData.size()) {
                throw new IllegalArgumentException("瞎J8传参数！！start必须＜数据源的size");
            }
            notifyItemRangeInserted(mXrecycleView.getHeaderViewSize() + 1 + start, addItems.size());
            mData.addAll(start, addItems);
        }

        /**
         * 指定为位置修改数据 这个position是去掉headerSize开始算起的
         *
         * @param position 指定item 更新
         * @param m        更新的对象
         */
        final public void notifyItemChange(int position, M m) {
            if (getCount() == 0 || position > getCount()) {
                throw new IllegalArgumentException("瞎J8传参数！！先设置数据吧");
            }
            mData.remove(position);
            mData.add(position, m);
            notifyItemChanged(position + 1 + mXrecycleView.getHeaderViewSize());
        }

        /**
         * 批量移除
         *
         * @param startPosition 这个position是去掉headerSize开始算起的(包括)
         * @param endPosition   不包括
         */
        final public void notifyItemRangeRemove(int startPosition, int endPosition) {
            if (mData == null) {
                throw new IllegalArgumentException("瞎J8删除！！你TM有数据吗");
            }
            mData.subList(startPosition, endPosition).clear();
            notifyItemRangeRemoved(startPosition + 1 + mXrecycleView.getHeaderViewSize(), endPosition + 1 + mXrecycleView.getHeaderViewSize());
        }

        /**
         * 移除指定位置的数据  这个position是去掉headerSize开始算起的
         *
         * @param position 被移除的位置
         */
        final public void notifyItemRemove(int position) {
            if (mData == null) {
                throw new IllegalArgumentException("瞎J8删除！！你TM有数据吗");
            }
            mData.remove(position);
            notifyItemRemoved(position + 1 + mXrecycleView.getHeaderViewSize());
        }

        public void setmXrecycleView(XRecycleView mXrecycleView) {
            this.mXrecycleView = mXrecycleView;
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

        public List<M> getData() {
            if (mData == null) {
                mData = new ArrayList<>();
            }
            return mData;
        }

        public void setData(List<M> mData) {
            this.mData = mData;
        }

        @Override
        public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View converView = null;
            switch (viewType) {
                case headerFlash:
                    converView = mXrecycleView.getHeaderFlashView();
                    break;
                case headerType:
                    if (mXrecycleView.getHeaderViews() != null) {
                        converView = mXrecycleView.getHeaderViews().get(headerPosition++);
                    }
                    break;
                case footLoadMore:
                    converView = mXrecycleView.getFooterView();
                    break;
                case emputy:
                    converView = mXrecycleView.getEmputyView();
                    break;
                default:
                    converView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                    break;
            }
            if (converView != null && (viewType == headerFlash || viewType == headerType || viewType == footLoadMore || viewType == emputy)) {
                parent.addView(converView);
                ViewGroup.LayoutParams converView_lp = converView.getLayoutParams();
                converView_lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                converView.setLayoutParams(converView_lp);
            }
            return new MyRecycleViewHolder(converView, viewType);
        }

        @Override
        final public void onBindViewHolder(MyRecycleViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case headerFlash:
                    break;
                case headerType:
                    if (mXrecycleView.getHeaderViews() != null) {
                        setHeaderData(holder, mXrecycleView.getHeaderViews().get(position - 1), position - 1);
                    }
                    break;
                case footLoadMore:
                    break;
                case emputy:
                    break;
                default:
                    bindHolder(holder, position - mXrecycleView.getHeaderViewSize() - 1);
                    break;
            }

        }

        public void setHeaderData(MyRecycleViewHolder holder, View headerView, int position) {

        }


        public void bindHolder(MyRecycleViewHolder holder, int position) {
            setViewData(holder, mData.get(position), position);
        }

        public abstract void setViewData(MyRecycleViewHolder t, M m, int position);


        @Override
        final public int getItemViewType(int position) {
            if (position == 0) {
                return headerFlash;
            }
            if (mXrecycleView.getHeaderViewSize() > 0 && position > 0 && position <= mXrecycleView.getHeaderViewSize()) {
                return headerType;
            }
            if (getCount() == 0 && position == mXrecycleView.getHeaderViewSize() + 1) {
                return emputy;
            }
            if ((position == getItemCount() - 1)) {
                return footLoadMore;
            }
            return getViewType(position - mXrecycleView.getHeaderViewSize() - 1);
        }

        public int getViewType(int position) {
            return 0;
        }

        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        final public int getItemCount() {
            if (getCount() == 0) {
                if (mXrecycleView.getEmputyView() != null) {
                    return getCount() + mXrecycleView.getHeaderViewSize() + 3;
                }
                return getCount() + mXrecycleView.getHeaderViewSize() + 2;
            } else {
                return getCount() + mXrecycleView.getHeaderViewSize() + 2;
            }
        }
    }

}
