package com.zzz.myemergencyclientnew.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    const val FILE_NAME = "share_data"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    @JvmStatic
    fun put(
        context: Context,
        key: String?,
        `object`: Any
    ) {
        val sp = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        if (`object` is String) {
            editor.putString(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }
        editor.commit()
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    @JvmStatic
    operator fun get(
        context: Context,
        key: String?,
        defaultObject: Any?
    ): Any? {
        val sp = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        if (defaultObject is String) {
            return sp.getString(key, defaultObject as String?)
        } else if (defaultObject is Int) {
            return sp.getInt(key, (defaultObject as Int?)!!)
        } else if (defaultObject is Boolean) {
            return sp.getBoolean(key, (defaultObject as Boolean?)!!)
        } else if (defaultObject is Float) {
            return sp.getFloat(key, (defaultObject as Float?)!!)
        } else if (defaultObject is Long) {
            return sp.getLong(key, (defaultObject as Long?)!!)
        }
        return null
    }

    /**
     * 移除某个key值已经对应的值
     */
    fun remove(context: Context, key: String?) {
        val sp = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.remove(key)
        editor.commit()
    }

    /**
     * 清除所有数据
     */
    fun clear(context: Context) {
        val sp = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.clear()
        editor.commit()
    }

    /**
     * 查询某个key是否已经存在
     */
    fun contains(context: Context, key: String?): Boolean {
        val sp = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        return sp.contains(key)
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(context: Context): Map<String, *> {
        val sp = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        return sp.all
    }

    /**
     * 根据key和预期的value类型获取value的值
     */
    fun <T> getValue(
        context: Context,
        key: String,
        clazz: Class<T>
    ): T? {
        val sp = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        return getValue(key, clazz, sp)
    }

    /**
     * 对于外部不可见的过渡方法
     */
    private fun <T> getValue(
        key: String,
        clazz: Class<T>,
        sp: SharedPreferences
    ): T? {
        val t: T
        try {
            t = clazz.newInstance()
            if (t is Int) {
                return Integer.valueOf(sp.getInt(key, 0)) as T
            } else if (t is String) {
                return sp.getString(key, "") as T?
            } else if (t is Boolean) {
                return java.lang.Boolean.valueOf(sp.getBoolean(key, false)) as T
            } else if (t is Long) {
                return java.lang.Long.valueOf(sp.getLong(key, 0L)) as T
            } else if (t is Float) {
                return java.lang.Float.valueOf(sp.getFloat(key, 0f)) as T
            }
        } catch (e: InstantiationException) {
            e.printStackTrace()
            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.message + "]")
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.message + "]")
        }
        Log.e("system", "无法找到" + key + "对应的值")
        return null
    }
}