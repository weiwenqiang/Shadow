package com.zzz.myemergencyclientnew.activity.login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;

public interface LoginContract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenter<View> {
//        public abstract void cutFragment(FragmentManager fm, String tag);
//        public abstract void removeFragment(FragmentManager fm, Fragment fragment);
    }
}