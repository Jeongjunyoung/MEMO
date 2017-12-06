package com.obg.memo.singleton;

import android.content.Context;


public class SettingSingleton {
    private static SettingSingleton instance;
    private Context mContext;
    private SettingSingleton(){}

    public static SettingSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new SettingSingleton(context);
        }
        return instance;
    }
    private SettingSingleton(Context context) {
        mContext = context;
    }
    public Context getmContext() {
        return mContext;
    }
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
