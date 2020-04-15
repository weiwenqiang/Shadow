//package com.zzz.myemergencyclientnew.service
//
//import android.app.IntentService
//import android.content.Context
//import android.content.Intent
//import android.os.Handler
//import android.widget.Toast
//import com.zzz.myemergencyclientnew.test.TestServiceActivity
//
//
//private const val ACTION_FOO = "ru.suleymanovtat.jobintentserviceapplication.action.FOO"
//private const val ACTION_BAZ = "ru.suleymanovtat.jobintentserviceapplication.action.BAZ"
//private const val EXTRA_PARAM1 = "ru.suleymanovtat.jobintentserviceapplication.extra.PARAM1"
//private const val EXTRA_PARAM2 = "ru.suleymanovtat.jobintentserviceapplication.extra.PARAM2"
//
//class SimpleIntentService : IntentService("SimpleIntentService") {
//
//    override fun onHandleIntent(intent: Intent?) {
//        when (intent?.action) {
//            ACTION_FOO -> {
//                val param1 = intent.getStringExtra(EXTRA_PARAM1)
//                val param2 = intent.getStringExtra(EXTRA_PARAM2)
//                handleActionFoo(param1, param2)
//            }
//            ACTION_BAZ -> {
//                val param1 = intent.getStringExtra(EXTRA_PARAM1)
//                val param2 = intent.getStringExtra(EXTRA_PARAM2)
//                handleActionBaz(param1, param2)
//            }
//        }
//    }
//
//    private fun handleActionFoo(param1: String?, param2: String?) {
//    }
//
//    private fun handleActionBaz(param1: String?, param2: String?) {
//        startActivity("start NextActivity");
//    }
//
//    companion object {
//
//        @JvmStatic
//        fun startActionFoo(context: Context, param1: String, param2: String) {
//            val intent = Intent(context, SimpleIntentService::class.java).apply {
//                action =
//                    ACTION_FOO
//                putExtra(EXTRA_PARAM1, param1)
//                putExtra(EXTRA_PARAM2, param2)
//            }
//            context.startService(intent)
//        }
//
//        @JvmStatic
//        fun startActionBaz(context: Context, param1: String, param2: String) {
//            val intent = Intent(context, SimpleIntentService::class.java).apply {
//                action =
//                    ACTION_BAZ
//                putExtra(EXTRA_PARAM1, param1)
//                putExtra(EXTRA_PARAM2, param2)
//            }
//            context.startService(intent)
//        }
//    }
//
//    val mHandler: Handler = Handler()
//
//    fun startActivity(text: CharSequence?) {
//        mHandler.post(Runnable {
//            Toast.makeText(this@SimpleIntentService, text, Toast.LENGTH_SHORT).show()
//            val myIntent = Intent(this@SimpleIntentService, TestServiceActivity::class.java)
//            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(myIntent)
//        })
//    }
//}