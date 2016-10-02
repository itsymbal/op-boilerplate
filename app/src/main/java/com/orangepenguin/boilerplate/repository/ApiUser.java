package com.orangepenguin.boilerplate.repository;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class ApiUser {
    public static JsonAdapter<ApiUser> jsonAdapter(Moshi moshi) {
        return new AutoValue_ApiUser.MoshiJsonAdapter(moshi);
    }

    @Json(name = "avatar_url")
    public abstract String avatarUrl();

    @Json(name = "repos_url")
    public abstract String reposUrl();

    @Json(name = "name")
    public abstract String name();

    @Json(name = "company")
    public abstract String company();

    @Json(name = "login")
    public abstract String login();
}
