package com.teang.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.util.LogUtil;
import com.teang.util.UiUtil;
import com.yinglan.scrolllayout.ScrollLayout;

import butterknife.BindView;

public class ScrollBottomActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.scroll_down_layout)
    ScrollLayout mScrollLayout;

    @Override
    protected int initView() {
        return R.layout.activity_scroll_bottom;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setText(R.string.scroll_bottom);
        //关闭状态时最上方预留高度
        mScrollLayout.setMinOffset(0);
        //打开状态时内容显示区域的高度
        int height = mScrollLayout.getLayoutParams().height;
        int maxOffset = (int) (UiUtil.getScreenHeight(this) * 0.5);
        mScrollLayout.setMaxOffset(height);
        //最低部退出状态时可看到的高度，0为不可见
        mScrollLayout.setExitOffset(UiUtil.dip2px(this, 100));
        //是否支持下滑退出，支持会有下滑到最底部时的回调
        mScrollLayout.setIsSupportExit(true);
        //是否支持横向滚动
        //mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        //底部
        mScrollLayout.setToExit();
        //打开
        //mScrollLayout.setToOpen();
        //关闭
        //mScrollLayout.setToClosed();
    }

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            LogUtil.e(TAG,"onScrollProgressChanged===>"+currentProgress);
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            LogUtil.e(TAG,"onScrollFinished===>"+currentStatus);
            if (currentStatus == ScrollLayout.Status.OPENED){
                mScrollLayout.setToExit();
            }
        }

        @Override
        public void onChildScroll(int top) {
            LogUtil.e(TAG,"onChildScroll===>"+top);
        }
    };
}
