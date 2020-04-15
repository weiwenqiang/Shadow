package com.zzz.myemergencyclientnew.fragment

import android.app.Activity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zzz.myemergencyclientnew.R
import com.zzz.myemergencyclientnew.base.BaseFragment
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.FragmentTangramBinding
import com.zzz.myemergencyclientnew.listener.SampleClickSupport
import com.zzz.myemergencyclientnew.utils.SPUtils
import com.zzz.myemergencyclientnew.widget.tangram.*
import com.zzz.myemergencyclientnew.widget.tangram.ApplyIconView
import com.zzz.myemergencyclientnew.widget.tangram.SingleImageView
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.tmall.wireless.tangram.TangramBuilder
import com.tmall.wireless.tangram.TangramEngine
import org.json.JSONArray


interface TangramFragmentC {
    interface View : BaseView {
        fun netInitData(str: String)
    }

    abstract class Presenter :
        BasePresenter<View>() {
        abstract fun netInitData(mActivity: Activity, api:String, token:String)
    }
}


class TangramFragmentP : TangramFragmentC.Presenter() {
    override fun netInitData(mActivity: Activity, api:String, token:String) {
        OkGo.post<RespJson<String>>(api)//"http://192.168.81.229:9090/release"
//        OkGo.post<RespJson<String>>("http://192.168.81.137:9999/app/home/publish")
//        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/home/publish")
            .tag(this)
            .headers("projectId", C.projectId)
            .headers("projectType", C.projectType)
            .headers("token",
                SPUtils.get(
                    mActivity,
                    C.sp_token,
                    ""
                ) as String
            )
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    mView.netInitData(response.body().data)
                }

                override fun onError(response: Response<RespJson<String>>) {

                }
            })
    }
}

@Route(path = C.TangramFragment)
class TangramFragment :
    BaseFragment<TangramFragmentP, FragmentTangramBinding>(),
    TangramFragmentC.View {

    @Autowired
    lateinit var api: String

    override fun getLayoutId(): Int {
        return R.layout.fragment_tangram
    }

    private lateinit var engine: TangramEngine
    private lateinit var builder: TangramBuilder.InnerBuilder
    override fun initView() {
        mViewBinding.mView=this
        ARouter.getInstance().inject(this)
        initEngine()
        mViewBinding.swipe.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mPresenter.netInitData(mActivity, api, "")
        })
    }

    private fun initEngine() {
        builder = TangramBuilder.newInnerBuilder(mActivity)
        builder.registerCell(199, SingleImageView::class.java)
        builder.registerCell(198, ApplyIconView::class.java)
        builder.registerCell(197, ApplyIconView::class.java)
        builder.registerCell(900, PublishPluginView::class.java)
        builder.registerCell(250, BannerView::class.java)
        builder.registerCell(260, NewsClassView::class.java)
        builder.registerCell(280, NewsImageZeroView::class.java)
        builder.registerCell(281, NewsImageOneView::class.java)
        builder.registerCell(282, NewsImageTwoView::class.java)
        builder.registerCell(283, NewsImageThreeView::class.java)
        builder.registerCell(284, NewsImageFourView::class.java)
        builder.registerCell(285, NewsImageFiveView::class.java)
        builder.registerCell(286, NewsImageSixView::class.java)
        builder.registerCell(287, NewsImageSevenView::class.java)
        builder.registerCell(288, NewsImageEightView::class.java)
        builder.registerCell(289, NewsImageNineView::class.java)
        builder.registerCell(290, NewsVideoView::class.java)
        builder.registerCell(380, IndexImageZeroView::class.java)
        builder.registerCell(381, IndexImageOneView::class.java)
        builder.registerCell(382, IndexImageTwoView::class.java)
        builder.registerCell(383, IndexImageThreeView::class.java)
        builder.registerCell(384, IndexImageFourView::class.java)
        builder.registerCell(385, IndexImageFiveView::class.java)
        builder.registerCell(386, IndexImageSixView::class.java)
        builder.registerCell(387, IndexImageSevenView::class.java)
        builder.registerCell(388, IndexImageEightView::class.java)
        builder.registerCell(389, IndexImageNineView::class.java)
        builder.registerCell(390, IndexVideoView::class.java)
        builder.registerCell(501, MeItemView::class.java)
        builder.registerCell(500, MeUserHeadView::class.java)
        builder.registerCell(401, ServeItemView::class.java)
        engine = builder.build()
        engine.addSimpleClickSupport(SampleClickSupport(activity))
        engine.enableAutoLoadMore(false)
        engine.bindView(mViewBinding.rv)

//        if (AppUtils.isNetConn(mActivity)) {
            mPresenter.netInitData(mActivity, api, "")

//        } else {
//            val home_news = SPUtils.get(
//                mActivity,
//                C.sp_home_news,
//                ""
//            ) as String
//            netInitData(home_news)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (engine != null) {
            engine.destroy()
        }
    }

    override fun netInitData(str: String) {
//        SPUtils.put(
//            mActivity,
//            C.sp_home_news,
//            str
//        )
        try {
            val data = JSONArray(str)
            engine.setData(data)
            mViewBinding.swipe.setRefreshing(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}