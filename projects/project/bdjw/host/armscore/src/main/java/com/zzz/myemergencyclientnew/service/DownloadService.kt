//package com.zzz.myemergencyclientnew.service
//
//import android.app.IntentService
//import android.content.Context
//import android.content.Intent
//import android.os.Handler
//import android.os.SystemClock
//import android.util.Log
//import android.widget.Toast
//import androidx.annotation.NonNull
//import androidx.core.app.JobIntentService
//
//class DownloadService : IntentService(DownloadService::class.simpleName) {
//    override fun onHandleIntent(workIntent: Intent?) {
//        // Gets data from the incoming Intent
//        val dataString = workIntent!!.dataString
////    ...
//        // Do work here, based on the contents of dataString
////    ...
//    }
//
////    override fun onHandleIntent(p0: Intent?) {
////        TODO("Not yet implemented")
////    }
//}
//
//
//class SimpleJobIntentTwoService : JobIntentService() {
//    override fun onHandleWork(@NonNull intent: Intent) {
//        // We have received work to do.  The system or framework is already
//        // holding a wake lock for us at this point, so we can just go.
//        Log.i("SimpleJobIntentService", "Executing work: $intent")
//        var label = intent.getStringExtra("label")
//        if (label == null) {
//            label = intent.toString()
//        }
//        toast("Executing: $label")
//        for (i in 0..4) {
//            Log.i(
//                "SimpleJobIntentService", "Running service " + (i + 1)
//                        + "/5 @ " + SystemClock.elapsedRealtime()
//            )
//            try {
//                Thread.sleep(1000)
//            } catch (e: InterruptedException) {
//            }
//        }
//        Log.i("SimpleJobIntentService", "Completed service @ " + SystemClock.elapsedRealtime())
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        toast("All work complete")
//    }
//
//    val mHandler: Handler = Handler()
//
//    // Helper for showing tests
//    fun toast(text: CharSequence?) {
//        mHandler.post(Runnable {
//            Toast.makeText(
//                this@SimpleJobIntentTwoService,
//                text,
//                Toast.LENGTH_SHORT
//            ).show()
//        })
//    }
//
//    companion object {
//        /**
//         * Unique job ID for this service.
//         */
//        const val JOB_ID = 1000
//
//        /**
//         * Convenience method for enqueuing work in to this service.
//         */
//        fun enqueueWork(context: Context, work: Intent) {
//            enqueueWork(
//                context,
//                SimpleJobIntentService::class.java, JOB_ID, work
//            )
//        }
//    }
//}