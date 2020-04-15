//package com.zzz.myemergencyclientnew.utils;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.AssetManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.media.MediaMetadataRetriever;
//import android.media.MediaPlayer;
//import android.media.ThumbnailUtils;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//import androidx.core.content.FileProvider;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zzz.myemergencyclientnew.host.HostApplication;
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.model.Response;
//import com.zzz.myemergencyclientnew.callback.StringDialogCallback;
//import com.zzz.myemergencyclientnew.listener.UploadFileListener;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//
///**
// * Created by 魏文强 on 2016/11/3.
// */
//public class FileUtils {
//
//
//    public static final String CACHE = "cache";
//    public static final String ICON = "icon";
//    public static final String ROOT = "GooglePlay";
//
//    /**
//     * 获取图片的缓存的路径
//     *
//     * @return
//     */
//    public static File getIconDir() {
//        return getDir(ICON);
//
//    }
//
//    /**
//     * 获取缓存路径
//     *
//     * @return
//     */
//    public static File getCacheDir() {
//        return getDir(CACHE);
//    }
//
//    public static File getDir(String cache) {
//        StringBuilder path = new StringBuilder();
//        if (isSDAvailable()) {
//            path.append(Environment.getExternalStorageDirectory()
//                    .getAbsolutePath());
//            path.append(File.separator);// '/'
//            path.append(ROOT);// /mnt/sdcard/GooglePlay
//            path.append(File.separator);
//            path.append(cache);// /mnt/sdcard/GooglePlay/cache
//
//        } else {
//            File filesDir = UiUtils.getContext().getCacheDir();    //  cache  getFileDir file
//            path.append(filesDir.getAbsolutePath());// /data/data/com.itheima.googleplay/cache
//            path.append(File.separator);///data/data/com.itheima.googleplay/cache/
//            path.append(cache);///data/data/com.itheima.googleplay/cache/cache
//        }
//        File file = new File(path.toString());
//        if (!file.exists() || !file.isDirectory()) {
//            file.mkdirs();// 创建文件夹
//        }
//        return file;
//
//    }
//
//    private static boolean isSDAvailable() {
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static File uri2File(Activity activity, Uri uri) {
//        File file = null;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
//                null, null);
//        int actual_image_column_index = actualimagecursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        actualimagecursor.moveToFirst();
//        String img_path = actualimagecursor
//                .getString(actual_image_column_index);
//        file = new File(img_path);
//        return file;
//    }
//
//    public static String getFromAssets(String fileName) throws IOException {
//        InputStreamReader inputReader = new InputStreamReader(HostApplication.getApp().getResources().getAssets().open(fileName), "utf-8");
//
//        BufferedReader bufReader = new BufferedReader(inputReader);
//        String line = "";
//        String Result = "";
//        while ((line = bufReader.readLine()) != null)
//            Result += line;
//        return Result;
//    }
//
//
//    public static String getFromRaw(int resId) throws IOException {
//
//        InputStreamReader inputReader = new InputStreamReader(HostApplication.getApp().getResources().openRawResource(resId), "utf-8");
//        BufferedReader bufReader = new BufferedReader(inputReader);
//        String line = "";
//        String Result = "";
//        while ((line = bufReader.readLine()) != null)
//            Result += line;
//        return Result;
//
//    }
//
//
//    public static String getRealFilePath(final Context context, final Uri uri) {
//        if (null == uri) return null;
//        final String scheme = uri.getScheme();
//        String data = null;
//        if (scheme == null)
//            data = uri.getPath();
//        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
//            data = uri.getPath();
//        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
//            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
//            if (null != cursor) {
//                if (cursor.moveToFirst()) {
//                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                    if (index > -1) {
//                        data = cursor.getString(index);
//                    }
//                }
//                cursor.close();
//            }
//        }
//        return data;
//    }
//
//    /**
//     * 获取assert资源内容
//     */
//    public static byte[] getAssertsFile(Context context, String fileName) {
//        InputStream inputStream = null;
//        AssetManager assetManager = context.getAssets();
//        try {
//            inputStream = assetManager.open(fileName);
//            if (inputStream == null) {
//                return null;
//            }
//
//            BufferedInputStream bis = null;
//            int length;
//            try {
//                bis = new BufferedInputStream(inputStream);
//                length = bis.available();
//                byte[] data = new byte[length];
//                bis.read(data);
//
//                return data;
//            } catch (IOException e) {
//
//            } finally {
//                if (bis != null) {
//                    try {
//                        bis.close();
//                    } catch (Exception e) {
//
//                    }
//                }
//            }
//
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
////    /**
////     * 获取本机音乐列表
////     *
////     * @return
////     */
////    public List<Music> getMusics() {
////        ArrayList<Music> musics = new ArrayList<>();
////        Cursor c = null;
////        try {
////            c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
////                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
////
////            while (c.moveToNext()) {
////                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径
////
////                if (!FileUtils.isExists(path)) {
////                    continue;
////                }
////
////                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
////                String album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)); // 专辑
////                String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)); // 作者
////                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));// 大小
////                int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));// 时长
////                int time = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));// 歌曲的id
////                // int albumId = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
////
////                Music music = new Music(name, path, album, artist, size, duration);
////                musics.add(music);
////            }
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            if (c != null) {
////                c.close();
////            }
////        }
////        return musics;
////    }
//
//    public static boolean isExists(String path) {
//        File file = new File(path);
//        return file.exists();
//    }
//
//    private static FileUtils mInstance;
//    private static Context mContext;
//    private static ContentResolver mContentResolver;
//    private static Object mLock = new Object();
//
//    public static FileUtils getInstance(Context context) {
//        if (mInstance == null) {
//            synchronized (mLock) {
//                if (mInstance == null) {
//                    mInstance = new FileUtils();
//                    mContext = context;
//                    mContentResolver = context.getContentResolver();
//                }
//            }
//        }
//        return mInstance;
//    }
//
////    public static class AssetsDBUtils {
////        public static final String packageBame = "com.zzz.myemergencyclientnew";
////        public static final String db_name = "division.db";
////        public static final String filePath = "data/data/" + packageBame + "/databases/" + db_name;
////        public static final String pathStr = "data/data/" + packageBame + "/databases";
////
////        public static  String getPath(Context context) {
////            System.out.println("filePath:" + filePath);
////            File dbFile = new File(filePath);
////            if (dbFile.exists()) {
////                return filePath;
////            } else {
////                File path = new File(pathStr);
////                path.mkdir();
////                try {
////                    InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/" + db_name);
////
////                    FileOutputStream fos = new FileOutputStream(dbFile);
////                    byte[] buffer = new byte[10240];
////                    int count = 0;
////                    while ((count = is.read(buffer)) > 0) {
////                        fos.write(buffer, 0, count);
////                    }
////                    fos.flush();
////                    fos.close();
////                    is.close();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                    return null;
////                }
////                return filePath;
////            }
////        }
////    }
//
//    /**
//     * 复制assets的单文件到应用包之下
//     */
////    public static String copyAssetsFile(Context context, String file_name) {
////        String as = FileDownloadUtils.getDefaultSaveRootPath() + File.separator;
//////        final String packageBame = context.getPackageName();
//////        final String file_name = "division.db";
////        final String filePath = "data/data/" + context.getPackageName() + "/assets/" + file_name;
////        final String pathStr = "data/data/" + context.getPackageName() + "/assets";
////
//////        System.out.println("filePath:" + filePath);
////        File dbFile = new File(filePath);
////        if (dbFile.exists()) {
////            return filePath;
////        } else {
////            File path = new File(pathStr);
////            path.mkdir();
////            try {
////                InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/" + file_name);
////
////                FileOutputStream fos = new FileOutputStream(dbFile);
////                byte[] buffer = new byte[10240];
////                int count = 0;
////                while ((count = is.read(buffer)) > 0) {
////                    fos.write(buffer, 0, count);
////                }
////                fos.flush();
////                fos.close();
////                is.close();
////            } catch (Exception e) {
////                e.printStackTrace();
////                return null;
////            }
////            return filePath;
////        }
////    }
///** 几个常用目录
// String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
// Log.d(TAG, "getExternalStorageDirectory: " + sdcardPath);
// // getExternalStorageDirectory: /storage/emulated/0
//
// String dataDir = getFilesDir().toString();
// Log.d(TAG, "getFilesDir(): " + dataDir);
// //getFilesDir(): /data/user/0/ai.nixie.copyassets/files
//
// String externalFilesDir = getExternalFilesDir(null).toString();
// Log.d(TAG, "getExternalFilesDir: " + externalFilesDir);
// //getExternalFilesDir: /storage/emulated/0/Android/data/ai.nixie.copyassets/files
//
//
// PackageManager packageManager = getPackageManager();
// String str =getPackageName();
// try {
// PackageInfo pi = packageManager.getPackageInfo(str, 0);
// str = pi.applicationInfo.dataDir;
// } catch (PackageManager.NameNotFoundException e) {
// e.printStackTrace();
// }
// Log.d(TAG, "applicationInfo.dataDir: " + str);
// //applicationInfo.dataDir: /data/user/0/ai.nixie.copyassets
// */
//
//    /**
//     * 复制assets目录下所有文件及文件夹到指定路径
//     *
//     * @param mActivity   上下文
//     * @param mAssetsPath Assets目录的相对路径
//     * @param mSavePath   复制文件的保存路径
//     * @return void
//     */
//    public static void copyAssetsFiles(Activity mActivity, String mAssetsPath, String mSavePath) {
//        try {
//            // 获取assets目录下的所有文件及目录名
//            String[] fileNames = mActivity.getResources().getAssets().list(mAssetsPath);
//            if (fileNames.length > 0) {
//                // 若是目录
//                for (String fileName : fileNames) {
//                    String newAssetsPath = "";
//                    // 确保Assets路径后面没有斜杠分隔符，否则将获取不到值
//                    if ((mAssetsPath == null) || "".equals(mAssetsPath) || "/".equals(mAssetsPath)) {
//                        newAssetsPath = fileName;
//                    } else {
//                        if (mAssetsPath.endsWith("/")) {
//                            newAssetsPath = mAssetsPath + fileName;
//                        } else {
//                            newAssetsPath = mAssetsPath + "/" + fileName;
//                        }
//                    }
//                    // 递归调用
//                    copyAssetsFiles(mActivity, newAssetsPath, mSavePath + "/" + fileName);
//                }
//            } else {
//                // 若是文件
//                File file = new File(mSavePath);
//                // 若文件夹不存在，则递归创建父目录
//                file.getParentFile().mkdirs();
//                InputStream is = mActivity.getResources().getAssets().open(mAssetsPath);
//                FileOutputStream fos = new FileOutputStream(new File(mSavePath));
//                byte[] buffer = new byte[1024];
//                int byteCount = 0;
//                // 循环从输入流读取字节
//                while ((byteCount = is.read(buffer)) != -1) {
//                    // 将读取的输入流写入到输出流
//                    fos.write(buffer, 0, byteCount);
//                }
//                // 刷新缓冲区
//                fos.flush();
//                fos.close();
//                is.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
//    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
//    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
//    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值
//
//    /**
//     * 获取文件指定文件的指定单位的大小
//     *
//     * @param filePath 文件路径
//     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
//     * @return double值的大小
//     */
//    public static double getFileOrFilesSize(String filePath, int sizeType) {
//        File file = new File(filePath);
//        long blockSize = 0;
//        try {
//            if (file.isDirectory()) {
//                blockSize = getFileSizes(file);
//            } else {
//                blockSize = getFileSize(file);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
////            Log.e(TAG,"获取文件大小失败!");
//        }
//        return FormetFileSize(blockSize, sizeType);
//    }
//
//    /**
//     * 调用此方法自动计算指定文件或指定文件夹的大小
//     *
//     * @param filePath 文件路径
//     * @return 计算好的带B、KB、MB、GB的字符串
//     */
//    public static String getAutoFileOrFilesSize(String filePath) {
//        File file = new File(filePath);
//        long blockSize = 0;
//        try {
//            if (file.isDirectory()) {
//                blockSize = getFileSizes(file);
//            } else {
//                blockSize = getFileSize(file);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
////            Log.e(TAG,"获取文件大小失败!");
//        }
//        return FormetFileSize(blockSize);
//    }
//
//    /**
//     * 获取指定文件大小
//     *
//     * @param file
//     * @return
//     * @throws Exception
//     */
//    private static long getFileSize(File file) throws Exception {
//        long size = 0;
//        if (file.exists()) {
//            FileInputStream fis = null;
//            fis = new FileInputStream(file);
//            size = fis.available();
//        } else {
//            file.createNewFile();
////            Log.e(TAG,"获取文件大小不存在!");
//        }
//        return size;
//    }
//
//    /**
//     * 获取指定文件夹
//     *
//     * @param f
//     * @return
//     * @throws Exception
//     */
//    private static long getFileSizes(File f) throws Exception {
//        long size = 0;
//        File flist[] = f.listFiles();
//        for (int i = 0; i < flist.length; i++) {
//            if (flist[i].isDirectory()) {
//                size = size + getFileSizes(flist[i]);
//            } else {
//                size = size + getFileSize(flist[i]);
//            }
//        }
//        return size;
//    }
//
//    /**
//     * 转换文件大小
//     *
//     * @param fileS
//     * @return
//     */
//    public static String FormetFileSize(long fileS) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        String fileSizeString = "";
//        String wrongSize = "0B";
//        if (fileS == 0) {
//            return wrongSize;
//        }
//        if (fileS < 1024) {
//            fileSizeString = df.format((double) fileS) + "B";
//        } else if (fileS < 1048576) {
//            fileSizeString = df.format((double) fileS / 1024) + "KB";
//        } else if (fileS < 1073741824) {
//            fileSizeString = df.format((double) fileS / 1048576) + "MB";
//        } else {
//            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
//        }
//        return fileSizeString;
//    }
//
//    /**
//     * 转换文件大小,指定转换的类型
//     *
//     * @param fileS
//     * @param sizeType
//     * @return
//     */
//    public static double FormetFileSize(long fileS, int sizeType) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        double fileSizeLong = 0;
//        switch (sizeType) {
//            case SIZETYPE_B:
//                fileSizeLong = Double.valueOf(df.format((double) fileS));
//                break;
//            case SIZETYPE_KB:
//                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
//                break;
//            case SIZETYPE_MB:
//                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
//                break;
//            case SIZETYPE_GB:
//                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
//                break;
//            default:
//                break;
//        }
//        return fileSizeLong;
//    }
//
////    public static void playMusic(Context context) {
////        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.tx);
////        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
////            public void onCompletion(MediaPlayer mp) {
////                mp.release();// 释放资源。让资源得到释放;;
////            }
////        });
////        if (true) {
////            mPlayer.start();
////        } else {
////            mPlayer.stop();
////        }
////    }
//
//    /**
//     * 根据系统时间、前缀、后缀产生一个文件
//     */
//    public static File createFile(File folder, String prefix, String suffix) {
//        if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
//        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;
//        return new File(folder, filename);
//    }
//
//
//    public static void deleteDirs(String path, boolean isDeleteDir) {
//
//        if (TextUtils.isEmpty(path)) {
//            return;
//        }
//        File dir = new File(path);
//        if (!dir.exists()) {
//            return;
//        }
//        File[] files = dir.listFiles();
//        for (File file : files) {
//            if (file.isDirectory()) {
//                deleteDirs(file.getAbsolutePath(), isDeleteDir);
//            } else {
//                file.delete();
//            }
//        }
//        if (isDeleteDir) {
//            dir.delete();
//        }
//
//    }
//
//
//    public static void copy(InputStream inputStream, OutputStream out) throws IOException {
//        byte[] buf = new byte[512];
//        int len = -1;
//        while ((len = inputStream.read(buf)) != -1) {
//            out.write(buf, 0, len);
//        }
//        inputStream.close();
//        out.close();
//    }
//
////    public static void uploadFile(Activity context, AgentWeb mAgentWeb, String name, String path) {
////        File file = new File(path);
////        if (!file.exists()) {
////            return;
////        }
////        //图片压缩
////        File compressedImageFile = file;
////        try {
////            compressedImageFile = new Compressor(context).compressToFile(file);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        OkGo.<String>post(Api.WEB_FASTDFS_UPLOAD)
////                .tag(context)
////                .params("file", compressedImageFile)
////                .execute(new StringDialogCallback(context) {
////                    @Override
////                    public void onSuccess(Response<String> response) {
////                        try {
////                            JSONObject jsonObject = JSONObject.parseObject(response.body().toString());
////                            int code = jsonObject.getInteger("code");
////                            String msg = jsonObject.getString("msg");
////                            if (code == 200) {
////                                JSONObject object = jsonObject.getJSONObject("object");
////                                String pathStr = object.getString("all_path");
////                                mAgentWeb.getJsAccessEntrace().quickCallJs(name, pathStr);
////                            }
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                    }
////
////                    @Override
////                    public void onError(Response<String> response) {
////                        Log.e("Error", response.body().toString());
////                    }
////                });
////    }
//
////    public static void uploadFile(Activity context, String path, UploadFileListener listener) {
////        File file = new File(path);
////        if (!file.exists()) {
////            return;
////        }
////        //图片压缩
////        File compressedImageFile = file;
////        try {
////            compressedImageFile = new Compressor(context).compressToFile(file);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        OkGo.<String>post("http://web.bxapi.sxbdjw.com/fastdfs/fastdfs/sxjyupload")
////                .tag(context)
////                .params("file", compressedImageFile)
////                .execute(new StringDialogCallback(context) {
////                    @Override
////                    public void onSuccess(Response<String> response) {
////                        listener.onSuccess(Api.WEB_STATIC_FILE + response.body());
////                    }
////
////                    @Override
////                    public void onError(Response<String> response) {
////                        Log.e("Error", response.body().toString());
////                    }
////                });
////    }
//
//    /**
//     * 新的先通过接口上传
//     */
////    public static void uploadFile3(Activity context, String path, UploadFileListener listener) {
////        File file = new File(path);
////        if (!file.exists()) {
////            return;
////        }
////        //图片压缩
////        File compressedImageFile = file;
////        try {
////            compressedImageFile = new Compressor(context).compressToFile(file);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        OkGo.<String>post(Api.JAVA_SERVICE_URL + "/app/fastdfs/upload")
////                .tag(context)
////                .params("file", compressedImageFile)
////                .execute(new StringDialogCallback(context) {
////                    @Override
////                    public void onSuccess(Response<String> response) {
////                        JSONObject obj = JSONObject.parseObject(response.body());
////                        int code = obj.getIntValue("code");
////                        String msg = obj.getString("msg");
////                        if (code == C.API_SUCCEED) {
////                            String data = obj.getString("data");
////                            listener.onSuccess(data);
////                            T.success(msg);
////                        } else {
////                            T.error(msg);
////                        }
////                    }
////
////                    @Override
////                    public void onError(Response<String> response) {
////                        Log.e("Error", response.body().toString());
////                    }
////                });
////    }
//
//    /**
//     * 批量文件上传
//     */
////    public static void uploadFileBatch(Activity context, ArrayList<ImageItem> images, UploadFileListener listener) {
////        List<File> files = new ArrayList<>();
////        for (int i = 0; i < images.size(); i++) {
////            File file = new File(images.get(i).path);
////            if (!file.exists()) {
////                return;
////            }
////            //图片压缩
////            File compressedImageFile = file;
////            try {
////                compressedImageFile = new Compressor(context).compressToFile(file);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            files.add(compressedImageFile);
////        }
////        OkGo.<String>post(Api.JAVA_SERVICE_URL + "/app/fastdfs/uploadMore")
////                .tag(context)
////                .addFileParams("files", files)
////                .execute(new StringDialogCallback(context) {
////                    @Override
////                    public void onSuccess(Response<String> response) {
////                        JSONObject obj = JSONObject.parseObject(response.body());
////                        int code = obj.getIntValue("code");
////                        String msg = obj.getString("msg");
////                        if (code == C.API_SUCCEED) {
////                            String data = obj.getString("data");
////                            listener.onSuccess(data);
//////                            T.success(msg);
////                        } else {
////                            T.error(msg);
////                        }
////                    }
////
////                    @Override
////                    public void onError(Response<String> response) {
////                        Log.e("Error", response.body().toString());
////                    }
////                });
////    }
//
////    public static void uploadFileTwo(Activity context, String path, UploadFileListener listener) {
////        File file = new File(path);
////        if (!file.exists()) {
////            return;
////        }
////        //图片压缩
////        File compressedImageFile = file;
////        try {
////            compressedImageFile = new Compressor(context).compressToFile(file);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        OkGo.<String>post(Api.WEB_FASTDFS_UPLOAD)
////                .tag(context)
////                .params("file", compressedImageFile)
////                .execute(new StringDialogCallback(context) {
////                    @Override
////                    public void onSuccess(Response<String> response) {
////                        try {
////                            JSONObject jsonObject = JSONObject.parseObject(response.body().toString());
////                            int code = jsonObject.getInteger("code");
////                            String msg = jsonObject.getString("msg");
////                            if (code == 200) {
////                                JSONObject object = jsonObject.getJSONObject("object");
////                                String pathStr = object.getString("all_path");
////                                listener.onSuccess(Api.WEB_STATIC_FILE + pathStr);
////                            }
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                    }
////
////                    @Override
////                    public void onError(Response<String> response) {
////                        Log.e("Error", response.body().toString());
////                    }
////                });
////    }
//
//    public static File getFileDir(Context context) {
//        return getFileDir(context, null);
//    }
//
//    public static File getFileDir(Context context, @Nullable String type) {
//        File root = context.getFilesDir();
//        if (TextUtils.isEmpty(type)) {
//            return root;
//        } else {
//            File dir = new File(root, type);
//            createDir(dir);
//            return dir;
//        }
//    }
//
//    public static boolean externalAvailable() {
//        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
//    }
//
//    public static File getExternalDir(Context context) {
//        return getExternalDir(context, null);
//    }
//
//    public static File getExternalDir(Context context, @Nullable String type) {
//        if (externalAvailable()) {
//            if (TextUtils.isEmpty(type)) {
//                return context.getExternalFilesDir(null);
//            }
//
//            File dir = context.getExternalFilesDir(type);
//            if (dir == null) {
//                dir = context.getExternalFilesDir(null);
//                dir = new File(dir, type);
//                createDir(dir);
//            }
//
//            return dir;
//        }
//        throw new RuntimeException("External storage device is not available.");
//    }
//
//    public static File getRootDir() {
//        return getRootDir(null);
//    }
//
//    public static File getRootDir(@Nullable String type) {
//        if (externalAvailable()) {
//            File root = Environment.getExternalStorageDirectory();
//            File appRoot = new File(root, "AndPermission");
//            createDir(appRoot);
//
//            if (TextUtils.isEmpty(type)) {
//                return appRoot;
//            }
//
//            File dir = new File(appRoot, type);
//            createDir(dir);
//
//            return dir;
//        }
//        throw new RuntimeException("External storage device is not available.");
//    }
//
//    public static void createDir(File dir) {
//        if (dir.exists()) {
//            if (!dir.isDirectory()) {
//                dir.delete();
//            }
//        }
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//    }
//
////    public static void takePhoto(Activity activity) {
////        // 步骤一：创建存储照片的文件
////        String path = activity.getFilesDir() + File.separator + "images" + File.separator;
////        File file = new File(path, "test.jpg");
////        if(!file.getParentFile().exists())
////            file.getParentFile().mkdirs();
////        Uri mUri;
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////            //步骤二：Android 7.0及以上获取文件 Uri
//////            mUri = FileProvider.getUriForFile(activity, "com.example.admin.custmerviewapplication", file);
////            mUri = FileProvider.getUriForFile(activity, ProviderUtil.getFileProviderName(activity), file);
////        } else {
////            //步骤三：获取文件Uri
////            mUri = Uri.fromFile(file);
////        }
////        //步骤四：调取系统拍照
////        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
////        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
////        activity.startActivityForResult(intent, 101);
////    }
//
//    /**
//     * 获取拍照的Intent
//     * @return
//     */
//    public static Intent getCaptureIntent(Uri outPutUri) {
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);//将拍取的照片保存到指定URI
//        return intent;
//    }
//
//    public Bitmap getBitmapFormUri(Context context, Uri uri) throws FileNotFoundException, IOException {
//        InputStream input = context.getContentResolver().openInputStream(uri);
//
//        //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
//        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
//        onlyBoundsOptions.inJustDecodeBounds = true;//不加载到内存
//        onlyBoundsOptions.inDither = true;//optional
//        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
//        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
//        input.close();
//        int originalWidth = onlyBoundsOptions.outWidth;
//        int originalHeight = onlyBoundsOptions.outHeight;
//        if ((originalWidth == -1) || (originalHeight == -1))
//            return null;
//
//        //图片分辨率以480x800为标准
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
//        //缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;//be=1表示不缩放
//        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (originalWidth / ww);
//        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (originalHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
//        //比例压缩
//        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//        bitmapOptions.inSampleSize = be;//设置缩放比例
//        bitmapOptions.inDither = true;
//        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
//        input = context.getContentResolver().openInputStream(uri);
//        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
//        input.close();
//
//        return compressImage(bitmap);//再进行质量压缩
//    }
//
//    public Bitmap compressImage(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//            if (options<=0)
//                break;
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }
//
////    根据文件Uri获取图片url
//    public static String getRealPathFromURI(Context context, Uri contentURI) {
//        String result;
//        Cursor cursor = context.getContentResolver().query(contentURI,
//                new String[]{MediaStore.Images.ImageColumns.DATA},//
//                null, null, null);
//        if (cursor == null) result = contentURI.getPath();
//        else {
//            cursor.moveToFirst();
//            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            result = cursor.getString(index);
//            cursor.close();
//        }
//        return result;
//    }
//
//    /**
//     * 工具类
//     * 从视频中获取第一贞图片
//     */
//    public static Bitmap createVideoThumbnail(String filepath_video) {
//        if ("".equals(filepath_video)) {
//            return null;
//        }
//        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filepath_video, MediaStore.Video.Thumbnails.MINI_KIND);
//        if (bitmap != null) {
//            return bitmap;
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 工具类
//     * 从视频中获取第一贞图片
//     *
//     * @param url
//     * @param width
//     * @param height
//     * @return
//     */
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    public static Bitmap createVideoThumbnail(String url, int width, int height) {
//        Bitmap bitmap = null;
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
//        try {
//            if (Build.VERSION.SDK_INT >= 14) {
//                retriever.setDataSource(url, new HashMap<String, String>());
//            } else {
//                retriever.setDataSource(url);
//            }
//            bitmap = retriever.getFrameAtTime();
//        } catch (IllegalArgumentException ex) {
//            // Assume this is a corrupt video file
//        } catch (RuntimeException ex) {
//            // Assume this is a corrupt video file.
//        } finally {
//            try {
//                retriever.release();
//            } catch (RuntimeException ex) {
//                // Ignore failures while cleaning up.
//            }
//        }
//        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        }
//        return bitmap;
//    }
//
//    public static String getRootDirPath(Context context) {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(), null)[0];
//            return file.getAbsolutePath();
//        } else {
//            return context.getApplicationContext().getFilesDir().getAbsolutePath();
//        }
//    }
//
//    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
//        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
//    }
//
//    private static String getBytesToMBString(long bytes){
//        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
//    }
//}
