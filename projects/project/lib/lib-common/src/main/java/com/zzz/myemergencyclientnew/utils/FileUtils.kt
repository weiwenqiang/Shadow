package com.zzz.myemergencyclientnew.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import androidx.core.content.ContextCompat
import java.io.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by 魏文强 on 2016/11/3.
 */
class FileUtils {
    @Throws(FileNotFoundException::class, IOException::class)
    fun getBitmapFormUri(
        context: Context,
        uri: Uri?
    ): Bitmap? {
        var input = context.contentResolver.openInputStream(uri!!)

        //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
        val onlyBoundsOptions =
            BitmapFactory.Options()
        onlyBoundsOptions.inJustDecodeBounds = true //不加载到内存
        onlyBoundsOptions.inDither = true //optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565 //optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
        input!!.close()
        val originalWidth = onlyBoundsOptions.outWidth
        val originalHeight = onlyBoundsOptions.outHeight
        if (originalWidth == -1 || originalHeight == -1) return null

        //图片分辨率以480x800为标准
        val hh = 800f //这里设置高度为800f
        val ww = 480f //这里设置宽度为480f
        //缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1 //be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) { //如果宽度大的话根据宽度固定大小缩放
            be = (originalWidth / ww).toInt()
        } else if (originalWidth < originalHeight && originalHeight > hh) { //如果高度高的话根据宽度固定大小缩放
            be = (originalHeight / hh).toInt()
        }
        if (be <= 0) be = 1
        //比例压缩
        val bitmapOptions =
            BitmapFactory.Options()
        bitmapOptions.inSampleSize = be //设置缩放比例
        bitmapOptions.inDither = true
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565
        input = context.contentResolver.openInputStream(uri)
        val bitmap =
            BitmapFactory.decodeStream(input, null, bitmapOptions)
        input!!.close()
        return compressImage(bitmap) //再进行质量压缩
    }

    fun compressImage(image: Bitmap?): Bitmap? {
        val baos = ByteArrayOutputStream()
        image!!.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            baos
        ) //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 100
        while (baos.toByteArray().size / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset() //重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(
                Bitmap.CompressFormat.JPEG,
                options,
                baos
            ) //这里压缩options，把压缩后的数据存放到baos中
            options -= 10 //每次都减少10
            if (options <= 0) break
        }
        val isBm =
            ByteArrayInputStream(baos.toByteArray()) //把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(
            isBm,
            null,
            null
        )
    }

    companion object {
        const val CACHE = "cache"
        const val ICON = "icon"
        const val ROOT = "GooglePlay"

        /**
         * 获取图片的缓存的路径
         *
         * @return
         */
        fun getIconDir(context: Context): File {
            return getDir(
                context,
                ICON
            )
        }

        /**
         * 获取缓存路径
         *
         * @return
         */
        fun getCacheDir(context: Context): File {
            return getDir(
                context,
                CACHE
            )
        }

        fun getDir(context: Context, cache: String?): File {
            val path = StringBuilder()
            if (isSDAvailable) {
                path.append(
                    Environment.getExternalStorageDirectory()
                        .absolutePath
                )
                path.append(File.separator) // '/'
                path.append(ROOT) // /mnt/sdcard/GooglePlay
                path.append(File.separator)
                path.append(cache) // /mnt/sdcard/GooglePlay/cache
            } else {
                val filesDir = context.cacheDir //  cache  getFileDir file
                path.append(filesDir.absolutePath) // /data/data/com.itheima.googleplay/cache
                path.append(File.separator) ///data/data/com.itheima.googleplay/cache/
                path.append(cache) ///data/data/com.itheima.googleplay/cache/cache
            }
            val file = File(path.toString())
            if (!file.exists() || !file.isDirectory) {
                file.mkdirs() // 创建文件夹
            }
            return file
        }

        private val isSDAvailable: Boolean
            private get() = if (Environment.getExternalStorageState() ==
                Environment.MEDIA_MOUNTED
            ) {
                true
            } else {
                false
            }

        fun uri2File(activity: Activity, uri: Uri?): File {
            var file: File? = null
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            val actualimagecursor = activity.managedQuery(
                uri, proj, null,
                null, null
            )
            val actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            actualimagecursor.moveToFirst()
            val img_path = actualimagecursor
                .getString(actual_image_column_index)
            file = File(img_path)
            return file
        }

        @Throws(IOException::class)
        fun getFromAssets(
            context: Context,
            fileName: String?
        ): String? {
            val inputReader = InputStreamReader(
                context.resources.assets.open(fileName!!),
                "utf-8"
            )
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var Result: String? = ""
            while (bufReader.readLine().also { line = it } != null) Result += line
            return Result
        }

        @Throws(IOException::class)
        fun getFromRaw(context: Context, resId: Int): String? {
            val inputReader =
                InputStreamReader(context.resources.openRawResource(resId), "utf-8")
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var Result: String? = ""
            while (bufReader.readLine().also { line = it } != null) Result += line
            return Result
        }

        fun getRealFilePath(
            context: Context,
            uri: Uri?
        ): String? {
            if (null == uri) return null
            val scheme = uri.scheme
            var data: String? = null
            if (scheme == null) data =
                uri.path else if (ContentResolver.SCHEME_FILE == scheme) {
                data = uri.path
            } else if (ContentResolver.SCHEME_CONTENT == scheme) {
                val cursor = context.contentResolver.query(
                    uri,
                    arrayOf(MediaStore.Images.ImageColumns.DATA),
                    null,
                    null,
                    null
                )
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        val index =
                            cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                        if (index > -1) {
                            data = cursor.getString(index)
                        }
                    }
                    cursor.close()
                }
            }
            return data
        }

        /**
         * 获取assert资源内容
         */
        fun getAssertsFile(
            context: Context,
            fileName: String
        ): ByteArray? {
            var inputStream: InputStream? = null
            val assetManager = context.assets
            try {
                inputStream = assetManager.open(fileName)
                if (inputStream == null) {
                    return null
                }
                var bis: BufferedInputStream? = null
                val length: Int
                try {
                    bis = BufferedInputStream(inputStream)
                    length = bis.available()
                    val data = ByteArray(length)
                    bis.read(data)
                    return data
                } catch (e: IOException) {
                } finally {
                    if (bis != null) {
                        try {
                            bis.close()
                        } catch (e: Exception) {
                        }
                    }
                }
                return null
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        fun isExists(path: String?): Boolean {
            val file = File(path)
            return file.exists()
        }

        private var mInstance: FileUtils? = null
        private var mContext: Context? = null
        private var mContentResolver: ContentResolver? = null
        private val mLock = Any()
        fun getInstance(context: Context): FileUtils? {
            if (mInstance == null) {
                synchronized(mLock) {
                    if (mInstance == null) {
                        mInstance =
                            FileUtils()
                        mContext = context
                        mContentResolver =
                            context.contentResolver
                    }
                }
            }
            return mInstance
        }

        /**
         * 复制assets目录下所有文件及文件夹到指定路径
         *
         * @param mActivity   上下文
         * @param mAssetsPath Assets目录的相对路径
         * @param mSavePath   复制文件的保存路径
         * @return void
         */
        fun copyAssetsFiles(
            mActivity: Activity,
            mAssetsPath: String?,
            mSavePath: String
        ) {
            try {
                // 获取assets目录下的所有文件及目录名
                val fileNames =
                    mActivity.resources.assets.list(mAssetsPath!!)
                if (fileNames!!.size > 0) {
                    // 若是目录
                    for (fileName in fileNames) {
                        var newAssetsPath = ""
                        // 确保Assets路径后面没有斜杠分隔符，否则将获取不到值
                        newAssetsPath =
                            if (mAssetsPath == null || "" == mAssetsPath || "/" == mAssetsPath) {
                                fileName
                            } else {
                                if (mAssetsPath.endsWith("/")) {
                                    mAssetsPath + fileName
                                } else {
                                    "$mAssetsPath/$fileName"
                                }
                            }
                        // 递归调用
                        copyAssetsFiles(
                            mActivity,
                            newAssetsPath,
                            "$mSavePath/$fileName"
                        )
                    }
                } else {
                    // 若是文件
                    val file = File(mSavePath)
                    // 若文件夹不存在，则递归创建父目录
                    file.parentFile.mkdirs()
                    val `is` =
                        mActivity.resources.assets.open(mAssetsPath)
                    val fos =
                        FileOutputStream(File(mSavePath))
                    val buffer = ByteArray(1024)
                    var byteCount = 0
                    // 循环从输入流读取字节
                    while (`is`.read(buffer).also { byteCount = it } != -1) {
                        // 将读取的输入流写入到输出流
                        fos.write(buffer, 0, byteCount)
                    }
                    // 刷新缓冲区
                    fos.flush()
                    fos.close()
                    `is`.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        const val SIZETYPE_B = 1 //获取文件大小单位为B的double值
        const val SIZETYPE_KB = 2 //获取文件大小单位为KB的double值
        const val SIZETYPE_MB = 3 //获取文件大小单位为MB的double值
        const val SIZETYPE_GB = 4 //获取文件大小单位为GB的double值

        /**
         * 获取文件指定文件的指定单位的大小
         *
         * @param filePath 文件路径
         * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
         * @return double值的大小
         */
        @JvmStatic
        fun getFileOrFilesSize(filePath: String?, sizeType: Int): Double {
            val file = File(filePath)
            var blockSize: Long = 0
            try {
                blockSize = if (file.isDirectory) {
                    getFileSizes(file)
                } else {
                    getFileSize(file)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //            Log.e(TAG,"获取文件大小失败!");
            }
            return FormetFileSize(
                blockSize,
                sizeType
            )
        }

        /**
         * 调用此方法自动计算指定文件或指定文件夹的大小
         *
         * @param filePath 文件路径
         * @return 计算好的带B、KB、MB、GB的字符串
         */
        fun getAutoFileOrFilesSize(filePath: String?): String {
            val file = File(filePath)
            var blockSize: Long = 0
            try {
                blockSize = if (file.isDirectory) {
                    getFileSizes(file)
                } else {
                    getFileSize(file)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //            Log.e(TAG,"获取文件大小失败!");
            }
            return FormetFileSize(blockSize)
        }

        /**
         * 获取指定文件大小
         *
         * @param file
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        private fun getFileSize(file: File): Long {
            var size: Long = 0
            if (file.exists()) {
                var fis: FileInputStream? = null
                fis = FileInputStream(file)
                size = fis.available().toLong()
            } else {
                file.createNewFile()
                //            Log.e(TAG,"获取文件大小不存在!");
            }
            return size
        }

        /**
         * 获取指定文件夹
         *
         * @param f
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        private fun getFileSizes(f: File): Long {
            var size: Long = 0
            val flist = f.listFiles()
            for (i in flist.indices) {
                size = if (flist[i].isDirectory) {
                    size + getFileSizes(
                        flist[i]
                    )
                } else {
                    size + getFileSize(
                        flist[i]
                    )
                }
            }
            return size
        }

        /**
         * 转换文件大小
         *
         * @param fileS
         * @return
         */
        fun FormetFileSize(fileS: Long): String {
            val df = DecimalFormat("#.00")
            var fileSizeString = ""
            val wrongSize = "0B"
            if (fileS == 0L) {
                return wrongSize
            }
            fileSizeString = if (fileS < 1024) {
                df.format(fileS.toDouble()) + "B"
            } else if (fileS < 1048576) {
                df.format(fileS.toDouble() / 1024) + "KB"
            } else if (fileS < 1073741824) {
                df.format(fileS.toDouble() / 1048576) + "MB"
            } else {
                df.format(fileS.toDouble() / 1073741824) + "GB"
            }
            return fileSizeString
        }

        /**
         * 转换文件大小,指定转换的类型
         *
         * @param fileS
         * @param sizeType
         * @return
         */
        fun FormetFileSize(fileS: Long, sizeType: Int): Double {
            val df = DecimalFormat("#.00")
            var fileSizeLong = 0.0
            when (sizeType) {
                SIZETYPE_B -> fileSizeLong =
                    java.lang.Double.valueOf(df.format(fileS.toDouble()))
                SIZETYPE_KB -> fileSizeLong =
                    java.lang.Double.valueOf(df.format(fileS.toDouble() / 1024))
                SIZETYPE_MB -> fileSizeLong =
                    java.lang.Double.valueOf(df.format(fileS.toDouble() / 1048576))
                SIZETYPE_GB -> fileSizeLong =
                    java.lang.Double.valueOf(df.format(fileS.toDouble() / 1073741824))
                else -> {
                }
            }
            return fileSizeLong
        }
        //    public static void playMusic(Context context) {
        //        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.tx);
        //        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        //            public void onCompletion(MediaPlayer mp) {
        //                mp.release();// 释放资源。让资源得到释放;;
        //            }
        //        });
        //        if (true) {
        //            mPlayer.start();
        //        } else {
        //            mPlayer.stop();
        //        }
        //    }
        /**
         * 根据系统时间、前缀、后缀产生一个文件
         */
        @JvmStatic
        fun createFile(
            folder: File,
            prefix: String,
            suffix: String
        ): File {
            if (!folder.exists() || !folder.isDirectory) folder.mkdirs()
            val dateFormat =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
            val filename =
                prefix + dateFormat.format(Date(System.currentTimeMillis())) + suffix
            return File(folder, filename)
        }

        fun deleteDirs(path: String?, isDeleteDir: Boolean) {
            if (TextUtils.isEmpty(path)) {
                return
            }
            val dir = File(path)
            if (!dir.exists()) {
                return
            }
            val files = dir.listFiles()
            for (file in files) {
                if (file.isDirectory) {
                    deleteDirs(
                        file.absolutePath,
                        isDeleteDir
                    )
                } else {
                    file.delete()
                }
            }
            if (isDeleteDir) {
                dir.delete()
            }
        }

        @Throws(IOException::class)
        fun copy(inputStream: InputStream, out: OutputStream) {
            val buf = ByteArray(512)
            var len = -1
            while (inputStream.read(buf).also { len = it } != -1) {
                out.write(buf, 0, len)
            }
            inputStream.close()
            out.close()
        }

        fun getFileDir(context: Context): File {
            return getFileDir(context, null)
        }

        fun getFileDir(context: Context, type: String?): File {
            val root = context.filesDir
            return if (TextUtils.isEmpty(type)) {
                root
            } else {
                val dir = File(root, type)
                createDir(dir)
                dir
            }
        }

        fun externalAvailable(): Boolean {
            return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
        }

        fun getExternalDir(context: Context): File? {
            return getExternalDir(
                context,
                null
            )
        }

        fun getExternalDir(context: Context, type: String?): File? {
            if (externalAvailable()) {
                if (TextUtils.isEmpty(type)) {
                    return context.getExternalFilesDir(null)
                }
                var dir = context.getExternalFilesDir(type)
                if (dir == null) {
                    dir = context.getExternalFilesDir(null)
                    dir = File(dir, type)
                    createDir(dir)
                }
                return dir
            }
            throw RuntimeException("External storage device is not available.")
        }

        val rootDir: File
            get() = getRootDir(null)

        fun getRootDir(type: String?): File {
            if (externalAvailable()) {
                val root = Environment.getExternalStorageDirectory()
                val appRoot = File(root, "AndPermission")
                createDir(appRoot)
                if (TextUtils.isEmpty(type)) {
                    return appRoot
                }
                val dir = File(appRoot, type)
                createDir(dir)
                return dir
            }
            throw RuntimeException("External storage device is not available.")
        }

        fun createDir(dir: File) {
            if (dir.exists()) {
                if (!dir.isDirectory) {
                    dir.delete()
                }
            }
            if (!dir.exists()) {
                dir.mkdirs()
            }
        }
        //    public static void takePhoto(Activity activity) {
        //        // 步骤一：创建存储照片的文件
        //        String path = activity.getFilesDir() + File.separator + "images" + File.separator;
        //        File file = new File(path, "test.jpg");
        //        if(!file.getParentFile().exists())
        //            file.getParentFile().mkdirs();
        //        Uri mUri;
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //            //步骤二：Android 7.0及以上获取文件 Uri
        ////            mUri = FileProvider.getUriForFile(activity, "com.example.admin.custmerviewapplication", file);
        //            mUri = FileProvider.getUriForFile(activity, ProviderUtil.getFileProviderName(activity), file);
        //        } else {
        //            //步骤三：获取文件Uri
        //            mUri = Uri.fromFile(file);
        //        }
        //        //步骤四：调取系统拍照
        //        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        //        activity.startActivityForResult(intent, 101);
        //    }
        /**
         * 获取拍照的Intent
         * @return
         */
        fun getCaptureIntent(outPutUri: Uri?): Intent {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.action = MediaStore.ACTION_IMAGE_CAPTURE //设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri) //将拍取的照片保存到指定URI
            return intent
        }

        //    根据文件Uri获取图片url
        fun getRealPathFromURI(
            context: Context,
            contentURI: Uri
        ): String? {
            val result: String?
            val cursor = context.contentResolver.query(
                contentURI,
                arrayOf(MediaStore.Images.ImageColumns.DATA),  //
                null,
                null,
                null
            )
            if (cursor == null) result = contentURI.path else {
                cursor.moveToFirst()
                val index =
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(index)
                cursor.close()
            }
            return result
        }

        /**
         * 工具类
         * 从视频中获取第一贞图片
         */
        fun createVideoThumbnail(filepath_video: String): Bitmap? {
            return if ("" == filepath_video) {
                null
            } else ThumbnailUtils.createVideoThumbnail(
                filepath_video,
                MediaStore.Video.Thumbnails.MINI_KIND
            )
        }

        /**
         * 工具类
         * 从视频中获取第一贞图片
         *
         * @param url
         * @param width
         * @param height
         * @return
         */
        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        fun createVideoThumbnail(
            url: String?,
            width: Int,
            height: Int
        ): Bitmap? {
            var bitmap: Bitmap? = null
            val retriever =
                MediaMetadataRetriever()
            val kind = MediaStore.Video.Thumbnails.MINI_KIND
            try {
                if (Build.VERSION.SDK_INT >= 14) {
                    retriever.setDataSource(url, HashMap())
                } else {
                    retriever.setDataSource(url)
                }
                bitmap = retriever.frameAtTime
            } catch (ex: IllegalArgumentException) {
                // Assume this is a corrupt video file
            } catch (ex: RuntimeException) {
                // Assume this is a corrupt video file.
            } finally {
                try {
                    retriever.release()
                } catch (ex: RuntimeException) {
                    // Ignore failures while cleaning up.
                }
            }
            if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
                bitmap = ThumbnailUtils.extractThumbnail(
                    bitmap,
                    width,
                    height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT
                )
            }
            return bitmap
        }

        @JvmStatic
        fun getRootDirPath(context: Context): String {
            return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                val file = ContextCompat.getExternalFilesDirs(
                    context.applicationContext,
                    null
                )[0]
                file.absolutePath
            } else {
                context.applicationContext.filesDir.absolutePath
            }
        }

        @JvmStatic
        fun getProgressDisplayLine(
            currentBytes: Long,
            totalBytes: Long
        ): String {
            return getBytesToMBString(
                currentBytes
            ) + "/" + getBytesToMBString(
                totalBytes
            )
        }

        private fun getBytesToMBString(bytes: Long): String {
            return String.format(
                Locale.ENGLISH,
                "%.2fMb",
                bytes / (1024.00 * 1024.00)
            )
        }
    }
}