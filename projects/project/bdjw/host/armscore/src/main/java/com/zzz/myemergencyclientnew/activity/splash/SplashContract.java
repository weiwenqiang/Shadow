package com.zzz.myemergencyclientnew.activity.splash;

import android.app.Activity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;
import com.zzz.myemergencyclientnew.response.ApkVersionResp;

public interface SplashContract {
    interface View extends BaseView {
        void startAnim();

        void jumpNextPage(boolean isJump);

        void netUpdate(ApkVersionResp resp);

        void goApkInstall(String llsApkFilePath);

        void goHomeActivity();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void startAnim(Activity activity, FrameLayout lyt);

        public abstract void netUpdate(Activity activity);

        public abstract void createRPDownloadTask(Activity activity, String url, TextView speedTv1, ProgressBar progressBar);

        public abstract void netAppLogin(Activity mActivity, String username, String password);
    }
}
