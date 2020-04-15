package com.zzz.myemergencyclientnew.fragment.login;

import androidx.appcompat.app.AppCompatActivity;

import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;


public interface RegisterContract {
    interface View extends BaseView {
        void netSendRegisterCode(String resp);
        void netVerifyRegister(String resp);
        void netAppRegister(String resp);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void netSendRegisterCode(AppCompatActivity mActivity, String phone);
        public abstract void netVerifyRegister(AppCompatActivity mActivity, String logid, String code);
        public abstract void netAppRegister(AppCompatActivity mActivity, String username, String password);

        public abstract void openLiabilityDialog(AppCompatActivity mActivity);
    }
}