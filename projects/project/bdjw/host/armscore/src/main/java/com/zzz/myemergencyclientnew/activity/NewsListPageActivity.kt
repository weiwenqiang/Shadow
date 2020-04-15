package com.zzz.myemergencyclientnew.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.android.vlayout.Range
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.tmall.wireless.tangram.TangramBuilder
import com.tmall.wireless.tangram.TangramEngine
import com.tmall.wireless.tangram.core.adapter.GroupBasicAdapter
import com.tmall.wireless.tangram.dataparser.concrete.Card
import com.tmall.wireless.tangram.structure.BaseCell
import com.tmall.wireless.tangram.support.async.AsyncPageLoader
import com.tmall.wireless.tangram.support.async.CardLoadSupport
import com.zzz.myemergencyclientnew.R
import com.zzz.myemergencyclientnew.base.BaseActivity
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.ActivityNewsListPageBinding
import com.zzz.myemergencyclientnew.listener.SampleClickSupport
import com.zzz.myemergencyclientnew.utils.FileUtils
import com.zzz.myemergencyclientnew.utils.SPUtils
import com.zzz.myemergencyclientnew.widget.tangram.*
import org.json.JSONArray
import org.json.JSONException

interface NewsListPageC {
    interface View : BaseView {
        fun netInitData(str: String)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun netInitData(
            mActivity: AppCompatActivity,
            netApi: String,
            params: String,
            page: Int
        )
    }
}

class NewsListPageP : NewsListPageC.Presenter() {
    override fun netInitData(
        mActivity: AppCompatActivity,
        netApi: String,
        params: String,
        page: Int
    ) {
        OkGo.post<RespJson<String>>(netApi)
            .headers("projectId", C.projectId)
            .headers("projectType", C.projectType)
            .headers("token",
                SPUtils.get(
                    mActivity,
                    C.sp_token,
                    ""
                ) as String
            )
            .params("params", params)
            .params("page", page)
            .params("pagesize", 10)
            .tag(this)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    mView.netInitData(response.body().data)
                }
            })
    }
}

@Route(path = C.NewsListPage)
class NewsListPageActivity :
    BaseActivity<NewsListPageP, ActivityNewsListPageBinding>(),
    NewsListPageC.View {

    @JvmField
    @Autowired
    var api: String = ""

    @JvmField
    @Autowired
    var title: String = ""

    @JvmField
    @Autowired
    var params: String = ""

    lateinit var engine: TangramEngine
    lateinit var builder: TangramBuilder.InnerBuilder

    override fun getLayoutId() = R.layout.activity_news_list_page
//    val layoutId: Int
//        get() = R.layout.activity_news_list_page

    override fun initView() {
        mViewBinding.setMView(this)
        ARouter.getInstance().inject(this)
        mViewBinding.toolbar.setTitle(title)
        mViewBinding.toolbar.setNavigationOnClickListener { finish() }
        initEngine()
    }

    private fun initEngine() {
        builder = TangramBuilder.newInnerBuilder(mContext)
        builder.registerCell(250, BannerView::class.java)
        builder.registerCell(260, NewsClassView::class.java)
        builder.registerCell(199, SingleImageView::class.java)
        builder.registerCell(198, ApplyIconView::class.java)
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
        engine = builder.build()
        engine.addCardLoadSupport(CardLoadSupport(AsyncPageLoader { page, card, callback ->
            card1 = card
            callback1 = callback
            mPresenter.netInitData(mContext, api, params, page)
        }))
//        engine.addCardLoadSupport(
//            CardLoadSupport(
//                object : AsyncPageLoader {
//                    override fun loadData(
//                        page: Int,
//                        @NonNull card1: Card,
//                        @NonNull callback1: AsyncPageLoader.LoadedCallback
//                    ) {
//                        card = card1
//                        callback = callback1
//                        mPresenter.netInitData(mContext, netApi, typeId, page)
//                    }
//                })
//        )
        engine.addSimpleClickSupport(SampleClickSupport(mContext))
        engine.enableAutoLoadMore(true)
        engine.bindView(mViewBinding.rv)
        mViewBinding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                engine.onScrolled()
            }
        })
        val json =
            String(FileUtils.getAssertsFile(mContext, "tangram_page_list.json")!!)
        var data: JSONArray? = null
        try {
            data = JSONArray(json)
            engine.setData(data)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private lateinit var card1: Card
    private lateinit var callback1: AsyncPageLoader.LoadedCallback

    override fun netInitData(str: String) {
        try {
            if(str.equals("[]")){

            }else {
                val data = JSONArray(str)
                //            engine.setData(data);

//            mViewBinding.swipe.setRefreshing(false);
                val cs = engine.parseComponent(data)
                if (card1.page === 1) {
                    val adapter: GroupBasicAdapter<Card, *> = engine.getGroupBasicAdapter()
                    card1.setCells(cs)
                    adapter.refreshWithoutNotify()
                    val range: Range<Int> = adapter.getCardRange(card1)
                    adapter.notifyItemRemoved(range.lower)
                    adapter.notifyItemRangeInserted(range.lower, cs.size)
                } else {
                    card1.addCells(cs)
                }

                //上线100页
                callback1.finish(card1.page !== 100)
                card1.notifyDataChange()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}