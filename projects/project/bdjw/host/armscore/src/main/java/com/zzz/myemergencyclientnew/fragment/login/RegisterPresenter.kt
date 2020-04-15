package com.zzz.myemergencyclientnew.fragment.login;

import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.fastjson.JSONObject
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.toast.T

class RegisterPresenter : RegisterContract.Presenter() {
    override fun openLiabilityDialog(mActivity: AppCompatActivity) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/member/getWaiver")
            .tag(this)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    MaterialDialog(mActivity).show {
                        title(text = "${response.body().data}")
                        positiveButton(text = "是")
//                        negativeButton(text = "否")
                    }
                }
            })
    }

    override fun netSendRegisterCode(mActivity: AppCompatActivity, phone: String) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/member/pushCodeRegisterApp")
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

    override fun netAppRegister(mActivity: AppCompatActivity, username: String, password: String) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/member/register")
            .tag(this)
            .params("username", username)
            .params("password", password)
            .params("phone", username)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    T.success(mActivity, response.body().msg)
                    mView.netAppRegister(response.body().data)
                }
            })
    }
}
