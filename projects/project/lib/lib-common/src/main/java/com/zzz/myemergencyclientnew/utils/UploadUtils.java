package com.zzz.myemergencyclientnew.utils;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.zzz.myemergencyclientnew.callback.StringDialogCallback;
import com.zzz.myemergencyclientnew.constant.pref.Api;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.listener.UploadFileListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.toast.T;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

public class UploadUtils {
    /**
     * 新的先通过接口上传
     */
    public static void uploadFile(Activity context, String path, UploadFileListener listener) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        //图片压缩
        File compressedImageFile = file;
        try {
            compressedImageFile = new Compressor(context).compressToFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkGo.<String>post(Api.JAVA_SERVICE_URL+"/app/fastdfs/upload")
                .tag(context)
                .params("file", compressedImageFile)
                .execute(new StringDialogCallback(context) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject obj = JSONObject.parseObject(response.body());
                        int code = obj.getIntValue("code");
                        String msg = obj.getString("msg");
                        if (code == C.API_SUCCEED) {
                            String data = obj.getString("data");
                            listener.onSuccess(data);
                            T.success(context, msg);
                        } else {
                            T.error(context, msg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("Error", response.body().toString());
                    }
                });
    }

    /**
     * 批量文件上传
     */
    public static void uploadFileBatch(Activity context, ArrayList<String> images, UploadFileListener listener) {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i));
            if (!file.exists()) {
                return;
            }
            //图片压缩
            File compressedImageFile = file;
            try {
                compressedImageFile = new Compressor(context).compressToFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            files.add(compressedImageFile);
        }
        OkGo.<String>post(Api.JAVA_SERVICE_URL + "/app/fastdfs/uploadMore")
                .tag(context)
                .addFileParams("files", files)
                .execute(new StringDialogCallback(context) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject obj = JSONObject.parseObject(response.body());
                        int code = obj.getIntValue("code");
                        String msg = obj.getString("msg");
                        if (code == C.API_SUCCEED) {
                            String data = obj.getString("data");
                            listener.onSuccess(data);
//                            T.success(msg);
                        } else {
                            T.error(context, msg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("Error", response.body().toString());
                    }
                });
    }
}
