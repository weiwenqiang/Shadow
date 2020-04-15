package com.zzz.myemergencyclientnew.entity.atom;

public class RouteFragmentEntity {
    //页面路由地址（下拉框加手写）
    public String route;

    public String fragmentRoute;

    public RouteFragmentEntity() {
    }

    public RouteFragmentEntity(String route, String fragmentRoute) {
        this.route = route;
        this.fragmentRoute = fragmentRoute;
    }
}
