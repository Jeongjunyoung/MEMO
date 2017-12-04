package com.obg.memo;


import android.content.Context;

public class Singleton {
    private static Singleton uniqueInstance;
    private Context mContext;
    private Singleton(){}

    public static Singleton getInstance(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton(context);
        }
        return uniqueInstance;
    }

    private Singleton(Context context) {
        mContext = context;
    }
    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
