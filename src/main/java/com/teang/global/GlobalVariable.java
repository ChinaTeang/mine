package com.teang.global;

import android.os.Environment;

/**
 * 全局变量
 */
public class GlobalVariable {

    /**
     * 相机照片存储路径
     */
    public static final String CameraPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.teang/photo.jpg";
    /**
     * 异常信息文件路径
     */
    public static final String CrashPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.teang/crash/";
}
