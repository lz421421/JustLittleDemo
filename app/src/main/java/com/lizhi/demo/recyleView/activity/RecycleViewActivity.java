package com.lizhi.demo.recyleView.activity;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.recyleView.OnFlashLoadMoreListener;
import com.lizhi.demo.utils.DensityUtil;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.CircleRotateView;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 39157 on 2016/11/18.
 */
@SuppressWarnings("unchecked")
public class RecycleViewActivity extends BaseActivity implements OnFlashLoadMoreListener {
    //    SwipeRefreshLayout srl_content;
    RecyclerView rv_recycleView;
    int spanCount = 2;
    int flashHeight = 0;
    int lastHeight = 1;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_recycleview);
//        srl_content = (SwipeRefreshLayout) findViewById(R.id.srl_content);
        rv_recycleView = (RecyclerView) findViewById(R.id.rv_recycleView);
//        srl_content.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                LogUtil.log("---正在刷新--->");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        srl_content.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });
        flashHeight = DensityUtil.dip2px(this, 60);
        initRecycleView();
    }

    int lastY;

    public void initRecycleView() {
        onFlashLoadMoreListener = this;
        rv_recycleView.setItemAnimator(new DefaultItemAnimator());
        rv_recycleView.addItemDecoration(new RecyccleItemDecoration());
        final MyAdapter<String> adapter = new MyAdapter<String>() {

            @Override
            public void setViewData(String item, MyViewHolder holder, int position) {
                holder.tv_content.setText(item);
            }

            @Override
            public void setHeaderData(MyViewHolder holder, int position) {
                Uri uri = Uri.parse("http://s1.dwstatic.com/group1/M00/B7/F7/40ede23b328757cea2cd14e0720b3275.gif");
                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable anim) {
//                        LogUtil.log("--------onFinalImageSet-------" + anim);
                        if (anim != null) {
                            // 其他控制逻辑
                            anim.start();
                        }
                    }
                };
                DraweeController draweeController =//设置GIF
                        Fresco.newDraweeControllerBuilder()
                                .setUri(uri).setControllerListener(controllerListener)
                                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                                .build();
                holder.header.setController(draweeController);
            }

        };
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("注意了这是第" + i + "条目");
        }

        adapter.setmDatas(datas);
        rv_recycleView.setAdapter(adapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_recycleView.setLayoutManager(linearLayoutManager);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize = adapter.getItemColumnSpan(position);
                return spanSize;
            }
        });
        rv_recycleView.setLayoutManager(gridLayoutManager);


 /*       final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize = adapter.getItemColumnSpan(position);
                return spanSize;
            }
        });
        rv_recycleView.setLayoutManager(staggeredGridLayoutManager);*/

        rv_recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LogUtil.log("----onScrolled---dx----->" + dx + "--dy->" + dy);
