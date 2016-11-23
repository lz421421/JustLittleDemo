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
    static XRecycleViewHeaderLayout headerFlashView;
    static XRecycleViewFooterLayout footerView;
    private float mLastY = -1; //记录的Y坐标

//    static View mHeaderFlashView;
//    static View mFooterView;


    public XRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        headerFlashView = new XRecycleViewHeaderLayout(context);
        footerView = new XRecycleViewFooterLayout(context);
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
        LogUtil.log("---addHeader-->" + view);
        if (headerView == null) {
            headerView = new ArrayList<>();
        }
        headerView.add(view);
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
        float y = event.getRawY();
        int action = event.getAction();
        float deltaY = y - mLastY;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTopDrageDown(deltaY)) {
                    headerFlashView.setHeightAdd((int) (deltaY / DRAG_RATE));
                    headerFlashView.setProgress((int) deltaY);
                } else if (isTopDrageUp(deltaY) && headerFlashView.getState() != XRecycleViewHeaderLayout.State.FLASHING) {
                    mLayoutManager.scrollToPositionWithOffset(0, (int) (-deltaY));
                    headerFlashView.setHeightAdd((int) (deltaY));
                    headerFlashView.setProgress((int) deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                headerFlashView.closeTo(onXRecycleListener);
                break;
        }
        mLastY = y;
        return super.onTouchEvent(event);
    }
    public void completeFlashOrLoad() {
        headerFlashView.setState(XRecycleViewHeaderLayout.State.FLASH_COMPLETE);
        headerFlashView.closeTo(onXRecycleListener);
        headerFlashView.start(false);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        LogUtil.log("---------onScrolled----------->" + dy);
    }

    /**
     * 是否是到达顶部 并且往下拉
     */
    public boolean isTopDrageDown(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition == 0 && deltaY > 0) {
            //刷新布局全部展示出来下拉
            return true;
        }
        return false;
    }

    /**
     * 出现刷新布局 往上滑动
     *
     * @param deltaY
     * @return
     */
    public boolean isTopDrageUp(float deltaY) {
        int firstCompletelyVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition == 1 && deltaY < 0) {
            //刷新布局全部展示出来下拉
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
            super.setLayoutManager(layout);
            this.mLayoutManager = (LinearLayoutManager) layout;
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

        private List<M> mDatas;

        public void setmDatas(List<M> mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public T onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case headerFlash:
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
                    bindHolder(holder, position - headerView.size());
                    break;
            }

        }

        public abstract void setHeaderData(View headerView, int position);

        public abstract T createHolder(ViewGroup parent, int viewType);

        public void bindHolder(T holder, int position) {
            setViewData(holder, mDatas.get(position - 1), position);
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
            return getViewType(position);
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
