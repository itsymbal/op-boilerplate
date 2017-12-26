package com.orangepenguin.boilerplate.util

interface SharedPreferencesUtil {

    fun getPreference(key: String, defaultValue: String?): String?
    fun savePreference(key: String, value: String)
    fun clearPreference(key: String)
}
