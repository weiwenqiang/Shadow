package com.zzz.myemergencyclientnew.fragment.home;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.base.BaseFragment;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.databinding.FragmentHomeNewsBinding;
import com.zzz.myemergencyclientnew.listener.SampleClickSupport;
import com.zzz.myemergencyclientnew.utils.AppUtils;
import com.zzz.myemergencyclientnew.utils.SPUtils;
import com.zzz.myemergencyclientnew.widget.tangram.BannerView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageEightView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageFiveView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageFourView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageNineView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageOneView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageSevenView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageSixView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageThreeView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageTwoView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexImageZeroView;
import com.zzz.myemergencyclientnew.widget.tangram.IndexVideoView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsClassView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageEightView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageFiveView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageFourView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageNineView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageOneView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageSevenView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageSixView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageThreeView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageTwoView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsImageZeroView;
import com.zzz.myemergencyclientnew.widget.tangram.NewsVideoView;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.zzz.myemergencyclientnew.fragment.home.NewsPresenter;

import org.json.JSONArray;

public class NewsFragment extends BaseFragment<NewsPresenter, FragmentHomeNewsBinding> implements NewsContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_news;
    }

//    LinearLayout scanningLayout;
//    LinearLayout advisoryLayout;
//    View homeTitleBarBgView;
//    SwipeRefreshLayout mSwipeRefreshLayout;
//    RecyclerView recyclerView;

    TangramEngine engine;
    TangramBuilder.InnerBuilder builder;


    private int distanceY;

    private static int DISTANCE_WHEN_TO_SELECTED = 40;

    @Override
    public void initView() {
        mViewBinding.setMView(this);

//        scanningLayout = mViewBinding.scanningLayout;
//        advisoryLayout = mViewBinding.advisoryLayout;
//        homeTitleBarBgView= mViewBinding.homeTitleBarBgView;
//        mSwipeRefreshLayout = mViewBinding.swipeContainer;
//        recyclerView= mViewBinding.mainView;

//        initSlide();

        initEngine();

        mViewBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.netInitData(mActivity);
            }
        });
    }

    private void initEngine() {
        builder = TangramBuilder.newInnerBuilder(mActivity);

        builder.registerCell(250, BannerView.class);
        builder.registerCell(260, NewsClassView.class);

        builder.registerCell(280, NewsImageZeroView.class);
        builder.registerCell(281, NewsImageOneView.class);
        builder.registerCell(282, NewsImageTwoView.class);
        builder.registerCell(283, NewsImageThreeView.class);
        builder.registerCell(284, NewsImageFourView.class);
        builder.registerCell(285, NewsImageFiveView.class);
        builder.registerCell(286, NewsImageSixView.class);
        builder.registerCell(287, NewsImageSevenView.class);
        builder.registerCell(288, NewsImageEightView.class);
        builder.registerCell(289, NewsImageNineView.class);
        builder.registerCell(290, NewsVideoView.class);

        builder.registerCell(380, IndexImageZeroView.class);
        builder.registerCell(381, IndexImageOneView.class);
        builder.registerCell(382, IndexImageTwoView.class);
        builder.registerCell(383, IndexImageThreeView.class);
        builder.registerCell(384, IndexImageFourView.class);
        builder.registerCell(385, IndexImageFiveView.class);
        builder.registerCell(386, IndexImageSixView.class);
        builder.registerCell(387, IndexImageSevenView.class);
        builder.registerCell(388, IndexImageEightView.class);
        builder.registerCell(389, IndexImageNineView.class);
        builder.registerCell(390, IndexVideoView.class);

        engine = builder.build();
        engine.addSimpleClickSupport(new SampleClickSupport(mActivity));

        engine.enableAutoLoadMore(false);

        engine.bindView(mViewBinding.rv);


        if (AppUtils.isNetConn(mActivity)) {
            mPresenter.netInitData(mActivity);
        }else{
            String home_news = (String) SPUtils.get(mActivity, C.sp_home_news, "");
            netInitData(home_news);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.destroy();
        }
    }


    @Override
    public void netInitData(String str) {
//        String json = new String(FileUtils.getAssertsFile(mActivity, "news.json"));
//        JSONArray data = null;
        SPUtils.put(mActivity, C.sp_home_news, str);
        try {
            JSONArray data = new JSONArray(str);
            engine.setData(data);

            mViewBinding.swipe.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
