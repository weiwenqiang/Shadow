package com.zzz.myemergencyclientnew

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.customListAdapter
import com.alibaba.android.arouter.launcher.ARouter
//import com.baidumap.eventbus.EvnMapPoiSelect
import com.zzz.myemergencyclientnew.adapter.gridview.ExpandImageAdapter
import com.zzz.myemergencyclientnew.base.BaseActivity
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.ActivityPublishIntellReportBinding
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
//import com.videocut.eventbus.EvnVideoPath
import com.zzz.myemergencyclientnew.utils.UploadUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

data class IntellTypeResp(
    var icon: String = "",
    var id: String = "",
    var is_show: String = "",
    var name: String = ""
)

data class GetArmsDepartmentResp(var regionId: String = "", var regionName: String = "")
data class PublishIntellReportReq(
    var type: String = "",
    var area_code: String = "",
    var address: String = "",
    var lat: String = "",
    var lon: String = "",
    var imgs: String = "",
    var video: String = ""
)

interface PublishIntellReportC {
    interface View : BaseView {
        fun openTypeDialog(resp: IntellTypeResp)
        fun netGetArmsDepartment(resp: GetArmsDepartmentResp)
    }

    abstract class Presenter :
        BasePresenter<View>() {
        abstract fun openTypeDialog(mActivity: AppCompatActivity)
        abstract fun netGetArmsDepartment(
            mActivity: AppCompatActivity,
            lat: String,
            lng: String
        )

        abstract fun netIntellReport(
            mActivity: AppCompatActivity,
            token: String,
            req: PublishIntellReportReq,
            service: String
        )
    }
}

class PublishIntellReportP : PublishIntellReportC.Presenter() {
    override fun netIntellReport(
        mActivity: AppCompatActivity,
        token: String,
        req: PublishIntellReportReq,
        service: String
    ) {
        OkGo.post<RespJson<String>>(service + "/app/release/addIntellNew")
            .headers("token", token)
            .params("area_code", req.area_code)
            .params("img", req.imgs)
            .params("video", req.video)
            .params("address", req.address)
            .params("lat", req.lat)
            .params("lon", req.lon)
            .params("type", req.type)
            .tag(this)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    T.success(mActivity, response.body().msg)
                    mActivity.finish()
                }
            })
    }

    override fun netGetArmsDepartment(mActivity: AppCompatActivity, lat: String, lng: String) {
        OkGo.post<RespJson<GetArmsDepartmentResp>>(Api.JAVA_SERVICE_URL + "/app/release/getArmsDepartment")
            .tag(this)
            .params("lat", lat)
            .params("lng", lng)
            .execute(object : JsonCallback<RespJson<GetArmsDepartmentResp>>() {
                override fun onSuccess(response: Response<RespJson<GetArmsDepartmentResp>>) {
                    mView.netGetArmsDepartment(response.body().data)
                }
            })
    }

    override fun openTypeDialog(mActivity: AppCompatActivity) {
        OkGo.post<RespJson<List<IntellTypeResp>>>(Api.JAVA_SERVICE_URL + "/app/release/intelltype")
            .tag(this)
            .execute(object : JsonCallback<RespJson<List<IntellTypeResp>>>() {
                override fun onSuccess(response: Response<RespJson<List<IntellTypeResp>>>) {
                    var iconList = mutableListOf<IntellTypeResp>()
                    var moreList = mutableListOf<IntellTypeResp>()
                    response.body().data.forEach {
                        if (it.icon.equals("-1")) {
                            moreList.add(it)
                        } else {
                            iconList.add(it)
                        }
                    }

                    var dialog = MaterialDialog(mActivity)
                    val mAdapter = PublishIntellTypeAdapter()
                    mAdapter.onItemClickListener =
                        BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
                            val entity = adapter.data[position] as IntellTypeResp
                            if (entity.id.equals("0")) {
                                mAdapter.remove(position)
                                mAdapter.addData(moreList)
                            } else {
                                mView.openTypeDialog(entity)
                                dialog.dismiss()
                            }
                        }
                    mAdapter.setNewData(iconList)
                    dialog.show {
                        cornerRadius(10f)
                        customListAdapter(mAdapter)
                    }
                }
            })
    }
}

