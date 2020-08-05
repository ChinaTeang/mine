package com.teang.view.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.util.UiUtil;

/**
 * 拍照、相册选择图片
 */
public class SelectPicturePopupWindow extends PopupWindow implements View.OnClickListener {
    Button takePhotoBtn;
    Button pickPictureBtn;
    Button cancelBtn;

    private BaseActivity context;
    private OnSelectedListener listener;
    public static final int CAMERA = 0;
    public static final int PICTURE = 1;
    public static final int CANCEL = 2;
    private PopupWindow popupWindow;
    private View popupView;

    public SelectPicturePopupWindow(BaseActivity context, OnSelectedListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_picture_selector, null);
        takePhotoBtn = popupView.findViewById(R.id.picture_selector_take_photo_btn);
        pickPictureBtn = popupView.findViewById(R.id.picture_selector_pick_picture_btn);
        cancelBtn = popupView.findViewById(R.id.picture_selector_cancel_btn);
        takePhotoBtn.setOnClickListener(this);
        pickPictureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        popupWindow.dismiss();
        switch (view.getId()) {
            case R.id.picture_selector_take_photo_btn:
                listener.onSelected(CAMERA);
                break;
            case R.id.picture_selector_pick_picture_btn:
                listener.onSelected(PICTURE);
                break;
            case R.id.picture_selector_cancel_btn:
                listener.onSelected(CANCEL);
                break;
        }
    }

    public interface OnSelectedListener {
        void onSelected(int position);
    }

    public void show() {
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, UiUtil.dip2px(context, 205));
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true); // 点击其他地方隐藏键盘 popupWindow
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod); // 设置窗口显示的动画效果
        popupWindow.update();
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }
}