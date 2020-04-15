package com.zzz.myemergencyclientnew.widget.tangram

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.tmall.wireless.tangram.structure.BaseCell
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle
import com.zzz.myemergencyclientnew.R
import com.zzz.myemergencyclientnew.db.ApkPluginEntity
import com.zzz.myemergencyclientnew.utils.SPUtils
import com.zzz.myemergencyclientnew.utils.UiUtils
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.util.*


class ServeItemView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) :
    FrameLayout(mContext, attrs, defStyleAttr),
    ITangramViewLifeCycle {

    init {
        initUI(context)
    }

    private lateinit var imgIcon: ImageView
    private lateinit var txtTitle: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var txtState: TextView

    private fun initUI(context: Context) {
        View.inflate(context, R.layout.v_serve_plugin, this)
        imgIcon = findViewById(R.id.img_icon)
        txtTitle = findViewById(R.id.txt_title)
        progressBar = findViewById(R.id.progressBar)
        txtState = findViewById(R.id.txt_state)
    }

    override fun cellInited(cell: BaseCell<*>) {
        setOnClickListener(cell)
    }

    override fun postBindView(cell: BaseCell<*>) {
        txtTitle.text = cell.optStringParam("title")
        Glide.with(context.applicationContext)
            .load(cell.optStringParam("iconNormal"))
            .dontAnimate()
            .placeholder(R.drawable.ic_placeholder)
            .into(imgIcon)
        val funType = cell.optIntParam("funType")
        if (funType == 1) { //是否是插件
            val plugCode = cell.optStringParam("plugCode")

            val realm: Realm = Realm.getDefaultInstance()

            var results: ApkPluginEntity? = realm.where<ApkPluginEntity>()
                .equalTo("plugCode", plugCode)
                .sort("plugCode", Sort.DESCENDING)
                .findFirst()
            var oldCode: Int = results?.apkVersionCode ?: 0
            val apkVersionCode = cell.optIntParam("apkVersionCode")
            if (apkVersionCode > oldCode) {
                val downloadLink = cell.optStringParam("downloadLink")
                val apkFileName = cell.optStringParam("apkFileName")
                val id = cell.optStringParam("id")

                SPUtils.put(mContext, plugCode, false)

                val llsApkFilePath =
                    context.getFilesDir().absolutePath + File.separator + apkFileName

                val file = File(llsApkFilePath)
                if (file.exists()) {
                    val isAnZ = file.delete()
                }
                val fileLib = File(llsApkFilePath + "_lib")
                if (fileLib.exists()) {
                    val isAnZ = fileLib.delete()
                }
                val fileOdex = File(llsApkFilePath + "_odex")
                if (fileOdex.exists()) {
                    val isAnZ = fileOdex.delete()
                }

                progressBar.isIndeterminate = false
                progressBar.indeterminateDrawable
                    .setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
                PRDownloader.download(
                        downloadLink,
                        context.getFilesDir().absolutePath,
                        apkFileName
                    )
                    .build()
                    .setOnProgressListener { progress ->
                        val progressPercent =
                            progress.currentBytes * 100 / progress.totalBytes
                        progressBar.progress = progressPercent.toInt()
                        txtState.text = UiUtils.getProgressDisplayLine(
                            progress.currentBytes,
                            progress.totalBytes
                        )
                    }
                    .start(object : OnDownloadListener {
                        override fun onDownloadComplete() {
                            txtState.text = "下载就绪"
                            realm.executeTransaction { realm ->
                                val apkPlugin: ApkPluginEntity =
                                    realm.createObject<ApkPluginEntity>(id)
                                apkPlugin.plugCode = plugCode
                                apkPlugin.apkVersionCode = apkVersionCode
                                apkPlugin.downloadLink = downloadLink
                                apkPlugin.apkFileName = apkFileName
                                apkPlugin.apkVersionName = cell.optStringParam("apkVersionName")
                                apkPlugin.enter = cell.optStringParam("enter")
                                apkPlugin.receiver = cell.optStringParam("receiver")
                                apkPlugin.receiverAction = cell.optStringParam("receiverAction")

                                EventBus.getDefault()
                                    .post(apkPlugin)

                            }
                            realm.close()


//                            SPUtils.put(mContext, plugCode, true)
////                                val apk:InstalledApk = PluginApkBloc(apkFileName).preparePlugin(context)
////                                HostApplication.mPluginMap.put(
////                                    plugCode,
////                                    apk
////                                )
                        }

                        override fun onError(error: Error) {
                            txtState.text = "下载异常"
                            progressBar.progress = 0
                        }
                    })
            } else {
                txtState.text = "无需更新 v${oldCode}"
                progressBar.progress = 100
                EventBus.getDefault()
                    .post(results)
            }

        } else {
            txtState.text = "不是插件"
        }
    }

    override fun postUnBindView(cell: BaseCell<*>) {}
}