package com.zzz.myemergencyclientnew.fragment.home

import android.app.Activity
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.fragment.home.NewsContract

class NewsPresenter : NewsContract.Presenter() {
    override fun netInitData(mActivity: Activity) {
        OkGo.post<RespJson<String>>(Api.JAVA_SERVICE_URL + "/app/home/news")
//            .headers("token", C.getToken())
            .tag(this)
            .execute(object : JsonCallback<RespJson<String>>() {
                override fun onSuccess(response: Response<RespJson<String>>) {
                    mView.netInitData(response.body().data)
                }

                override fun onError(response: Response<RespJson<String>>) {

                }
            })
    }

//    override fun setLocation(mActivity: AppCompatActivity) {
//        MaterialDialog(mActivity).show {
//            title(text = "修改请求地址")
//            input(
//                hint = "请输入IP:端口号",
//                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
//            ) { _, text ->
//                Api.JAVA_SERVICE_URL = "http://"+"$text"
//            }
//            positiveButton(R.string.confirm)
//            negativeButton(R.string.cancel)
//        }
//    }
}