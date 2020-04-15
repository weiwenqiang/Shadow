//package com.zzz.myemergencyclientnew.utils;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.util.Log;
//
//import java.util.Map;
//
//public class SPUtils {
//    /**
//     * 保存在手机里面的文件名
//     */
//    public static final String FILE_NAME = "share_data";
//
//    /**
//     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
//     */
//    public static void put(Context context, String key, Object object) {
//
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//
//        if (object instanceof String) {
//            editor.putString(key, (String) object);
//        } else if (object instanceof Integer) {
//            editor.putInt(key, (Integer) object);
//        } else if (object instanceof Boolean) {
//            editor.putBoolean(key, (Boolean) object);
//        } else if (object instanceof Float) {
//            editor.putFloat(key, (Float) object);
//        } else if (object instanceof Long) {
//            editor.putLong(key, (Long) object);
//        } else {
//            editor.putString(key, object.toString());
//        }
//        editor.commit();
//    }
//
//    /**
//     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
//     */
//    public static Object get(Context context, String key, Object defaultObject) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//
//        if (defaultObject instanceof String) {
//            return sp.getString(key, (String) defaultObject);
//        } else if (defaultObject instanceof Integer) {
//            return sp.getInt(key, (Integer) defaultObject);
//        } else if (defaultObject instanceof Boolean) {
//            return sp.getBoolean(key, (Boolean) defaultObject);
//        } else if (defaultObject instanceof Float) {
//            return sp.getFloat(key, (Float) defaultObject);
//        } else if (defaultObject instanceof Long) {
//            return sp.getLong(key, (Long) defaultObject);
//        }
//
//        return null;
//    }
//
//    /**
//     * 移除某个key值已经对应的值
//     */
//    public static void remove(Context context, String key) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.remove(key);
//        editor.commit();
//    }
//
//    /**
//     * 清除所有数据
//     */
//    public static void clear(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
//        editor.commit();
//    }
//
//    /**
//     * 查询某个key是否已经存在
//     */
//    public static boolean contains(Context context, String key) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        return sp.contains(key);
//    }
//
//    /**
//     * 返回所有的键值对
//     */
//    public static Map<String, ?> getAll(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
//                Context.MODE_PRIVATE);
//        return sp.getAll();
//    }
//
//
//    /**
//     * 根据key和预期的value类型获取value的值
//     */
//    public static  <T> T getValue(Context context, String key, Class<T> clazz) {
//        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//        return getValue(key, clazz, sp);
//    }
//
//    /**
//     * 对于外部不可见的过渡方法
//     */
//    private static  <T> T getValue(String key, Class<T> clazz, SharedPreferences sp) {
//        T t;
//        try {
//
//            t = clazz.newInstance();
//
//            if (t instanceof Integer) {
//                return (T) Integer.valueOf(sp.getInt(key, 0));
//            } else if (t instanceof String) {
//                return (T) sp.getString(key, "");
//            } else if (t instanceof Boolean) {
//                return (T) Boolean.valueOf(sp.getBoolean(key, false));
//            } else if (t instanceof Long) {
//                return (T) Long.valueOf(sp.getLong(key, 0L));
//            } else if (t instanceof Float) {
//                return (T) Float.valueOf(sp.getFloat(key, 0L));
//            }
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
//        }
//        Log.e("system", "无法找到" + key + "对应的值");
//        return null;
//    }
//}
