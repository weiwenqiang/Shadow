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

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.EditText;

import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.activity.login.LoginActivity;
import com.zzz.myemergencyclientnew.activity.plugin.PluginListActivity;
import com.zzz.myemergencyclientnew.activity.splash.SplashActivity;
import com.zzz.myemergencyclientnew.constant.pref.Api;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.test.PreparePluginPushMsgApkBloc;
import com.zzz.myemergencyclientnew.utils.SPUtils;
import com.tencent.shadow.core.common.InstalledApk;
import com.tencent.shadow.core.load_parameters.LoadParameters;
import com.tencent.shadow.core.loader.ShadowPluginLoader;
import com.tencent.shadow.core.runtime.container.ContentProviderDelegateProviderHolder;
import com.tencent.shadow.core.runtime.container.DelegateProviderHolder;
import com.toast.T;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.examples.kotlin.KotlinExampleActivity;


public class MainActivity extends Activity {
    private HostApplication application;

    private static final PreparePluginPushMsgApkBloc sPluginPushMsgBloc = new PreparePluginPushMsgApkBloc("plugin-push-msg-debug.apk");
    private static final PreparePluginPushMsgApkBloc sPluginScanQRBloc = new PreparePluginPushMsgApkBloc("plugin-scan-qr-debug.apk");
    private static final PreparePluginPushMsgApkBloc sPluginHelpFeedbackBloc = new PreparePluginPushMsgApkBloc("plugin-help-feedback-debug.apk");
    private static final PreparePluginPushMsgApkBloc sPluginIntellReportBloc = new PreparePluginPushMsgApkBloc("plugin-intell-report-debug.apk");
    private static final PreparePluginPushMsgApkBloc sPluginPublishSosBloc = new PreparePluginPushMsgApkBloc("plugin-publish-sos-debug.apk");
    private static final PreparePluginPushMsgApkBloc sPluginPublishNotypeBloc = new PreparePluginPushMsgApkBloc("plugin-publish-notype-debug.apk");
    private static final PreparePluginPushMsgApkBloc sPluginAppReleaseBloc = new PreparePluginPushMsgApkBloc("plugin-third-party-debug.apk");
    private final Map<String, InstalledApk> mPluginMap = new HashMap<>();
    private ShadowPluginLoader mPluginLoader;

    public final static String PART_PUSH_MSG = "partPushMsg";
    public final static String PART_SCAN_QR = "partScanQR";
    public final static String PLUGIN_HELP_FEEDBACK = "PLUGIN_HELP_FEEDBACK";
    public final static String PART_INTELL_REPORT = "partIntellReport";
    public final static String PART_PUBLISH_SOS = "partPublishSos";
    public final static String PART_Publish_Notype = "partPublishNotype";
    public final static String PART_AppRelease = "PART_AppRelease";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TestHostTheme);
        setContentView(R.layout.activity_main);
        application = (HostApplication) getApplication();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        ShadowPluginLoader loader = mPluginLoader = new TestPluginLoader(getApplicationContext());
        loader.onCreate();
        DelegateProviderHolder.setDelegateProvider(loader.getDelegateProviderKey(), loader);
        ContentProviderDelegateProviderHolder.setContentProviderDelegateProvider(loader);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        throw new RuntimeException("必须赋予权限.");
                    }
                }
            }
        }
    }

    public void startPlugin(View view) {
//        application.loadPlugin(PART_MAIN, new Runnable() {
//            @Override
//            public void run() {
//                Intent pluginIntent = new Intent();
//                pluginIntent.setClassName(getPackageName(), "com.tencent.shadow.test.plugin.general_cases.lib.usecases.activity.TestListActivity");
//                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
//                Intent intent = application.getPluginLoader().getMComponentManager().convertPluginActivityIntent(pluginIntent);
//                startActivity(intent);
//            }http://192.168.81.190:9090/getFunList?code=HOST
//        });

    }

    public void btn_host(View view) {
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);
    }

    public void btn_init_plugin_manage(View view) {
        ShadowPluginLoader loader = mPluginLoader = new TestPluginLoader(getApplicationContext());
        loader.onCreate();
        DelegateProviderHolder.setDelegateProvider(loader.getDelegateProviderKey(), loader);
        ContentProviderDelegateProviderHolder.setContentProviderDelegateProvider(loader);
    }

    public void btn_plugin_list(View view) {
        Intent intent = new Intent(MainActivity.this, PluginListActivity.class);
        startActivity(intent);
    }


    public void btn_load_push_msg(View view) {
        //推送消息插件
        InstalledApk pushMsgApk = sPluginPushMsgBloc.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PART_PUSH_MSG, pushMsgApk);
//                InstalledApk installedApk = mPluginMap.get(PART_PUSH_MSG);

//        mPluginMap.put(PART_PUSH_MSG, pushMsgApk);
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

    public void btn_push_msg(View view) {


        loadPlugin(PART_PUSH_MSG, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.pushmsg.activity.WebActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });

