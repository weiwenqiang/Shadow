package com.zzz.myemergencyclientnew.fragment.home;

import android.app.Activity;

import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;

public interface NewsContract {
    interface View extends BaseView {
        void netInitData(String str);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void netInitData(Activity mActivity);
    }
}