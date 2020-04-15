package com.zzz.myemergencyclientnew.activity.plugin

import android.app.Activity
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.entity.PluginListEntity

class PluginListPresenter : PluginListContract.Presenter() {
    override fun netInitData(mActivity: Activity) {
        OkGo.post<RespJson<List<PluginListEntity>>>(Api.JAVA_SERVICE_URL + "/app/plugin/allPluginList")
            .tag(this)
            .execute(object : JsonCallback<RespJson<List<PluginListEntity>>>() {
                override fun onSuccess(response: Response<RespJson<List<PluginListEntity>>>) {
                    mView.netInitData(response.body().data)
                }
            })
    }
}