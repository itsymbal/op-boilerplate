package com.orangepenguin.boilerplate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * generated using jsonschema2pojo.org using Gson annotation style.
 * TODO: probably don't need @Expose annotation, and probably can make properties default access (package-private)
 * TODO: use AutoValue with Parcel extension to save this in a Parcel for saving state
 */
@Generated("org.jsonschema2pojo")
public class User implements Serializable {

    @SerializedName("login")
    @Expose
    public String login;
    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;
    @SerializedName("repos_url")
    @Expose
    public String reposUrl;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("company")
    @Expose
    public String company;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}