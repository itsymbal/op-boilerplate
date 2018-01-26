package com.orangepenguin.boilerplate.util

interface SharedPreferencesUtil {

    fun getPreference(key: String, defaultValue: String?): String?
    fun getPreference(key: String, defaultValue: Boolean): Boolean

    fun savePreference(key: String, value: String)
    fun savePreference(key: String, value: Boolean)

    fun clearPreference(key: String)
}
