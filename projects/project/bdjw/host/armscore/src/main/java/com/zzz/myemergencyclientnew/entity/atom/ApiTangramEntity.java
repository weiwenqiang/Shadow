package com.zzz.myemergencyclientnew.entity.atom;

public class ApiTangramEntity {
    //页面路由地址（下拉框加手写）
    public String fragmentRoute;
    //接口地址（下拉框加手写）
    public String api;
    //接口版本号（下拉框加手写）
    public String apiVersion;

    public ApiTangramEntity(String fragmentRoute, String api) {
        this.fragmentRoute = fragmentRoute;
        this.api = api;
        this.apiVersion= "v1";
    }
}
