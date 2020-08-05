package com.teang.global;

import android.app.Application;
import android.content.Context;

import com.teang.BuildConfig;
import com.teang.util.SystemTTS;

import cn.jpush.android.api.JPushInterface;

public class MyApp extends Application {
    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //异常信息保存 在首页初始化
        //CrashHandler.getInstance().init();
        //语音播报
        SystemTTS.getInstance();
        //极光
        JPushInterface.setDebugMode(BuildConfig.LOG);
        JPushInterface.init(this);
        //定位服务
        //startService(new Intent(this, LocationService.class));
    }



    public static MyApp getInstance(){
        return app;
    }

    public static Context getAppContext() {
        return getInstance().getApplicationContext();
    }
}
