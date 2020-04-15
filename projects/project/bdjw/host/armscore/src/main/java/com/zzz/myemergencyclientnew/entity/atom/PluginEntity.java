package com.zzz.myemergencyclientnew.entity.atom;

public class PluginEntity {
    //唯一标识
    public String plugCode;
    //数据库ID，不能重复
    public String id;
    //apk下载地址（手写或选择）
    public String downloadLink;
    //android文件夹层级路径（手写）
    public String filePath;
    //apk文件名（手写）
    public String apkFileName;
    //apk版本号（手写或系统自动生成）
    public int apkVersionCode;
    //apk版本名（手写）
    public String apkVersionName;
    //入口地址
    public String enter;
    //广播入口
    public String receiver;
    //广播携带标识
    public String receiverAction;


//    public PluginEntity(String id, String title, String downloadLink,  String apkFileName, int apkVersionCode, String apkVersionName, String params, String enter, String plugCode) {
//        this.id = id;
//        this.downloadLink = downloadLink;
//        this.filePath = "/";
//        this.apkFileName = apkFileName;
//        this.apkVersionCode = apkVersionCode;
//        this.apkVersionName = apkVersionName;
//        this.enter = enter;
//        this.receiver = "com.zzz.myemergencyclientnew.MyReceiver";
//        this.receiverAction = "com.action";
//        this.plugCode = plugCode;
//    }

    public PluginEntity(String plugCode, String id, String downloadLink, String apkFileName, int apkVersionCode, String apkVersionName, String enter) {
        this.plugCode = plugCode;
        this.id = id;
        this.downloadLink = downloadLink;
        this.filePath = "/";
        this.apkFileName = apkFileName;
        this.apkVersionCode = apkVersionCode;
        this.apkVersionName = apkVersionName;
        this.enter = enter;
        this.receiver = "com.zzz.myemergencyclientnew.MyReceiver";
        this.receiverAction = "com.action";
    }

    public PluginEntity() {
    }
}
