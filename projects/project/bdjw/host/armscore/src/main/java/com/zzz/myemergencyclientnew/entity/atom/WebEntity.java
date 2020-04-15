package com.zzz.myemergencyclientnew.entity.atom;

public class WebEntity {
    //页面路由地址（下拉框加手写）
    public String route;
    //Web地址
    public String url;

    public WebEntity(String route, String url) {
        this.route = route;
        this.url = url;
    }

    public WebEntity() {
    }
}
