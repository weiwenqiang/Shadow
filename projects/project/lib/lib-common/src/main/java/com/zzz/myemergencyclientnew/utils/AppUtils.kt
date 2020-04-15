package com.zzz.myemergencyclientnew.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*

/**
 * Created by Administrator on 2016/4/26.
 * 跟App相关的辅助类
 */
class AppUtils private constructor() {
    companion object {
        const val OS_TYPE = "1"
        const val API_VERSION = "1.0"

        /**
         * 获取应用程序名称
         */
        fun getAppName(context: Context): String? {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
                )
                val labelRes = packageInfo.applicationInfo.labelRes
                return context.resources.getString(labelRes)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * [获取应用程序版本名称信息]
         *
         * @param context
         * @return 当前应用的版本名称
         */
        fun getVersionName(context: Context): String? {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
                )
                return packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * [获取应用程序版本名称信息]
         *
         * @param context
         * @return 当前应用的版本名称
         */
        @JvmStatic
        fun getVersionCode(context: Context): Int {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
                )
                return packageInfo.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return 0
        }
        /**
         * 获取设备SN
         * @return
         */
        //    public static String getSN() {//失败
        //        String v = "";
        //        try {
        //            Class<?> c = Class.forName("android.os.SystemProperties");
        //            Method get = c.getMethod("get", String.class, String.class);
        //            v = (String) (get.invoke(c, "ro.serialno", "unknown"));
        //        } catch (Exception e) {
        //        }
        //        return v;
        //    }
        /**
         * 通过android.os获取sn号
         */
        val sN: String
            get() = Build.SERIAL

        /**
         * 方法（一）通过反射获取sn号
         */
        val deviceSN: String?
            get() {
                var serial: String? = null
                try {
                    val c = Class.forName("android.os.SystemProperties")
                    val get = c.getMethod("get", String::class.java)
                    serial = get.invoke(c, "ro.serialno") as String
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return serial
            }//8.0-

        //8.0+
        @get:SuppressLint("NewApi", "MissingPermission")
        val serialNumber: String?
            get() {
                var serial: String? = ""
                try {
                    serial = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) { //8.0+
                        Build.SERIAL
                    } else { //8.0-
                        val c =
                            Class.forName("android.os.SystemProperties")
                        val get =
                            c.getMethod("get", String::class.java)
                        get.invoke(c, "ro.serialno") as String
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return serial
            }

        /**
         * 判断服务是否运行
         *
         * @param context
         * @param clazz   要判断的服务的class
         * @return
         */
        fun isServiceRunning(
            context: Context,
            clazz: Class<out Service?>
        ): Boolean {
            val manager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val services =
                manager.getRunningServices(100)
            for (i in services.indices) {
                val className = services[i].service.className
                if (className == clazz.name) {
                    return true
                }
            }
            return false
        }

        /**
         * Return pseudo unique ID
         *
         * @return ID
         */
        fun getUniquePsuedoID(context: Context): String {
            // If all else fails, if the user does have lower than API 9 (lower
            // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
            // returns 'null', then simply the ID returned will be solely based
            // off their Android device information. This is where the collisions
            // can happen.
            // Thanks http://www.pocketmagic.net/?p=1662!
            // Try not to use DISPLAY, HOST or ID - these items could change.
            // If there are collisions, there will be overlapping data
            val m_szDevIDShort =
                "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10

            // Thanks to @Roman SL!
            // http://stackoverflow.com/a/4789483/950427
            // Only devices with API >= 9 have android.os.Build.SERIAL
            // http://developer.android.com/reference/android/os/Build.html#SERIAL
            // If a user upgrades software or roots their device, there will be a duplicate activity_pay_entry
            var serial: String? = null
            try {
                serial = Build::class.java.getField("SERIAL")[null].toString()

                // Go ahead and return the serial for api => 9
                return UUID(
                    m_szDevIDShort.hashCode().toLong(),
                    serial.hashCode().toLong()
                ).toString()
            } catch (exception: Exception) {
                // String needs to be initialized
                serial = getAndroidId(context) // some value
            }

            // Thanks @Joe!
            // http://stackoverflow.com/a/2853253/950427
            // Finally, combine the values we have found by using the UUID class to create a unique identifier
            return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
        }

        /**
         * 获取当前手机系统版本号
         *
         * @return 系统版本号
         */
        val systemVersion: String
            get() = Build.VERSION.RELEASE

        /**
         * 获取手机型号
         *
         * @return 手机型号
         */
        val systemModel: String
            get() = Build.MODEL

        /**
         * 获取进程号对应的进程名
         *
         * @param pid 进程号
         * @return 进程名
         */
        fun getProcessName(pid: Int): String? {
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
                var processName = reader.readLine()
                if (!TextUtils.isEmpty(processName)) {
                    processName = processName.trim { it <= ' ' }
                }
                return processName
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            } finally {
                try {
                    reader?.close()
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }
            return null
        }

        fun getAndroidId(context: Context): String {
            return Settings.System.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        /**
         * 判断是手机还是平板，如果是平板返回true，是手机返回false
         */
        fun isPad(context: Context): Boolean {
            return ((context.resources.configuration.screenLayout
                    and Configuration.SCREENLAYOUT_SIZE_MASK)
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE)
        }

        /**
         * 关闭软键盘
         */
        @JvmStatic
        fun closeKeybord(activity: Activity) {
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }

        /**
         * 获取wifi和流量连接状态
         */
        fun getNetConn(activity: Activity): Map<String, Boolean> {
            val netConn: MutableMap<String, Boolean> =
                HashMap()
            val connMgr =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var isWifiConn = false
            var isMobileConn = false
            for (network in connMgr.allNetworks) {
                val networkInfo = connMgr.getNetworkInfo(network)
                if (networkInfo!!.type == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = isWifiConn or networkInfo.isConnected
                }
                if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = isMobileConn or networkInfo.isConnected
                }
            }
            netConn["Wifi"] = isWifiConn
            netConn["Mobile"] = isMobileConn
            return netConn
        }

        /**
         * 是否联网
         */
        @JvmStatic
        fun isNetConn(activity: Activity): Boolean {
            val connMgr =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }
}