package com.teang.base;

import android.os.Bundle;
import android.view.InflateException;

import androidx.annotation.Nullable;

import com.teang.R;
import com.teang.global.AppManager;
import com.teang.util.ToastUtil;
import com.teang.util.UiUtil;
import com.teang.view.activity.MainActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

public abstract class BaseActivity extends RxAppCompatActivity {
    public BaseActivity context;
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        AppManager.getInstance(this).addActivity(this);
        EventBus.getDefault().register(this);
        try {
            int layoutResID = initView();
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) {
                throw e;
            }
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppManager.getInstance(context).setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
    }

    public void startActivity(Class activityClass) {
        AppManager.getInstance(context).startActivity(activityClass);
    }

    @Optional
    @OnClick(R.id.ivBack)
    public void back() {
        killMySelf();
    }

    protected void killMySelf() {
        AppManager.getInstance(this).finishActivity(this);
    }

    /**
     * 判断是否是主页面
     *
     * @return
     */
    private boolean isLoginOut() {
        return getClass().getSimpleName().equals(MainActivity.class.getSimpleName());
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (isLoginOut()) {
            //连续按2次返回键退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast(context, UiUtil.getStringById(R.string.press_exit));
                exitTime = System.currentTimeMillis();
            } else {
                AppManager.getInstance(context).appExit();
            }
        } else {
            super.onBackPressed();
        }
    }

    protected abstract int initView();

    protected abstract void initData(Bundle savedInstanceState);
}
