package com.lizhi.demo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by 39157 on 2016/12/7.
 */

public class PermissionUtils {


    /**
     * 请求联系人的权限
     *
     * @param activity
     * @param requestCode
     */
    public static boolean permissionContacts(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionRead = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
            int permissionWrite = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS);
            if (permissionRead == PackageManager.PERMISSION_GRANTED && permissionWrite == PackageManager.PERMISSION_GRANTED) {
                //都准许
                return true;
            } else {
                //请求
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, requestCode);
                return false;
            }
        } else {
            return true;
        }

    }

}
