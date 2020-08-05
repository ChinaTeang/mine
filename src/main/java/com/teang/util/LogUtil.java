package com.teang.util;

import android.util.Log;

import com.teang.BuildConfig;

public class LogUtil {
    public static void e(String tag, String message) {
        if (BuildConfig.LOG) {
            Log.e(tag, message);
        }
    }
}
