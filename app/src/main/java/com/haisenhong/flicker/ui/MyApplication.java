package com.haisenhong.flicker.ui;

import android.app.Application;

/**
 * Created by hison7463 on 10/12/16.
 */

public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    public static synchronized MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
