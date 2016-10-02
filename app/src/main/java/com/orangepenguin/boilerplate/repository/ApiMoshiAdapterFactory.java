package com.orangepenguin.boilerplate.repository;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

@MoshiAdapterFactory
public abstract class ApiMoshiAdapterFactory implements JsonAdapter.Factory {
    // Static factory method to access the package
    // private generated implementation
    public static JsonAdapter.Factory create() {
        return new AutoValueMoshi_ApiMoshiAdapterFactory();
    }
}