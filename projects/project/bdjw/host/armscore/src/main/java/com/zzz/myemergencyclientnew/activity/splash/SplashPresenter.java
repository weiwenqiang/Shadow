package com.zzz.myemergencyclientnew.activity.splash;

import android.app.Activity;
import android.graphics.Color;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.zzz.myemergencyclientnew.constant.pref.Api;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnProgressListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zzz.myemergencyclientnew.callback.JsonCallback;
import com.zzz.myemergencyclientnew.callback.RespJson;
import com.zzz.myemergencyclientnew.response.ApkVersionResp;
import com.zzz.myemergencyclientnew.utils.FileUtils;
import com.zzz.myemergencyclientnew.utils.SPUtils;

import java.io.File;

public class SplashPresenter extends SplashContract.Presenter {
    @Override
    public void startAnim(Activity activity, FrameLayout lyt) {
        AnimationSet set = new AnimationSet(false);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 1);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);

        set.addAnimation(alphaAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isJump = (boolean) SPUtils.get(activity, C.sp_slider, false);
                mView.jumpNextPage(isJump);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lyt.startAnimation(set);
    }

    @Override
    public void netUpdate(Activity activity) {
        OkGo.<RespJson<ApkVersionResp>>post("http://192.168.81.229:9090/getHostVersion?code=HOST&version=198")
//        OkGo.<RespJson<ApkVersionResp>>post(Api.JAVA_SERVICE_URL + "/app/member/getApkVersion")
                .tag(this)
//                .params("versionCode", AppUtils.getVersionCode(activity))
                .execute(new JsonCallback<RespJson<ApkVersionResp>>() {
                    @Override
                    public void onSuccess(Response<RespJson<ApkVersionResp>> response) {
                        mView.netUpdate(response.body().data);
                    }

                    @Override
                    public void onError(Response<RespJson<ApkVersionResp>> response) {
                        mView.startAnim();
                    }
                });
    }


    @Override
    public void createRPDownloadTask(Activity activity, String url, TextView textViewProgressFourteen, ProgressBar progressBarFourteen) {
        String llsApkFilePath = FileUtils.getRootDirPath(activity) + File.separator + "app-release.apk";
        File file = new File(llsApkFilePath);
        if (file.exists()) {
            boolean isAnZ = file.delete();
        }

        progressBarFourteen.setIndeterminate(true);
        progressBarFourteen.getIndeterminateDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

        PRDownloader.download(url, FileUtils.getRootDirPath(activity), "app-release.apk")
                .build()
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        progressBarFourteen.setProgress((int) progressPercent);
                        textViewProgressFourteen.setText(FileUtils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        String apkFilePath = FileUtils.getRootDirPath(activity)+ File.separator +"app-release.apk";
                        mView.goApkInstall(apkFilePath);
                    }

                    @Override
                    public void onError(Error error) {
                        textViewProgressFourteen.setText("下载异常");
                        progressBarFourteen.setProgress(0);
                        progressBarFourteen.setIndeterminate(false);
                    }
                });
    }

    @Override
    public void netAppLogin(Activity mActivity, String username, String password) {
        OkGo.<String>post(Api.JAVA_SERVICE_URL + "/app/member/login")
                .tag(this)
                .params("username", username)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body().toString());
                        int code = jsonObject.getInteger("code");
                        String data = jsonObject.getString("data");
                        String msg = jsonObject.getString("msg");
//                        T.success(msg);
                        if (code == C.API_SUCCEED) {
//                            C.login(mActivity, data);
                            mView.goHomeActivity();
                        } else {
//                            C.logout(mActivity);
                            mView.goHomeActivity();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        mView.goHomeActivity();
                    }
                });
    }
}
