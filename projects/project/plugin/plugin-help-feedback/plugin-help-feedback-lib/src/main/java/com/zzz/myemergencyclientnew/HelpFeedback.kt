package com.zzz.myemergencyclientnew

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.alibaba.android.arouter.launcher.ARouter
import com.zzz.myemergencyclientnew.base.BaseActivity
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.ActivityHelpFeedbackBinding
import com.zzz.myemergencyclientnew.eventbus.EvnImagePath
import com.zzz.myemergencyclientnew.library.imagepicker.ImagePicker
import com.zzz.myemergencyclientnew.media.GlideImageLoaderTwo
import com.zzz.myemergencyclientnew.utils.UploadUtils
import com.bumptech.glide.Glide
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.toast.T
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

data class HelpFeedbackReq(var content: String = "", var phone: String = "", var imgs: String = "")

interface HelpFeedbackC {
    interface View : BaseView
    abstract class Presenter :
        BasePresenter<View>() {
        abstract fun netHelpFeedback(
            mActivity: AppCompatActivity,
            token: String,
            req: HelpFeedbackReq
        )
    }
}

class HelpFeedbackP : HelpFeedbackC.Presenter() {
    override fun netHelpFeedback(
        mActivity: AppCompatActivity,
        token: String,
        req: HelpFeedbackReq
    ) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/release/putfeedback")
            .headers("projectId", C.projectId)
            .headers("projectType", C.projectType)
            .headers("token", token)
            .params("content", req.content)
            .params("phone", req.phone)
            .params("imgs", req.imgs)
            .tag(this)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    T.success(mActivity, "${response.body().msg}")
                    mActivity.finish()
                }
            })
    }
}

class HelpFeedbackActivity :
    BaseActivity<HelpFeedbackP, ActivityHelpFeedbackBinding>(),
    HelpFeedbackC.View {
    private var imagePicker: ImagePicker = initImagePicker()
    private val req = HelpFeedbackReq()
    private var token: String = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_help_feedback
    }

    override fun initView() {
//        ARouter.init(mContext.application)
        mViewBinding.mView = this
        token = intent.getStringExtra("token") ?:" 没Token"
        mViewBinding.toolbar.setTitle("帮助反馈")
        mViewBinding.toolbar.setNavigationOnClickListener { finish() }
        EventBus.getDefault().register(this)
    }

    fun affirm(view: View) {
        req.content = mViewBinding.edtText.getText().toString().trim()
        req.phone = mViewBinding.edtPhone.getText().toString().trim()
        if (TextUtils.isEmpty(req.content)) {
            T.error(mContext, "描述不能空")
        } else {
            mPresenter.netHelpFeedback(mContext, token, req)
        }
    }

    fun addImg(view: View) {
        MaterialDialog(mContext).show {
            listItems(items = listOf("相册选取", "相机拍照")) { _, index, text ->
                when (index) {
                    0 -> ARouter.getInstance().build(C.ImageSelect).navigation()
                    1 -> ARouter.getInstance().build(C.ImageSelect).withBoolean("directPhoto", true)
                        .navigation()
                }
            }
        }
    }

    private fun initImagePicker(): ImagePicker {
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoaderTwo()
        imagePicker.isCrop = false
        imagePicker.isMultiMode = false
        imagePicker.isShowCamera = false //不显示相机
        return imagePicker
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun evnEvnImagePath(evn: EvnImagePath) {
        UploadUtils.uploadFile(
            mContext,
            evn.imageItems.get(0).path
        ) { pathStr ->
            req.imgs = pathStr
            Glide.with(mContext).load(Api.IMG_URL + req.imgs)
                .into(
                    mViewBinding.imgAddDefault
                )
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().removeAllStickyEvents()
        EventBus.getDefault().unregister(this)
    }
}

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
    }
}