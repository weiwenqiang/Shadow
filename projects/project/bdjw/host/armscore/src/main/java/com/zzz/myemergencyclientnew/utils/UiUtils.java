//package com.zzz.myemergencyclientnew.utils;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Point;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.util.DisplayMetrics;
//import android.util.TypedValue;
//import android.view.Display;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.view.WindowManager;
//import android.widget.ImageView;
//
//import com.zzz.myemergencyclientnew.R;
//import com.zzz.myemergencyclientnew.host.HostApplication;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by 001 on 2017/2/22.
// */
//
//public class UiUtils {
//    public static String[] getStringArray(int array_id) {
//        return getResource().getStringArray(array_id);
//    }
//
//    public static Resources getResource() {
//        return HostApplication.getApp().getResources();
//    }
//
//    public static Context getContext() {
//        return HostApplication.getApp();
//    }
//
//    /**
//     * dip转换px
//     */
//    public static int dp2px(int dip) {
//        final float scale = getResource().getDisplayMetrics().density;
//        return (int) (dip * scale + 0.5f);
//    }
//
//    public static int dp2px(float dip) {
//        final float scale = getResource().getDisplayMetrics().density;
//        return (int) (dip * scale + 0.5f);
//    }
//
//    /**
//     * pxz转换dip
//     */
//
//    public static int px2dp(int px) {
//        final float scale = getResource().getDisplayMetrics().density;
//        return (int) (px / scale + 0.5f);
//    }
//
//    public static float sp2px(float spValue) {
//        if (spValue <= 0) return 0;
//        final float scale = getResource().getDisplayMetrics().scaledDensity;
//        return spValue * scale;
//    }
//
////    /**
////     * 方法提交主线程运行
////     *
////     * @param runnable
////     */
////    public static void runOnUiThread(Runnable runnable) {
////        if (Thread.currentThread().getId() == App.getMainTid()) {
////            runnable.run();
////        } else {
////            App.getHandler().post(runnable);
////        }
////    }
//
//    public static View inflate(int id) {
//        return View.inflate(getContext(), id, null);
//    }
//
//    public static Drawable getDrawalbe(int id) {
//        return getResource().getDrawable(id);
//    }
//
//    public static float getDimens(int homePictureHeight) {
//        return getResource().getDimension(homePictureHeight);
//    }
//
////    //延迟执行当前方法
////    public static void postDelayed(Runnable runnable, int time) {
////        App.getHandler().postDelayed(runnable, time);
////    }
//
////    //取消任务
////    public static void cancel(Runnable runnable) {
////        App.getHandler().removeCallbacks(runnable);
////    }
//
//    //通过反射获取状态栏高度，默认25dp
//    public static int getStatusBarHeight(Context context) {
//        int statusBarHeight = dp2px(25);
//        try {
//            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//            Object object = clazz.newInstance();
//            int height = Integer.parseInt(clazz.getField("status_bar_height")
//                    .get(object).toString());
//            statusBarHeight = context.getResources().getDimensionPixelSize(height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return statusBarHeight;
//    }
//
//    /**
//     * 获取屏幕尺寸
//     */
//    @SuppressWarnings("deprecation")
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    public static Point getScreenSize(Context context) {
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
//            return new Point(display.getWidth(), display.getHeight());
//        } else {
//            Point point = new Point();
//            display.getSize(point);
//            return point;
//        }
//    }
//
//    public static int getDialogTheme(Context context, int attr) {
//        Resources.Theme theme = context.getTheme();
//        if (theme != null) {
//            TypedValue outValue = new TypedValue();
//            theme.resolveAttribute(attr, outValue, true);
//            return outValue.resourceId;
//        }
//        return 0;
//    }
//
//    /**
//     * 获取屏幕内容区宽高
//     *
//     * @param context
//     * @return
//     */
//    public static HashMap<String, Integer> getScreenWidthHeight(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        HashMap<String, Integer> map = new HashMap<>();
//        map.put("height", height);
//        map.put("width", width);
//        return map;
//    }
//
//    public static int getWindowWidth(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        return wm.getDefaultDisplay().getWidth();
//    }
//
//    public static int getWindowHeight(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        return wm.getDefaultDisplay().getHeight();
//    }
//
//    public static int getMipmapResource(String imageName) {
//        Class mipmap = R.mipmap.class;
//        try {
//            Field field = mipmap.getField(imageName);
//            int resId = field.getInt(imageName);
//            return resId;
//        } catch (NoSuchFieldException e) {//如果没有在"mipmap"下找到imageName,将会返回0
//            return 0;
//        } catch (IllegalAccessException e) {
//            return 0;
//        }
//    }
//
//    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
//        Bitmap image = null;
//        AssetManager am = context.getResources().getAssets();
//        try {
//            InputStream is = am.open(fileName);
//            image = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return image;
//
//    }
//
//    /*** 半角转换为全角
//     *
//     * @param input
//     * @return
//     */
//    public static String ToDBC(String input) {
//        char[] c = input.toCharArray();
//        for (int i = 0; i < c.length; i++) {
//            if (c[i] == 12288) {
//                c[i] = (char) 32;
//                continue;
//            }
//            if (c[i] > 65280 && c[i] < 65375)
//                c[i] = (char) (c[i] - 65248);
//        }
//        return new String(c);
//    }
//
//    /**
//     * 去除特殊字符或将所有中文标号替换为英文标号
//     *
//     * @param str
//     * @return
//     */
//    public static String stringFilter(String str) {
//        str = str.replaceAll("【", "[").replaceAll("】", "]")
//                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
//        String regEx = "[『』]"; // 清除掉特殊字符
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(str);
//        return m.replaceAll("").trim();
//    }
//
//    public static String formatNum(int time) {
//        return time < 10 ? "0" + time : String.valueOf(time);
//    }
//
//    public static String formatMillisecond(int millisecond) {
//        String retMillisecondStr;
//
//        if (millisecond > 99) {
//            retMillisecondStr = String.valueOf(millisecond / 10);
//        } else if (millisecond <= 9) {
//            retMillisecondStr = "0" + millisecond;
//        } else {
//            retMillisecondStr = String.valueOf(millisecond);
//        }
//
//        return retMillisecondStr;
//    }
//
//    /**
//     * 获取屏幕宽度
//     *
//     * @return
//     */
//    public static int getScreenWidth() {
//        Context context = HostApplication.getApp();
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        return dm.widthPixels;
//    }
//
//    /**
//     * 获取屏幕高度
//     *
//     * @return
//     */
//    public static int getScreenHeight() {
//        Context context = HostApplication.getApp();
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        return dm.heightPixels;
//    }
//
//    /**
//     * 获取屏幕的宽度px
//     *
//     * @param context 上下文
//     * @return 屏幕宽px
//     */
//    public static int getScreenWidth(Context context) {
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
//        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
//        return outMetrics.widthPixels;
//    }
//
//    /**
//     * 获取屏幕的高度px
//     *
//     * @param context 上下文
//     * @return 屏幕高px
//     */
//    public static int getScreenHeight(Context context) {
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
//        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
//        return outMetrics.heightPixels;
//    }
//
//    /**
//     * dip转为PX
//     */
//    public static int dip2px(Context context, float dipValue) {
//        float fontScale = context.getResources().getDisplayMetrics().density;
//        return (int) (dipValue * fontScale + 0.5f);
//    }
//
//    /**
//     * 不变形地将矩形图像转换为正方形,未实际使用
//     */
//    public static void squareBitmapFigureImage(Bitmap bitmap_org) {
//        //获取原始宽高，并获取宽高中较大的
//        int nWidth = bitmap_org.getWidth();
//        int nHeight = bitmap_org.getHeight();
//        int nMax = Math.max(nWidth, nHeight);
//        //新建一个正方形的bitmap
//        Bitmap bitmap = Bitmap.createBitmap(nMax, nMax, bitmap_org.getConfig());
//        float left = 0;
//        float top = 0;
//        if (nWidth >= nHeight) {
//            int nLen = nWidth - nHeight;
//            top = (float) (nLen / 2.0);
//        } else {
//            int nLen = nHeight - nWidth;
//            left = (float) (nLen / 2.0);
//        }
//        Canvas canvas = new Canvas(bitmap);
//        //生成正方形
//        canvas.drawBitmap(bitmap_org, left, top, null);
//        canvas = null;
//    }
//    /**
//     * 动态监听VIEW的长宽，这里让它随宽度变化设置高度，变成正方形
//     * 可以使用
//     */
//    public static void squareMonitorImageView(ImageView view){
//        ViewTreeObserver vto = view.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                view.setMaxHeight(view.getWidth());
//            }
//        });
//    }
//}
