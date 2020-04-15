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
import com.baidumap.eventbus.EvnMapPoiSelect
import com.zzz.myemergencyclientnew.adapter.gridview.ExpandImageAdapter
import com.zzz.myemergencyclientnew.base.BaseActivity
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.ActivityPublishSosBinding
import com.zzz.myemergencyclientnew.eventbus.EvnImagePath
import com.zzz.myemergencyclientnew.library.imagepicker.ImagePicker
import com.zzz.myemergencyclientnew.library.imagepicker.bean.ImageItem
import com.zzz.myemergencyclientnew.media.GlideImageLoaderTwo
import com.zzz.myemergencyclientnew.utils.FileUtils
import com.zzz.myemergencyclientnew.utils.StringMyUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.toast.T
import com.videocut.eventbus.EvnVideoPath
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

data class PublishSOSReq(var title: String="",
                         var content: String="",
                         var address: String="",
                         var lat: String="",
                         var lng: String="",
                         var img:String = "",
                         var video:String = "",
                         var help_range: String="")

data class HelpRangeResp(var remark:String, var id:Int, var content:Int)

interface PublishSOSC {
    interface View : BaseView {
        fun getHelpRange(list: HelpRangeResp)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun netPublishSOS(mActivity: AppCompatActivity, req: PublishSOSReq)
        abstract fun getHelpRange(mActivity: AppCompatActivity)
    }
}

class PublishSOSP : PublishSOSC.Presenter() {
    override fun netPublishSOS(mActivity: AppCompatActivity, req: PublishSOSReq) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/release/putAskHelp")
//            .headers("token", C.getToken())
            .params("title", req.title)
            .params("content", req.content)
            .params("address", req.address)
            .params("lat", req.lat)
            .params("lng", req.lng)
            .params("img", req.img)
            .params("video", req.video)
            .params("help_range", req.help_range)
            .tag(this)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    T.success(mActivity, response.body().msg)
                    mActivity.finish()
                }
            })
    }

    override fun getHelpRange(mActivity: AppCompatActivity) {
        OkGo.post<RespJson<List<HelpRangeResp>>>(Api.JAVA_SERVICE_URL + "/app/release/getHelpRange")
//            .headers("token", C.getToken())
            .tag(this)
            .execute(object : JsonCallback<RespJson<List<HelpRangeResp>>>() {
                override fun onSuccess(response: Response<RespJson<List<HelpRangeResp>>>) {
                    var showList: MutableList<String> = mutableListOf()
                    response.body().data.forEach {
                        showList.add(it.remark)
                    }
                    MaterialDialog(mActivity).show {
                        title(text = "请选择求救范围")
                        listItems(items = showList) { _, indices, text ->
                            mView.getHelpRange(response.body().data.get(indices))
                            dismiss()
//                            txt.text = "Selected items ${text.joinToString()} are_at indices ${indices.joinToString()}"
                        }
//                        positiveButton(R.string.confirm)
                    }
                }
            })
    }
}

