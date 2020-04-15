package com.zzz.myemergencyclientnew.entity.atom;

public class RouteActivityEntity {
    //页面路由地址（下拉框加手写）
    public String route;
    //Api地址
    public String api;

    public RouteActivityEntity(String route, String api) {
        this.route = route;
        this.api = api;
    }
}
