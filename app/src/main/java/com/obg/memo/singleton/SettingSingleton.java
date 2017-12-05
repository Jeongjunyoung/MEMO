package com.obg.memo.singleton;

import android.content.Context;

/**
 * Created by d1jun on 2017-12-05.
 */

public class SettingSingleton {
    private static SettingSingleton uniqueInstance;
    private Context mContext;
    private SettingSingleton(){}

    public static SettingSingleton getInstance(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new SettingSingleton(context);
        }
        return uniqueInstance;
    }
    public SettingSingleton(Context mContext) {
        this.mContext = mContext;
    }
    public static SettingSingleton getUniqueInstance() {
        return uniqueInstance;
    }
    public static void setUniqueInstance(SettingSingleton uniqueInstance) {
        SettingSingleton.uniqueInstance = uniqueInstance;
    }
    public Context getmContext() {
        return mContext;
    }
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
