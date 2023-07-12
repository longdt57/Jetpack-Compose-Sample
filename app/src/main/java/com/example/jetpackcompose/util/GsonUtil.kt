package com.example.jetpackcompose.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

@SuppressWarnings("SwallowedException")
object GsonUtil {

    inline fun <reified T> fromJson(json: String?): T? {
        return try {
            Gson().fromJson(json, T::class.java)
        } catch (ex: JsonSyntaxException) {
            null
        }
    }
}