class PublishSOSActivity :
    BaseActivity<PublishSOSP, ActivityPublishSosBinding>(),
    PublishSOSC.View {
    private var images: ArrayList<ImageItem> = ArrayList<ImageItem>()
    private var imagePicker: ImagePicker = initImagePicker()
    private val req = PublishSOSReq()
    private var token: String = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_publish_sos
    }

    override fun initView() {
        ARouter.init(mContext.application)
        mViewBinding.mView = this
        token = intent.getStringExtra("token") ?:" 没Token"
        mViewBinding.toolbar.setTitle("情报报知${token}")
        mViewBinding.toolbar.setNavigationOnClickListener { finish() }
        initImagePicker()
        EventBus.getDefault().register(this)

        mViewBinding.stvSosRange.setTag(1);//本地初始化范围
        mViewBinding.stvAddress.setRightString(StringMyUtils.showAddress(C.addr));
        req.address = StringMyUtils.showAddress(C.addr);
        req.lat = C.lat;
        req.lng = C.lon;
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
        req.title = mViewBinding.edtTitle.getText().toString().trim()
        req.content = mViewBinding.edtText.getText().toString().trim()
        req.help_range = "" + mViewBinding.stvSosRange.getTag()
        req.address = mViewBinding.stvAddress.getRightString()
        if (TextUtils.isEmpty(req.title) || TextUtils.isEmpty(req.content)) {
            T.error(mContext, "标题和内容不能空")
        } else {
            if (images != null) {
//                FileUtils.uploadFileBatch(mActivity, images, new UploadFileListener() {
//                    @Override
//                    public void onSuccess(String pathStr) {
//                        req.img = pathStr;
//                        mPresenter.netPublishSOS(mActivity, req);
//                    }
//                });
            }
        }
    }

    fun address(view: View) {
        ARouter.getInstance().build(C.MapPoiSelect).navigation()
    }

    fun sosRange(view: View) {
        mPresenter.getHelpRange(mContext)
    }

    fun addImage(view: View) {
        ARouter.getInstance().build(C.ImageSelect).navigation()
    }

    fun addVideo(view: View) {
//        GlobalKt.openVideoAddDialog(mActivity);
    }

    override fun getHelpRange(list: HelpRangeResp) {
        mViewBinding.stvSosRange.setRightString(list.remark)
        mViewBinding.stvSosRange.setTag(list.id)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun evnEvnMapPoiSelect(evn: EvnMapPoiSelect) {
        mViewBinding.stvAddress.setRightString(StringMyUtils.showAddress(evn.addr))
        req.lat = evn.lat
        req.lng = evn.lon
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun evnEvnImagePath(evn: EvnImagePath) {
        images = evn.imageItems
        val adapter = ExpandImageAdapter(mContext, mViewBinding.gridview, images)
        mViewBinding.gridview.setAdapter(adapter)
        mViewBinding.stvVideo.setRightString("")
        mViewBinding.gridview.setVisibility(View.VISIBLE)
        mViewBinding.videoPlayer.setVisibility(View.GONE)
        req.video = ""
        var imgsSize: Long = 0
        for (i in images.indices) {
            imgsSize += images[i].size
        }
        mViewBinding.stvImage.setRightString(images.size.toString() + "张 共" + FileUtils.FormetFileSize(imgsSize))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun evnEvnVideoPath(evn: EvnVideoPath) {
        mViewBinding.stvImage.setRightString("")
        mViewBinding.stvVideo.setRightString(evn.fileSize + "MB")
        mViewBinding.gridview.setVisibility(View.GONE)
        mViewBinding.videoPlayer.setVisibility(View.VISIBLE)
        req.img = ""
        req.video = evn.urlPath
        mViewBinding.videoPlayer.setUp(req.video, true, "视频")
        mViewBinding.videoPlayer.getTitleTextView().setVisibility(View.GONE)
        mViewBinding.videoPlayer.getBackButton().setVisibility(View.GONE)
        mViewBinding.videoPlayer.setIsTouchWiget(false)
        mViewBinding.videoPlayer.getFullscreenButton().setVisibility(View.GONE)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().removeAllStickyEvents()
        EventBus.getDefault().unregister(this)
    }
    
}

//class PublishIntellTypeAdapter : BaseQuickAdapter<IntellTypeResp, BaseViewHolder>(R.layout.item_publish_article_type, null) {
//    override fun convert(helper: BaseViewHolder, item: IntellTypeResp) {
////        helper.setText(R.id.txt_id, ""+item.getId());
//        helper.setText(R.id.txt_title, item.getName())
//        val img_icon = helper.getView<ImageView>(R.id.img_icon)
//        Glide.with(mContext).load(Api.JAVA_SERVICE_URL + "/" + item.getIcon()).into(img_icon)
//    }
//}

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
    }
}