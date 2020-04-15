package com.zzz.myemergencyclientnew.entity;

import java.util.List;

public class NavigationEntity extends BaseNavigationEntity {
    //常态图标(选择或上传)
    public String iconNormal;
    //选中图标(选择或上传)
    public String iconChecked;
    //显示方位 t b l r f 上下左右漂浮（下拉框，5选项）
    public String orient;

    public List<NavigationEntity> child;

    public List<NavigationEntity> getChild() {
        return child;
    }

    public void setChild(List<NavigationEntity> child) {
        this.child = child;
    }

    public NavigationEntity(String title, String params,String iconNormal, String iconChecked, String orient) {
        super(title, params);
        this.iconNormal = iconNormal;
        this.iconChecked = iconChecked;
        this.orient = orient;
    }
}
