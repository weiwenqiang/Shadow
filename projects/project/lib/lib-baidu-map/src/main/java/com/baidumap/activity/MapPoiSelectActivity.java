package com.baidumap.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidumap.PoiListAdapter;
import com.baidumap.PoiOverlay;
import com.baidumap.R;
import com.baidumap.databinding.ActivityMapPoiSelectBinding;
import com.baidumap.eventbus.EvnMapPoiSelect;
import com.zzz.myemergencyclientnew.base.BaseActivity;
import com.zzz.myemergencyclientnew.base.DataBindingActivity;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.utils.AppUtils;
import com.toast.T;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

@Route(path = "/MapPoiSelect/activity")
public class MapPoiSelectActivity extends AppCompatActivity implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener, AdapterView.OnItemClickListener, PoiListAdapter.OnGetChildrenLocationListener, BaiduMap.OnMapLongClickListener, OnGetGeoCoderResultListener {

    private GeoCoder mSearch = null;
    private PoiSearch mPoiSearch = null;
    private BaiduMap mBaiduMap = null;

    private List<PoiInfo> mAllPoi;
    private BitmapDescriptor mBitmap;

    private EvnMapPoiSelect evn;

    ActivityMapPoiSelectBinding mViewBinding;
    public AppCompatActivity mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mContext = this;
        // 默认本地个性化地图初始化方法
//        SDKInitializer.initialize(mContext.getApplicationContext());
//        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);

