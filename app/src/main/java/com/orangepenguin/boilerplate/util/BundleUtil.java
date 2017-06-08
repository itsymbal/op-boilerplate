package com.orangepenguin.boilerplate.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BundleUtil {
    @NonNull
    public static Bundle mapToBundle(@NonNull Map<String, String> map) {
        Bundle out = new Bundle();
        for (String key : map.keySet()) {
            out.putString(key, map.get(key));
        }
        return out;
    }

    @NonNull
    public static Map<String, String> bundleToMap(@Nullable Bundle bundle) {
        if (bundle == null) {
            return Collections.emptyMap();
        }
        Map<String, String> stateMap = new HashMap<>();
        Set<String> keySet = bundle.keySet();
        for (String key : keySet) {
            stateMap.put(key, bundle.getString(key));
        }
        return stateMap;
    }
}
