package com.teang.view.activity.map;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapRouteActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.util.LogUtil;
import com.teang.util.PermissionUtil;
import com.teang.util.ToastUtil;
import com.teang.util.UiUtil;
import com.teang.view.adapter.MapAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MapActivity extends BaseActivity implements INaviInfoCallback {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> list = new ArrayList<>();
    private AMapNavi mapNavi;
    private boolean hasPermission = false;

    @Override
    protected int initView() {
        return R.layout.activity_map;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setText(UiUtil.getStringById(R.string.map));
        list.add(UiUtil.getStringById(R.string.location));
        list.add(UiUtil.getStringById(R.string.navigation));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MapAdapter(list, new MapAdapter.ItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                switch (position) {
                    case 0:
                        startActivity(LocationActivity.class);
                        break;
                    case 1:
                        startNavi();
                        //startActivity(NavigationActivity.class);
                        break;
                }
            }
        }));
        hasPermission = PermissionUtil.checkLocationPermission(context);
        if (!hasPermission) {
            getLocationPermission();
        }
    }

    private void startNavi() {
        Poi start = new Poi("法兰原著", new LatLng(34.602021, 113.71229), "");
        Poi end = new Poi("康城棕榈泉", new LatLng(34.730838, 113.686509), "");

//        List<Poi> wayList = new ArrayList();//途径点目前最多支持3个。
//        wayList.add(new Poi("团结湖", new LatLng(39.93413,116.461676), ""));
//        wayList.add(new Poi("呼家楼", new LatLng(39.923484,116.461327), ""));
//        wayList.add(new Poi("华润大厦", new LatLng(39.912914,116.434247), ""));
        AmapNaviParams amapNaviParams = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER);//AmapPageType.NAVI
        AMapCarInfo aMapCarInfo = new AMapCarInfo();

        aMapCarInfo.setCarType("0");//设置车辆类型，0小车，1货车
        amapNaviParams.setCarInfo(aMapCarInfo);
        AmapNaviPage.getInstance().showRouteActivity(context, amapNaviParams, this, AmapRouteActivity.class);
    }

    private void getLocationPermission() {
        PermissionUtil.getLocationPermission(context, new PermissionUtil.PermissionState() {
            @Override
            public void granted(int status) {
                if (status == 0) {
                    hasPermission = true;
                } else if (status == 1) {
                    getLocationPermission();
                } else {
                    ToastUtil.showToast(context, "定位权限被拒绝，请从设置中打开。");
                }
            }
        });
    }


    @Override
    public void onInitNaviFailure() {
        LogUtil.e(TAG, "onInitNaviFailure");
    }

    @Override
    public void onGetNavigationText(String s) {
        LogUtil.e(TAG, "onGetNavigationText------" + s);
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        LogUtil.e(TAG, "onLocationChange");
    }

    @Override
    public void onArriveDestination(boolean b) {
        LogUtil.e(TAG, "onArriveDestination");
    }

    @Override
    public void onStartNavi(int i) {
        LogUtil.e(TAG, "onStartNavi");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        LogUtil.e(TAG, "onCalculateRouteFailure====》" + i);
    }

    @Override
    public void onStopSpeaking() {
        LogUtil.e(TAG, "onStopSpeaking");
    }

    @Override
    public void onReCalculateRoute(int i) {
        LogUtil.e(TAG, "onReCalculateRoute");
    }

    @Override
    public void onExitPage(int i) {
        LogUtil.e(TAG, "onExitPage");
    }

    @Override
    public void onStrategyChanged(int i) {
        LogUtil.e(TAG, "onStrategyChanged");
    }

    @Override
    public View getCustomNaviBottomView() {
        LogUtil.e(TAG, "getCustomNaviBottomView");
        return null;
    }

    @Override
    public View getCustomNaviView() {
        LogUtil.e(TAG, "getCustomNaviView");
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {
        LogUtil.e(TAG, "onArrivedWayPoint");
    }

    @Override
    public void onMapTypeChanged(int i) {
        LogUtil.e(TAG, "onMapTypeChanged");
    }

    @Override
    public View getCustomMiddleView() {
        LogUtil.e(TAG, "getCustomMiddleView");
        return null;
    }

    @Override
    public void onNaviDirectionChanged(int i) {
        LogUtil.e(TAG, "onNaviDirectionChanged");
    }

    @Override
    public void onDayAndNightModeChanged(int i) {
        LogUtil.e(TAG, "onDayAndNightModeChanged");
    }

    @Override
    public void onBroadcastModeChanged(int i) {
        LogUtil.e(TAG, "onBroadcastModeChanged");
    }

    @Override
    public void onScaleAutoChanged(boolean b) {
        LogUtil.e(TAG, "onScaleAutoChanged");
    }

}
