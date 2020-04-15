package com.zzz.myemergencyclientnew.entity;

import java.util.List;

public class ApkFunctionEntity {
    //标题
    public String title;
    //常态图标
    public String iconNormal;
    //选中图标
    public String iconChecked;
//    //apk插件-api接口
//    public int funType;
//    //apk下载地址
//    public String downloadLink;
//    //android文件夹层级路径
//    public String filePath;
//    //apk文件名
//    public String apkFileName;
//    //apk版本号
//    public int apkVersionCode;
//    //apk版本名
//    public String apkVersionName;
    //显示方位 t b l r f 上下左右漂浮
    public String orient;
    //点击事件类型：导航-路由页面跳转-打开web页面
    public int click;
    //携带参数列表【{k,v}，{k,v}，{k,v}】
    public String params;
    //页面路由地址
    public String route;
    //接口地址
    public String api;
    //接口版本号
    public String apiVersion;
    //功能状态：启用-关闭-废弃-测试
    public String state;

    public List<ApkFunctionEntity> child;

    public List<ApkFunctionEntity> getChild() {
        return child;
    }

    public void setChild(List<ApkFunctionEntity> child) {
        this.child = child;
    }

//    public ApkFunctionEntity(String title, String iconNormal, String iconChecked, int funType, String downloadLink, String filePath, String apkFileName, int apkVersionCode, String apkVersionName, String orient, int click, String params, String route, String api, String apiVersion, int state) {
//        this.title = title;
//        this.iconNormal = iconNormal;
//        this.iconChecked = iconChecked;
//        this.funType = funType;
//        this.downloadLink = downloadLink;
//        this.filePath = filePath;
//        this.apkFileName = apkFileName;
//        this.apkVersionCode = apkVersionCode;
//        this.apkVersionName = apkVersionName;
//        this.orient = orient;
//        this.click = click;
//        this.params = params;
//        this.route = route;
//        this.api = api;
//        this.apiVersion = apiVersion;
//        this.state = state;
//    }

    public ApkFunctionEntity(String title, String iconNormal, String iconChecked, String orient, int click, String params, String route, String api, String apiVersion, String state, List<ApkFunctionEntity> child) {
        this.title = title;
        this.iconNormal = iconNormal;
        this.iconChecked = iconChecked;
        this.orient = orient;
        this.click = click;
        this.params = params;
        this.route = route;
        this.api = api;
        this.apiVersion = apiVersion;
        this.state = state;
        this.child = child;
    }

    public ApkFunctionEntity() {
    }
}
