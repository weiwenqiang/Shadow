package com.zzz.myemergencyclientnew.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //路由
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }
}
