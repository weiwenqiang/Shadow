//package com.zzz.myemergencyclientnew.activity
//
//import android.app.Activity
//import android.app.AlertDialog
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.PorterDuff
//import android.net.Uri
//import android.os.Build
//import android.provider.Settings
//import android.text.TextUtils
//import android.view.View
//import android.view.animation.AlphaAnimation
//import android.view.animation.Animation
//import android.view.animation.AnimationSet
//import android.widget.FrameLayout
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.core.content.FileProvider
//import com.alibaba.android.arouter.launcher.ARouter
//import com.alibaba.fastjson.JSONObject
//import com.downloader.Error
//import com.downloader.OnDownloadListener
//import com.downloader.PRDownloader
//import com.lzy.okgo.OkGo
//import com.lzy.okgo.callback.StringCallback
//import com.lzy.okgo.model.Response
//import com.zzz.myemergencyclientnew.R
//import com.zzz.myemergencyclientnew.activity.splash.SplashActivity
//import com.zzz.myemergencyclientnew.base.BaseActivity
//import com.zzz.myemergencyclientnew.base.BasePresenter
//import com.zzz.myemergencyclientnew.base.BaseView
//import com.zzz.myemergencyclientnew.callback.JsonCallback
//import com.zzz.myemergencyclientnew.callback.RespJson
//import com.zzz.myemergencyclientnew.constant.pref.Api
//import com.zzz.myemergencyclientnew.constant.pref.C
//import com.zzz.myemergencyclientnew.databinding.ASplashBinding
//import com.zzz.myemergencyclientnew.response.ApkVersionResp
//import com.zzz.myemergencyclientnew.utils.AppUtils
//import com.zzz.myemergencyclientnew.utils.FileUtils
//import com.zzz.myemergencyclientnew.utils.SPUtils.get
//import java.io.File
//
//interface SplashC {
//    interface View : BaseView {
//        fun startAnim()
//
//        fun jumpNextPage(isJump: Boolean)
//
//        fun netUpdate(resp: ApkVersionResp)
//
//        fun goApkInstall(llsApkFilePath: String)
//
//        fun goHomeActivity()
//    }
//
//    abstract class Presenter : BasePresenter<View>() {
//        abstract fun startAnim(
//            activity: Activity,
//            lyt: FrameLayout
//        )
//
//        abstract fun netUpdate(activity: Activity)
//
//        abstract fun createRPDownloadTask(
//            activity: Activity,
//            url: String,
//            speedTv1: TextView,
//            progressBar: ProgressBar
//        )
//
//        abstract fun netAppLogin(
//            mActivity: Activity,
//            username: String,
//            password: String
//        )
//    }
//}
//
//class SplashP : SplashC.Presenter() {
//    override fun startAnim(activity: Activity, lyt: FrameLayout) {
//        val set = AnimationSet(false)
//
//        val alphaAnimation = AlphaAnimation(1f, 1f)
//        alphaAnimation.duration = 500
//        alphaAnimation.fillAfter = true
//
//        set.addAnimation(alphaAnimation)
//
//        set.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation) {}
//            override fun onAnimationEnd(animation: Animation) {
//                val isJump = get(
//                    activity,
//                    C.sp_slider,
//                    false
//                ) as Boolean
//                mView.jumpNextPage(isJump)
//            }
//
//            override fun onAnimationRepeat(animation: Animation) {}
//        })
//        lyt.startAnimation(set)
//    }
//
//    override fun netUpdate(activity: Activity) {
//        OkGo.post<RespJson<ApkVersionResp>>("http://192.168.81.229:9090/getHostVersion?code=HOST&version=198") //        OkGo.<RespJson<ApkVersionResp>>post(Api.JAVA_SERVICE_URL + "/app/member/getApkVersion")
//            .tag(this)
//            .params("versionCode", AppUtils.getVersionCode(activity))
//            .execute(object :
//                JsonCallback<RespJson<ApkVersionResp>>() {
//                override fun onSuccess(response: Response<RespJson<ApkVersionResp>>) {
//                    mView.netUpdate(response.body().data)
//                }
//
//                override fun onError(response: Response<RespJson<ApkVersionResp>>) {
//                    mView.startAnim()
//                }
//            })
//    }
//
//    override fun createRPDownloadTask(
//        activity: Activity,
//        url: String,
//        speedTv1: TextView,
//        progressBar: ProgressBar
//    ) {
//        val llsApkFilePath =
//            FileUtils.getRootDirPath(activity) + File.separator + "app-release.apk"
//        val file = File(llsApkFilePath)
//        if (file.exists()) {
//            val isAnZ = file.delete()
//        }
//
//        progressBar.setIndeterminate(true)
//        progressBar.getIndeterminateDrawable()
//            .setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
//
//        PRDownloader.download(
//                url,
//                FileUtils.getRootDirPath(activity),
//                "app-release.apk"
//            )
//            .build()
//            .setOnProgressListener { progress ->
//                val progressPercent =
//                    progress.currentBytes * 100 / progress.totalBytes
//                progressBar.setProgress(progressPercent.toInt())
//                speedTv1.setText(
//                    FileUtils.getProgressDisplayLine(
//                        progress.currentBytes,
//                        progress.totalBytes
//                    )
//                )
//            }
//            .start(object : OnDownloadListener {
//                override fun onDownloadComplete() {
//                    val apkFilePath =
//                        FileUtils.getRootDirPath(activity) + File.separator + "app-release.apk"
//                    mView.goApkInstall(apkFilePath)
//                }
//
//                override fun onError(error: Error) {
//                    speedTv1.text="下载异常"
//                    progressBar.progress=0
//                    progressBar.isIndeterminate=false
//                }
//            })
//    }
//
//    override fun netAppLogin(mActivity: Activity, username: String, password: String) {
//        OkGo.post<String>(Api.JAVA_SERVICE_URL + "/app/member/login")
//            .tag(this)
//            .params("username", username)
//            .params("password", password)
//            .execute(object : StringCallback() {
//                override fun onSuccess(response: Response<String>) {
//                    val jsonObject =
//                        JSONObject.parseObject(response.body().toString())
//                    val code = jsonObject.getInteger("code")
//                    val data = jsonObject.getString("data")
//                    val msg = jsonObject.getString("msg")
//                    //                        T.success(msg);
//                    if (code == C.API_SUCCEED) {
////                            C.login(mActivity, data);
//                        mView.goHomeActivity()
//                    } else {
////                            C.logout(mActivity);
//                        mView.goHomeActivity()
//                    }
//                }
//
//                override fun onError(response: Response<String>) {
//                    mView.goHomeActivity()
//                }
//            })
//    }
//}
//
//class SplashActivity :
//    BaseActivity<SplashP, ASplashBinding>(),
//    SplashC.View {
//
//    override fun getLayoutId(): Int {
//        return R.layout.a_splash
//    }
//
//    override fun initView() {
//        if (AppUtils.isNetConn(mContext)) {
//            mPresenter.startAnim(mContext, mViewBinding.lyt)
////            mPresenter.netUpdate(mContext);
//        } else {
////            AlertDialog.Builder builder = new AlertDialog.Builder(this);
////            builder.setTitle("网络异常");
////            builder.setMessage("请检查网络连接并重启");
////            builder.setCancelable(false);
////            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    mContext.finish();
////                }
////            });
////            builder.create().show();
////            C.logout(mContext);
//
////            AlertDialog.Builder builder = new AlertDialog.Builder(this);
////            builder.setTitle("网络异常");
////            builder.setMessage("请检查网络连接并重启");
////            builder.setCancelable(false);
////            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    mContext.finish();
////                }
////            });
////            builder.create().show();
////            C.logout(mContext);
//            goHomeActivity()
//        }
//    }
//
//    override fun startAnim() {
//        mPresenter.startAnim(mContext, mViewBinding.lyt)
//    }
//
//    override fun jumpNextPage(isJump: Boolean) {
//        if (isJump) {
//            val token = get(
//                mContext,
//                C.sp_token,
//                ""
//            ) as String?
//            if (TextUtils.isEmpty(token)) {
//                goHomeActivity()
//            } else {
//                val username = get(
//                    mContext,
//                    C.sp_username,
//                    ""
//                ) as String?
//                val password = get(
//                    mContext,
//                    C.sp_password,
//                    ""
//                ) as String?
//                mPresenter.netAppLogin(mContext, username!!, password!!)
//            }
//        } else {
//            ARouter.getInstance().build(C.SLIDER)
//                .navigation()
//        }
//    }
//
//    override fun netUpdate(resp: ApkVersionResp) {
//        if (resp.versionCode > AppUtils.getVersionCode(mContext)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //8.0以上有是否允许应用安装应用权限
//                val hasInstallPermission =
//                    mContext.packageManager.canRequestPackageInstalls()
//                if (!hasInstallPermission) {
//                    startInstallPermissionSettingActivity(mContext)
//                } else {
//                    mViewBinding.llContent.visibility = View.VISIBLE
//                    //                    downloadId1 = mPresenter.createDownloadTask(mContext, resp.getVerDownload(), mViewBinding.speedTv1, mViewBinding.filenameTv1, mViewBinding.progressBar);
//                    mPresenter.createRPDownloadTask(
//                        mContext,
//                        "http://192.168.81.137:8080/test/armscore-debug.apk",
//                        mViewBinding.speedTv1,
//                        mViewBinding.progressBar
//                    )
//                    //                    mPresenter.createRPDownloadTask(mContext, resp.getDownloadLink(), mViewBinding.speedTv1, mViewBinding.progressBar);
//                }
//            } else {
//                mViewBinding.llContent.visibility = View.VISIBLE
//                //                downloadId1 = mPresenter.createDownloadTask(mContext, resp.getVerDownload(), mViewBinding.speedTv1, mViewBinding.filenameTv1, mViewBinding.progressBar);
//                mPresenter.createRPDownloadTask(
//                    mContext,
//                    "http://192.168.81.137:8080/test/armscore-debug.apk",
//                    mViewBinding.speedTv1,
//                    mViewBinding.progressBar
//                )
//                //                mPresenter.createRPDownloadTask(mContext, resp.getDownloadLink(), mViewBinding.speedTv1, mViewBinding.progressBar);
//            }
//        } else {
//            mPresenter.startAnim(mContext, mViewBinding.lyt)
//        }
//    }
//
//    override fun goApkInstall(llsApkFilePath: String) {
//        val intent: Intent =
//            getInstallAppIntent(mContext, File(llsApkFilePath))
//        if (mContext.packageManager.queryIntentActivities(intent, 0).size > 0) {
//            mContext.startActivityForResult(
//                intent,
//                INSTALL_COURSE_CODE
//            )
//        }
//    }
//
//    override fun goHomeActivity() {
//        ARouter.getInstance().build(C.HOME).navigation()
////        finish();
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
////        FileDownloader.getImpl().pause(downloadId1);
//    }
//
//    fun getInstallAppIntent(
//        context: Activity,
//        appFile: File
//    ): Intent {
//        val intent =
//            Intent(Intent.ACTION_VIEW)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.action = Intent.ACTION_DEFAULT
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //区别于 FLAG_GRANT_READ_URI_PERMISSION 跟 FLAG_GRANT_WRITE_URI_PERMISSION， URI权限会持久存在即使重启，直到明确的用 revokeUriPermission(Uri, int) 撤销。 这个flag只提供可能持久授权。但是接收的应用必须调用ContentResolver的takePersistableUriPermission(Uri, int)方法实现
//            intent.flags =
//                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
//            val fileUri = FileProvider.getUriForFile(
//                context,
//                context.applicationContext.packageName + ".fileProvider",
//                appFile!!
//            )
//            intent.setDataAndType(fileUri, "application/vnd.android.package-archive")
//        } else {
//            intent.setDataAndType(
//                Uri.fromFile(appFile),
//                "application/vnd.android.package-archive"
//            )
//        }
//        return intent
//    }
//
//    /**
//     * 跳转到设置-允许安装未知来源-页面
//     */
//    //    @RequiresApi(api = Build.VERSION_CODES.O)
//    fun startInstallPermissionSettingActivity(context: Activity) {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("安装权限")
//        builder.setMessage("有更新，需要打开允许来自此来源，请去设置中开启此权限")
//        builder.setCancelable(false)
//        builder.setPositiveButton(
//            "确认"
//        ) { dialog, which -> //注意这个是8.0新API
//            val packageURI =
//                Uri.parse("package:" + context.packageName)
//            val intent = Intent(
//                Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
//                packageURI
//            )
//            //                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivityForResult(
//                intent,
//                INSTALL_PERMISS_CODE
//            )
//        }
//        builder.setNegativeButton(
//            "取消"
//        ) { dialog, which -> context.finish() }
//        builder.create().show()
//    }
//
//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == INSTALL_PERMISS_CODE) { //同意安装授权
//            mPresenter.netUpdate(mContext)
//        } else if (resultCode == Activity.RESULT_CANCELED && requestCode == INSTALL_PERMISS_CODE) { //取消安装授权
//            startInstallPermissionSettingActivity(mContext)
//        } else if (resultCode == Activity.RESULT_CANCELED && requestCode == INSTALL_COURSE_CODE) { //取消安装
//            mContext.finish()
//        }
//    }
//
//    //8.0以上安装权限监听
//    private val INSTALL_PERMISS_CODE = 0x01
//
//    //监听安装页面是否取消
//    private val INSTALL_COURSE_CODE = 0x02
//}