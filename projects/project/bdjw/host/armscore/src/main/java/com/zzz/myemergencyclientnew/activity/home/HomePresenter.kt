package com.zzz.myemergencyclientnew.activity.home

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.zzz.myemergencyclientnew.callback.JsonCallback
import com.zzz.myemergencyclientnew.callback.RespJson
import com.zzz.myemergencyclientnew.constant.pref.Api
import com.zzz.myemergencyclientnew.constant.pref.C
import com.zzz.myemergencyclientnew.entity.ApkFunctionEntity
import com.zzz.myemergencyclientnew.entity.HomeBottomNavigationEntity
import com.zzz.myemergencyclientnew.entity.NavigationEntity
import com.zzz.myemergencyclientnew.utils.SPUtils

class HomePresenter : HomeContract.Presenter() {
    override fun netInitData(mActivity: Activity) {
        OkGo.post<RespJson<List<HomeBottomNavigationEntity>>>(Api.NEW_JAVA_SERVICE_URL + "/app/home/allFunctionList")
            .tag(this)
            .execute(object : JsonCallback<RespJson<List<HomeBottomNavigationEntity>>>() {
                override fun onSuccess(response: Response<RespJson<List<HomeBottomNavigationEntity>>>) {
                    mView.netInitData(response.body().data)
                }
            })
    }
    override fun netInitData2(mActivity: Activity) {
        OkGo.post<RespJson<List<ApkFunctionEntity>>>(Api.NEW_JAVA_SERVICE_URL+Api.MAIN_FUN_API)
//        OkGo.post<RespJson<List<ApkFunctionEntity>>>(Api.NEW_JAVA_SERVICE_URL + "/app/function/allFunctionList")
            .tag(this)
            .headers("projectId", C.projectId)
            .headers("projectType", C.projectType)
            .headers("token",
                SPUtils.get(
                    mActivity,
                    C.sp_token,
                    ""
                ) as String
            )
            .execute(object : JsonCallback<RespJson<List<ApkFunctionEntity>>>() {
                override fun onSuccess(response: Response<RespJson<List<ApkFunctionEntity>>>) {
                    mView.netInitData2(response.body().data)
                }
            })
    }

    override fun netInitData3(mActivity: Activity) {
        OkGo.post<RespJson<List<NavigationEntity>>>(Api.NEW_JAVA_SERVICE_URL+Api.MAIN_FUN_API)
//        OkGo.post<RespJson<List<ApkFunctionEntity>>>(Api.NEW_JAVA_SERVICE_URL + "/app/function/allFunctionList")
            .tag(this)
            .headers("projectId", C.projectId)
            .headers("projectType", C.projectType)
            .headers("token",
                SPUtils.get(
                    mActivity,
                    C.sp_token,
                    ""
                ) as String
            )
            .execute(object : JsonCallback<RespJson<List<NavigationEntity>>>() {
                override fun onSuccess(response: Response<RespJson<List<NavigationEntity>>>) {
                    mView.netInitData3(response.body().data)
                }
            })
    }
}