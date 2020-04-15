package com.zzz.myemergencyclientnew.activity.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.activity.home.HomePresenter;
import com.zzz.myemergencyclientnew.base.BaseActivity;
import com.zzz.myemergencyclientnew.constant.pref.Api;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.databinding.ActivityHomeBinding;
import com.zzz.myemergencyclientnew.db.ApkPluginEntity;
import com.zzz.myemergencyclientnew.entity.ApkFunctionEntity;
import com.zzz.myemergencyclientnew.entity.HomeBottomNavigationEntity;
import com.zzz.myemergencyclientnew.entity.NavigationEntity;
import com.zzz.myemergencyclientnew.eventbus.EvnApkPlugin;
import com.zzz.myemergencyclientnew.fragment.TangramFragment;
import com.zzz.myemergencyclientnew.fragment.home.NewsFragment;
import com.zzz.myemergencyclientnew.host.PluginApkBloc;
import com.zzz.myemergencyclientnew.host.TestComponentManager;
import com.zzz.myemergencyclientnew.host.TestPluginLoader;
import com.zzz.myemergencyclientnew.service.BaiduLocationService;
import com.zzz.myemergencyclientnew.utils.NotificationUtils;
import com.zzz.myemergencyclientnew.utils.SPUtils;
import com.zzz.myemergencyclientnew.widget.custom.dynamicnavigation.TabUnitView;
import com.zzz.myemergencyclientnew.widget.custom.navigation.NavigationViewListener;
import com.tencent.shadow.core.common.InstalledApk;
import com.tencent.shadow.core.load_parameters.LoadParameters;
import com.tencent.shadow.core.loader.ShadowPluginLoader;
import com.tencent.shadow.core.runtime.container.ContentProviderDelegateProviderHolder;
import com.tencent.shadow.core.runtime.container.DelegateProviderHolder;
import com.toast.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;
import static androidx.core.content.ContextCompat.checkSelfPermission;

@Route(path = C.HOME)
public class HomeActivity extends BaseActivity<HomePresenter, ActivityHomeBinding> implements HomeContract.View {
//    private List<Fragment> fragmentList = new ArrayList<>();
//    private TangramFragment tangramFragment;
//    private NewsFragment applyFragment;
//    private NewsFragment publishFragment;
//    private NewsFragment serveFragment;
//    private NewsFragment meFragment;

    public ShadowPluginLoader mPluginLoader;

    public static Map<String, InstalledApk> mPluginMap = new HashMap<>();

    private List<ApkFunctionEntity> functionList = new ArrayList<>();
    private List<NavigationEntity> functionList3 = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);

        ShadowPluginLoader loader = mPluginLoader = new TestPluginLoader(getApplicationContext());
        loader.onCreate();
        DelegateProviderHolder.setDelegateProvider(loader.getDelegateProviderKey(), loader);
        ContentProviderDelegateProviderHolder.setContentProviderDelegateProvider(loader);
        EventBus.getDefault().register(this);


//        Window window = mContext.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        window.setStatusBarColor(mContext.getResources().getColor(R.color.white));

//        tangramFragment = new TangramFragment();
//        applyFragment = new NewsFragment();
//        publishFragment = new NewsFragment();
//        serveFragment = new NewsFragment();
//        meFragment = new NewsFragment();
//        fragmentList.add(tangramFragment);
//        fragmentList.add(applyFragment);
//        fragmentList.add(publishFragment);
//        fragmentList.add(serveFragment);
//        fragmentList.add(meFragment);


        //重要代码，防止Fragment被销毁的问题
        mViewBinding.viewPager.setOffscreenPageLimit(5);

        //        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, new NewsFragment()).commitAllowingStateLoss();
