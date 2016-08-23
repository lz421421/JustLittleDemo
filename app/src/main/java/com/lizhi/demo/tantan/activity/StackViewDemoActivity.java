package com.lizhi.demo.tantan.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.StackView;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.tantan.adapter.ColorAdapter;

/**
 * Created by Administrator on 2016/8/12.
 */
public class StackViewDemoActivity extends BaseActivity {


    private int[] mColors = {Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.RED, Color.DKGRAY, Color.LTGRAY};
    private int[] images = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
    private StackView stackView;
    /**
     * 上一张按钮
     */
    private Button previousButon;
    /**
     * 下一张按钮
     */
    private Button nextButton;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_stackview);
        initViews();
        addListener();
    }

    private void addListener() {
        previousButon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stackView.showPrevious();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stackView.showNext();
            }
        });
    }

    private void initViews() {
        stackView = (StackView) findViewById(R.id.mStackView);
        ColorAdapter colorAdapter = new ColorAdapter(this, images);
        stackView.setAdapter(colorAdapter);
        previousButon = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);
    }
}
