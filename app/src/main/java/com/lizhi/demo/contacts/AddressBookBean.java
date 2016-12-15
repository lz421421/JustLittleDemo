package com.lizhi.demo.contacts;


import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Email;

import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 39157 on 2016/12/7.
 */

public class AddressBookBean {


    public String id;//(string, optional),
    public String surname;// (string, optional): 姓氏 ,
    public String name;//(string, optional): 名字 ,
    public List<String> mobile = new ArrayList<>();//(string, optional),手机
    public List<String> home_mobile = new ArrayList<>();//(string, optional),手机
    public List<String> work_mobile = new ArrayList<>();//(string, optional),手机
    public List<String> other_mobile = new ArrayList<>();//(string, optional),手机
    public List<String> iPhone_mobile = new ArrayList<>();//(string, optional),手机

    public List<String> home_email = new ArrayList<>();// (string, optional),邮箱
    public List<String> work_email = new ArrayList<>();// (string, optional),邮箱
    public List<String> other_email = new ArrayList<>();// (string, optional),邮箱
    public List<String> iCloud_email = new ArrayList<>();// (string, optional),邮箱
    public String remark;// (string, optional) 备注

    private static final int phone_iPhoneType = 30;
    private static final int email_iPhoneType = 31;
    public int[] phoneTypes = new int[]{Phone.TYPE_MOBILE, Phone.TYPE_HOME, Phone.TYPE_WORK, Phone.TYPE_OTHER, phone_iPhoneType};
    public int[] emailTypes = new int[]{Email.TYPE_HOME, Email.TYPE_WORK, Email.TYPE_OTHER, email_iPhoneType};


    /**
     * 根据 电话类型 获取电话的集合
     *
     * @param type
     * @return
     */
    public List<String> getPhoneListByType(int type) {
        switch (type) {
            case Phone.TYPE_MOBILE:
                return mobile;
            case Phone.TYPE_HOME:
                return home_mobile;
            case Phone.TYPE_WORK:
                return work_mobile;
            case Phone.TYPE_OTHER:
                return other_mobile;
            case phone_iPhoneType:
                return iPhone_mobile;
            default:
                return other_mobile;
        }
    }

    /**
     * 根据类型添加 数据
     *
     * @param type
     * @param phone
     */
    public void addPhone(int type, String phone) {
        getPhoneListByType(type).add(phone);
    }


    public List<String> getEmailListByType(int type) {
        switch (type) {
            case Email.TYPE_HOME:
                return home_email;
            case Email.TYPE_WORK:
                return work_email;
            case Email.TYPE_OTHER:
                return other_email;
            case email_iPhoneType:
                return iCloud_email;
            default:
                return other_email;
        }
    }

    public void addEmail(int type, String email) {
        getEmailListByType(type).add(email);
    }


    @Override
    public String toString() {
        return "AddressBookBean{" +
                "name='" + name + '\'' +
                ", mobile=" + mobile +
                ", home_mobile=" + home_mobile +
                ", work_mobile=" + work_mobile +
                ", other_mobile=" + other_mobile +
                ", iPhone_mobile=" + iPhone_mobile +
                ", home_email=" + home_email +
                ", work_email=" + work_email +
                ", other_email=" + other_email +
                ", iCloud_email=" + iCloud_email +
                ", remark='" + remark + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressBookBean that = (AddressBookBean) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (home_mobile != null ? !home_mobile.equals(that.home_mobile) : that.home_mobile != null)
            return false;
        if (work_mobile != null ? !work_mobile.equals(that.work_mobile) : that.work_mobile != null)
            return false;
        if (other_mobile != null ? !other_mobile.equals(that.other_mobile) : that.other_mobile != null)
            return false;
        if (iPhone_mobile != null ? !iPhone_mobile.equals(that.iPhone_mobile) : that.iPhone_mobile != null)
            return false;
        if (home_email != null ? !home_email.equals(that.home_email) : that.home_email != null)
            return false;
        if (work_email != null ? !work_email.equals(that.work_email) : that.work_email != null)
            return false;
        if (other_email != null ? !other_email.equals(that.other_email) : that.other_email != null)
            return false;
        return iCloud_email != null ? iCloud_email.equals(that.iCloud_email) : that.iCloud_email == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (home_mobile != null ? home_mobile.hashCode() : 0);
        result = 31 * result + (work_mobile != null ? work_mobile.hashCode() : 0);
        result = 31 * result + (other_mobile != null ? other_mobile.hashCode() : 0);
        result = 31 * result + (iPhone_mobile != null ? iPhone_mobile.hashCode() : 0);
        result = 31 * result + (home_email != null ? home_email.hashCode() : 0);
        result = 31 * result + (work_email != null ? work_email.hashCode() : 0);
        result = 31 * result + (other_email != null ? other_email.hashCode() : 0);
        result = 31 * result + (iCloud_email != null ? iCloud_email.hashCode() : 0);
        return result;
    }
}
