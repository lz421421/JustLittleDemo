package com.lizhi.demo.CanvasPath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;

public class CanvasPathActivity extends BaseActivity {
    DuiHaoAnimationView adv_aav;
    DrawBitmap drawBitmap;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_canvas_path);
        drawBitmap = (DrawBitmap) findViewById(R.id.drawBitmap);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBitmap.add();
            }
        });
//        adv_aav = (DuiHaoAnimationView) findViewById(R.id.adv_aav);

    }


}
