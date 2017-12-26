package com.orangepenguin.boilerplate.util

import java.util.*

class SharedPreferenceUtilInMemoryImpl : SharedPreferencesUtil {

    override fun getPreference(key: String, defaultValue: String?): String? = repository[key] ?: defaultValue

    override fun savePreference(key: String, value: String) {
        repository.put(key, value)
    }

    override fun clearPreference(key: String) {
        repository.remove(key)
    }

    companion object {
        private val repository = HashMap<String, String>()
    }
}
