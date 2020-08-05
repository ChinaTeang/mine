package com.teang.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.teang.util.SystemTTS;

/**
 * 语音播报服务
 */
public class TTSService extends Service {
    private static String TAG = TTSService.class.getName();
    //private static final long LOOP_TIME = 20; //循环时间
    //private static ScheduledExecutorService mExecutorService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*mExecutorService =Executors.newScheduledThreadPool(2);
        mExecutorService.schedule(mRunnable,LOOP_TIME,TimeUnit.SECONDS);*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String text = "";
        if (intent != null) {
            text = intent.getStringExtra("text");
        }
        SystemTTS.getInstance().playText(text);
        return super.onStartCommand(intent, flags, startId);
    }

    /*private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };*/

    @Override
    public void onDestroy() {
        /*if (mRunnable != null) {
            mRunnable = null;
        }*/
        SystemTTS.getInstance().stopSpeak();
        super.onDestroy();
    }
}
