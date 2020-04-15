package com.zzz.myemergencyclientnew.activity.home;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;
import com.zzz.myemergencyclientnew.entity.ApkFunctionEntity;
import com.zzz.myemergencyclientnew.entity.HomeBottomNavigationEntity;
import com.zzz.myemergencyclientnew.entity.NavigationEntity;

import java.util.List;

public interface HomeContract {
    interface View extends BaseView {
        void netInitData(List<HomeBottomNavigationEntity> list);
        void netInitData2(List<ApkFunctionEntity> list);
        void netInitData3(List<NavigationEntity> list);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void netInitData(Activity mActivity);
        public abstract void netInitData2(Activity mActivity);
        public abstract void netInitData3(Activity mActivity);
    }
}
