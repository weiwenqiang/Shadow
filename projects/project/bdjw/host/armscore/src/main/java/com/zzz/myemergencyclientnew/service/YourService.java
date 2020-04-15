//package com.zzz.myemergencyclientnew.service;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.JobIntentService;
//
//import com.toast.T;
//
//public class YourService extends JobIntentService {
//
//    public static final int JOB_ID = 1;
//
//    public static void enqueueWork(Context context, Intent work) {
//        enqueueWork(context, YourService.class, JOB_ID, work);
//    }
//
//    @Override
//    protected void onHandleWork(@NonNull Intent intent) {
//        // 具体逻辑
//        String downloadUrl = intent.getStringExtra("downloadUrl");
////        T.success(YourService.this, downloadUrl);
//        Log.e("downloadUrl", downloadUrl);
//    }
//
//}
