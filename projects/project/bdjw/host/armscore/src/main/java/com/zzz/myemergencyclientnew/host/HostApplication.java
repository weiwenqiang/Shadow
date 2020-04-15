/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.zzz.myemergencyclientnew.host;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcel;
import android.os.StrictMode;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
//import com.baidu.mapapi.CoordType;
//import com.baidu.mapapi.SDKInitializer;
import com.zzz.myemergencyclientnew.test.PreparePluginPushMsgApkBloc;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.squareup.picasso.Picasso;
import com.tencent.shadow.core.common.InstalledApk;
import com.tencent.shadow.core.common.LoggerFactory;
import com.tencent.shadow.core.load_parameters.LoadParameters;
import com.tencent.shadow.core.loader.ShadowPluginLoader;
import com.tencent.shadow.core.runtime.container.ContentProviderDelegateProviderHolder;
import com.tencent.shadow.core.runtime.container.DelegateProviderHolder;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.util.IInnerImageSetter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class HostApplication extends Application {
    private static Application sApp;

//    public final static String PART_MAIN = "partMain";
//
//    private static final PreparePluginApkBloc sPluginPrepareBloc = new PreparePluginApkBloc("plugin.apk");

    static {
        detectNonSdkApiUsageOnAndroidP();

        LoggerFactory.setILoggerFactory(new SLoggerFactory());
    }

    public static ShadowPluginLoader mPluginLoader;

    public static Map<String, InstalledApk> mPluginMap = new HashMap<>();

    public static void loadPlugin(final String partKey, final Runnable completeRunnable) {
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
                        throw new RuntimeException("加载失败", e);
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

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

        ShadowPluginLoader loader = mPluginLoader = new TestPluginLoader(getApplicationContext());
        loader.onCreate();
        DelegateProviderHolder.setDelegateProvider(loader.getDelegateProviderKey(), loader);
        ContentProviderDelegateProviderHolder.setContentProviderDelegateProvider(loader);
//
//        InstalledApk installedApk = sPluginPrepareBloc.preparePlugin(this.getApplicationContext());
//        mPluginMap.put(PART_MAIN, installedApk);

        /**
         * ========================================================================================
         */

        /**
         * ========================================================================================
         */
//布局
        TangramBuilder.init(sApp, new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view,
                                                                 @Nullable String url) {
                //假设你使用 Picasso 加载图片
                Picasso.with(sApp).load(url).into(view);
            }
        }, ImageView.class);
        // 默认本地个性化地图初始化方法
//        SDKInitializer.initialize(this);
//        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);
        //路由
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        //PR下载
        PRDownloaderConfig prDownloaderConfig = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(this, prDownloaderConfig);

        Realm.init(this);
    }



    private static void detectNonSdkApiUsageOnAndroidP() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
//        boolean isRunningEspressoTest;
//        try {
//            Class.forName("android.support.test.espresso.Espresso");
//            isRunningEspressoTest = true;
//        } catch (Exception ignored) {
//            isRunningEspressoTest = false;
//        }
//        if (isRunningEspressoTest) {
//            return;
//        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectNonSdkApiUsage();
        StrictMode.setVmPolicy(builder.build());

//        setThreadPolicy();
//        setVmPolicy();
    }

    public static Application getApp() {
        return sApp;
    }

    public ShadowPluginLoader getPluginLoader() {
        return mPluginLoader;
    }
//
//    //线程策略检测
//    private static void setThreadPolicy() {
//        StrictMode.ThreadPolicy.Builder builder = new StrictMode.ThreadPolicy.Builder()
//                .detectAll() //detectAll() 检测下述所有
////                .detectCustomSlowCalls()   //自定义耗时调用
////                .detectDiskReads()         //磁盘读取操作
////                .detectDiskWrites()        //磁盘写入操作
////                .detectNetwork()            //网络操作
////                .detectResourceMismatches()  //资源类型不匹配 android 23增加
//                .penaltyLog();                 //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
//        StrictMode.setThreadPolicy(builder.build());
//    }
//
//    //虚拟机策略检测
//    private static void setVmPolicy() {
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder()
//                .detectAll()                         //检测下述所有
////                .detectActivityLeaks()             //Activity泄漏
////                .detectLeakedClosableObjects()     //未关闭Closable对象泄漏
////                .detectLeakedSqlLiteObjects()      //SqlLite对象泄漏
////                .detectCleartextNetwork()           //网络流量监控 android 23增加
////                .detectLeakedRegistrationObjects()   //广播或者服务等未注销导致泄漏  android 23增加
////                .detectFileUriExposure()             //文件uri暴露   android增加
//                .penaltyLog();                        //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
//        StrictMode.setVmPolicy(builder.build());
//    }
}
