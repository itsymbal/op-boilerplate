package com.orangepenguin.boilerplate.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.orangepenguin.boilerplate.repository.ApiUser;

@AutoValue
public abstract class User implements Parcelable {
    public static Builder builder() {
        return new AutoValue_User.Builder();
    }

    public static Builder fromApiUser(ApiUser apiUser) {
        return builder()
                .avatarUrl(apiUser.avatarUrl())
                .reposUrl(apiUser.reposUrl())
                .name(apiUser.name())
                .company(apiUser.company())
                .login(apiUser.login());
    }

    public abstract Builder toBuilder();

    public abstract String avatarUrl();
    public abstract String reposUrl();
    public abstract String name();
    public abstract String company();
    public abstract String login();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder avatarUrl(String avatarUrl);
        public abstract Builder reposUrl(String reposUrl);
        public abstract Builder name(String name);
        public abstract Builder company(String company);
        public abstract Builder login(String login);
        public abstract User build();
    }
}