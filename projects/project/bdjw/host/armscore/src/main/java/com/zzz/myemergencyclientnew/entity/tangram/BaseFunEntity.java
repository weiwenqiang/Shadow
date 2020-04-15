package com.zzz.myemergencyclientnew.entity.tangram;

import com.zzz.myemergencyclientnew.entity.atom.PluginEntity;
import com.zzz.myemergencyclientnew.entity.atom.RouteActivityEntity;
import com.zzz.myemergencyclientnew.entity.atom.RouteFragmentEntity;
import com.zzz.myemergencyclientnew.entity.atom.WebEntity;

public class BaseFunEntity extends BaseTangramView {
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

    public RouteActivityEntity routeActivityEntity;

    public void initWeb(WebEntity webEntity) {
        this.funType = 1;
        this.click = 1;
        this.webEntity = webEntity;
    }

    public void initRouteFragment(RouteFragmentEntity routeFragmentEntity) {
        this.funType = 2;
        this.click = 2;
        this.routeFragmentEntity = routeFragmentEntity;
    }

    public void initRouteActivity(RouteActivityEntity routeActivityEntity) {
        this.funType = 7;
        this.click = 7;
        this.routeActivityEntity = routeActivityEntity;
    }

    public void initPlugin(PluginEntity pluginEntity) {
        this.funType = 1;
        this.click = 9;
        this.pluginEntity = pluginEntity;
    }

    public BaseFunEntity(int type, String title) {
        super(type);
        this.title= title;
    }
}
