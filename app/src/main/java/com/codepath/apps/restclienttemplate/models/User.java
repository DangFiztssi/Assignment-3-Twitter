package com.codepath.apps.restclienttemplate.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DangF on 10/26/16.
 */

public class User {
    public static final String TAG = User.class.getSimpleName().toUpperCase();

    @SerializedName("name")
    private String username;

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("profile_image_url_https")
    private String avatar;

    public String getUsername() {
        return username;
    }

    public String getScreenName() {
        return "@" + screenName;
    }

    public String getAvatar() {
        return avatar.replace("_normal","");
    }
}
