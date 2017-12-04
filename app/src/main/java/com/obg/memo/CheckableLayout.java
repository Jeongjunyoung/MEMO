package com.obg.memo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Checkable;

public class CheckableLayout extends LinearLayout implements Checkable {
    public CheckableLayout(Context context) {
        super(context);
    }
    public CheckableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        if (checkBox.isChecked() != checked) {
            checkBox.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        return checkBox.isChecked();
    }

    @Override
    public void toggle() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        setChecked(checkBox.isChecked() ? false : true);
    }
}
