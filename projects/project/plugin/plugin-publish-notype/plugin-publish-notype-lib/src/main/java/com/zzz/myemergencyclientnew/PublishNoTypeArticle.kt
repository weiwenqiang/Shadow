package com.zzz.myemergencyclientnew

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.toast.T
import com.zzz.myemergencyclientnew.adapter.gridview.ExpandImageAdapter
import com.zzz.myemergencyclientnew.base.BaseActivity
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.ActivityPublishNotypeArticleBinding
import com.zzz.myemergencyclientnew.eventbus.EvnImagePath
import com.zzz.myemergencyclientnew.library.imagepicker.ImagePicker
import com.zzz.myemergencyclientnew.library.imagepicker.bean.ImageItem
import com.zzz.myemergencyclientnew.media.GlideImageLoaderTwo
import com.zzz.myemergencyclientnew.utils.FileUtils
import com.zzz.myemergencyclientnew.utils.UploadUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList

data class PublishNoTypeReq(
    var id: Int = 0,
    var type: Int = 0,
    var title: String = "",
    var content: String = "",
    var imgs: String = "",
    var video: String = ""
)

interface PublishNoTypeArticleC {
    interface View : BaseView
    abstract class Presenter :
        BasePresenter<View>() {
        abstract fun netPublishNoTypeArticle(
            mActivity: AppCompatActivity,
            token: String,
            params: String,
            req: PublishNoTypeReq,
            service: String
        )
    }
}

class PublishNoTypeArticleP : PublishNoTypeArticleC.Presenter() {
    override fun netPublishNoTypeArticle(
        mActivity: AppCompatActivity,
        token: String,
        params: String,
        req: PublishNoTypeReq,
        service: String
    ) {
        OkGo.post<RespJson<String>>(service + "/app/release/putMessage")
            .tag(this)
            .headers("projectId", C.projectId)
            .headers("projectType", C.projectType)
            .headers("token", token)
            .params("cate", req.type)
            .params("params", params)
            .params("title", req.title)
            .params("content", req.content)
            .params("img", req.imgs)
            .params("video", req.video)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    T.success(mActivity, response.body().msg)
                    mActivity.finish()
                }
            })
    }
}

class PublishNoTypeArticleActivity :
    BaseActivity<PublishNoTypeArticleP, ActivityPublishNotypeArticleBinding>(),
    PublishNoTypeArticleC.View {
    private var images: ArrayList<ImageItem> = ArrayList<ImageItem>()
    private var imagePicker: ImagePicker = initImagePicker()
    private val req = PublishNoTypeReq()
    private var imgFiles: ArrayList<String> = ArrayList<String>()

    private var token: String = ""
    private var title: String = ""
    private var params: String = ""

    private var service: String = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_publish_notype_article
    }

    override fun initView() {
        ARouter.init(mContext.application)
        mViewBinding.mView = this
        mViewBinding.req = req
        token = intent.getStringExtra("token") ?: " 没Token"
        title = intent.getStringExtra("title") ?: " 空标题"
        params = intent.getStringExtra("params") ?: "[]"
        service = intent.getStringExtra("service") ?: Api.JAVA_SERVICE_URL

        mViewBinding.toolbar.title = "${title} ${params} ${token}"
        mViewBinding.toolbar.setNavigationOnClickListener { finish() }
        initImagePicker()
        EventBus.getDefault().register(this)
    }

    private fun initImagePicker(): ImagePicker {
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoaderTwo()
        imagePicker.isCrop = false
        imagePicker.isMultiMode = true
        imagePicker.selectLimit = 9 //最多选9张
        imagePicker.isShowCamera = false //不显示相机
        return imagePicker
    }

    fun affirm(view: View) {
        req.title = mViewBinding.edtTitle.text.toString().trim()
        req.content = mViewBinding.edtText.text.toString().trim()
//        req.type = Integer.valueOf(oid)
        req.id = req.type
        if (TextUtils.isEmpty(req.title) || TextUtils.isEmpty(req.content)) {
            T.error(mContext, "标题和内容不能空")
        } else {
            if (images != null) {
                UploadUtils.uploadFileBatch(
                    mContext,
                    imgFiles
                ) { pathStr ->
                    req.imgs = pathStr!!
                    mPresenter.netPublishNoTypeArticle(mContext, token, params, req, service)
                }
            }
        }
    }

    fun addImage(view: View) {
        ARouter.getInstance().build(C.ImageSelect).navigation()
    }

    fun addVideo(view: View) {
//        GlobalKt.openVideoAddDialog(mActivity);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun evnEvnImagePath(evn: EvnImagePath) {
        images = evn.imageItems
        val adapter = ExpandImageAdapter(mContext, mViewBinding.gridview, images)
        mViewBinding.gridview.adapter = adapter
        mViewBinding.stvVideo.setRightString("")
        mViewBinding.gridview.visibility = View.VISIBLE
        mViewBinding.videoPlayer.visibility = View.GONE
        req.video = ""
        var imgsSize: Long = 0
        for (i in images.indices) {
            imgsSize += images[i].size
            imgFiles.add(images[i].path)
        }
        mViewBinding.stvImage.setRightString(
            images.size.toString() + "张 共" + FileUtils.FormetFileSize(
                imgsSize
            )
        )
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun evnEvnVideoPath(evn: EvnVideoPath) {
//        mViewBinding.stvImage.setRightString("")
//        mViewBinding.stvVideo.setRightString(evn.fileSize + "MB")
//        mViewBinding.gridview.visibility = View.GONE
//        mViewBinding.videoPlayer.visibility = View.VISIBLE
//        req.imgs = ""
//        req.video = evn.urlPath
//        mViewBinding.videoPlayer.setUp(req.video, true, "视频")
//        mViewBinding.videoPlayer.titleTextView.visibility = View.GONE
//        mViewBinding.videoPlayer.backButton.visibility = View.GONE
//        mViewBinding.videoPlayer.setIsTouchWiget(false)
//        mViewBinding.videoPlayer.fullscreenButton.visibility = View.GONE
//    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().removeAllStickyEvents()
        EventBus.getDefault().unregister(this)
    }
}

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
    }
}