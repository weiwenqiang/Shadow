//package com.zzz.myemergencyclientnew.service
//
//import android.content.Context
//import android.content.Intent
//import android.os.Handler
//import android.os.SystemClock
//import android.util.Log
//import android.widget.Toast
//import androidx.core.app.JobIntentService
//
//
//public class SimpleJobIntentService : JobIntentService() {
//
//    val JOB_ID = 1000
//
//    public fun enqueueWork(context: Context?, work: Intent?) {
//        JobIntentService.enqueueWork(context!!, SimpleJobIntentService::class.java, JOB_ID, work!!)
//    }
//
//    override fun onHandleWork(intent: Intent) {
//        var label = intent.getStringExtra("label")
//        if (label == null) {
//            label = intent.toString()
//        }
//        toast("Executing: $label")
//        for (i in 0..40) {
//            Log.i("Service", "Running service " + (i + 1) + "/5 @ " + SystemClock.elapsedRealtime())
//            try {
//                Thread.sleep(1000)
//            } catch (e: InterruptedException) {
//            }
//        }
//        Log.i("Service", "Completed service @ " + SystemClock.elapsedRealtime())
//        toast("END: $label")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        toast("All work complete")
//    }
//
//    val mHandler: Handler = Handler()
//
//    fun toast(text: CharSequence?) {
//        mHandler.post(Runnable {
//            Toast.makeText(this@SimpleJobIntentService, text, Toast.LENGTH_SHORT).show()
//        })
//    }
//}