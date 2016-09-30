package com.orangepenguin.boilerplate.repository;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * generated using jsonschema2pojo.org using Gson annotation style.
 * TODO: probably can make properties default access (package-private)
 */
@Generated("org.jsonschema2pojo")
@AutoValue
@AutoGson
public abstract class ApiUser {
    public static TypeAdapter<ApiUser> typeAdapter(Gson gson) {
        return new AutoValue_ApiUser.GsonTypeAdapter(gson);
    }

    @SerializedName("avatar_url")
    public abstract String avatarUrl();

    @SerializedName("repos_url")
    public abstract String reposUrl();

    @SerializedName("name")
    public abstract String name();

    @SerializedName("company")
    public abstract String company();

    @SerializedName("login")
    public abstract String login();
}
