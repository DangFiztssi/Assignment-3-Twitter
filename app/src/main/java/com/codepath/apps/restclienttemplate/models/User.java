package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DangF on 10/26/16.
 */

public class User implements Parcelable {
    public static final String TAG = User.class.getSimpleName().toUpperCase();

    @SerializedName("name")
    private String username;

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("profile_image_url_https")
    private String avatar;

    protected User(Parcel in) {
        username = in.readString();
        screenName = in.readString();
        avatar = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getScreenName() {
        return "@" + screenName;
    }

    public String getAvatar() {
        return avatar.replace("_normal","");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(screenName);
        dest.writeString(avatar);
    }
}
