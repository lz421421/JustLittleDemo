package com.lizhi.demo.contacts;


import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

import com.lizhi.demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 39157 on 2016/12/3.
 */
public class ContactUtil {

    private static ContactUtil contactUtil = null;
    int raw_id = -1;
    boolean isUploading;
    OnUploadContactListListener onUploadContactListListener;

    // ContactsContract.Contacts.CONTENT_URI= content://com.android.contacts/contacts;
    // ContactsContract.Data.CONTENT_URI = content://com.android.contacts/data;

    //更新联系人
    private List<AddressBookBean> bookBeanList;


    private ContactUtil() {
    }

    public ContactUtil(Context context) {
    }

    public static ContactUtil getInstance() {
        if (contactUtil == null) {
            contactUtil = new ContactUtil();
        }
        return contactUtil;
    }

    public void updataCotact(Context context, long rawContactId) {
        ContentValues values = new ContentValues();
        values.put(Phone.NUMBER, "13800138000");
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        String where = ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(rawContactId), Phone.CONTENT_ITEM_TYPE};
        context.getContentResolver().update(ContactsContract.Data.CONTENT_URI, values, where, selectionArgs);
    }


    public void insetPhone(Context context, AddressBookBean bean, String raw_contactsId) {
        if (bean == null) {
            return;
        }
        int phoneType[] = bean.phoneTypes;
        final int length = phoneType.length;
        for (int i = 0; i < length; i++) {
            int type = phoneType[i];
            if (i == length-1){
                type = Phone.TYPE_OTHER;
            }
            List<String> phones = bean.getPhoneListByType(type);
            if (phones != null) {
                //插入数据
                for (String phone : phones) {
                    ContentValues values = new ContentValues();
                    values.put(Data.RAW_CONTACT_ID, raw_contactsId);
                    values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                    values.put(Phone.TYPE, type);
                    values.put(Phone.NUMBER, phone);
                    context.getContentResolver().insert(Data.CONTENT_URI, values);
                }
            }
        }
    }


    public void insetEmail(Context context, AddressBookBean bean, String raw_contactsId) {
        if (bean == null) {
            return;
        }
        int emailType[] = bean.emailTypes;
        final int length = emailType.length;
        for (int i = 0; i < length; i++) {
            int type = emailType[i];
            if (i == length-1){
                type = Email.TYPE_OTHER;
            }
            List<String> emails = bean.getEmailListByType(type);
            if (emails != null) {
                //插入数据
                for (String email : emails) {
                    ContentValues values = new ContentValues();
                    values.put(Data.RAW_CONTACT_ID, raw_contactsId);
                    values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
                    values.put(Email.TYPE, type);
                    values.put(Email.ADDRESS, email);
                    context.getContentResolver().insert(Data.CONTENT_URI, values);
                }
            }
        }
    }

    public void insetName(Context context, AddressBookBean bookBean, String raw_contactsId) {
        ContentValues values = new ContentValues();
        values.put(Data.RAW_CONTACT_ID, raw_contactsId);
        values.put(StructuredName.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        values.put(StructuredName.DISPLAY_NAME, bookBean.name);
        values.put(StructuredName.FAMILY_NAME, bookBean.surname);
        context.getContentResolver().insert(Data.CONTENT_URI, values);
    }

    public void insetConmpany(Context context, AddressBookBean bookBean, String raw_contactsId) {
        // 插入公司
        ContentValues values = new ContentValues();
        values.put(Data.RAW_CONTACT_ID, raw_contactsId);
        values.put(Organization.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
//        values.put(Organization.COMPANY, bookBean.company);
//        values.put(Organization.OFFICE_LOCATION, bookBean.workUnit);
        context.getContentResolver().insert(Data.CONTENT_URI, values);

    }

    public boolean insertContacts(Context context, AddressBookBean bookBean) {
        //如果云端的 和本机的姓名手机号一样 则视为同一个人
        synchronized (contactUtil) {
            List<AddressBookBean> beans = getContacts(context, bookBean.name);
            if (beans.contains(bookBean)) {
                return false;
            }
            ContentResolver resolver = context.getContentResolver();
            ContentValues values = new ContentValues();
         /* 往raw_contacts 中添加数据，并获取添加的id号*/
            values.put(RawContacts.AGGREGATION_MODE, RawContacts.AGGREGATION_MODE_DISABLED);//防止同姓名的联系人合并
            Uri insertUri = resolver.insert(RawContacts.CONTENT_URI, values);
            long raw_contactsId = ContentUris.parseId(insertUri);
//
            Uri dataUri = Data.CONTENT_URI;
//        //插入姓名
            insetName(context, bookBean, String.valueOf(raw_contactsId));
            insetPhone(context, bookBean, String.valueOf(raw_contactsId));
            insetEmail(context, bookBean, String.valueOf(raw_contactsId));
            insetConmpany(context, bookBean, String.valueOf(raw_contactsId));
            // 插入备注
            values.clear();
            values.put(Data.RAW_CONTACT_ID, raw_contactsId);
            values.put(Note.MIMETYPE, Note.CONTENT_ITEM_TYPE);
            values.put(Note.NOTE, bookBean.remark);
            resolver.insert(dataUri, values);
            return true;
        }
    }


    //查询contact表 查询到 contacts_id
    // ContactsContract.Contacts.CONTENT_URI= content://com.android.contacts/contacts;
    public List<AddressBookBean> getContacts(Context context, String name) {
        bookBeanList = new ArrayList<>();
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ? " + (name == null ? "" : "AND " + ContactsContract.Contacts.DISPLAY_NAME + " = ? ");
        String args[] = name == null ? new String[]{"1"} : new String[]{"1", name};
        Cursor contactsCursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, selection, args, ContactsContract.Contacts._ID + " asc");

        while (contactsCursor.moveToNext()) {
            int idIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts._ID);
            int contacts_id = contactsCursor.getInt(idIndex);
            AddressBookBean bean = new AddressBookBean();
            bookBeanList.add(bean);

            //根据contacts_id 去RawContacts查询
            Cursor raw_contactsCursor = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, RawContacts.CONTACT_ID + " = ?", new String[]{contacts_id + ""}, ContactsContract.RawContacts._ID + " desc");
            while (raw_contactsCursor.moveToNext()) {
                // 可能会有多个awContacts._ID值  但是这都是同一个人
                String rawContactsId = raw_contactsCursor.getString(raw_contactsCursor.getColumnIndex(RawContacts._ID));
                LogUtil.log("-------rawContactsId------->" + rawContactsId);
                //根据rawContactsId 去data表查询
                Cursor dataCursor = context.getContentResolver().query(Data.CONTENT_URI, null, Data.RAW_CONTACT_ID + " = ?", new String[]{rawContactsId}, Data.RAW_CONTACT_ID + " asc");
                while (dataCursor.moveToNext()) {
                    // 从data里获取 详细信息
                    String mimeType = getStringColumnValue(dataCursor, Data.MIMETYPE);
                    switch (mimeType) {
                        case Phone.CONTENT_ITEM_TYPE://手机信息
                            getPhone(context, dataCursor, bean);
                            break;
                        case Email.CONTENT_ITEM_TYPE://邮箱信息
                            getEmail(context, dataCursor, bean);
                            break;
                        case Organization.CONTENT_ITEM_TYPE://组织信息
                            getOrganization(dataCursor, bean);
                            break;
                        case StructuredName.CONTENT_ITEM_TYPE://名字相关
                            getNames(dataCursor, bean);
                            break;
                        case Note.CONTENT_ITEM_TYPE://备注相关 remark
                            getNotes(dataCursor, bean);
                            break;
                        case Event.CONTENT_ITEM_TYPE://时间相关 比如 生日什么的
                            getEvents(context, dataCursor, bean);//
                            break;
                        case Nickname.CONTENT_ITEM_TYPE://昵称
                            getNickNmaes(dataCursor, bean);
                            break;
                        case ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE://昵称
                            getNickNmaes(dataCursor, bean);
                            break;
                    }

                }
                dataCursor.close();
            }
            raw_contactsCursor.close();
        }
        contactsCursor.close();
        return bookBeanList;
    }



    public String getNickNmaes(Cursor dataCursor, AddressBookBean bookBean) {
        int type = getIntColumnValue(dataCursor, Nickname.TYPE);
//        LogUtils.log("-------getNickNmaes------->" + type);
        String nickName = getStringColumnValue(dataCursor, Nickname.NAME);
//        bookBean.nickNames = nickName;
        return nickName;
    }

    /**
     * 取出时间相关
     *
     * @param dataCursor
     * @return <p>TYPE_ANNIVERSARY = 1 周年纪念日;<p/>
     * <p>TYPE_OTHER = 2;<p/>
     * <p>TYPE_BIRTHDAY = 3  生日<p/>
     */
    public void getEvents(Context context, Cursor dataCursor, AddressBookBean bookBean) {
        String data = getStringColumnValue(dataCursor, Event.DATA);//日期
        int type = getIntColumnValue(dataCursor, Event.TYPE);
        int typeString = Event.getTypeResource(type);
        String resString = context.getString(typeString);
//        bookBean.data.put(resString, data);
    }

    public void getNotes(Cursor dataCursor, AddressBookBean bookBean) {
        String note = getStringColumnValue(dataCursor, Note.NOTE);//全部名字
//        bean.notes.add(note);
        bookBean.remark = note;
    }

    /**
     * @param dataCursor
     * @return 依次： 全名  姓
     */
    public void getNames(Cursor dataCursor, AddressBookBean bookBean) {
        String display_name = getStringColumnValue(dataCursor, StructuredName.DISPLAY_NAME);//全部名字
        String suName = getStringColumnValue(dataCursor, StructuredName.FAMILY_NAME);//姓
        bookBean.surname = suName;
        bookBean.name = display_name;
    }

    /**
     * @param dataCursor
     * @return 公司信息 依次：
     * <p>Organization.COMPANY公司名字</p>
     * <p>Organization.TITLE 职务</p>
     * <p>Organization.DEPARTMENT 部门</p>
     * <p>Organization.JOB_DESCRIPTION 工作描述</p>
     * <p>Organization.OFFICE_LOCATION 办公地址</p>
     */
    public void getOrganization(Cursor dataCursor, AddressBookBean bookBean) {
        String company = getStringColumnValue(dataCursor, Organization.COMPANY);//公司
        String zhiWu = getStringColumnValue(dataCursor, Organization.TITLE);//职务
        String DEPARTMENT = getStringColumnValue(dataCursor, Organization.DEPARTMENT);//部门
        String JOB_DESCRIPTION = getStringColumnValue(dataCursor, Organization.JOB_DESCRIPTION);//工作描述
        String OFFICE_LOCATION = getStringColumnValue(dataCursor, Organization.OFFICE_LOCATION);//办公地点
//        bookBean.company = company;
//        bookBean.workUnit = OFFICE_LOCATION;
    }


    /**
     * 获取手机号集合
     *
     * @return KEY 手机号的类型 比如 Home or Work.
     * <p>Phone.TYPE_HOME = 1;</p>
     * <p>Phone.TYPE_MOBILE = 2;</p>
     * <p>Phone.TYPE_WORK = 3;</p>
     * <p>Phone.TYPE_FAX_WORK = 4;</p>
     * <p>Phone.TYPE_FAX_HOME = 5;</p>
     * <p>Phone.TYPE_PAGER = 6;</p>
     * <p>Phone.TYPE_OTHER = 7;</p>
     * <p>Phone.TYPE_CALLBACK = 8;</p>
     * <p>Phone.TYPE_CAR = 9;</p>
     * <p>Phone.TYPE_COMPANY_MAIN = 10;</p>
     * <p>Phone.TYPE_ISDN = 11;</p>
     * <p>Phone.TYPE_MAIN = 12;</p>
     * <p> Phone.TYPE_OTHER_FAX = 13;</p>
     * <p> Phone.TYPE_RADIO = 14;</p>
     * <p>Phone.TYPE_TELEX = 15;</p>
     * <p>Phone.TYPE_TTY_TDD = 16;</p>
     * <p>Phone.TYPE_WORK_MOBILE = 17;</p>
     * <p>Phone.TYPE_WORK_PAGER = 18;</p>
     * <p>Phone.TYPE_ASSISTANT = 19;</p>
     * <p>Phone.TYPE_MMS = 20; </p>
     * value 值 手机号
     */
    public void getPhone(Context context, Cursor dataCursor, AddressBookBean bookBean) {
        int type = getIntColumnValue(dataCursor, Phone.TYPE);
        String phone = getStringColumnValue(dataCursor, Phone.NUMBER);
        CharSequence typeString = Phone.getTypeLabel(context.getResources(), type, "自定义");
        bookBean.addPhone(type, phone);
    }

    /**
     * @param dataCursor
     * @return KEY 邮箱的类型 比如 Home or Work.
     * <p>Email.YPE_HOME = 1;</p>
     * <p>Email.TYPE_WORK = 2;</p>
     * <p>Email.YPE_OTHER = 3;</p>
     * <p>Email.TYPE_MOBILE = 4;</p>
     * <p>
     * value 值 邮箱地址
     */
    public void getEmail(Context context, Cursor dataCursor, AddressBookBean bookBean) {
        int type = getIntColumnValue(dataCursor, Email.TYPE);
        String eamil = getStringColumnValue(dataCursor, Email.ADDRESS);
        CharSequence typeString = Email.getTypeLabel(context.getResources(), type, "自定义");
        bookBean.addEmail(type, eamil);
    }


    /**
     * 获取一行里面 对应的列的值
     *
     * @param dataCursor
     * @param columnName
     * @return
     */
    public String getStringColumnValue(Cursor dataCursor, String columnName) {
        int index = dataCursor.getColumnIndex(columnName);
        return dataCursor.getString(index);
    }

    /**
     * 获取一行里面 对应的列的值
     *
     * @param dataCursor
     * @param columnName
     * @return
     */
    public int getIntColumnValue(Cursor dataCursor, String columnName) {
        int index = dataCursor.getColumnIndex(columnName);
        return dataCursor.getInt(index);
    }

    public boolean isUploading() {
        return isUploading;
    }


    public void getAlldAllContactsThread(final Context context, final Handler threadHandler) {
        raw_id = -1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AddressBookBean> addressBookBeanList = getContacts(context, null);
                Message msg = threadHandler.obtainMessage();
                msg.what = 0;
                msg.obj = addressBookBeanList;
                threadHandler.sendMessage(msg);
            }
        }).start();
    }


    public interface OnUploadContactListListener {
        void onBeforeUpload();

        void onUploading(int progress);

        void onUploadComplete(boolean isOk, int count);
    }


}
