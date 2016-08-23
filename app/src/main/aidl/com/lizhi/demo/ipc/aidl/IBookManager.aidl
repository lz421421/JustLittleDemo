// IBookManager.aidl
package com.lizhi.demo.ipc.aidl;

// Declare any non-default types here with import statements
import com.lizhi.demo.ipc.aidl.Book;
import com.lizhi.demo.ipc.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void addBook(in Book book);
    List<Book> getBookList();

    void registerListener( in IOnNewBookArrivedListener listener);
    void unregisterListener(in IOnNewBookArrivedListener listener);

}