class PublishIntellReportActivity :
    BaseActivity<PublishIntellReportP, ActivityPublishIntellReportBinding>(),
    PublishIntellReportC.View {
    private var images: ArrayList<ImageItem> = ArrayList<ImageItem>()
    private var imagePicker: ImagePicker = initImagePicker()
    private val req = PublishIntellReportReq()
    private var imgFiles: ArrayList<String> = ArrayList<String>()

    private var token: String = ""
    private var title: String = ""
    private var params: String = ""

    private var service: String = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_publish_intell_report
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

        mViewBinding.stvAddress.setRightString(StringMyUtils.showAddress(C.addr))
        req.address = StringMyUtils.showAddress(C.addr)
        req.lat = C.lat
        req.lon = C.lon

        mPresenter.openTypeDialog(mContext)
        mPresenter.netGetArmsDepartment(mContext, C.lat, C.lon)
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
//        req.type = mViewBinding.edtTitle.getText().toString().trim();
//        req.content = mViewBinding.edtText.getText().toString().trim();
        if (TextUtils.isEmpty(req.type)) {
            T.error(mContext, "请选择情报类型")
        } else if (TextUtils.isEmpty(req.area_code)) {
            T.error(mContext, "获取人武部失败")
        } else if ((images == null || images.size == 0) && TextUtils.isEmpty(req.video)) {
            T.error(mContext, "请选择图片或视频上传")
        } else {
            if (images != null) {
//                UploadUtils.uploadFileBatch(mActivity, images, new UploadFileListener() {
//                    @Override
//                    public void onSuccess(String pathStr) {
//                        req.img = pathStr;
//                        mPresenter.netPublishSOS(mActivity, req);
//                    }
//                });
                UploadUtils.uploadFileBatch(
                    mContext,
                    imgFiles
                ) { pathStr ->
                    req.imgs = pathStr!!
                    mPresenter.netIntellReport(mContext, token, req, service)
                }
            } else {
                mPresenter.netIntellReport(mContext, token, req, service)
            }
        }
    }

    fun publishType(view: View) {
        mPresenter.openTypeDialog(mContext)
    }

    fun address(view: View) {
//        ARouter.getInstance().build(C.MapPoiSelect).navigation()
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
            imgsSize += images.get(i).size
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
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun evnEvnMapPoiSelect(evn: EvnMapPoiSelect) {
//        mViewBinding.stvAddress.setRightString(StringMyUtils.showAddress(evn.addr))
//        req.lat = evn.lat
//        req.lon = evn.lon
//        mPresenter.netGetArmsDepartment(mContext, evn.lat, evn.lon)
//    }

    override fun openTypeDialog(resp: IntellTypeResp) {
        req.type = resp.id
        mViewBinding.stvPublishType.setRightString(resp.name)
    }

    override fun netGetArmsDepartment(resp: GetArmsDepartmentResp) {
        req.area_code = resp.regionId
        mViewBinding.stvTargetDepartment.setRightString("${resp.regionName}人武部")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().removeAllStickyEvents()
        EventBus.getDefault().unregister(this)
    }
}

class PublishIntellTypeAdapter :
    BaseQuickAdapter<IntellTypeResp, BaseViewHolder>(
        R.layout.item_publish_article_type,
        null
    ) {
    override fun convert(
        helper: BaseViewHolder,
        item: IntellTypeResp
    ) {
        helper.setText(R.id.txt_title, item.name)
        val img_icon = helper.getView<ImageView>(R.id.img_icon)
        Glide.with(mContext).load(Api.JAVA_SERVICE_URL + "/" + item.icon).into(img_icon)
    }
}

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
    }
}