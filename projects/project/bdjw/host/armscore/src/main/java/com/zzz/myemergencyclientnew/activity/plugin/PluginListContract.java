package com.zzz.myemergencyclientnew.activity.plugin;

import android.app.Activity;

import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;
import com.zzz.myemergencyclientnew.entity.PluginListEntity;

import java.util.List;

public interface PluginListContract {
    interface View extends BaseView {
        void netInitData(List<PluginListEntity> list);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void netInitData(Activity mActivity);
    }
}
