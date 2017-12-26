package com.orangepenguin.boilerplate.util

import android.content.Context

class AndroidSharedPreferenceUtil(private val context: Context) : SharedPreferencesUtil {

    override fun getPreference(key: String, defaultValue: String?): String? =
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(key, defaultValue)

    override fun savePreference(key: String, value: String) =
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply()

    override fun clearPreference(key: String) =
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().remove(key).apply()

    companion object {
        private val FILE_NAME = "APPLICATION_PREFERENCES"
    }
}
