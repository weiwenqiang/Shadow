package com.baidumap.activity;

import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;

public interface MapPoiSelectContract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenter<View> {
    }
}