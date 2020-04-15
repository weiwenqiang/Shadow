/*
 * MIT License
 *
 * Copyright (c) 2018 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zzz.myemergencyclientnew.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzz.myemergencyclientnew.activity.home.HomeActivity;
import com.zzz.myemergencyclientnew.eventbus.EvnApkPlugin;
import com.zzz.myemergencyclientnew.host.HostApplication;
import com.zzz.myemergencyclientnew.host.PluginApkBloc;
import com.zzz.myemergencyclientnew.host.TestComponentManager;
import com.zzz.myemergencyclientnew.utils.SPUtils;
import com.tencent.shadow.core.common.InstalledApk;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.SimpleClickSupport;
import com.toast.T;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;


/**
 * Created by longerian on 17/3/7.
 */

public class SampleClickSupport extends SimpleClickSupport {
    private Activity activity;
    public SampleClickSupport(Activity activity) {
        setOptimizedMode(true);
        this.activity =activity;
    }

    @Override
    public void defaultClick(View targetView, BaseCell cell, int eventType) {
        super.defaultClick(targetView, cell, eventType);
        try {
//            switch (cell.type){
//                case 270:
//                    ARouter.getInstance().build(C.NewsDetails).navigation();
//                    break;
//                case 260:
//                    T.success(cell.optStringParam("title"));
//                    break;
//                case 199:
//
//                    break;
//            }
            int click = cell.optIntParam("click");
            if(click == 1){
                JSONObject webEntity = cell.optJsonObjectParam("webEntity");

                String url = webEntity.getString("url");
                String route = webEntity.getString("route");
                ARouter.getInstance().build(route).withString("url", url).navigation();
            }else if(click ==2){
                JSONObject routeEntity = cell.optJsonObjectParam("routeFragmentEntity");

                String route = routeEntity.getString("route");
                String fragmentRoute = routeEntity.getString("fragmentRoute");
                String title = cell.optStringParam("title");
//                String oid = cell.optStringParam("oid");
                ARouter.getInstance().build(route).withString("fragmentRoute", fragmentRoute).withString("title", title).navigation();
            }else if(click ==3){
                String route = cell.optStringParam("route");
                String oid = cell.optStringParam("oid");
                ARouter.getInstance().build(route).withString("oid", oid).navigation();
            }else if(click == 4){//文章详情
                String route = cell.optStringParam("route");
                String oid = cell.optStringParam("oid");
//                String url = Api.VUE_URL + "#/home/detail?Art=%5Bobject%20Object%5D&jwttoken=" +C.getToken()+"&type=android";
//                ARouter.getInstance().build(route).withString("url", url).navigation();
            }else if(click == 5){
                String msg = cell.optStringParam("msg");
//                T.info(msg);
            }else if(click == 6){
//                ARouter.getInstance().build(C.AdWhiteCardDialog).navigation();
            }else if(click == 7){
                JSONObject routeEntity = cell.optJsonObjectParam("routeActivityEntity");

                String title = cell.optStringParam("title");
                String params = cell.optStringParam("params");

                String route = routeEntity.getString("route");
                String api = routeEntity.getString("api");

                ARouter.getInstance().build(route).withString("title", title).withString("api", api).withString("params", params).navigation();
            }else if(click == 9){
                JSONObject pluginEntity = cell.optJsonObjectParam("pluginEntity");

                String title = cell.optStringParam("title");
                String params = cell.optStringParam("params");
//                val plugCode = pluginEntity.getString("plugCode")
                String apkFileName = pluginEntity.getString("apkFileName");
                String plugCode = pluginEntity.getString("plugCode");
                String enter = pluginEntity.getString("enter");
//                HomeActivity homeActivity = (HomeActivity) activity;
//                InstalledApk apk  = new PluginApkBloc("plugin-help-feedback-debug.apk").preparePlugin(homeActivity);
//                homeActivity.mPluginMap.put("PLUGIN_HELP_FEEDBACK", apk);
//                homeActivity.loadPlugin("PLUGIN_HELP_FEEDBACK", new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent pluginIntent = new Intent();
//                        pluginIntent.setClassName(homeActivity.getPackageName(), "com.zzz.myemergencyclientnew.HelpFeedbackActivity");
////                        pluginIntent.setClassName(homeActivity.getPackageName(), enter);
//                        pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
//                        pluginIntent.putExtra("token", "123456789");
//                        Intent intent = HostApplication.mPluginLoader.getMComponentManager().convertPluginActivityIntent(pluginIntent);
//                        homeActivity.startActivity(intent);
//
////                                        Intent pluginIntent = new Intent();
////                pluginIntent.setClassName(getPackageName(), "com.tencent.shadow.test.plugin.general_cases.lib.usecases.activity.TestListActivity");
////                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
////                Intent intent = application.getPluginLoader().getMComponentManager().convertPluginActivityIntent(pluginIntent);
////                startActivity(intent);
//                    }
//                });
                boolean isReady = (boolean) SPUtils.get(activity, plugCode, false);
                if(isReady){
                    EventBus.getDefault().post(new EvnApkPlugin(apkFileName,plugCode,enter, title, params));
                }else{
                    T.info(activity, "还未加载");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
