package com.lizhi.demo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

public class DrawableRadioButton
        extends RadioButton
        implements CompoundButton.OnCheckedChangeListener {
    private Drawable mSelectedDrawableLeft;
    private Drawable mSelectedDrawableRight;

    public DrawableRadioButton(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        this.mSelectedDrawableLeft = getCompoundDrawables()[0];
        if (this.mSelectedDrawableLeft != null) {
            this.mSelectedDrawableLeft.setBounds(0, 0, this.mSelectedDrawableLeft.getIntrinsicWidth(), this.mSelectedDrawableLeft.getIntrinsicHeight());
        }
        this.mSelectedDrawableRight = getCompoundDrawables()[2];
        if (this.mSelectedDrawableRight != null) {
            this.mSelectedDrawableRight.setBounds(0, 0, this.mSelectedDrawableRight.getIntrinsicWidth(), this.mSelectedDrawableRight.getIntrinsicHeight());
        }
        setSelectedIconVisible(isChecked());
        setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
        setSelectedIconVisible(paramBoolean);
    }

    public void setSelectedIconVisible(boolean paramBoolean) {
        Drawable localDrawable1;
        Drawable localDrawable2;
        localDrawable1 = this.mSelectedDrawableLeft;
        localDrawable2 = this.mSelectedDrawableRight;
        if (paramBoolean) {
            setCompoundDrawables(localDrawable1, null, localDrawable2, null);
        } else {
            if (this.mSelectedDrawableLeft != null) {
                setCompoundDrawables(localDrawable1, null, null, null);
                return;
            }
            if (this.mSelectedDrawableRight != null) {
                setCompoundDrawables(null, null, localDrawable2, null);
            }
        }
    }
}
