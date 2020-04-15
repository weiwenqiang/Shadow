package com.zzz.myemergencyclientnew.activity.login;


import android.view.KeyEvent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.base.BaseActivity;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.databinding.ActivityLoginBinding;
import com.zzz.myemergencyclientnew.fragment.login.ForgetPasswordFragment;
import com.zzz.myemergencyclientnew.fragment.login.RegisterFragment;
import com.zzz.myemergencyclientnew.fragment.login.SignInFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = C.Login)
public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginContract.View {
    public FragmentManager fm;

    //    @Autowired
//    public String fragmentRoute;
    private SignInFragment signInFragment;
    private RegisterFragment registerFragment;
    private ForgetPasswordFragment forgetPasswordFragment;

    private List<String> popBackStack = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    public void initView() {
//        ARouter.getInstance().inject(this);
        signInFragment = new SignInFragment();
        setToolbarTitle("登录");
        mViewBinding.toolbar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        });

        fm = getSupportFragmentManager();
        cutFragment(fm, TAG_SIGN_IN);
    }

    public void setToolbarTitle(String title){
        mViewBinding.toolbar.setTxtTitle(title);
    }


    public void cutFragment(FragmentManager fm, String tag) {
        Fragment fragment = createFragmentFactory(tag);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.lyt, fragment, tag);
        if (!popBackStack.contains(tag)) {//避免堆叠回退栈，重复过滤
            popBackStack.add(tag);
            ft.addToBackStack(tag);//不处理会导致界面反复堆积
        } else {
            popBackStack.remove(tag);
        }
        ft.commit();
    }

    //回退栈中某个Fragment之上的所有Fragment
    public void popBackStack(FragmentManager fm, String tag) {
        fm.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static final String TAG_SIGN_IN = "sign_in";
    public static final String TAG_REGISTER = "register";
    public static final String TAG_FORGET_PASSWORD = "forget_password";

    public Fragment createFragmentFactory(String fragmentStr) {
        if (fragmentStr.equals(TAG_SIGN_IN)) {
            if (signInFragment == null) {
                signInFragment = new SignInFragment();
            }
            return signInFragment;
        } else if (fragmentStr.equals(TAG_REGISTER)) {
            if (registerFragment == null) {
                registerFragment = new RegisterFragment();
            }
            return registerFragment;
        } else if (fragmentStr.equals(TAG_FORGET_PASSWORD)) {
            if (forgetPasswordFragment == null) {
                forgetPasswordFragment = new ForgetPasswordFragment();
            }
            return forgetPasswordFragment;
        } else {
            if (signInFragment == null) {
                signInFragment = new SignInFragment();
            }
            return signInFragment;
        }
    }

    /**
     * 隐藏所有
     */
    private void hideAllFragment(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        if (signInFragment != null && !signInFragment.isHidden()) {
            ft.hide(signInFragment);
        }
        if (signInFragment != null && !registerFragment.isHidden()) {
            ft.hide(registerFragment);
        }
        if (signInFragment != null && !forgetPasswordFragment.isHidden()) {
            ft.hide(forgetPasswordFragment);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 为了多个页面同时显示判断
     */
    private void addFragment(FragmentManager fm, Fragment fragment, String tag) {
        if (!fragment.isAdded() && null == fm.findFragmentByTag(tag)) {
            FragmentTransaction ft = fm.beginTransaction();
            fm.executePendingTransactions();
            ft.add(R.id.lyt, fragment, tag);
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 显示fragment
     */
    private void showFragment(FragmentManager fm, Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    private void clickBack() {
        int count = fm.getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            String tag = fm.getBackStackEntryAt(count-1).getName();
            if(tag.equals(TAG_SIGN_IN)){
                finish();// 登录页面的话直接关闭
            }else{
                popBackStack.remove(tag);//自己解决回退栈反复堆叠
                fm.popBackStack();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            clickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}