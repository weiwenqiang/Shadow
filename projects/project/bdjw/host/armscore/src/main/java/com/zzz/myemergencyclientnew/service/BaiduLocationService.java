package com.zzz.myemergencyclientnew.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;

public class BaiduLocationService extends Service {
//    public LocationClient mLocClient = null;
//    private MyLocationListener myListener = new MyLocationListener();

    private ServiceBinder serviceBinder = new ServiceBinder();

    private String token;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @Override
    public void onCreate() {
//        mLocationClient = new LocationClient(getApplicationContext());
//        //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);
//        //注册监听函数
//        LocationClientOption option = new LocationClientOption();
//        option.setIsNeedAddress(true);
////可选，是否需要地址信息，默认为不需要，即参数为false
////如果开发者需要获得当前点的地址信息，此处必须为true
//
//        mLocationClient.setLocOption(option);
////mLocationClient为第二步初始化过的LocationClient对象
////需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
////更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
//        mLocationClient.start();

//        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        mLocClient.setLocOption(option);
//        mLocClient.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        token = intent.getStringExtra("token");

        return START_STICKY;
    }

    /**
     * 内部类继承Binder
     */
    public class ServiceBinder extends Binder {
        /**
         * 声明方法返回值是MyService本身
         */
        public BaiduLocationService getService() {
            return BaiduLocationService.this;
        }
    }


//    public class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            //以下只列举部分获取地址相关的结果信息
//            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//
//            String addr = location.getAddrStr();    //获取详细地址信息
//            String country = location.getCountry();    //获取国家
//            String province = location.getProvince();    //获取省份
//            String city = location.getCity();    //获取城市
//            String district = location.getDistrict();    //获取区县
//            String street = location.getStreet();    //获取街道信息
//
//        }
//    }
}
