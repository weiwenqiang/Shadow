package com.zzz.myemergencyclientnew.utils

import com.google.gson.Gson
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

object GsonUtils {
    var gson: Gson = Gson()

    fun <T> jsonToArrayList(json: String, clazz: Class<T>): ArrayList<T> {
        val type: Type = object : TypeToken<ArrayList<JsonObject>>() {}.getType()
        val jsonObjects: ArrayList<JsonObject> =
            gson.fromJson(json, type)
        val arrayList: ArrayList<T> = ArrayList()
        for (jsonObject in jsonObjects) {
            arrayList.add(
                gson.fromJson(
                    jsonObject,
                    clazz
                )
            )
        }
        return arrayList
    }
}