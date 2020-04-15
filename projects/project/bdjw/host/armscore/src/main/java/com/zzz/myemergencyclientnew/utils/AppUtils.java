//package com.zzz.myemergencyclientnew.utils;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.app.Service;
//import android.content.Context;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.res.Configuration;
//import android.net.ConnectivityManager;
//import android.net.Network;
//import android.net.NetworkInfo;
//import android.os.Build;
//import android.provider.Settings;
//import android.text.TextUtils;
//import android.view.inputmethod.InputMethodManager;
//
//
//import com.zzz.myemergencyclientnew.host.HostApplication;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * Created by Administrator on 2016/4/26.
// * 跟App相关的辅助类
// */
//public class AppUtils {
//
//    public static final String OS_TYPE = "1";
//    public static final String API_VERSION = "1.0";
//
//    private AppUtils() {
//        /* cannot be instantiated */
//        throw new UnsupportedOperationException("cannot be instantiated");
//
//    }
//
//    /**
//     * 获取应用程序名称
//     */
//    public static String getAppName(Context context) {
//        try {
//            PackageManager packageManager = context.getPackageManager();
//            PackageInfo packageInfo = packageManager.getPackageInfo(
//                    context.getPackageName(), 0);
//            int labelRes = packageInfo.applicationInfo.labelRes;
//            return context.getResources().getString(labelRes);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * [获取应用程序版本名称信息]
//     *
//     * @param context
//     * @return 当前应用的版本名称
//     */
//    public static String getVersionName(Context context) {
//        try {
//            PackageManager packageManager = context.getPackageManager();
//            PackageInfo packageInfo = packageManager.getPackageInfo(
//                    context.getPackageName(), 0);
//            return packageInfo.versionName;
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * [获取应用程序版本名称信息]
//     *
//     * @param context
//     * @return 当前应用的版本名称
//     */
//    public static int getVersionCode(Context context) {
//        try {
//            PackageManager packageManager = context.getPackageManager();
//            PackageInfo packageInfo = packageManager.getPackageInfo(
//                    context.getPackageName(), 0);
//            return packageInfo.versionCode;
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    /**
//     * 获取设备SN
//     * @return
//     */
////    public static String getSN() {//失败
////        String v = "";
////        try {
////            Class<?> c = Class.forName("android.os.SystemProperties");
////            Method get = c.getMethod("get", String.class, String.class);
////            v = (String) (get.invoke(c, "ro.serialno", "unknown"));
////        } catch (Exception e) {
////        }
////        return v;
////    }
//    /**
//     * 通过android.os获取sn号
//     */
//    public static String getSN() {
//        String serialNumber = Build.SERIAL;
//        return serialNumber;
//    }
//
//    /**
//     * 方法（一）通过反射获取sn号
//     */
//    public static String getDeviceSN(){
//        String serial = null;
//        try {
//            Class<?> c =Class.forName("android.os.SystemProperties");
//            Method get =c.getMethod("get", String.class);
//            serial = (String)get.invoke(c, "ro.serialno");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return serial;
//    }
//
//    @SuppressLint({"NewApi", "MissingPermission"})
//    public static String getSerialNumber() {
//        String serial = "";
//        try {
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {//8.0+
//                serial = Build.SERIAL;
//            } else {//8.0-
//                Class<?> c = Class.forName("android.os.SystemProperties");
//                Method get = c.getMethod("get", String.class);
//                serial = (String) get.invoke(c, "ro.serialno");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return serial;
//    }
//    /**
//     * 判断服务是否运行
//     *
//     * @param context
//     * @param clazz   要判断的服务的class
//     * @return
//     */
//    public static boolean isServiceRunning(Context context,
//                                           Class<? extends Service> clazz) {
//        ActivityManager manager = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//
//        List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(100);
//        for (int i = 0; i < services.size(); i++) {
//            String className = services.get(i).service.getClassName();
//            if (className.equals(clazz.getName())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Return pseudo unique ID
//     *
//     * @return ID
//     */
//    public static String getUniquePsuedoID() {
//        // If all else fails, if the user does have lower than API 9 (lower
//        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
//        // returns 'null', then simply the ID returned will be solely based
//        // off their Android device information. This is where the collisions
//        // can happen.
//        // Thanks http://www.pocketmagic.net/?p=1662!
//        // Try not to use DISPLAY, HOST or ID - these items could change.
//        // If there are collisions, there will be overlapping data
//        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
//
//        // Thanks to @Roman SL!
//        // http://stackoverflow.com/a/4789483/950427
//        // Only devices with API >= 9 have android.os.Build.SERIAL
//        // http://developer.android.com/reference/android/os/Build.html#SERIAL
//        // If a user upgrades software or roots their device, there will be a duplicate activity_pay_entry
//        String serial = null;
//        try {
//            serial = Build.class.getField("SERIAL").get(null).toString();
//
//            // Go ahead and return the serial for api => 9
//            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
//        } catch (Exception exception) {
//            // String needs to be initialized
//            serial = AppUtils.getAndroidId(); // some value
//        }
//
//        // Thanks @Joe!
//        // http://stackoverflow.com/a/2853253/950427
//        // Finally, combine the values we have found by using the UUID class to create a unique identifier
//        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
//    }
//
//
//    /**
//     * 获取当前手机系统版本号
//     *
//     * @return 系统版本号
//     */
//    public static String getSystemVersion() {
//        return Build.VERSION.RELEASE;
//    }
//
//    /**
//     * 获取手机型号
//     *
//     * @return 手机型号
//     */
//    public static String getSystemModel() {
//        return Build.MODEL;
//    }
//
//
//    /**
//     * 获取进程号对应的进程名
//     *
//     * @param pid 进程号
//     * @return 进程名
//     */
//    public static String getProcessName(int pid) {
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
//            String processName = reader.readLine();
//            if (!TextUtils.isEmpty(processName)) {
//                processName = processName.trim();
//            }
//            return processName;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public static String getAndroidId() {
//        return Settings.System.getString(HostApplication.getApp().getContentResolver(), Settings.Secure.ANDROID_ID);
//    }
//
//    /**
//     * 判断是手机还是平板，如果是平板返回true，是手机返回false
//     */
//    public static boolean isPad(Context context) {
//        return (context.getResources().getConfiguration().screenLayout
//                & Configuration.SCREENLAYOUT_SIZE_MASK)
//                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
//    }
//
//    /**
//     * 关闭软键盘
//     */
//    public static void closeKeybord(Activity activity) {
//        InputMethodManager imm =  (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if(imm != null) {
//            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
//        }
//    }
//
//    /**
//     * 获取wifi和流量连接状态
//     */
//    public static Map<String, Boolean> getNetConn(Activity activity){
//        Map<String, Boolean> netConn = new HashMap<>();
//        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
//        boolean isWifiConn = false;
//        boolean isMobileConn = false;
//        for (Network network : connMgr.getAllNetworks()) {
//            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
//            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                isWifiConn |= networkInfo.isConnected();
//            }
//            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                isMobileConn |= networkInfo.isConnected();
//            }
//        }
//        netConn.put("Wifi", isWifiConn);
//        netConn.put("Mobile", isMobileConn);
//        return netConn;
//    }
//
//    /**
//     * 是否联网
//     */
//    public static boolean isNetConn(Activity activity){
//        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        return networkInfo != null && networkInfo.isConnected();
//    }
//}