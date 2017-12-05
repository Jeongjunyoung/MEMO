package com.obg.memo.singleton;

import android.content.Context;

public class MainSingleton {
    private static MainSingleton uniqueInstance;
    private Context mContext;
    private MainSingleton(){}

    public static MainSingleton getInstance(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new MainSingleton(context);
        }
        return uniqueInstance;
    }

    private MainSingleton(Context context) {
        mContext = context;
    }
    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
