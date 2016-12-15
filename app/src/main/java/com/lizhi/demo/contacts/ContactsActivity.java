package com.lizhi.demo.contacts;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.utils.PermissionUtils;

import java.util.List;

public class ContactsActivity extends BaseActivity implements View.OnClickListener {
    List<AddressBookBean> beans;
    private static final int PERMISSION_REQUEST_CONTACT_TONGBU = 201;
    private static final int PERMISSION_REQUEST_CONTACT_huifu = 202;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_contacts);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3:
//                获取通讯录
                if (PermissionUtils.permissionContacts(this, PERMISSION_REQUEST_CONTACT_TONGBU)) {
                    tongBuPhoneBook();
                }
                break;
            case R.id.button4:
//                插入通讯录
                if (PermissionUtils.permissionContacts(this, PERMISSION_REQUEST_CONTACT_huifu)) {
                    recoveryPhone();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionResult = grantResults[0];
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT_TONGBU:
                if (permissionResult == PackageManager.PERMISSION_GRANTED) {
                    tongBuPhoneBook();
                } else {
                }
                break;
            case PERMISSION_REQUEST_CONTACT_huifu:
                if (permissionResult == PackageManager.PERMISSION_GRANTED) {
                    recoveryPhone();
                } else {
                }
                break;

        }
    }

    private void recoveryPhone() {
        int size = beans == null ? 0 : beans.size();
        for (int i = 0; i < size; i++) {
            boolean isInsert = ContactUtil.getInstance().insertContacts(this, beans.get(i));
            if (!isInsert) {
                continue;
            }
        }
    }

    private void tongBuPhoneBook() {
        beans = ContactUtil.getInstance().getContacts(this,null);
        int beansSize = beans == null ? 0 : beans.size();
        for (int i = 0; i < beansSize; i++) {
            LogUtil.log("---获取通讯录-->" + beans.get(i));
        }
    }
}
