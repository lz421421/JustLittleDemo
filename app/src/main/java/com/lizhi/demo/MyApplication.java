package com.lizhi.demo;

import android.app.Application;
import android.os.Environment;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

/**
 * Created by Administrator on 2016/3/29.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

//    public static String _dirName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

     /*   CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);*/

        Fresco.initialize(this);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

}
