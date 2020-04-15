package com.zzz.myemergencyclientnew.fragment.login;


import androidx.appcompat.app.AppCompatActivity;

import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;


public interface SignInContract {
    interface View extends BaseView {
        void netAppLogin(String resp);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void netAppLogin(AppCompatActivity mActivity, String username, String password);

        public abstract void netAuthRegister(AppCompatActivity mActivity);
    }
}