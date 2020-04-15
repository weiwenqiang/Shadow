package com.zzz.myemergencyclientnew.fragment.common

import android.graphics.Bitmap
import android.webkit.*
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.zzz.myemergencyclientnew.R
import com.zzz.myemergencyclientnew.activity.AndroidInterface
import com.zzz.myemergencyclientnew.base.BaseFragment
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.FragmentCommonWebSingleBinding


interface WebSingleContract {
    interface View : BaseView
    abstract class Presenter : BasePresenter<View?>()
}

class WebSinglePresenter :
    WebSingleContract.Presenter()

@Route(path = C.WebSingle)
class WebSingleFragment :
    BaseFragment<WebSinglePresenter, FragmentCommonWebSingleBinding>(),
    WebSingleContract.View {
    private lateinit var mAgentWeb: AgentWeb

    @JvmField
    @Autowired
    var url: String = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_common_web_single
    }

    override fun initView() {
        ARouter.getInstance().inject(this)

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mViewBinding.lyt, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //                .setWebLayout(new WebLayout(this))
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(url)

        mAgentWeb.jsInterfaceHolder.addJavaObject("app_os", AndroidInterface(mAgentWeb, mActivity))
        // AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。
        mAgentWeb.webCreator.webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        mAgentWeb.webCreator.webView.settings.javaScriptEnabled = true
        //优先使用网络
        mAgentWeb.webCreator.webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        //将图片调整到适合webview的大小
        mAgentWeb.webCreator.webView.settings.useWideViewPort = true
        //支持内容重新布局
        mAgentWeb.webCreator.webView.settings.layoutAlgorithm =
            WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //支持自动加载图片
        mAgentWeb.webCreator.webView.settings.loadsImagesAutomatically = true
        //当webview调用requestFocus时为webview设置节点
        mAgentWeb.webCreator.webView.settings.setNeedInitialFocus(true)
        //自适应屏幕
        mAgentWeb.webCreator.webView.settings.useWideViewPort = true
        mAgentWeb.webCreator.webView.settings.loadWithOverviewMode = true
        //开启DOM storage API功能（HTML5 提供的一种标准的接口，主要将键值对存储在本地，在页面加载完毕后可以通过 javascript 来操作这些数据。）
        mAgentWeb.webCreator.webView.settings.domStorageEnabled = true
        //允许webview对文件的操作
        mAgentWeb.webCreator.webView.settings.allowFileAccess = true
        mAgentWeb.webCreator.webView.settings.allowFileAccessFromFileURLs = true
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(
            view: WebView?,
            url: String?,
            favicon: Bitmap?
        ) {
        }
    }
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {}
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb.webLifeCycle.onDestroy()
    }
}