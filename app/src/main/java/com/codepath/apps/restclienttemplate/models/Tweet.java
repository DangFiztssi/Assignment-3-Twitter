package com.codepath.apps.restclienttemplate.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DangF on 10/26/16.
 */

public class Tweet {
    @SerializedName("created_at")
    private String createDate;

    @SerializedName("favorited")
    private boolean isFavorited;

    @SerializedName("text")
    private String body;

    @SerializedName("user")
    private User user;

    @SerializedName("retweet_count")
    private int retweet;

    @SerializedName("favourite_count")
    private int favorited;

    public String getCreateDate() {
        return createDate;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public int getRetweet() {
        return retweet;
    }

    public int getFavorited() {
        return favorited;
    }

}