//                LogUtil.log("----onScrolled---getScrollY----->" + recyclerView.getScrollY());
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                LogUtil.log("----onScrollStateChanged-------->" + newState);
            }
        });
        rv_recycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int y = (int) event.getRawY();
                int deltaY = y - lastY;
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int firstCompletelyVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                        int firstLastCompletelyVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                        if (firstCompletelyVisibleItemPosition == 1 && deltaY < 0) {
                            //出现刷新布局的时候上滑
//                            LogUtil.log("--------现刷新布局的时候上滑------->" + deltaY);
                            if (!isFlashing && getFooterHeight(R.id.ll_header_flash) >= lastHeight) {
                                if (crv_header_falsh != null) {
                                    crv_header_falsh.setProgress(deltaY - 3);
                                }
                                setRecycleFooterHeight(deltaY - 2, R.id.ll_header_flash);
                            }

                        } else if (firstCompletelyVisibleItemPosition == 0 && deltaY > 0) {
                            //出现刷新布局的时候下拉 刷新布局的高度全部在屏幕内
//                            LogUtil.log("--------出现刷新布局的时候下拉------>" + firstCompletelyVisibleItemPosition);
                            if (crv_header_falsh != null) {
                                crv_header_falsh.setProgress(deltaY / 2);
                            }
                            setRecycleFooterHeight(deltaY / 2, R.id.ll_header_flash);

                        }
                        if (deltaY < 0 && (firstLastCompletelyVisibleItemPosition == adapter.getItemCount() - 1 || firstLastCompletelyVisibleItemPosition == adapter.getItemCount() - 2)) {
                            //上滑
//                            LogUtil.log("----上滑---->" + deltaY);
                            if (crv_foot_load != null) {
                                crv_foot_load.setProgress(deltaY / 2);
                            }
                            setRecycleFooterHeight((int) -(deltaY / 2), R.id.ll_footer);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isFlashing) {
                            if (getFooterHeight(R.id.ll_header_flash) > flashHeight / 2) {
                                closeTo(getFooterHeight(R.id.ll_header_flash), flashHeight / 2, R.id.ll_header_flash);
                            }
                        } else {
                            if (getFooterHeight(R.id.ll_header_flash) >= flashHeight) {
                                //刷新
                                closeTo(getFooterHeight(R.id.ll_header_flash), flashHeight / 2, R.id.ll_header_flash);
                            } else {
                                closeTo(getFooterHeight(R.id.ll_header_flash), lastHeight, R.id.ll_header_flash);
                            }
                        }

                        if (isLoadMoreing) {
                            if (getFooterHeight(R.id.ll_footer) > flashHeight / 2) {
                                closeTo(getFooterHeight(R.id.ll_footer), flashHeight / 2, R.id.ll_footer);
                            }
                        } else {
                            if (getFooterHeight(R.id.ll_footer) > flashHeight) {
                                closeTo(getFooterHeight(R.id.ll_footer), flashHeight / 2, R.id.ll_footer);
                            } else {
                                closeTo(getFooterHeight(R.id.ll_footer), lastHeight, R.id.ll_footer);
                            }
                        }

                        break;
                }
                lastY = y;
                return false;
            }
        });
    }

    //是否正在刷新
    boolean isFlashing = false;
    boolean isLoadMoreing = false;

    public void closeTo(int from, final int to, final int layoutId) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float values = (float) valueAnimator.getAnimatedValue();
                if (layoutId == R.id.ll_header_flash) {
                    if (values == flashHeight / 2 && to != lastHeight) {
                        if (!isFlashing) {
                            isFlashing = true;
                            flash();
                        }
                    }
                } else {
                    if (values == flashHeight / 2 && to != lastHeight) {
                        if (!isLoadMoreing) {
                            isLoadMoreing = true;
                            if (crv_foot_load != null) {
                                crv_foot_load.isStart(true);
                            }
                            loadMore();
                        }
                    }
                }
                setHeightReally((int) values, layoutId);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    TextView tv_header_flash;
    TextView tv_header_flash_point;
    CircleRotateView crv_header_falsh;
    CircleRotateView crv_foot_load;

    public void flash() {
        tv_header_flash.setText("正在刷新");
        crv_header_falsh.isStart(true);
        tv_header_flash_point.setText(".");
        setText(tv_header_flash_point);
        if (onFlashLoadMoreListener != null) {
            onFlashLoadMoreListener.onFlash();
        }
    }

    public void falshOrLoadMoreComplete() {
        if (isFlashing) {
            closeTo(flashHeight / 2, lastHeight, R.id.ll_header_flash);
            stopText();
            isFlashing = false;
        }
        if (isLoadMoreing) {
            crv_foot_load.isStart(false);
            closeTo(flashHeight / 2, lastHeight, R.id.ll_footer);
            isLoadMoreing = false;
        }
    }

    Handler handler;
    Runnable runnable;

    public void stopText() {
        if (handler != null) {
            crv_header_falsh.isStart(false);
            handler.removeCallbacks(runnable);
        }
    }

    public void setText(final TextView tv_header_flash) {
        final StringBuilder stringBuilder = new StringBuilder(".");
        if (handler == null) {
            handler = new Handler();
        }
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (stringBuilder.length() == 3) {
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                    stringBuilder.append(".");
                    tv_header_flash.setText(stringBuilder.toString());
                    handler.postDelayed(runnable, 500);
                }
            };
        }
        handler.postDelayed(runnable, 500);
    }


    OnFlashLoadMoreListener onFlashLoadMoreListener;

    @Override
    public void onFlash() {
        LogUtil.log("-----onFlash------>");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                falshOrLoadMoreComplete();
            }
        }, 8000);
    }

    @Override
    public void onLoadMore() {
        LogUtil.log("-----onLoadMore------>");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                falshOrLoadMoreComplete();
            }
        }, 8000);
    }


    public void loadMore() {
        if (onFlashLoadMoreListener != null) {
            onFlashLoadMoreListener.onLoadMore();
        }
    }

    View ll_header_flash;
    View ll_footer;
    int footHeight = -1;


    public void setHeightReally(int height, int layoutId) {
        View header_footView = rv_recycleView.findViewById(layoutId);
        if (header_footView != null) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) header_footView.getLayoutParams();
            lp.height = height;
            header_footView.setLayoutParams(lp);
        }

    }

    public int getFooterHeight(int layoutId) {
        View header_footView = rv_recycleView.findViewById(layoutId);
        if (header_footView != null) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) header_footView.getLayoutParams();
            int height = lp.height;
            return height;
        } else {
            return lastHeight;
        }
    }

    public void initHeaderView(View view) {
        tv_header_flash = (TextView) view.findViewById(R.id.tv_header_flash);
        tv_header_flash_point = (TextView) view.findViewById(R.id.tv_header_flash_point);
        crv_header_falsh = (CircleRotateView) view.findViewById(R.id.crv_header_falsh);
    }

    public void initFootView(View view) {
        crv_foot_load = (CircleRotateView) view.findViewById(R.id.crv_foot_load);
    }

    public void setRecycleFooterHeight(int height, int layoutId) {
        View header_footView = rv_recycleView.findViewById(layoutId);
        if (header_footView != null) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) header_footView.getLayoutParams();
            if (footHeight == -1) {
                footHeight = lp.height;
            }
            lp.height += height;
            if (lp.height < 0) {
                lp.height = lastHeight;
            }
            if (layoutId == R.id.ll_header_flash) {
                initHeaderView(header_footView);
                tv_header_flash_point.setText("");
                if (!isFlashing) {
                    if (lp.height >= flashHeight) {
                        tv_header_flash.setText("松开刷新");
                    } else {
                        tv_header_flash.setText("下拉刷新");
                    }
                }
            } else {
                initFootView(header_footView);
            }
            header_footView.setLayoutParams(lp);
        }
    }


    public abstract class MyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public List<T> mDatas;
        public final static int HEADER_FLASH = 10000;
        public final static int HEADER = 10001;
        public final static int FOOT = 1002;

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEADER_FLASH;
            } else if (position == 1) {
                return HEADER;
            } else if (position == getItemCount() - 1) {
                return FOOT;
            }
            return super.getItemViewType(position);
        }

        public void setmDatas(List<T> mDatas) {
            this.mDatas = mDatas;
        }

        public int getItemColumnSpan(int position) {
            switch (getItemViewType(position)) {
                case HEADER:
                case FOOT:
                case HEADER_FLASH:
                    return spanCount;
                default:
                    return 1;

            }
        }

        public T getItem(int position) {
            T t = null;
            if (position > 0) {
                t = mDatas.get(position - 2);
            }
            return t;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case HEADER_FLASH:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header_flash, parent, false);
                    break;
                case HEADER:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleheader, parent, false);
                    break;
                case FOOT:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_footer, parent, false);
                    break;
                default:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
                    break;
            }
            return new MyViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            LogUtil.log("--------onBindViewHolder----->" + position);
            switch (getItemViewType(position)) {
                case HEADER_FLASH:
                    break;
                case HEADER:
                    setHeaderData((MyViewHolder) holder, position);
                    break;
                case FOOT:
                    break;
                default:
                    setViewData(getItem(position), (MyViewHolder) holder, position);
                    break;
            }
        }

        public abstract void setViewData(T t, MyViewHolder holder, int position);

        public abstract void setHeaderData(MyViewHolder holder, int position);

        @Override
        public int getItemCount() {
            return mDatas == null ? 3 : mDatas.size() + 3;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            SimpleDraweeView header;
            TextView tv_content;

            public MyViewHolder(View itemView, int viewType) {
                super(itemView);
                switch (viewType) {
                    case HEADER:
                        header = (SimpleDraweeView) itemView.findViewById(R.id.img_header);
                        break;
                    default:
                        tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                        break;
                }
            }
        }
    }

    public class RecyccleItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            LogUtil.log("----RecyccleItemDecoration---onDraw-------->");

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int itemPositoin = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//            LogUtil.log("----RecyccleItemDecoration---getItemOffsets-------->" + itemPositoin);
        }
    }
}
