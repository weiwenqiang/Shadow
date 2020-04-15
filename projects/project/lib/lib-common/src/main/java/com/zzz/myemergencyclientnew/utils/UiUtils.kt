package com.zzz.myemergencyclientnew.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.ImageView
import com.zzz.myemergencyclientnew.common.R
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

/**
 * Created by 001 on 2017/2/22.
 */
object UiUtils {
    fun getStringArray(
        context: Context,
        array_id: Int
    ): Array<String> {
        return getResource(context)
            .getStringArray(array_id)
    }

    fun getResource(context: Context): Resources {
        return context.resources
    }
    //
    //    public static Context getContext() {
    //        return App.getApplication();
    //    }
    /**
     * dip转换px
     */
    fun dp2px(context: Context, dip: Int): Int {
        val scale = getResource(context)
            .displayMetrics.density
        return (dip * scale + 0.5f).toInt()
    }

    fun dp2px(context: Context, dip: Float): Int {
        val scale = getResource(context)
            .displayMetrics.density
        return (dip * scale + 0.5f).toInt()
    }

    /**
     * pxz转换dip
     */
    fun px2dp(context: Context, px: Int): Int {
        val scale = getResource(context)
            .displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun sp2px(context: Context, spValue: Float): Float {
        if (spValue <= 0) return 0f
        val scale = getResource(context)
            .displayMetrics.scaledDensity
        return spValue * scale
    }

    fun inflate(context: Context?, id: Int): View {
        return View.inflate(context, id, null)
    }

    fun getDrawalbe(
        context: Context,
        id: Int
    ): Drawable {
        return getResource(context).getDrawable(id)
    }

    fun getDimens(context: Context, homePictureHeight: Int): Float {
        return getResource(context)
            .getDimension(homePictureHeight)
    }

    //通过反射获取状态栏高度，默认25dp
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight =
            dp2px(context, 25)
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
            statusBarHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusBarHeight
    }

    /**
     * 获取屏幕尺寸
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    fun getScreenSize(context: Context): Point {
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point(display.width, display.height)
        } else {
            val point = Point()
            display.getSize(point)
            point
        }
    }

    fun getDialogTheme(context: Context, attr: Int): Int {
        val theme = context.theme
        if (theme != null) {
            val outValue = TypedValue()
            theme.resolveAttribute(attr, outValue, true)
            return outValue.resourceId
        }
        return 0
    }

    /**
     * 获取屏幕内容区宽高
     *
     * @param context
     * @return
     */
    fun getScreenWidthHeight(context: Context): HashMap<String, Int> {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
        val height = wm.defaultDisplay.height
        val map =
            HashMap<String, Int>()
        map["height"] = height
        map["width"] = width
        return map
    }

    fun getWindowWidth(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    fun getWindowHeight(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.height
    }

    fun getMipmapResource(imageName: String?): Int {
        val mipmap: Class<*> = R.mipmap::class.java
        return try {
            val field = mipmap.getField(imageName)
            field.getInt(imageName)
        } catch (e: NoSuchFieldException) { //如果没有在"mipmap"下找到imageName,将会返回0
            0
        } catch (e: IllegalAccessException) {
            0
        }
    }

    fun getImageFromAssetsFile(
        context: Context,
        fileName: String?
    ): Bitmap? {
        var image: Bitmap? = null
        val am = context.resources.assets
        try {
            val `is` = am.open(fileName!!)
            image = BitmapFactory.decodeStream(`is`)
            `is`.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    /*** 半角转换为全角
     *
     * @param input
     * @return
     */
//    fun ToDBC(input: String): String {
//        val c = input.toCharArray()
//        for (i in c.indices) {
//            if (c[i] == 12288) {
//                c[i] = 32.toChar()
//                continue
//            }
//            if (c[i] > 65280 && c[i] < 65375) c[i] = (c[i] - 65248)
//        }
//        return String(c)
//    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    fun stringFilter(str: String): String {
        var str = str
        str = str.replace("【".toRegex(), "[").replace("】".toRegex(), "]")
            .replace("！".toRegex(), "!").replace("：".toRegex(), ":") // 替换中文标号
        val regEx = "[『』]" // 清除掉特殊字符
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        return m.replaceAll("").trim { it <= ' ' }
    }

    fun formatNum(time: Int): String {
        return if (time < 10) "0$time" else time.toString()
    }

    fun formatMillisecond(millisecond: Int): String {
        val retMillisecondStr: String
        retMillisecondStr = if (millisecond > 99) {
            (millisecond / 10).toString()
        } else if (millisecond <= 9) {
            "0$millisecond"
        } else {
            millisecond.toString()
        }
        return retMillisecondStr
    }
    /**
     * 获取屏幕宽度
     *
     * @return
     */
    //    public static int getScreenWidth(Context context) {
    //        DisplayMetrics dm = context.getResources().getDisplayMetrics();
    //        return dm.widthPixels;
    //    }
    /**
     * 获取屏幕高度
     *
     * @return
     */
    //    public static int getScreenHeight() {
    //        Context context = App.getContext();
    //        DisplayMetrics dm = context.getResources().getDisplayMetrics();
    //        return dm.heightPixels;
    //    }
    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics() // 创建了一张白纸
        windowManager.defaultDisplay.getMetrics(outMetrics) // 给白纸设置宽高
        return outMetrics.widthPixels
    }

    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    fun getScreenHeight(context: Context): Int {
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics() // 创建了一张白纸
        windowManager.defaultDisplay.getMetrics(outMetrics) // 给白纸设置宽高
        return outMetrics.heightPixels
    }

    /**
     * dip转为PX
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val fontScale = context.resources.displayMetrics.density
        return (dipValue * fontScale + 0.5f).toInt()
    }

    /**
     * 不变形地将矩形图像转换为正方形,未实际使用
     */
    fun squareBitmapFigureImage(bitmap_org: Bitmap) {
        //获取原始宽高，并获取宽高中较大的
        val nWidth = bitmap_org.width
        val nHeight = bitmap_org.height
        val nMax = Math.max(nWidth, nHeight)
        //新建一个正方形的bitmap
        val bitmap =
            Bitmap.createBitmap(nMax, nMax, bitmap_org.config)
        var left = 0f
        var top = 0f
        if (nWidth >= nHeight) {
            val nLen = nWidth - nHeight
            top = (nLen / 2.0).toFloat()
        } else {
            val nLen = nHeight - nWidth
            left = (nLen / 2.0).toFloat()
        }
        var canvas: Canvas? = Canvas(bitmap)
        //生成正方形
        canvas!!.drawBitmap(bitmap_org, left, top, null)
        canvas = null
    }

    /**
     * 动态监听VIEW的长宽，这里让它随宽度变化设置高度，变成正方形
     * 可以使用
     */
    fun squareMonitorImageView(view: ImageView) {
        val vto = view.viewTreeObserver
        vto.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                view.maxHeight = view.width
            }
        })
    }

    fun getProgressDisplayLine(currentBytes: Long, totalBytes: Long): String? {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes)
    }

    private fun getBytesToMBString(bytes: Long): String? {
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00))
    }
}