//        Intent pluginIntent = new Intent();
//        pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.pushmsg.activity.WebActivity");
//        pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
//        Intent intent = application.getPluginLoader().getMComponentManager().convertPluginActivityIntent(pluginIntent);
//        startActivity(intent);
    }

    public void btn_get_fragment_class(View view) {

    }

    public void btn_load_scan_qr(View view) {
        //推送消息插件
        InstalledApk scanQrApk = sPluginScanQRBloc.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PART_SCAN_QR, scanQrApk);
    }

    public void btn_scan_qr(View view) {
        loadPlugin(PART_SCAN_QR, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.scanqr.activity.WebActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
    }

    public void btn_database(View view) {
//        startActivity(new Intent(MainActivity.this, KotlinExampleActivity.class));
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        realm.close();
        T.success(MainActivity.this, "数据库已经清空");
    }

    public void btn_login(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void btn_out_log(View view) {
        SPUtils.put(MainActivity.this, C.sp_token, "");
    }

    public void btn_guidance(View view) {
        SPUtils.put(MainActivity.this, C.sp_slider, false);
    }

    public void btn_open_three_apk(View view) {
        InstalledApk scanIntellReport = sPluginAppReleaseBloc.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PART_AppRelease, scanIntellReport);
        loadPlugin(PART_AppRelease, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.PublishIntellReportActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                pluginIntent.putExtra("token", "123456789");
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
    }

    public void btn_ip(View view){
        EditText edt_ip = findViewById(R.id.edt_ip);
        Api.NEW_JAVA_SERVICE_URL = edt_ip.getText().toString();
    }
    public void btn_fun1(View view){
        EditText edt_main_api1 = findViewById(R.id.edt_main_api1);
        Api.MAIN_FUN_API = edt_main_api1.getText().toString();
    }
    public void btn_fun2(View view){
        EditText edt_main_api2 = findViewById(R.id.edt_main_api2);
        Api.MAIN_FUN_API = edt_main_api2.getText().toString();
    }

    public void btn_old_ip(View view){
        EditText btn_old_ip = findViewById(R.id.edt_old_ip);
        Api.JAVA_SERVICE_URL = btn_old_ip.getText().toString();
    }
    public void btn_download_service(View view){
//        Intent jobIntent = new Intent(this, SimpleJobIntentService.class);
//        jobIntent.putExtra("token", "user");
//        startService(jobIntent);
//
//        int RSS_JOB_ID = 1000;
//        SimpleJobIntentService jobIntentService = new SimpleJobIntentService();
//        jobIntentService.enqueueWork(MainActivity.this, SimpleJobIntentService.class, RSS_JOB_ID, jobIntent);
    }

    public void btn_download_service2(View view){
//        Intent intent = new Intent();
//        intent.putExtra("downloadUrl", "http://192.168.81.182:9090/");
//        YourService.enqueueWork(MainActivity.this, intent);
    }

    public void btn_help_feedback(View view) {
        InstalledApk scanHelpFeedback = sPluginHelpFeedbackBloc.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PLUGIN_HELP_FEEDBACK, scanHelpFeedback);
        loadPlugin(PLUGIN_HELP_FEEDBACK, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.HelpFeedbackActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                pluginIntent.putExtra("token", (String) SPUtils.get(MainActivity.this, C.sp_token, "123456789"));
                pluginIntent.putExtra("title", "帮助反馈");
                pluginIntent.putExtra("params", "[oid:12]");
                pluginIntent.putExtra("service", Api.JAVA_SERVICE_URL);
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
    }

    public void btn_intell_report(View view) {
        InstalledApk scanIntellReport = sPluginIntellReportBloc.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PART_INTELL_REPORT, scanIntellReport);
        loadPlugin(PART_INTELL_REPORT, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.PublishIntellReportActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                pluginIntent.putExtra("token", (String) SPUtils.get(MainActivity.this, C.sp_token, "123456789"));
                pluginIntent.putExtra("title", "信息报告");
                pluginIntent.putExtra("params", "[oid:33]");
                pluginIntent.putExtra("service", Api.JAVA_SERVICE_URL);
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
    }

    public void btn_publish_sos(View view) {
        InstalledApk scanIntellReport = sPluginPublishSosBloc.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PART_PUBLISH_SOS, scanIntellReport);
        loadPlugin(PART_PUBLISH_SOS, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.PublishSOSActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                pluginIntent.putExtra("token", (String) SPUtils.get(MainActivity.this, C.sp_token, "123456789"));
                pluginIntent.putExtra("title", "求救");
                pluginIntent.putExtra("params", "[oid:31]");
                pluginIntent.putExtra("service", Api.JAVA_SERVICE_URL);
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
    }

    public void btn_publish_notype(View view) {
        InstalledApk scanIntellReport = sPluginPublishNotypeBloc.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PART_Publish_Notype, scanIntellReport);
        loadPlugin(PART_Publish_Notype, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.PublishNoTypeArticleActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                pluginIntent.putExtra("token", (String) SPUtils.get(MainActivity.this, C.sp_token, "123456789"));
                pluginIntent.putExtra("title", "无类型文章");
                pluginIntent.putExtra("params", "[oid:31]");
                pluginIntent.putExtra("service", Api.JAVA_SERVICE_URL);
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
    }

    public void btn_publish_type(View view) {
        InstalledApk scanIntellReport = sPluginPublishNotypeBloc.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PART_Publish_Notype, scanIntellReport);
        loadPlugin(PART_Publish_Notype, new Runnable() {
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.zzz.myemergencyclientnew.PublishTypeArticleActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                pluginIntent.putExtra("token", (String) SPUtils.get(MainActivity.this, C.sp_token, "123456789"));
                pluginIntent.putExtra("title", "有类型文章");
                pluginIntent.putExtra("params", "[oid:121]");
                pluginIntent.putExtra("service", Api.JAVA_SERVICE_URL);
                Intent intent = mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
    }
}
