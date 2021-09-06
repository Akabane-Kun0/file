package com.example.xiaoyuanapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionSearch;

import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.adapter.PoiItemAdapter;

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Pack200;

public class MapActivity extends AppCompatActivity {
//        implements OnGetPoiSearchResultListener, OnGetSuggestionResultListener,
//        BaiduMap.OnMapClickListener, BaiduMap.OnMarkerClickListener {

    LocationClient mLocationClient;
    //地图全局变量
    // 地图View实例
    private MapView mMapView;

    private BaiduMap mBaiduMap = null;

    private boolean isFirstLoc = true;//第一次定位

    private MyLocationConfiguration.LocationMode locationMode;//当前定位模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_map);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        //获取地图组件
        mMapView = findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();

//        List<String> permissionList = new ArrayList<String>();
//        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if(!permissionList.isEmpty()) {
//            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
//            ActivityCompat.requestPermissions(MapActivity.this, permissions, 1);
//        }
//        else {
//            requestLocation();
//        }

        //获取系统locationManager对象
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //添加权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,//指定GPS定位提供者
                5000,//间隔时间
                1,//间隔距离
                //监听GPS定位信息是否改变
                new LocationListener() {

                    @Override
                    //GPS定位信息改变时回调
                    public void onLocationChanged(Location location) {
                        //GPS发生改变，更新位置
                        LocationUpdates(location);
                    }

                    @Override
                    //GPS状态改变时回调
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    //定位提供者启动时回调
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    //定位提供者关闭时回调
                    public void onProviderDisabled(String provider) {

                    }
                }
        );
        //获取最新的GPS信息
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //将最新的定位信息，传递给
        LocationUpdates(location);

    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选,设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy 高精度
        //LocationMode.Battery_Saving 低功耗
        //LocationMode.Device_Sensors 仅使用设备

        option.setCoorType("BD09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02 国测距坐标
        //BD09LL 百度经纬度坐标
        //BD09 百度墨卡托坐标
        //海外地区坐标，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(1000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用GPS，默认false
        //使用高精度和仅使用设备两种定位模式的，参数必须设置false

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1s/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            navigateTo(bdLocation);
        }
    }

    private void navigateTo(BDLocation bdLocation) {

        //设置定位图标
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_geo);

        LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        Log.i("Location:", "纬度：" + bdLocation.getLatitude() + "  |  经度：" + bdLocation.getLongitude());

        if (isFirstLoc) {

            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);

//            OverlayOptions options = new MarkerOptions()
//                    .position(ll)
//                    .icon(bitmapDescriptor);
//            mBaiduMap.addOverlay(options);

            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            mBaiduMap.animateMapStatus(update);
            isFirstLoc = false;
        }
        else {
            MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
            locationBuilder.longitude(bdLocation.getLongitude());
            locationBuilder.latitude(bdLocation.getLatitude());
            MyLocationData locationData = locationBuilder.build();
            Log.i("Location:", "纬度：" + bdLocation.getLatitude() + "  |  经度：" + bdLocation.getLongitude());
            mBaiduMap.setMyLocationData(locationData);

        }

//        //设置定位模式
//        locationMode = MyLocationConfiguration.LocationMode.NORMAL;
//        //设置构造方式
//        MyLocationConfiguration config = new MyLocationConfiguration(locationMode, true, bitmapDescriptor);
//        //显示定位图标
//        mBaiduMap.setMyLocationConfiguration(config);

        OverlayOptions options2 = new MarkerOptions()
                .position(ll)
                .icon(bitmapDescriptor);
        mBaiduMap.addOverlay(options2);

    }

    public void LocationUpdates(Location location) {
        if(location!=null) {

            //获取用户当前位置经纬度
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            Log.i("Location:","纬度："+location.getLatitude()+"  |  经度："+location.getLongitude());

            if(isFirstLoc) {
                // 设置初始中心点
                LatLng center = new LatLng(31.772500, 117.190300);

                //更新坐标位置
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(center);
                mBaiduMap.animateMapStatus(u);
                u = MapStatusUpdateFactory.zoomTo(20f);
                //设置地图位置
                mBaiduMap.animateMapStatus(u);
                isFirstLoc=false;
            }
            //构造定位数据
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getAccuracy())
                    .direction(100)//设置方向信息
            .latitude(location.getLatitude())//设置为纬度坐标
            .longitude(location.getLongitude())//设置经度坐标
            .build();

            //设置定位数据
            mBaiduMap.setMyLocationData(locData);
            //设置定位图标
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_geo);
            //设置定位模式
            locationMode = MyLocationConfiguration.LocationMode.NORMAL;
            //设置构造方式
            MyLocationConfiguration config = new MyLocationConfiguration(locationMode, true, bitmapDescriptor);
            //显示定位图标
            mBaiduMap.setMyLocationConfiguration(config);
        }
        else {
            Log.i("Location:","没有获取到GPS信息!!");
        }

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
//        mMapView=null;
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();

    }

    // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
//        if (null != mMapView) {
//            mMapView.onResume();
//        }
    }

    // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    @SuppressWarnings("checkstyle:WhitespaceAround")
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
//        if (null != mMapView) {
//            mMapView.onPause();
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定图层
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定图层
        mBaiduMap.setMyLocationEnabled(false);
    }

}
