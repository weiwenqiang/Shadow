package com.zzz.myemergencyclientnew.fragment.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.activity.login.LoginActivity;
import com.zzz.myemergencyclientnew.base.BaseFragment;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.databinding.FragmentLoginSigninBinding;
import com.zzz.myemergencyclientnew.response.AppLoginResp;
import com.zzz.myemergencyclientnew.utils.SPUtils;
import com.google.gson.Gson;

public class SignInFragment extends BaseFragment<SignInPresenter, FragmentLoginSigninBinding> implements SignInContract.View {
    private LoginActivity loginActivity;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_signin;
    }

    @Override
    public void initView() {
        loginActivity = (LoginActivity) mActivity;
        loginActivity.setToolbarTitle("登录");
        mViewBinding.setMView(this);

//        mViewBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.netAppLogin(mActivity,
//                        mViewBinding.edtLoginPhone.getText().toString().trim(),
//                        mViewBinding.edtLoginPassword.getText().toString().trim());
//            }
//        });

//        mViewBinding.txtLoginRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginActivity.mPresenter.cutFragment(loginActivity.fm, LoginPresenter.TAG_REGISTER);
//            }
//        });
    }

    public void login(View view){
        mPresenter.netAppLogin(mActivity,
                mViewBinding.edtLoginPhone.getText().toString().trim(),
                mViewBinding.edtLoginPassword.getText().toString().trim());
    }

    public void register(View view){
        loginActivity.cutFragment(loginActivity.fm, loginActivity.TAG_REGISTER);
//        ARouter.getInstance().build(C.Web).withString("url", Api.WEB_REGISTER).navigation();
    }

    public void forgetPassword(View view){
        loginActivity.cutFragment(loginActivity.fm, loginActivity.TAG_FORGET_PASSWORD);
//        ARouter.getInstance().build(C.Web).withString("url", Api.WEB_FORGET_PASSWORD).navigation();
    }

    @Override
    public void netAppLogin(String resp) {
        try {
//            SPUtils.put(mActivity, C.sp_appLogin, resp);

            Gson gson = new Gson();
            AppLoginResp appLoginResp = gson.fromJson(resp, AppLoginResp.class);
            String token = appLoginResp.getToken();
            SPUtils.put(mActivity, C.sp_token, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
//    public void netAppLogin(String resp) {
//        try {
////                                    User user = C.gson.fromJson(response.);
////            JSONObject object = JSONObject.parseObject(response.body());
////            String appLoginStr = resp.toString();
//            SPUtils.put(mActivity, "appLogin", resp);
//
//            T.success(resp);
//
////            JSONObject data = object.getJSONObject("data");
////            String token = data.getString("token");
////            SPUtils.put(mActivity, "token", token);
////
////            SPUtils.put(mActivity, "applyList", data.getString("applyList"));
////            SPUtils.put(mActivity, "serveList", data.getString("serveList"));
////
////            JSONObject userinfo = data.getJSONObject("userinfo");
////            String company_authen_status = userinfo.getString("company_authen_status");
////            String is_boss = userinfo.getString("is_boss");
////            SPUtils.put(mActivity, "company_authen_status", company_authen_status);
////            SPUtils.put(mActivity, "is_boss", is_boss);
////
////            SPUtils.put(mActivity, "user_name", userinfo.getString("name"));
////            SPUtils.put(mActivity, "is_boss", is_boss);
////
////            EvnLogin evnLogin = new EvnLogin();
////            evnLogin.name = userinfo.getString("name");
////            evnLogin.photo = userinfo.getString("photo");
////            evnLogin.nickname = userinfo.getString("nickname");
////            EventBus.getDefault().post(evnLogin);
//
//
//
////            mActivity.finish();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}