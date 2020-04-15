package com.zzz.myemergencyclientnew.activity.splash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.base.BaseActivity;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.databinding.ActivitySplashBinding;
import com.zzz.myemergencyclientnew.response.ApkVersionResp;
import com.zzz.myemergencyclientnew.utils.AppUtils;
import com.zzz.myemergencyclientnew.utils.SPUtils;

import java.io.File;

public class SplashActivity extends BaseActivity<SplashPresenter, ActivitySplashBinding> implements SplashContract.View {
    //记录下载线程
    private int downloadId1;
    //8.0以上安装权限监听
    private static int INSTALL_PERMISS_CODE = 0x01;
    //监听安装页面是否取消
    private static int INSTALL_COURSE_CODE = 0x02;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    public void initView() {
        if (AppUtils.isNetConn(mContext)) {
            mPresenter.startAnim(mContext, mViewBinding.lyt);
//            mPresenter.netUpdate(mContext);
        }else{
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("网络异常");
//            builder.setMessage("请检查网络连接并重启");
//            builder.setCancelable(false);
//            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    mContext.finish();
//                }
//            });
//            builder.create().show();
//            C.logout(mContext);
            goHomeActivity();
        }
    }

    @Override
    public void startAnim() {
        mPresenter.startAnim(mContext, mViewBinding.lyt);
    }

    public void jumpNextPage(boolean isJump) {
        if (isJump) {
            String token = (String) SPUtils.get(mContext, C.sp_token, "");
            if(TextUtils.isEmpty(token)){
                goHomeActivity();
            }else{
                String username = (String) SPUtils.get(mContext, C.sp_username, "");
                String password = (String) SPUtils.get(mContext, C.sp_password, "");
                mPresenter.netAppLogin(mContext, username, password);
            }
        } else {
            ARouter.getInstance().build(C.SLIDER).navigation();
        }
    }

    @Override
    public void netUpdate(ApkVersionResp resp) {
        if (resp.getVersionCode() > AppUtils.getVersionCode(mContext)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0以上有是否允许应用安装应用权限
                boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    startInstallPermissionSettingActivity(mContext);
                } else {
                    mViewBinding.llContent.setVisibility(View.VISIBLE);
//                    downloadId1 = mPresenter.createDownloadTask(mContext, resp.getVerDownload(), mViewBinding.speedTv1, mViewBinding.filenameTv1, mViewBinding.progressBar);
                    mPresenter.createRPDownloadTask(mContext, "http://192.168.81.137:8080/test/armscore-debug.apk", mViewBinding.speedTv1, mViewBinding.progressBar);
//                    mPresenter.createRPDownloadTask(mContext, resp.getDownloadLink(), mViewBinding.speedTv1, mViewBinding.progressBar);
                }
            } else {
                mViewBinding.llContent.setVisibility(View.VISIBLE);
//                downloadId1 = mPresenter.createDownloadTask(mContext, resp.getVerDownload(), mViewBinding.speedTv1, mViewBinding.filenameTv1, mViewBinding.progressBar);
                mPresenter.createRPDownloadTask(mContext, "http://192.168.81.137:8080/test/armscore-debug.apk", mViewBinding.speedTv1, mViewBinding.progressBar);
//                mPresenter.createRPDownloadTask(mContext, resp.getDownloadLink(), mViewBinding.speedTv1, mViewBinding.progressBar);
            }
        } else {
            mPresenter.startAnim(mContext, mViewBinding.lyt);
        }
    }

    @Override
    public void goApkInstall(String llsApkFilePath) {
        Intent intent = getInstallAppIntent(mContext, new File(llsApkFilePath));
        if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            mContext.startActivityForResult(intent, INSTALL_COURSE_CODE);
        }
    }

    @Override
    public void goHomeActivity() {
        ARouter.getInstance().build(C.HOME).navigation();
//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        FileDownloader.getImpl().pause(downloadId1);
    }


    public Intent getInstallAppIntent(Activity context, File appFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //区别于 FLAG_GRANT_READ_URI_PERMISSION 跟 FLAG_GRANT_WRITE_URI_PERMISSION， URI权限会持久存在即使重启，直到明确的用 revokeUriPermission(Uri, int) 撤销。 这个flag只提供可能持久授权。但是接收的应用必须调用ContentResolver的takePersistableUriPermission(Uri, int)方法实现
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", appFile);
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(appFile), "application/vnd.android.package-archive");
        }
        return intent;
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startInstallPermissionSettingActivity(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("安装权限");
        builder.setMessage("有更新，需要打开允许来自此来源，请去设置中开启此权限");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //注意这个是8.0新API
                Uri packageURI = Uri.parse("package:" + context.getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivityForResult(intent, INSTALL_PERMISS_CODE);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.finish();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INSTALL_PERMISS_CODE) {//同意安装授权
            mPresenter.netUpdate(mContext);
        } else if (resultCode == RESULT_CANCELED && requestCode == INSTALL_PERMISS_CODE) {//取消安装授权
            startInstallPermissionSettingActivity(mContext);
        } else if (resultCode == RESULT_CANCELED && requestCode == INSTALL_COURSE_CODE) {//取消安装
            mContext.finish();
        }
    }
}
