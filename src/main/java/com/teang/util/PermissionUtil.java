package com.teang.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.teang.BuildConfig;
import com.teang.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class PermissionUtil {
    public static final String TAG = "PermissionUtil";

    private PermissionUtil() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    @SuppressLint("CheckResult")
    private static void getPermission(BaseActivity context, PermissionState state, String... permission) {
        RxPermissions rxPermissions = new RxPermissions(context);
        Observable.timer(10, TimeUnit.MILLISECONDS)
                .compose(rxPermissions.ensureEachCombined(permission))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        LogUtil.e(TAG, permission.toString());
                        if (permission.granted) {
                            state.granted(0);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            state.granted(1);
                        } else {
                            state.granted(2);
                        }
                    }
                }).isDisposed();
    }

    public interface PermissionState {
        void granted(int status);
    }

    /**
     * 定位权限
     */
    public static boolean checkLocationPermission(BaseActivity context) {
        PackageManager pm = context.getPackageManager();
        boolean accessCoarse = PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.ACCESS_COARSE_LOCATION", BuildConfig.APPLICATION_ID);
        boolean accessFine = PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.ACCESS_FINE_LOCATION", BuildConfig.APPLICATION_ID);
        return accessCoarse && accessFine;
    }

    public static void getLocationPermission(BaseActivity context, PermissionState state) {
        getPermission(context, state, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
     * 读写权限
     */
    public static boolean checkWritePermission(BaseActivity context) {
        PackageManager pm = context.getPackageManager();
        boolean accessWrite = PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", BuildConfig.APPLICATION_ID);
        boolean accessRead = PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.READ_EXTERNAL_STORAGE", BuildConfig.APPLICATION_ID);
        return accessWrite && accessRead;
    }

    public static void getWritePermission(BaseActivity context, PermissionState state) {
        getPermission(context, state, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 相机权限
     */
    public static boolean checkCameraPermission(BaseActivity context) {
        PackageManager pm = context.getPackageManager();
        return PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.CAMERA", BuildConfig.APPLICATION_ID);
    }

    public static void getCameraPermission(BaseActivity context, PermissionState state) {
        getPermission(context, state, Manifest.permission.CAMERA);
    }
}
