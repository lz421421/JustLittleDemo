package com.lizhi.demo.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;

import com.lizhi.demo.ipc.aidl.Book;
import com.lizhi.demo.ipc.aidl.IBookManager;
import com.lizhi.demo.ipc.aidl.IOnNewBookArrivedListener;
import com.lizhi.demo.utils.LogUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2015/12/30.
 */
public class AidlSercice extends Service {

    //线程安全的List
    CopyOnWriteArrayList<Book> mList = new CopyOnWriteArrayList<>();
    //    CopyOnWriteArrayList<IOnNewBookArrivedListener> listeners = new CopyOnWriteArrayList<>();
    RemoteCallbackList<IOnNewBookArrivedListener> listeners = new RemoteCallbackList<>();
    int count;

    @Override
    public void onCreate() {
        super.onCreate();
        mList.add(new Book("葵花宝典", "1"));
        mList.add(new Book("皮鞋简朴", "2"));
        LogUtil.log("--------onCreate-----");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        onNewBoolAdd(new Book("" + count, "" + count));
                        count++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    Binder mBinder = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void addBook(Book book) throws RemoteException {
            LogUtil.log("---------addBook-------addBook------" + book);
            mList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            LogUtil.log("---------AidlSercice-------getBookList------" + mList);
            return mList;
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
        }
    };


    public void onNewBoolAdd(Book book) {
        final int N = listeners.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = listeners.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.onNetBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        listeners.finishBroadcast();
        mList.add(book);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
