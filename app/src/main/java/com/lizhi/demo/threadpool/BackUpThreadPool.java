package com.lizhi.demo.threadpool;

import com.lizhi.demo.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 39157 on 2017/1/13.
 * <p>
 * 备份相册和视频的  线程池
 */

public class BackUpThreadPool extends ThreadPoolExecutor {

    private static Map<String, BackUpThreadPool> maps = new HashMap<>();
    private static final String albumKey = "albumKey";//备份相册的  key
    private static final String videoKey = "videoKey";//备份视频的  key
    private boolean isPaused;//是否暂停
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unPaused = pauseLock.newCondition();

    public BackUpThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

    }

    public static BackUpThreadPool getBackAlbumPool() {
        BackUpThreadPool pool = null;
        if (maps.containsKey(albumKey)) {
            pool = maps.get(albumKey);
            if (pool == null) {
                pool = new BackUpThreadPool(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
                maps.put(albumKey, pool);
            }
        } else {
            pool = new BackUpThreadPool(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
            maps.put(albumKey, pool);
        }
        return pool;
    }

    public static BackUpThreadPool getBackVideoPool() {
        BackUpThreadPool pool = null;
        if (maps.containsKey(videoKey)) {
            pool = maps.get(videoKey);
            if (pool == null) {
                pool = new BackUpThreadPool(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
                maps.put(videoKey, pool);
            }
        } else {
            pool = new BackUpThreadPool(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
            maps.put(videoKey, pool);
        }
        return pool;
    }

    //    开始执行前
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        LogUtil.log("---------beforeExecute--一个线程执行开始了------->");
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused()) unPaused.await();
        } catch (InterruptedException e) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 开始
     */
    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 是否暂停
     *
     * @return
     */
    public boolean isPaused() {
        return isPaused;
    }


    //执行结束
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        LogUtil.log("---------afterExecute--一个线程执行结束了------->");
        super.afterExecute(r, t);
    }

    //线程池执行结束
    @Override
    protected void terminated() {
        LogUtil.log("---------terminated----所有线程执行结束了----->");
        super.terminated();
    }

    public void stopAll() {
        int size = getQueue().size();
        LogUtil.log("---------size----->"+size);
        getQueue().clear();

    }
}
