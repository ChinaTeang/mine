package com.teang.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.global.CrashHandler;
import com.teang.net.RequestApi;
import com.teang.net.bean.Test;
import com.teang.net.utils.MyObserver;
import com.teang.util.LogUtil;
import com.teang.util.PermissionUtil;
import com.teang.util.ToastUtil;
import com.teang.util.UiUtil;
import com.teang.view.activity.map.MapActivity;
import com.teang.view.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<String> list = new ArrayList<>();
    private boolean hasPermission;

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.GONE);
        title.setText(R.string.home);
        setRecycler();
        setRefresh();
        hasPermission = PermissionUtil.checkWritePermission(context);
        if (hasPermission) {
            CrashHandler.getInstance().init();
        } else {
            getWritePermission();
        }

    }

    private void setRefresh() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setRefreshHeader(new MaterialHeader(context));
        refreshLayout.setRefreshFooter(new ClassicsFooter(context));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e(TAG, "onRefresh");
                refreshLayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e(TAG, "onLoadMore");
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

    private void setRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MainAdapter(list, new MainAdapter.ItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                startActivity(position);
            }
        }));
        list.add(UiUtil.getStringById(R.string.map));
        list.add("getDemo");
        list.add(UiUtil.getStringById(R.string.camera));
        list.add(UiUtil.getStringById(R.string.speech_text));
        list.add(UiUtil.getStringById(R.string.verify_seek));
        list.add(UiUtil.getStringById(R.string.scroll_bottom));
    }

    /**
     * @param position 0 地图
     */
    private void startActivity(int position) {
        switch (position) {
            case 0://地图
                startActivity(MapActivity.class);
                //UiUtil.jumpMINIPermission(context);
                break;
            case 1://网络请求
                //getNetData();
                UiUtil.jumpAppSetting(context);
                break;
            case 2://相机
                startActivity(CameraActivity.class);
                break;
            case 3://语音播报
                startActivity(SpeechTextActivity.class);
                break;
            case 4://滑动验证
                startActivity(VerifySeekActivity.class);
                break;
            case 5://底部上划
                startActivity(ScrollBottomActivity.class);
                break;
        }
    }

    /**
     * 获取读写权限
     */
    private void getWritePermission() {
        PermissionUtil.getWritePermission(context, new PermissionUtil.PermissionState() {
            @Override
            public void granted(int status) {
                if (status == 0) {
                    hasPermission = true;
                    CrashHandler.getInstance().init();
                } else if (status == 1) {
                    getWritePermission();
                } else {
                    ToastUtil.showToast(context, "存储权限被拒绝，请从设置中打开。");
                }
            }
        });
    }

    /**
     * 网络请求示例
     */
    private void getNetData() {
        RequestApi.getDemo(this, new MyObserver<Test>(context) {
            @Override
            public void onSuccess(Test result) {
                ToastUtil.showToast(context, result.toString());
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
                ToastUtil.showToast(context, errorMsg);
            }
        });
    }
}