package com.teang.view.activity.map;

import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviPoi;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.teang.R;
import com.teang.base.BaseActivity;
import com.teang.util.LogUtil;

import butterknife.BindView;

public class NavigationActivity extends BaseActivity implements AMapNaviListener {
    @BindView(R.id.naviView)
    AMapNaviView naviView;
    @BindView(R.id.title)
    TextView title;

    private AMapNavi mapNavi;

    @Override
    protected int initView() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        title.setText(R.string.navigation);
        naviView.onCreate(savedInstanceState);
        mapNavi = AMapNavi.getInstance(context);
        mapNavi.addAMapNaviListener(this);
    }

    @Override
    protected void onResume() {
        naviView.onResume();
        if (mapNavi != null) {
            mapNavi.resumeNavi();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        naviView.onPause();
        if (mapNavi != null) {
            mapNavi.pauseNavi();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        naviView.onDestroy();
        if (mapNavi != null) {
            mapNavi.stopNavi();
        }
        super.onDestroy();
    }

    @Override
    public void onInitNaviFailure() {
        LogUtil.e(TAG, "onInitNaviFailure");
    }

    @Override
    public void onInitNaviSuccess() {
        LogUtil.e(TAG, "onInitNaviSuccess");
        int strategy = 0;
        try {
            strategy = mapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AMapCarInfo aMapCarInfo = new AMapCarInfo();
        aMapCarInfo.setCarType("0");//设置车辆类型，0小车，1货车
        aMapCarInfo.setCarNumber("豫AFZ239");//设置车辆的车牌号码. 如:京DFZ239,京ABZ239
        aMapCarInfo.setVehicleSize("4");// 设置货车的等级
        aMapCarInfo.setVehicleLoad("100");//设置货车的总重，即车重+核定载重，单位：吨。
        aMapCarInfo.setVehicleWeight("99");//设置货车的核定载重，单位：吨。
        aMapCarInfo.setVehicleLength("25");//设置货车的最大长度，单位：米。
        aMapCarInfo.setVehicleWidth("2");//设置货车的最大宽度，单位：米。 如:1.8，1.5等等。
        aMapCarInfo.setVehicleHeight("4");//设置货车的高度，单位：米。
        aMapCarInfo.setVehicleAxis("6");//设置货车的轴数
        aMapCarInfo.setVehicleLoadSwitch(true);//设置车辆的载重是否参与算路
        aMapCarInfo.setRestriction(true);//设置是否躲避车辆限行。

        mapNavi.setCarInfo(aMapCarInfo);
        NaviPoi start = new NaviPoi("", new LatLng(34.606378, 113.700642), "");
        NaviPoi end = new NaviPoi("", new LatLng(34.600325, 113.755774), "");
        mapNavi.calculateDriveRoute(start, end, null, strategy);
    }

    @Override
    public void onStartNavi(int i) {
        LogUtil.e(TAG, "onStartNavi");
    }

    @Override
    public void onTrafficStatusUpdate() {
        LogUtil.e(TAG, "onTrafficStatusUpdate");
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        LogUtil.e(TAG, "onLocationChange");
    }

    @Override
    public void onGetNavigationText(int i, String s) {
        LogUtil.e(TAG, "onGetNavigationText");
    }

    @Override
    public void onGetNavigationText(String s) {
        LogUtil.e(TAG, "onGetNavigationText");
    }

    @Override
    public void onEndEmulatorNavi() {
        LogUtil.e(TAG, "onEndEmulatorNavi");
    }

    @Override
    public void onArriveDestination() {
        LogUtil.e(TAG, "onArriveDestination");
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        LogUtil.e(TAG, "onCalculateRouteFailure");
    }

    @Override
    public void onReCalculateRouteForYaw() {
        LogUtil.e(TAG, "onReCalculateRouteForYaw");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        LogUtil.e(TAG, "onReCalculateRouteForTrafficJam");
    }

    @Override
    public void onArrivedWayPoint(int i) {
        LogUtil.e(TAG, "onArrivedWayPoint");
    }

    @Override
    public void onGpsOpenStatus(boolean b) {
        LogUtil.e(TAG, "onGpsOpenStatus");
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        LogUtil.e(TAG, "onNaviInfoUpdate");
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
        LogUtil.e(TAG, "onNaviInfoUpdated");
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
        LogUtil.e(TAG, "updateCameraInfo");
    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {
        LogUtil.e(TAG, "updateIntervalCameraInfo");
    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
        LogUtil.e(TAG, "onServiceAreaUpdate");
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        LogUtil.e(TAG, "showCross");
    }

    @Override
    public void hideCross() {
        LogUtil.e(TAG, "hideCross");
    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {
        LogUtil.e(TAG, "showModeCross");
    }

    @Override
    public void hideModeCross() {
        LogUtil.e(TAG, "hideModeCross");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
        LogUtil.e(TAG, "showLaneInfo");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {
        LogUtil.e(TAG, "showLaneInfo");
    }

    @Override
    public void hideLaneInfo() {
        LogUtil.e(TAG, "hideLaneInfo");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        LogUtil.e(TAG, "onCalculateRouteSuccess");

        mapNavi.startNavi(NaviType.GPS);
    }

    @Override
    public void notifyParallelRoad(int i) {
        LogUtil.e(TAG, "notifyParallelRoad");
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        LogUtil.e(TAG, "OnUpdateTrafficFacility");
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        LogUtil.e(TAG, "OnUpdateTrafficFacility");
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        LogUtil.e(TAG, "OnUpdateTrafficFacility");
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        LogUtil.e(TAG, "updateAimlessModeStatistics");
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        LogUtil.e(TAG, "updateAimlessModeCongestionInfo");
    }

    @Override
    public void onPlayRing(int i) {
        LogUtil.e(TAG, "onPlayRing");
    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        mapNavi.startNavi(NaviType.GPS);
        LogUtil.e(TAG, "onCalculateRouteSuccess");
    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
        LogUtil.e(TAG, "onCalculateRouteFailure");
    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {
        LogUtil.e(TAG, "onNaviRouteNotify");
    }

    @Override
    public void onGpsSignalWeak(boolean b) {
        LogUtil.e(TAG, "onGpsSignalWeak");
    }
}
