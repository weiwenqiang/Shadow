package com.zzz.myemergencyclientnew.entity.tangram;

public class MeItemView extends BaseFunEntity  {

    //常态图标(选择或上传)
    public String iconNormal;
    //背景资源
    public String background;

    public MeItemView(String title, String iconNormal, String background) {
        super(501, title);
        this.iconNormal = iconNormal;
        this.background = background;
    }
}
