package com.orangepenguin.boilerplate.util;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferenceUtilInMemoryImpl implements SharedPreferencesUtil {

    private static Map<String, String> repository = new HashMap<>();

    @Override
    public String getPreference(String key, String defaultValue) {
        if (!repository.containsKey(key)) {
            return defaultValue;
        }
        return repository.get(key);
    }

    @Override
    public void savePreference(String key, String value) {
        repository.put(key, value);
    }

    @Override
    public void clearPreference(String key) {
        repository.remove(key);
    }
}
