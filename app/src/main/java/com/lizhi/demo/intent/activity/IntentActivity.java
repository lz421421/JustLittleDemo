package com.lizhi.demo.intent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lizhi.demo.R;
import com.lizhi.demo.intent.utils.Utils;
import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2016/1/25.
 */
public class IntentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        Utils.initTextView(this, "第一个");
        LogUtil.log("--------onCreate--->");
    }

    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.button1:
                intent.setClass(this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                intent.setClass(this, ThirdActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String flag = intent.getStringExtra("flag");
        LogUtil.log("--------flag->" + flag);
        setIntent(intent);
    }
}
