package com.orangepenguin.boilerplate.util

import android.os.Bundle
import java.util.*

object BundleUtil {
    fun mapToBundle(map: Map<String, String>): Bundle {
        val out = Bundle()
        for (key in map.keys) {
            out.putString(key, map[key])
        }
        return out
    }

    fun bundleToMap(bundle: Bundle?): Map<String, String> {
        if (bundle == null) {
            return emptyMap()
        }
        val stateMap = HashMap<String, String>()
        val keySet = bundle.keySet()
        for (key in keySet) {
            stateMap.put(key, bundle.getString(key))
        }
        return stateMap
    }
}
