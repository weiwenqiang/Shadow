package com.zzz.myemergencyclientnew.entity.tangram;

public class MeUserHeadEntity extends BaseFunEntity  {

    //用户名
    public String userName;
    //用户头像
    public String imgHeadPortrait;

    public MeUserHeadEntity(String userName, String imgHeadPortrait) {
        super(500, "个人资料");
        this.userName = userName;
        this.imgHeadPortrait = imgHeadPortrait;
    }
}