//        mViewBinding.lytNavigation.tabOne.setCurrentFocus(true);
//        mViewBinding.lytNavigation.setListener(new NavigationViewListener() {
//            @Override
//            public void getNavigaIntex(int i) {
//                switch (i) {
//                    case 0:
////                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
////                        window.setStatusBarColor(mContext.getResources().getColor(R.color.white));
//                        mViewBinding.viewPager.setCurrentItem(0);
//                        break;
//                    case 1:
////                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
////                        window.setStatusBarColor(mContext.getResources().getColor(R.color.white));
//                        mViewBinding.viewPager.setCurrentItem(1);
//                        break;
//                    case 2:
////                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
////                        window.setStatusBarColor(mContext.getResources().getColor(R.color.main_color));
//                        mViewBinding.viewPager.setCurrentItem(2);
//                        break;
//                    case 3:
////                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
////                        window.setStatusBarColor(mContext.getResources().getColor(R.color.main_color));
//                        mViewBinding.viewPager.setCurrentItem(3);
//                        break;
//                    case 4:
////                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ////                        window.setStatusBarColor(mContext.getResources().getColor(R.color.main_color));
//                        mViewBinding.viewPager.setCurrentItem(4);
//                        break;
//                }
//            }
//        });
//        mViewBinding.bnve.enableAnimation(false);
//        mViewBinding.bnve.enableItemShiftingMode(false);
//        mViewBinding.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//                switch (menuItem.getItemId()) {
//                    case R.id.i_news:
//                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                        window.setStatusBarColor(mContext.getResources().getColor(R.color.white));
//                        mViewBinding.viewPager.setCurrentItem(0);
////                        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, new NewsFragment()).commitAllowingStateLoss();
//                        break;
//                    case R.id.i_apply:
//                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                        window.setStatusBarColor(mContext.getResources().getColor(R.color.white));
//                        mViewBinding.viewPager.setCurrentItem(1);
////                        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, new ApplyFragment()).commitAllowingStateLoss();
//                        break;
//                    case R.id.i_publish:
//                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                        window.setStatusBarColor(mContext.getResources().getColor(R.color.main_color));
//                        mViewBinding.viewPager.setCurrentItem(2);
////                        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, new PublishFragment()).commitAllowingStateLoss();
//                        break;
//                    case R.id.i_serve:
//                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                        window.setStatusBarColor(mContext.getResources().getColor(R.color.main_color));
//                        mViewBinding.viewPager.setCurrentItem(3);
////                        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, new ServeFragment()).commitAllowingStateLoss();
//                        break;
//                    case R.id.i_me:
//                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                        window.setStatusBarColor(mContext.getResources().getColor(R.color.main_color));
//                        mViewBinding.viewPager.setCurrentItem(4);
////                        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, new MeFragment()).commitAllowingStateLoss();
//                        break;
//                }
//                return true;
//            }
//        });

        /**
         * 动态权限申请
         */
        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.LOCATION_HARDWARE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECORD_AUDIO
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 1);
        }
        /**
         * 开启定位服务
         */
        getPersimmions();

//        Intent myMqttService = new Intent(this, MyMqttService.class);
//        myMqttService.putExtra("token", "user");
//        startService(myMqttService);
//        bindService(myMqttService, connection, BIND_AUTO_CREATE);

//        Intent locationService = new Intent(this, BaiduLocationService.class);
//        locationService.putExtra("token", "user");
//        startService(locationService);
//        bindService(locationService, connection, BIND_AUTO_CREATE);

//        LocationClient mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        mLocClient.setLocOption(option);
//        mLocClient.start();

//        // 定位初始化
//        mClient = new LocationClient(this);
//        LocationClientOption mOption = new LocationClientOption();
//        mOption.setScanSpan(5000);
//        mOption.setCoorType("bd09ll");
//        mOption.setIsNeedAddress(true);
//        mOption.setOpenGps(true);
//        mClient.setLocOption(mOption);
//        mClient.registerLocationListener(myLocationListener);


        //设置后台定位
        //android8.0及以上使用NotificationUtils
        if (Build.VERSION.SDK_INT >= 26) {
            mNotificationUtils = new NotificationUtils(this);
            Notification.Builder builder2 = mNotificationUtils.getAndroidChannelNotification
                    ("适配android 8限制后台定位功能", "正在后台定位");
            notification = builder2.build();
        } else {
            //获取一个Notification构造器
            Notification.Builder builder = new Notification.Builder(mContext);
            Intent nfIntent = new Intent(mContext, HomeActivity.class);

            builder.setContentIntent(PendingIntent.
                    getActivity(mContext, 0, nfIntent, 0)) // 设置PendingIntent
                    .setContentTitle("适配android 8限制后台定位功能") // 设置下拉列表里的标题
                    .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                    .setContentText("正在后台定位") // 设置上下文内容
                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

            notification = builder.build(); // 获取构建好的Notification
        }
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音

//        mClient.enableLocInForeground(1, notification);
////        isEnableLocInForeground = true;
////        mForegroundBtn.setText(R.string.stopforeground);
//        mClient.start();

//        mPresenter.netInitData(mActivity);
        mPresenter.netInitData3(mContext);
    }

//    private LocationClient mClient;
//    private MyLocationListener myLocationListener = new MyLocationListener();

    private NotificationUtils mNotificationUtils;
    private Notification notification;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mMapView.onDestroy();
//        mMapView = null;
        // 关闭前台定位服务
