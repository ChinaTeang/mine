package com.teang.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.global.GlobalVariable;
import com.teang.util.PermissionUtil;
import com.teang.util.ToastUtil;
import com.teang.view.custom.SelectPicturePopupWindow;

import java.io.File;

import butterknife.BindView;

public class CameraActivity extends BaseActivity implements SelectPicturePopupWindow.OnSelectedListener {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tvAdd)
    TextView tvAdd;
    @BindView(R.id.imageFrame)
    FrameLayout imageFrame;

    private SelectPicturePopupWindow popupWindow;
    private final int CAMERA_REQUEST_CODE = 0;
    private final int PICTURE_REQUEST_CODE = 1;
    private boolean hasPermission = false;

    @Override
    protected int initView() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        imageFrame.setOnClickListener(view -> {
            if (popupWindow == null) {
                popupWindow = new SelectPicturePopupWindow(context, this);
            }
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                popupWindow.show();
            }
        });
    }

    /**
     * @param position 0拍照 1相册 2取消
     */
    @Override
    public void onSelected(int position) {
        switch (position) {
            case 0://拍照
                hasPermission = PermissionUtil.checkCameraPermission(context);
                if (hasPermission) {
                    startCamera();
                } else {
                    getCameraPermission();
                }
                break;
            case 1://相册
                startPicture();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    tvAdd.setVisibility(View.GONE);
                    Glide.with(this).load(GlobalVariable.CameraPath).into(imageView);
                    break;
                case PICTURE_REQUEST_CODE:
                    tvAdd.setVisibility(View.GONE);
                    Glide.with(this).load(data.getData()).into(imageView);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getCameraPermission() {
        PermissionUtil.getCameraPermission(this, new PermissionUtil.PermissionState() {
            @Override
            public void granted(int status) {
                if (status == 0) {
                    hasPermission = true;
                    startCamera();
                } else if (status == 1) {
                    getCameraPermission();
                } else {
                    ToastUtil.showToast(context, "相机权限被拒绝，请从设置中打开。");
                }
            }
        });
    }

    /**
     * 打开相机页面
     */
    private void startCamera() {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(GlobalVariable.CameraPath)));
        startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
    }

    /**
     * 打开相册页面
     */
    private void startPicture() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, PICTURE_REQUEST_CODE);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        return super.onTouchEvent(event);
    }
}
