package com.obg.memo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class MemoItemView extends LinearLayout {
    TextView dateTextView;
    TextView contentTextView;
    ImageView importanceImage;

    public MemoItemView(Context context) {
        super(context);
        init(context);
    }

    public MemoItemView(Context context, @Nullable AttributeSet attrs) {
        super(context);
        init(context);
    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.memo_item,this,true);
        contentTextView = (TextView)findViewById(R.id.memo_text);
        dateTextView = (TextView)findViewById(R.id.memo_date);
        importanceImage = (ImageView) findViewById(R.id.memo_importance);
    }
    public void setDateTextView(String date) {
        dateTextView.setText(date);
    }
    public void setContentTextView(String content) {
        contentTextView.setText(content);
    }
    public void setImportance(int resId) {
        importanceImage.setImageResource(resId);
    }
}
