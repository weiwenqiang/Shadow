package com.zzz.myemergencyclientnew.entity;

public class HomeBottomNavigationEntity {
    //功能名字
    public String title;
    //常态图标
    public String icon_normal;
    //选中图标
    public String icon_checked;
    //接口地址
    public String api;

    public HomeBottomNavigationEntity(String title, String icon_normal, String icon_checked, String api) {
        this.title = title;
        this.icon_normal = icon_normal;
        this.icon_checked = icon_checked;
        this.api = api;
    }
}
