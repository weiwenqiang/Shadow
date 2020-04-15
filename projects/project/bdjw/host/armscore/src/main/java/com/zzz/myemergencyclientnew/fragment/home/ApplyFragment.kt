package com.zzz.myemergencyclientnew.fragment.home

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zzz.myemergencyclientnew.R
import com.zzz.myemergencyclientnew.base.BaseFragment
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.FragmentHomeApplyBinding
import com.zzz.myemergencyclientnew.entity.BaseNavigationEntity
import com.zzz.myemergencyclientnew.utils.GsonUtils
import com.zzz.myemergencyclientnew.widget.plug.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView


interface ApplyC {
    interface View : BaseView
    abstract class Presenter : BasePresenter<View>()
}

class ApplyP :
    ApplyC.Presenter() {
    
    fun initMagicIndicator2(
        mActivity: AppCompatActivity,
        magicIndicator: MagicIndicator,
        mViewPager: ViewPager,
        fm: FragmentManager,
        beanList: List<BaseNavigationEntity>
    ) {
        val fragmentList: List<Fragment> = FragmentFactory2(beanList)
        val strings = getTabNames2(beanList)
        val adapter = ApplyPagerAdapter(fm, fragmentList, strings)
        mViewPager.adapter = adapter
        magicIndicator.setBackgroundColor(Color.parseColor("#ffffff"))
        val commonNavigator7 = CommonNavigator(mActivity)
        commonNavigator7.setScrollPivotX(0.65f)
        commonNavigator7.setAdapter(object : CommonNavigatorAdapter() {

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView = ColorFlipPagerTitleView(context)
                simplePagerTitleView.setText(strings[index])
                simplePagerTitleView.setTextSize(16f)
                simplePagerTitleView.setNormalColor(Color.parseColor("#888888"))
                simplePagerTitleView.setSelectedColor(
                    mActivity.getResources().getColor(R.color.main_color)
                )
                simplePagerTitleView.setOnClickListener {
                    val bean: BaseNavigationEntity = beanList[index]
                    mViewPager.currentItem = index
                    if (bean.click === 2) {
                        ARouter.getInstance().build(bean.webEntity.route).withString("url", bean.webEntity.url)
                            .navigation()
                    }  }
                return simplePagerTitleView
            }

            override fun getCount(): Int {
                return strings?.size ?: 0
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY)
                indicator.setLineHeight(UIUtil.dip2px(context, 6.0).toFloat())
                indicator.setLineWidth(UIUtil.dip2px(context, 14.0).toFloat())
                indicator.setRoundRadius(UIUtil.dip2px(context, 3.0).toFloat())
                indicator.setStartInterpolator(AccelerateInterpolator())
                indicator.setEndInterpolator(DecelerateInterpolator(2.0f))
                indicator.setColors(mActivity.getResources().getColor(R.color.main_color))
                return indicator
            }
        })
        magicIndicator.setNavigator(commonNavigator7)
        ViewPagerHelper.bind(magicIndicator, mViewPager)
    }

    fun FragmentFactory2(beanList: List<BaseNavigationEntity>): List<Fragment> {
        val fragmentList: MutableList<Fragment> = ArrayList()
        for (i in beanList.indices) {
            val BaseNavigationEntity: BaseNavigationEntity = beanList[i]
            if (BaseNavigationEntity.click === 4) {
                fragmentList.add(
                    ARouter.getInstance().build(BaseNavigationEntity.apiTangramEntity.fragmentRoute)
                        .withString("api", BaseNavigationEntity.apiTangramEntity.api).navigation() as Fragment
                )
            } else if (BaseNavigationEntity.click === 1) {
                fragmentList.add(
                    ARouter.getInstance().build(BaseNavigationEntity.webEntity.route)
                        .withString("url", BaseNavigationEntity.webEntity.url).navigation() as Fragment
                )
            } else if (BaseNavigationEntity.click === 7) {
                fragmentList.add(
                    ARouter.getInstance().build(BaseNavigationEntity.routeFragmentEntity.route).navigation() as Fragment
                )
            }
        }
        return fragmentList
    }
    fun getTabNames2(beanList: List<BaseNavigationEntity>): List<String> {
        val tabList: MutableList<String> = ArrayList()
        for (i in beanList.indices) {
            val BaseNavigationEntity: BaseNavigationEntity = beanList[i]
            tabList.add(BaseNavigationEntity.title)
        }
        return tabList
    }
}

@Route(path = C.ApplyFragment)
class ApplyFragment : BaseFragment<ApplyP, FragmentHomeApplyBinding>(),
    ApplyC.View {

    @JvmField
    @Autowired
    var applyList: String = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_apply
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
//        T.success(mActivity, applyList)
        initFragment()
//        EventBus.getDefault().register(this)
    }

    private fun initFragment() {
//        val applyList =
//            SPUtils.get(mActivity, C.sp_applyList, C.applyList) as String
        val BaseNavigationEntitys: List<BaseNavigationEntity> = GsonUtils.jsonToArrayList(
            applyList,
            BaseNavigationEntity::class.java
        )
        val fm: FragmentManager = fragmentManager!!
        mPresenter.initMagicIndicator2(
            mActivity,
            mViewBinding.magicIndicator7,
            mViewBinding.viewPager,
            fm,
            BaseNavigationEntitys
        )
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun evnEvnLogin(evn: EvnLogin) {
//        initFragment()
//    }

    override fun onDestroy() {
        super.onDestroy()
//        EventBus.getDefault().removeAllStickyEvents()
//        EventBus.getDefault().unregister(this)
    }
}

class ApplyPagerAdapter(
    fm: FragmentManager,
    private val mFragments: List<Fragment>,
    private val titles: List<String>
) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
    }
}