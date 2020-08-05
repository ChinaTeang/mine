package com.teang.net.utils;


import android.app.ProgressDialog;
import android.content.Context;

import com.teang.util.NetUtil;
import com.teang.util.ToastUtil;

import io.reactivex.disposables.Disposable;

/**
 * Observer加入加载框
 * @param <T>
 */
public abstract class MyObserver<T> extends BaseObserver<T> {
    private boolean mShowDialog;
    private ProgressDialog dialog;
    private Context mContext;
    private Disposable d;

    public MyObserver(Context context, Boolean showDialog) {
        mContext = context;
        mShowDialog = showDialog;
    }

    public MyObserver(Context context) {
        this(context,true);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (!NetUtil.isConnected(mContext)) {
            ToastUtil.showToast(mContext,"未连接网络");
            if (d.isDisposed()) {
                d.dispose();
            }
        } else {
            if (dialog == null && mShowDialog == true) {
                dialog = new ProgressDialog(mContext);
                dialog.setMessage("正在加载中");
                dialog.show();
            }
        }
    }
    @Override
    public void onError(Throwable e) {
        if (d.isDisposed()) {
            d.dispose();
        }
        hidDialog();
        super.onError(e);
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
        hidDialog();
        super.onComplete();
    }


    public void hidDialog() {
        if (dialog != null && mShowDialog == true)
            dialog.dismiss();
        dialog = null;
    }

    /**
     * 取消订阅
     */
    public void cancleRequest(){
        if (d!=null&&d.isDisposed()) {
            d.dispose();
            hidDialog();
        }
    }
}
