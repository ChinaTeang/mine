package com.teang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.util.UiUtil;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected int initView() {
        UiUtil.setFullScreen(this);
        return R.layout.activity_splash;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
        UiUtil.getAndroidScreenProperty(context);
        Glide.with(this)
                .load(R.mipmap.splash)
                .centerCrop()
                .into(imageView);
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(MainActivity.class);
                killMySelf();
            }
        }.start();
    }


}
