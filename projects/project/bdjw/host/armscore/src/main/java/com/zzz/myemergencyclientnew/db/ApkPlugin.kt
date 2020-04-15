package com.zzz.myemergencyclientnew.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// 你的模型扩展RealmObject。此外,该类必须与开放(芬兰湾的科特林类注释的决赛默认情况下)
open class ApkPluginEntity(
    //构造函数,只要你可以把属性都是初始化
//默认值。这将确保一个空构造函数生成。
//默认所有属性都依然存在。
//属性可以与PrimaryKey注释或索引。
//如果使用非空类型属性必须初始化与非空值。
    @PrimaryKey var id: String = "",
//    //标题(手写)
    var title: String = "",
////常态图标(选择或上传)
//    var iconNormal: String = "",
////选中图标(选择或上传)
//    var iconChecked: String = "",
////apk插件-api接口（下拉框，两选项）
//    var funType: Int = 0,
//apk下载地址（手写或选择）
    var downloadLink: String = "",
////android文件夹层级路径（手写）
//    var filePath: String = "",
//apk文件名（手写）
    var apkFileName: String = "",
//apk版本号（手写或系统自动生成）
    var apkVersionCode: Int = 0,
//apk版本名（手写）
    var apkVersionName: String = "",
////显示方位 t b l r f 上下左右漂浮（下拉框，5选项）
//    var orient: String = "",
////点击事件类型：导航-路由页面跳转-打开web页面（下拉框，多选项）
//    var click: Int = 0,
////携带参数列表【{k,v}，{k,v}，{k,v}】(手写)
//    var params: String = "",
////页面路由地址（下拉框加手写）
//    var route: String = "",
//接口地址（下拉框加手写）
//    var api: String = "",
////接口版本号（下拉框加手写）
//    var apiVersion: String = "",
////功能状态：启用-关闭-废弃-测试（下拉框，多选项）
//    var state: Int = 0,
//入口地址
    var enter: String = "",
//广播入口
    var receiver: String = "",
//广播携带标识
    var receiverAction: String = "",
//唯一标识
    var plugCode: String = ""
) : RealmObject() {
    //芬兰湾的科特林编译器生成标准的getter和setter。
    //领域将过载和代码里面是忽略。
    //如果你喜欢你也可以只有空洞的抽象方法。
}