        View rootView = getLayoutInflater().inflate(R.layout.activity_map_poi_select, null, false);
        mViewBinding = DataBindingUtil.bind(rootView);
        setContentView(rootView);
        initView();
    }


    public void initView() {
        mViewBinding.setMView(this);

        mBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        mBaiduMap = mViewBinding.map.getMap();

        // 创建poi检索实例，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        mViewBinding.poiList.setOnItemClickListener(this);
        // 地图点击事件
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                showPoiDetailView(false);
            }

            @Override
            public void onMapPoiClick(MapPoi poi) {

            }
        });
        mBaiduMap.setOnMapLongClickListener(this);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);


        evn = new EvnMapPoiSelect(C.addr, C.lat, C.lon);
        initMyAddress();
    }

    /**
     * 初始化当前定位显示
     */
    public void initMyAddress(){
        mViewBinding.txtAddress.setText(C.addr);
        // 开启定位图层
        UiSettings settings = mBaiduMap.getUiSettings();
//        settings.setAllGesturesEnabled(false);   //关闭一切手势操作
        settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
        settings.setRotateGesturesEnabled(false);//屏蔽旋转
//        settings.setZoomGesturesEnabled(false);//获取是否允许缩放手势返回:是否允许缩放手势

        mBaiduMap.setMyLocationEnabled(true);
        MyLocationData myLocationData = new MyLocationData.Builder()
                .accuracy(Float.parseFloat(C.radius))// 设置定位数据的精度信息，单位：米
//                .direction(mCurrentDirection)// 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(Double.parseDouble(C.lat))
                .longitude(Double.parseDouble(C.lon))
                .build();
        mBaiduMap.setMyLocationData(myLocationData);

        LatLng cenpt = new LatLng(Double.parseDouble(C.lat),Double.parseDouble(C.lon)); //设定中心点坐标
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt)
                .zoom(18)
                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态

        // 传入null，则为默认图标
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
        MapStatus.Builder builder1 = new MapStatus.Builder();
        builder1.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
    }

    /**
     * 响应城市内搜索按钮点击事件
     */
    public void search(View v) {
        //  按搜索按钮时隐藏软件盘，为了在结果回调时计算 PoiDetailView 控件的高度，把地图中poi展示到合理范围内
        AppUtils.closeKeybord(this);
        // 获取检索关键字
        String keyWordStr = mViewBinding.autoSearch.getText().toString();
        // 发起请求
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(C.city)
                .keyword(keyWordStr)
                .pageNum(0) // 分页编号
                .cityLimit(true)
                .scope(1));
    }

    public void affirm(View v){
        EventBus.getDefault().post(evn);
        finish();
    }

    /**
     * 获取城市poi检索结果
     */
    @Override
    public void onGetPoiResult(final PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            mBaiduMap.clear();
            showPoiDetailView(false);
            Toast.makeText(mContext, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            showPoiDetailView(true);
            mBaiduMap.clear();
            // 监听 View 绘制完成后获取view的高度
            mViewBinding.poiList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int padding = 50;
                    // 添加poi
                    PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(result);
                    overlay.addToMap();
                    // 获取 view 的高度
                    int PaddingBootom = mViewBinding.poiList.getMeasuredHeight();
                    // 设置显示在规定宽高中的地图地理范围
                    overlay.zoomToSpanPaddingBounds(padding, padding, padding, PaddingBootom);
                    // 加载完后需要移除View的监听，否则会被多次触发
                    mViewBinding.poiList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

            // 获取poi结果
            mAllPoi = result.getAllPoi();
            PoiListAdapter poiListAdapter = new PoiListAdapter(this, mAllPoi);
            poiListAdapter.setOnGetChildrenLocationListener(this);
            // 把poi结果添加到适配器
            mViewBinding.poiList.setAdapter(poiListAdapter);

            return;
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult result) {

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {

    }

    /**
     * poilist 点击处理
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PoiInfo poiInfo = mAllPoi.get(position);
        if (poiInfo.getLocation() == null) {
            return;
        }
        addPoiLoction(poiInfo.getLocation());
        evn.addr = poiInfo.address;
        evn.lat = ""+poiInfo.location.latitude;
        evn.lon = ""+poiInfo.location.longitude;
        mViewBinding.txtAddress.setText(poiInfo.address);
    }

    /**
     * 点击子节点list 获取经纬添加poi更新地图,子节点经纬度
     */
    @Override
    public void getChildrenLocation(LatLng childrenLocation) {
        addPoiLoction(childrenLocation);
    }

    /**
     * 更新到子节点的位置,子节点经纬度
     */
    private void addPoiLoction(LatLng latLng) {
        mBaiduMap.clear();
        showPoiDetailView(false);
        OverlayOptions markerOptions = new MarkerOptions().position(latLng).icon(mBitmap);
        mBaiduMap.addOverlay(markerOptions);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng);
        builder.zoom(18.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        showPoiDetailView(false);
        evn.addr = String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
        evn.lat = ""+latLng.latitude;
        evn.lon = ""+latLng.longitude;
        mViewBinding.txtAddress.setText(evn.addr);

        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(mBitmap);
        mBaiduMap.clear();
        mBaiduMap.addOverlay(ooA);
        // 更新地图中心点
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));

        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption()
                .location(latLng) // 设置反地理编码位置坐标
                .newVersion(0) // 设置是否返回新数据 默认值0不返回，1返回
                .radius(500) //  POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .pageNum(0);
        // 发起反地理编码请求，该方法必须在监听之后执行，否则会在某些场景出现拿不到回调结果的情况
        mSearch.reverseGeoCode(reverseGeoCodeOption);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            T.info(mContext,"抱歉，未能找到结果");
            return;
        }
        // 获取周边poi结果
        List<PoiInfo> poiList = result.getPoiList();
        if (null != poiList && poiList.size() > 0){
            PoiInfo poiInfo = poiList.get(0);
            evn.addr = poiInfo.address+"附近";
            mViewBinding.txtAddress.setText(evn.addr);
        }
    }

    protected class MyPoiOverlay extends PoiOverlay {
        MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            Toast.makeText(mContext, poi.address, Toast.LENGTH_LONG).show();
            return true;
        }
    }

    /**
     * 是否展示详情 view
     */
    private void showPoiDetailView(boolean whetherShow) {
        if (whetherShow) {
            mViewBinding.poiList.setVisibility(View.VISIBLE);
            mViewBinding.lytAffirm.setVisibility(View.GONE);
        } else {
            mViewBinding.poiList.setVisibility(View.GONE);
            mViewBinding.lytAffirm.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewBinding.map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 隐藏控件
        showPoiDetailView(false);
        mViewBinding.map.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放bitmap
        mBitmap.recycle();
        // 释放检索对象
        mPoiSearch.destroy();
        // 清空地图所有的覆盖物
        mBaiduMap.clear();
        // 释放地图组件
        mViewBinding.map.onDestroy();
    }
}