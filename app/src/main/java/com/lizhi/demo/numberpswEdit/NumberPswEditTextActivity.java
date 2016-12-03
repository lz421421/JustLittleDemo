package com.lizhi.demo.numberpswEdit;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;
import com.lizhi.demo.view.NumberPswEditText;

/**
 * Created by 39157 on 2016/12/3.
 */

public class NumberPswEditTextActivity extends BaseActivity {
    NumberPswEditText net_numberEditText;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_number_psw_edittext);
        net_numberEditText = (NumberPswEditText) findViewById(R.id.net_numberEditText);
        net_numberEditText.setOnInputFinishListener(new NumberPswEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                showToast(password);
            }
        });
    }
}
