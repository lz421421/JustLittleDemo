package com.lizhi.demo.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lizhi.demo.R;
import com.lizhi.demo.ipc.aidl.Book;
import com.lizhi.demo.ipc.aidl.IBookManager;
import com.lizhi.demo.ipc.aidl.IOnNewBookArrivedListener;
import com.lizhi.demo.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/12/21.
 */
public class IpcActivity extends AppCompatActivity implements View.OnClickListener {
    Messenger messenger;
    IBookManager iBookManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        findViewById(R.id.bind).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
        findViewById(R.id.bind_aidl).setOnClickListener(this);
    }

    int count = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_aidl:
                Intent aidlIntent = new Intent();
                aidlIntent.setClass(this, AidlSercice.class);
                boolean aidlBind = bindService(aidlIntent, aidlConnection, Context.BIND_AUTO_CREATE);
                LogUtil.log("-------aidlBind------->" + aidlBind);
                break;
            case R.id.bind:
                Intent intent = new Intent();
                intent.setClass(this, IpcService.class);
                boolean bind = bindService(intent, connection, Context.BIND_AUTO_CREATE);
                LogUtil.log("-------bindService------->" + bind);
                break;
            case R.id.send:
                try {
                    iBookManager.addBook(new Book("降龙十八掌", "3"));
                    LogUtil.log("-------bookList----->" + iBookManager.getBookList());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

              /*
                Message msg = new Message();
                msg.arg1 = count;
                count++;
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }*/
                break;

        }

    }

    public IOnNewBookArrivedListener onNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onNetBookArrived(Book book) throws RemoteException {
            LogUtil.log("-------新书到了------>" + book);
        }
    };


    ServiceConnection aidlConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
            List<Book> bookList = null;
            try {
                bookList = iBookManager.getBookList();
                LogUtil.log("---------aidl----->" + bookList.getClass().getCanonicalName());
                LogUtil.log("-------aidl--toString----->" + bookList.toString());
                iBookManager.registerListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.log("-------aidl--onServiceDisconnected----->");

        }
    };

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            LogUtil.log("-----onServiceConnected-----------");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.log("-----onServiceDisconnected-----------");

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (iBookManager !=null &&iBookManager.asBinder().isBinderAlive()){
                iBookManager.unregisterListener(onNewBookArrivedListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(aidlConnection);
//        unbindService(connection);

    }
}
