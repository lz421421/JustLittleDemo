package com.lizhi.demo.intent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lizhi.demo.R;
import com.lizhi.demo.intent.utils.Utils;

/**
 * Created by Administrator on 2016/1/25.
 */
public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        Utils.initTextView(this, "第三个");
    }


    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.button1:
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                intent.putExtra("flag", "111111");
                intent.setClass(this, IntentActivity.class);
                intent.setAction(Long.toString(System.currentTimeMillis()));
                startActivity(intent);
                break;
            case R.id.button2:
                intent.setClass(this, ThirdActivity.class);
                startActivity(intent);
                break;
        }
    }
}
