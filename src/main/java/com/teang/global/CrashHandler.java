package com.teang.global;

import android.os.Environment;
import android.util.Log;

import com.teang.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";

    private static CrashHandler INSTANCE = new CrashHandler();
    /**
     * 系统默认的UncaughtException处理类
     **/
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * 用来存储设备信息和异常信息
     **/
    private Map<String, String> mInfos = new HashMap<String, String>();

    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");


    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    public void init() {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * @描述: 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LogUtil.e(this.getClass().toString(), e == null ? "" : e.toString());
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * @return true:如果处理了该异常信息;否则返回false.
     * @throws
     * @描述:自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     */
    private boolean handleException(Throwable ex) {
        if (null == ex) {
            return false;
        }
        // 使用Toast来显示异信息
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(QfAdTvApp.getApp(), "程序开了个小差，即将退出.", Toast.LENGTH_LONG);
//                Looper.loop();
//            }
//        }.start();
        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }


    /**
     * @throws
     * @描述:保存错误信息到文件中 目录/sdcard/qfangadtv/crash/
     * @返回类型 void 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = mFormatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                /*String path = Environment.getExternalStorageDirectory().getPath();
                File dir = new File(path, "/Android/data/com.teang/crash/");*/
                File dir = new File(GlobalVariable.CrashPath);
                if (!dir.exists()) {
                    //经测试，只有在官方包（Android/data/com.teang）下，并且不多层创建，才能成功
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(GlobalVariable.CrashPath + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}