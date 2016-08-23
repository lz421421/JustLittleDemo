package com.lizhi.demo.ipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/30.
 */
public class Book implements Parcelable {

    public Book(String bookName, String bookId) {
        this.bookName = bookName;
        this.bookId = bookId;
    }

    public String bookName;
    public String bookId;


    protected Book(Parcel in) {
        bookName = in.readString();
        bookId = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(bookId);
    }


    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", bookId='" + bookId + '\'' +
                '}';
    }
}
