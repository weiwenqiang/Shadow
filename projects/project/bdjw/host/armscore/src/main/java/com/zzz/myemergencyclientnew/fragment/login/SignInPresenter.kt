package com.zzz.myemergencyclientnew.fragment.login;

import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSONObject
import com.zzz.myemergencyclientnew.callback.StringDialogCallback
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.encrypt.rsa.KeyUtils
import com.zzz.myemergencyclientnew.utils.SPUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.toast.T

class SignInPresenter : SignInContract.Presenter() {
    override fun netAppLogin(mActivity: AppCompatActivity, username: String, password: String) {
        OkGo.post<String>(Api.JAVA_SERVICE_URL + "/app/member/login")
//        OkGo.post<String>("http://192.168.81.230:9900" + "/app/member/login")
            .tag(this)
//            .headers("projectId", C.projectId)
//            .headers("projectType", C.projectType)
            .params("username", username)
            .params("password", KeyUtils.sha1(password))
            .execute(object : StringDialogCallback(mActivity) {
                override fun onSuccess(response: Response<String>) {
                    val jsonObject = JSONObject.parseObject(response.body().toString())
                    val code = jsonObject.getInteger("code")
                    val data = jsonObject.getString("data")
                    val msg = jsonObject.getString("msg")
                    T.success(mActivity, msg)
                    if (code == C.API_SUCCEED) {
//                        C.login(mActivity, data)
//                        T.success(mActivity, data)
//                        C.login(mActivity, data)
                        mView.netAppLogin(data)
                        netAuthRegister(mActivity)
//                        mActivity.finish()
                    }
                }

                override fun onError(response: Response<String>) {
                }
            })
    }

    override fun netAuthRegister(mActivity: AppCompatActivity?) {
        OkGo.post<String>("http://192.168.81.229:9090" + "/v1.01/publish/user/register")
//        OkGo.post<String>(Api.JAVA_SERVICE_URL + "/app/member/login")
            .tag(this)
            .headers("projectId", C.projectId)
            .headers("projectType", C.projectType)
            .headers("token", SPUtils.get(mActivity!!, C.sp_token, "") as String)
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>) {

                }

                override fun onError(response: Response<String>) {
                }
            })
    }
}