//        mClient.disableLocInForeground(true);
//        // 取消之前注册的 BDAbstractLocationListener 定位监听函数
//        mClient.unRegisterLocationListener(myLocationListener);
//        // 停止定位sdk
//        mClient.stop();

        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    private ServiceConnection connection = new ServiceConnection() {
        /**
         * 服务解除绑定时候调用
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        /**
         * 绑定服务的时候调用
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BaiduLocationService.ServiceBinder binder = (BaiduLocationService.ServiceBinder) service;
            BaiduLocationService grayService = binder.getService();


//            /**
//             * 实现回调，得到实时刷新的数据
//             */
//            grayService.setCallback(new LocationServiceListener() {
//                @Override
//                public void getLocation(double latitude, double longitude) {
//                    GlobalContants.latitude = latitude;
//                    GlobalContants.longitude = longitude;
//                }
//            });
        }
    };

    @Override
    public void netInitData(List<HomeBottomNavigationEntity> list) {
//        List<TabUnitView> tabUnitViews = new ArrayList<>();
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
//        for(int i=0;i<list.size(); i++){
//            TabUnitView tab = new TabUnitView(mActivity);
//            tab.setDate(list.get(i));
//            tab.setLayoutParams(params);
//            tabUnitViews.add(tab);
//        }
//        mViewBinding.lytNavigation.initTabUnitList(tabUnitViews);
    }

    @Override
    public void netInitData2(List<ApkFunctionEntity> list) {
//        functionList = list;
//        List<Fragment> fragmentList = new ArrayList<>();
//        List<TabUnitView> tabUnitViews = new ArrayList<>();
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
//        for (int i = 0; i < list.size(); i++) {
//            int index = i;
//            ApkFunctionEntity homeFunction = list.get(i);
//
//            TabUnitView tab = new TabUnitView(mContext);
//            tab.setDate(homeFunction);
//            tab.setLayoutParams(params);
//            tab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //中央界面切换
//                    mViewBinding.viewPager.setCurrentItem(index);
//                    //底部按钮选中状态
//                    mViewBinding.lytNavigation.setChecked(index);
//                    //顶部切换
//                    List<TabUnitView> topUnit = new ArrayList<>();
//                    if (homeFunction.getChild() != null && homeFunction.getChild().size() > 0) {
//                        for (int j = 0; j < homeFunction.getChild().size(); j++) {
//                            ApkFunctionEntity topFunction = homeFunction.getChild().get(j);
//                            if (topFunction.orient.equals("t")) {
//                                TabUnitView top = new TabUnitView(mContext);
//                                top.setDate(topFunction);
//                                top.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Toast.makeText(mContext, topFunction.title, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                                topUnit.add(top);
//                            }
//                        }
//                    }
//                    mViewBinding.lytTop.initTabUnitList(homeFunction.title, topUnit);
//                }
//            });
//            tabUnitViews.add(tab);
//            fragmentList.add((Fragment) ARouter.getInstance().build(homeFunction.route).withString("api", homeFunction.api).navigation());
////            fragmentList.add((Fragment)ARouter.getInstance().build(homeFunction.route).withString("api", "http://192.168.81.190:9090"+homeFunction.api).navigation());
//        }
//        mViewBinding.lytNavigation.initTabUnitList(tabUnitViews);
//        mViewBinding.lytNavigation.setChecked(0);
//
//        mViewBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public int getCount() {
//                return fragmentList.size();
//            }
//
//            @Override
//            public Fragment getItem(int index) {
//                return fragmentList.get(index);
//            }
//        });
//        //重要代码，防止Fragment被销毁的问题
//        mViewBinding.viewPager.setOffscreenPageLimit(5);
    }

    @Override
    public void netInitData3(List<NavigationEntity> list) {
        functionList3 = list;
        List<Fragment> fragmentList = new ArrayList<>();
        List<TabUnitView> tabUnitViews = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        for (int i = 0; i < list.size(); i++) {
            int index = i;
            NavigationEntity homeFunction = list.get(i);

            TabUnitView tab = new TabUnitView(mContext);
            tab.setDate(homeFunction);
            tab.setLayoutParams(params);
            tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //中央界面切换
                    mViewBinding.viewPager.setCurrentItem(index);
                    //底部按钮选中状态
                    mViewBinding.lytNavigation.setChecked(index);
                    //顶部切换
                    List<TabUnitView> topUnit = new ArrayList<>();
                    if (homeFunction.getChild() != null && homeFunction.getChild().size() > 0) {
                        for (int j = 0; j < homeFunction.getChild().size(); j++) {
                            NavigationEntity topFunction = homeFunction.getChild().get(j);
                            if (topFunction.orient.equals("t")) {
                                TabUnitView top = new TabUnitView(mContext);
                                top.setDate(topFunction);
                                top.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(mContext, topFunction.title, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                topUnit.add(top);
                            }
                        }
                    }
                    mViewBinding.lytTop.initTabUnitList(homeFunction.title, topUnit);
                }
            });
            tabUnitViews.add(tab);

            if(homeFunction.click == 4){
                fragmentList.add((Fragment) ARouter.getInstance().build(homeFunction.apiTangramEntity.fragmentRoute).withString("api", homeFunction.apiTangramEntity.api).navigation());
            }else if(homeFunction.click == 5){
                String applyList = homeFunction.applyList;
                fragmentList.add((Fragment) ARouter.getInstance().build(C.ApplyFragment).withString("applyList", applyList).navigation());
            }
