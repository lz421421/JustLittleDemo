package com.lizhi.demo.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.lizhi.demo.R;
import com.lizhi.demo.coordinatorlayout_behavior.MyTextView;

/**
 * Created by Administrator on 2015/12/22.
 */
public class MainActivityHelper {


    public static void initMainActivity(final Context context, View contentView) {
        final MyTextView first = (MyTextView) contentView.findViewById(R.id.first);
        if (first != null) {
            first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("com.111");
//                intent.setAction("com.ipc");
                    intent.addCategory("com.ipc222");
                    intent.setDataAndType(Uri.parse("http://abc:80"), "image/*");
                    context.startActivity(intent);
                }
            });
        }
    }
}
