package com.zzz.myemergencyclientnew.fragment.login;

import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.toast.T

class ForgetPasswordPresenter : ForgetPasswordContract.Presenter(){

    override fun netSendRegisterCode(mActivity: AppCompatActivity, phone: String) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/member/pushCodeForgetApp")
            .tag(this)
            .params("phone", phone)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    T.info(mActivity, response.body().msg)
                    mView.netSendRegisterCode(response.body().data)
                }
            })
    }

    override fun netVerifyRegister(mActivity: AppCompatActivity, logid: String, code: String) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/member/checkcodeApp")
            .tag(this)
            .params("logid", logid)
            .params("verifyNum", code)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    mView.netVerifyRegister(response.body().data)
//                    T.success(response.body().msg)
                }
            })
    }

    override fun netAppForgetPassword(mActivity: AppCompatActivity, username: String, password: String) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/member/changeForgetPassword")
            .tag(this)
            .params("phone", username)
            .params("passwd", password)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    T.success(mActivity, response.body().msg)
                    mView.netAppForgetPassword(response.body().data)
                }
            })
    }
}
