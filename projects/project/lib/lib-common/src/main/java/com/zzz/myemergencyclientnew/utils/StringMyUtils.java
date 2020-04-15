package com.zzz.myemergencyclientnew.utils;

import com.zzz.myemergencyclientnew.constant.pref.C;

public class StringMyUtils {
    /**
     * 地址截调前字符，缩减地址，用于显示
     */
    public static String showAddress(String addr){
        String showAddr = addr.replace(C.country, "");
        showAddr = showAddr.replace(C.province, "");
        showAddr = showAddr.replace(C.city, "");
        return showAddr;
    }
}
