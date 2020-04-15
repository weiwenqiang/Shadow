package com.zzz.myemergencyclientnew.eventbus;

public class EvnApkPlugin {
    public String apkFileName;
    public String plugCode;
    public String enter;
    public String title;
    public String params;

//    public EvnApkPlugin(String apkFileName, String plugCode, String enter) {
//        this.apkFileName = apkFileName;
//        this.plugCode = plugCode;
//        this.enter = enter;
//    }

    public EvnApkPlugin(String apkFileName, String plugCode, String enter, String title, String params) {
        this.apkFileName = apkFileName;
        this.plugCode = plugCode;
        this.enter = enter;
        this.title = title;
        this.params = params;
    }
}
