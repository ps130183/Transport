package com.km.transport.module.home.map;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.ps.androidlib.utils.DialogUtils;

public class MapActivity extends BaseActivity {

    @BindView(R.id.mapView)
    TextureMapView mMapView;

    private BaiduMap mBaiduMap;

    private LocationClient mLocationClient;

    private GoodsOrderDetailDto mGoodsInfo;

    private boolean useDefaultIcon = true;

    private RoutePlanSearch mSearch;


    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;

    private double curLatitude = 0;
    private double curLongitude = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_map;
    }

    @Override
    protected String getTitleName() {
        return "当前位置";
    }

    @Override
    protected void onCreate() {

        mGoodsInfo = getIntent().getParcelableExtra("goodsInfo");

        initData();


        mBaiduMap = mMapView.getMap();
        //普通地图（包含3D地图）
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMaxAndMinZoomLevel(3,15);
        BaiduMapRoutePlan.setSupportWebRoute(true);
        initMapLocation();
        planRoutePath();
    }

    private void initData(){
        StringBuffer fromBuf = new StringBuffer();
        fromBuf.append(mGoodsInfo.getSourceProvince())
                .append(mGoodsInfo.getSourceCity())
                .append(mGoodsInfo.getSourceZoning())
                .append(mGoodsInfo.getSourceAdressDetail());
        tvStart.setText(fromBuf.toString());

        StringBuffer toBuf = new StringBuffer();
        toBuf.append(mGoodsInfo.getBournProvince())
                .append(mGoodsInfo.getBournCity())
                .append(mGoodsInfo.getBournZoning())
                .append(mGoodsInfo.getBournAdressDetail());
        tvEnd.setText(toBuf.toString());
    }

    /**
     * 初始化定位
     */
    private void initMapLocation() {
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();

        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000 * 10);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);


        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                //以下只列举部分获取经纬度相关（常用）的结果信息
                //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

                curLatitude = location.getLatitude();    //获取纬度信息
                curLongitude = location.getLongitude();    //获取经度信息


                float radius = location.getRadius();    //获取定位精度，默认值为0.0f

                String coorType = location.getCoorType();
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

                int errorCode = location.getLocType();
                //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();

// 设置定位数据
                mBaiduMap.setMyLocationData(locData);

                int accuracyCircleFillColor = 0xAAFFFF88;//自定义精度圈填充颜色
                int accuracyCircleStrokeColor = 0xAA00FF00;//自定义精度圈边框颜色

// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                        .fromResource(R.mipmap.map_arrow);
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker,accuracyCircleFillColor,accuracyCircleStrokeColor);
                mBaiduMap.setMyLocationConfiguration(config);

//                mBaiduMap.setMyLocationData();
            }
        });

        mLocationClient.start();
    }


    /**
     * 规划驾车路线
     */
    private void planRoutePath(){
        mSearch = RoutePlanSearch.newInstance();

//        PlanNode fromNode = PlanNode.withCityNameAndPlaceName(mGoodsInfo.getSourceCity(),mGoodsInfo.getSourceAdressDetail());
//        PlanNode endNode = PlanNode.withCityNameAndPlaceName(mGoodsInfo.getBournCity(),mGoodsInfo.getBournAdressDetail());

        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            /**
             * 驾车路线规划
             * @param drivingRouteResult
             */
            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
//                Logger.d("获得路径=====" + drivingRouteResult.getRouteLines().size());
                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    showToast("抱歉，未找到合适的路线");
                    return;
                }
                RouteLine route = drivingRouteResult.getRouteLines().get(0);
//                Logger.d(route.getTitle());
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
                OverlayManager routeOverlay = overlay;
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
//                mBtnPre.setVisibility(View.VISIBLE);
//                mBtnNext.setVisibility(View.VISIBLE);
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });


        PlanNode fromNode = PlanNode.withCityNameAndPlaceName(
                mGoodsInfo.getSourceCity(),
                mGoodsInfo.getSourceZoning()+mGoodsInfo.getSourceAdressDetail());
        PlanNode endNode = PlanNode.withCityNameAndPlaceName(
                mGoodsInfo.getBournCity(),
                mGoodsInfo.getBournZoning()+mGoodsInfo.getBournAdressDetail());
        mSearch.drivingSearch((new DrivingRoutePlanOption()).from(fromNode).to(endNode));
//        mSearch.destroy();

    }

    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.mipmap.map_icon_en);
            }
            return null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (mSearch != null){
            mSearch.destroy();
        }
    }

    @OnClick(R.id.btn_open_navigation)
    public void openNavigation(View view){

        GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(final GeoCodeResult geoCodeResult) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    showToast("抱歉，没有检索到终点坐标");
                    return;
                }

                //获取地理编码结果
                DialogUtils.showDefaultAlertDialog("将要开启百度地图导航，是否继续？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        openNavigation(geoCodeResult.getLocation());
                    }
                });

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });


        mSearch.geocode(new GeoCodeOption()
                .city(mGoodsInfo.getBournCity())
                .address(mGoodsInfo.getBournZoning() + mGoodsInfo.getBournAdressDetail()));

    }

    private void openNavigation(LatLng endLatLng){
        LatLng pt_start = new LatLng(curLatitude, curLongitude);
//        LatLng pt_end = new LatLng(mLat2, mLon2);

        // 构建 route搜索参数以及策略，起终点也可以用name构造
        RouteParaOption para = new RouteParaOption()
                .startPoint(pt_start)
                .endPoint(endLatLng);
        try {
//            BaiduMapRoutePlan.openBaiduMapTransitRoute(para, this);
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
