package com.zzz.myemergencyclientnew.fragment.me

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zzz.myemergencyclientnew.R
import com.zzz.myemergencyclientnew.base.BaseFragment
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.FragmentMeSystemSettingBinding
import com.zzz.myemergencyclientnew.utils.AppUtils


interface SystemSettingC {
    interface View : BaseView
    abstract class Presenter : BasePresenter<View>()
}

class SystemSettingP :
    SystemSettingC.Presenter()

@Route(path = C.SystemSetting)
class SystemSettingFragment :
    BaseFragment<SystemSettingP, FragmentMeSystemSettingBinding>(),
    SystemSettingC.View {

    override fun getLayoutId(): Int {
        return R.layout.fragment_me_system_setting
    }

    override fun initView() {
        mViewBinding.mView = this
        ARouter.getInstance().inject(this)
        mViewBinding.stvVersionNumber.setRightString(AppUtils.getVersionName(mActivity))
    }

    fun AboutUs(view: View) {
//        ARouter.getInstance().build(C.Web).withString("url", Api.WEB_About_Us).navigation()
    }

    fun logout(view: View) {
//        C.logout(mActivity)
        mActivity.finish()
    }
}