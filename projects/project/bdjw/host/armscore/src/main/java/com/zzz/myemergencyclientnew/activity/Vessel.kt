package com.zzz.myemergencyclientnew.activity

import android.content.Intent
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zzz.myemergencyclientnew.R
import com.zzz.myemergencyclientnew.base.BaseActivity
import com.zzz.myemergencyclientnew.base.BasePresenter
import com.zzz.myemergencyclientnew.base.BaseView
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.databinding.ActivityVesselBinding


interface VesselC {
    interface View : BaseView
    abstract class Presenter : BasePresenter<View?>()
}

class VesselP :
    VesselC.Presenter()

@Route(path = C.Vessel)
class VesselActivity :
    BaseActivity<VesselP, ActivityVesselBinding>(),
    VesselC.View {

    private lateinit var fragment: Fragment

    @JvmField
    @Autowired
    var fragmentRoute: String = ""

    @JvmField
    @Autowired
    var title: String = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_vessel
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        mViewBinding.toolbar.title = title
        mViewBinding.toolbar.setNavigationOnClickListener { finish() }

        fragment = ARouter.getInstance().build(fragmentRoute).navigation() as Fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_frame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment.onActivityResult(requestCode, resultCode, data)
    }
}