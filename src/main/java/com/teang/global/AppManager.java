package com.teang.global;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.teang.base.BaseActivity;

import org.simple.eventbus.EventBus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AppManager {
    public List<BaseActivity> mActivityList;
    private BaseActivity mCurrentActivity;
    @SuppressLint("StaticFieldLeak")
    private static AppManager instance;
    private Context context;

    private AppManager(Context context) {
        this.context = context;
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance(Context context) {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager(context);
                }
            }
        }
        return instance;
    }

    public void startActivity(Intent intent) {
        if (getCurrentActivity() == null) {
            //如果没有前台的activity就使用new_task模式启动activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return;
        }
        getCurrentActivity().startActivity(intent);
    }

    public void startActivity(Class activityClass) {
        startActivity(new Intent(context, activityClass));
    }

    /**
     * 获得当前在前台的activity
     */
    private BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(BaseActivity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    public void addActivity(BaseActivity activity) {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        synchronized (AppManager.class) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        }
    }

    /**
     * 返回一个存储所有未销毁的activity的集合
     */
    public List<BaseActivity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }

    /**
     * 删除集合里的指定activity
     */
    public void removeActivity(BaseActivity activity) {
        if (mActivityList == null) {
            return;
        }
        synchronized (AppManager.class) {
            mActivityList.remove(activity);
        }
    }

    /**
     * 关闭指定activity
     */
    public void finishActivity(BaseActivity activity) {
        if (activity != null) {
            activity.finish();
            removeActivity(activity);
        }
    }

    private BaseActivity baseFragmentActivity;

    /**
     * 杀死指定Class的activity
     */
    public void finishActivity(Class<?> activityClass) {
        baseFragmentActivity = null;
        for (BaseActivity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                baseFragmentActivity = activity;
            }
        }
        if (baseFragmentActivity != null) {
            finishActivity(baseFragmentActivity);
        }
    }

    /**
     * 关闭所有activity
     */
    public void killAll() {
        Iterator<BaseActivity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            iterator.next().finish();
            iterator.remove();
        }
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            killAll();
            release();
            if (mActivityList != null) {
                mActivityList = null;
            }
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        EventBus.getDefault().unregister(this);
        mActivityList.clear();
        mActivityList = null;
        mCurrentActivity = null;
    }
}
