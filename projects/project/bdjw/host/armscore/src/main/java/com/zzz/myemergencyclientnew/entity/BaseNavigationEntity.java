package com.zzz.myemergencyclientnew.entity;


import com.zzz.myemergencyclientnew.entity.atom.ApiTangramEntity;
import com.zzz.myemergencyclientnew.entity.atom.PluginEntity;
import com.zzz.myemergencyclientnew.entity.atom.RouteFragmentEntity;
import com.zzz.myemergencyclientnew.entity.atom.WebEntity;

public class BaseNavigationEntity {
    //功能类型
    public int funType;
    //点击事件类型
    public int click;
    //功能和点击事件对应对象
    //标题(手写)
    public String title;
    //携带参数列表【{k,v}，{k,v}，{k,v}】(手写)
    public String params;

    public PluginEntity pluginEntity;

    public WebEntity webEntity;

    public RouteFragmentEntity routeFragmentEntity;

    public ApiTangramEntity apiTangramEntity;

//    public List<BaseNavigationEntity> apiTangramList;

    public String applyList;

    public void initWeb(WebEntity webEntity) {
        this.funType = 1;
        this.click = 1;
        this.webEntity = webEntity;
    }

    public void initRoute(RouteFragmentEntity routeFragmentEntity) {
        this.funType = 2;
        this.click = 2;
        this.routeFragmentEntity = routeFragmentEntity;
    }

    public void initApiTangram(ApiTangramEntity apiTangramEntity){
        this.funType =4;
        this. click =4;
        this.apiTangramEntity = apiTangramEntity;
    }

    public void initPlugin(PluginEntity pluginEntity) {
        this.funType = 1;
        this.click = 9;
        this.pluginEntity = pluginEntity;
    }

    public void initApiTangramList(String applyList){
        this.funType =5;
        this. click =5;
        this.applyList = applyList;
    }

    public BaseNavigationEntity(String title, String params) {
        this.title = title;
        this.params = params;
    }
}
