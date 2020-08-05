package com.teang.view.activity.map;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.teang.R;
import com.teang.base.BaseActivity;

import butterknife.BindView;

public class LocationActivity extends BaseActivity {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.title)
    TextView title;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    @Override
    protected int initView() {
        return R.layout.activity_location;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        title.setText(R.string.location);
        //初始化地图
        AMap map = mapView.getMap();
        map.setMapType(AMap.MAP_TYPE_NORMAL);
        //缩放等级
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        //实现定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //定位模式
        //https://lbs.amap.com/api/android-sdk/guide/create-map/mylocation
        //LOCATION_TYPE_LOCATE 只定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //定位不显示圆圈
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        map.setMyLocationStyle(myLocationStyle);
        //显示实时路况
        map.setTrafficEnabled(true);
        //定位按钮（不支持自定义位置）
        map.getUiSettings().setMyLocationButtonEnabled(true);
        //true表示启动显示定位蓝点
        map.setMyLocationEnabled(true);
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
}