//            fragmentList.add((Fragment) ARouter.getInstance().build(homeFunction.apiTangramEntity.fragmentRoute).withString("api", homeFunction.apiTangramEntity.api).navigation());
//            fragmentList.add((Fragment)ARouter.getInstance().build(homeFunction.route).withString("api", "http://192.168.81.190:9090"+homeFunction.api).navigation());
        }
        mViewBinding.lytNavigation.initTabUnitList(tabUnitViews);
        mViewBinding.lytNavigation.setChecked(0);

        mViewBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int index) {
                return fragmentList.get(index);
            }
        });
        //重要代码，防止Fragment被销毁的问题
        mViewBinding.viewPager.setOffscreenPageLimit(5);
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
//
//            if (TextUtils.isEmpty(country) || TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(district) || TextUtils.isEmpty(street)) {
//
//            } else {
//                C.addr = addr;
//                C.country = country;
//                C.province = province;
//                C.city = city;
//                C.district = district;
//                C.street = street;
//                C.lat = "" + location.getLatitude();
//                C.lon = "" + location.getLongitude();
//                C.radius = "" + location.getRadius();//获取定位精度
////                Toast.makeText(mContext, "经"+location.getLatitude()+"纬"+location.getLongitude()+addr+country+province+city+district+street, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private final int SDK_PERMISSION_REQUEST = 127;

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void loadPlugin(final String partKey, final Runnable completeRunnable) {
        InstalledApk installedApk = mPluginMap.get(partKey);
        if (installedApk == null) {
            throw new NullPointerException("partKey == " + partKey);
        }

        if (mPluginLoader.getPluginParts(partKey) == null) {
            LoadParameters loadParameters = new LoadParameters(null, partKey, null, null);

            Parcel parcel = Parcel.obtain();
            loadParameters.writeToParcel(parcel, 0);
            final InstalledApk plugin = new InstalledApk(
                    installedApk.apkFilePath,
                    installedApk.oDexPath,
                    installedApk.libraryPath,
                    parcel.marshall()
            );

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    ShadowPluginLoader pluginLoader = mPluginLoader;
                    Future<?> future = null;
                    try {
                        future = pluginLoader.loadPlugin(plugin);
                        future.get(10, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        e.printStackTrace();
//                        handler.sendEmptyMessage(0);
//                        T.info(mContext, "还未加载");
//                        throw new RuntimeException("加载失败", e);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mPluginLoader.callApplicationOnCreate(partKey);
                    completeRunnable.run();
                }
            }.execute();
            parcel.recycle();
        } else {
            completeRunnable.run();
        }
    }

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            T.success(mContext, "还未加载");
//        }
//    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void evnApkPluginEntity(ApkPluginEntity evn) {
        InstalledApk scanHelpFeedback = new PluginApkBloc(evn.getApkFileName()).preparePlugin(this.getApplicationContext());
        mPluginMap.put(evn.getPlugCode(), scanHelpFeedback);
        SPUtils.put(mContext, evn.getPlugCode(), true);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void evnEvnApkPlugin(EvnApkPlugin evn) {
//        try {
//            InstalledApk scanHelpFeedback = new PluginApkBloc(evn.apkFileName).preparePlugin(this.getApplicationContext());
//            mPluginMap.put(evn.plugCode, scanHelpFeedback);
        loadPlugin(evn.plugCode, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), evn.enter);
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                pluginIntent.putExtra("token", (String)SPUtils.get(HomeActivity.this, C.sp_token,""));
                pluginIntent.putExtra("title", evn.title);
                pluginIntent.putExtra("params", evn.params);
                pluginIntent.putExtra("service", Api.JAVA_SERVICE_URL);
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
//        } catch (Exception e) {
//            e.printStackTrace();
//            T.info(mContext, "插件正在加载或加载失败");
//        }
    }

}
