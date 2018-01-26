package com.orangepenguin.boilerplate.util

class SharedPreferenceUtilInMemoryImpl : SharedPreferencesUtil {

    override fun getPreference(key: String, defaultValue: String?): String? =
            stringRepository[key] ?: defaultValue

    override fun getPreference(key: String, defaultValue: Boolean): Boolean =
            booleanRepository[key] ?: defaultValue

    override fun savePreference(key: String, value: String) {
        stringRepository[key] = value
    }

    override fun savePreference(key: String, value: Boolean) {
        booleanRepository[key] = value
    }

    override fun clearPreference(key: String) {
        stringRepository.remove(key)
    }

    companion object {
        private val stringRepository = HashMap<String, String>()
        private val booleanRepository = HashMap<String, Boolean>()
    }
}
