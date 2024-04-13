package org.example.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JSONManager {
    companion object {
        inline fun <reified T> jsonToMap(json: String): Map<String, T> {
            val mapType = object : TypeToken<Map<String, T>>() {}.type
            return Gson().fromJson(json, mapType)
        }
    }
}
