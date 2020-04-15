package com.zzz.myemergencyclientnew.fragment.login;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.activity.login.LoginActivity;
import com.zzz.myemergencyclientnew.base.BaseFragment;
import com.zzz.myemergencyclientnew.databinding.FragmentLoginRegisterBinding;
import com.zzz.myemergencyclientnew.listener.RegexCorrectListener;
import com.zzz.myemergencyclientnew.provider.SmsObserver;
import com.toast.T;


public class RegisterFragment extends BaseFragment<RegisterPresenter, FragmentLoginRegisterBinding> implements RegisterContract.View {
    private LoginActivity loginActivity;

    private String phoneStr;
    private String passwordStr;
    private String passConfirmStr;
    private String codeStr;

    private boolean phoneCorrect = false;
    private boolean codeCorrect = false;
    private boolean passwordCorrect = false;
    private boolean passConfirmCorrect = false;
    private boolean checkBoxCorrect = false;

    private String logid="";//发送验证码的ID号，用于校验验证码

    private static final int REQUEST_CODE_SEND_SMS = 0x01;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_register;
    }

    @Override
    public void initView() {
        loginActivity = (LoginActivity) mActivity;
        loginActivity.setTitle("忘记密码");

        mViewBinding.setMView(this);

        mViewBinding.edtLoginPhone.setRegexCorrectListener(new RegexCorrectListener() {
            @Override
            public void isRegexCorrect(View view, boolean isRegexCorrect) {
                phoneCorrect = isRegexCorrect;
                mViewBinding.edtLoginCode.getBtnButton().setEnabled(phoneCorrect && passwordCorrect && passConfirmCorrect);
            }
        });
        mViewBinding.edtLoginPassword.setRegexCorrectListener(new RegexCorrectListener() {
            @Override
            public void isRegexCorrect(View view, boolean isRegexCorrect) {
                passwordCorrect = isRegexCorrect;
                mViewBinding.edtLoginCode.getBtnButton().setEnabled(phoneCorrect && passwordCorrect && passConfirmCorrect);
            }
        });
        mViewBinding.edtLoginPassConfirm.setRegexCorrectListener(new RegexCorrectListener() {
            @Override
            public void isRegexCorrect(View view, boolean isRegexCorrect) {
                passConfirmCorrect = isRegexCorrect;
                mViewBinding.edtLoginCode.getBtnButton().setEnabled(phoneCorrect && passwordCorrect && passConfirmCorrect);
            }
        });
        mViewBinding.edtLoginCode.setRegexCorrectListener(new RegexCorrectListener() {
            @Override
            public void isRegexCorrect(View view, boolean isRegexCorrect) {
                codeCorrect = isRegexCorrect;
                mViewBinding.btnConfirm.setEnabled(phoneCorrect && passwordCorrect && passConfirmCorrect && codeCorrect && checkBoxCorrect);
            }
        });

        mViewBinding.edtLoginCode.setBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInspect()) {
                    mPresenter.netSendRegisterCode(mActivity, mViewBinding.edtLoginPhone.getText().toString().trim());
                    timer.start();
                    mViewBinding.edtLoginPhone.getEdtEdit().setEnabled(false);
                    mViewBinding.edtLoginCode.getBtnButton().setEnabled(false);
                    mViewBinding.edtLoginPassword.getEdtEdit().setEnabled(false);
                    mViewBinding.edtLoginPassConfirm.getEdtEdit().setEnabled(false);
                }
            }
        });
        mViewBinding.chbLoginCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkBoxCorrect = b;
                mViewBinding.btnConfirm.setEnabled(phoneCorrect && passwordCorrect && passConfirmCorrect && codeCorrect && checkBoxCorrect);
            }
        });

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE_SEND_SMS);
        }else{
            autoFillSMS();
        }
    }

    //倒计时
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mViewBinding.edtLoginCode.getBtnButton().setText(millisUntilFinished / 1000 + " 秒");
        }

        @Override
        public void onFinish() {
            mViewBinding.edtLoginCode.getBtnButton().setText("发送验证码");
            mViewBinding.edtLoginPhone.getEdtEdit().setEnabled(true);
           mViewBinding.edtLoginCode.getBtnButton().setEnabled(true);
            mViewBinding.edtLoginPassword.getEdtEdit().setEnabled(true);
            mViewBinding.edtLoginPassConfirm.getEdtEdit().setEnabled(true);
        }
    };

    //数据校验
    private boolean isInspect() {
        phoneStr = mViewBinding.edtLoginPhone.getText().toString().trim();
        passwordStr = mViewBinding.edtLoginPassword.getText().toString().trim();
        passConfirmStr = mViewBinding.edtLoginPassConfirm.getText().toString().trim();
        codeStr = mViewBinding.edtLoginCode.getText().toString().trim();
        if (!passwordStr.equals(passConfirmStr)) {
            T.error(mActivity,"两次密码不一致");
            return false;
        } else {
            return true;
        }
    }

    public void confirm(View view){
        if (isInspect()) {
            if(!TextUtils.isEmpty(logid) && !TextUtils.isEmpty(codeStr)){
                mPresenter.netVerifyRegister(mActivity, logid, codeStr);
            }else {

            }



//            String passString = KeyUtils.encryptDataMD5(passConfirmStr);
//            mViewBinding.btnConfirm.setText(passConfirmStr);
//                    User req = new User(phoneStr, passString,codeStr);
//                    mPresenter.user_register(req);
        }
    }

    public void goLogin(View view){
        loginActivity.cutFragment(loginActivity.fm, loginActivity.TAG_SIGN_IN);
    }

    public void goService(View view){
        mPresenter.openLiabilityDialog(mActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void netSendRegisterCode(String resp) {
        logid= resp;
    }

    @Override
    public void netVerifyRegister(String resp) {
        mPresenter.netAppRegister(mActivity, phoneStr, passwordStr);
    }

    @Override
    public void netAppRegister(String resp) {
        loginActivity.cutFragment(loginActivity.fm, loginActivity.TAG_SIGN_IN);
    }

    /**
     * 自动填充短信验证码
     */
    public void autoFillSMS(){//通知类短信权限，垃圾小米，有兼容问题
        SmsObserver smsObserver = new SmsObserver(mActivity, new Handler(),
                new SmsObserver.SmsListener() {
                    @Override
                    public void onResult(String smsContent) {
                        mViewBinding.edtLoginCode.getEdtEdit().setText(smsContent);
                    }
                });
        mActivity.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsObserver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_CODE_SEND_SMS){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                autoFillSMS();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}