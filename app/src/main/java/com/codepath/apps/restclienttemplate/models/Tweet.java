package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DangF on 10/26/16.
 */

public class Tweet implements Parcelable{
    @SerializedName("id_str")
    private String id;

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

    @SerializedName("favorite_count")
    private int favorited;

    @SerializedName("retweeted")
    private boolean isRetweeted;

    @SerializedName("entities")
    private Entities entities;

    protected Tweet(Parcel in) {
        id = in.readString();
        createDate = in.readString();
        isFavorited = in.readByte() != 0;
        body = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        retweet = in.readInt();
        favorited = in.readInt();
        isRetweeted = in.readByte() != 0;
        entities = in.readParcelable(Entities.class.getClassLoader());
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    public String getId() {
        return id;
    }

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

    public boolean isRetweeted() {
        return isRetweeted;
    }

    public void setCountRetweet(int retweet) {
        this.retweet = retweet;
    }

    public void setCountFavorited(int favorited) {
        this.favorited = favorited;
    }

    public void setRetweeted(boolean retweeted) {
        isRetweeted = retweeted;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public String getDisplayUrl(){
        if(entities != null)
            return entities.getUrl();
        return "";
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", createDate='" + createDate + '\'' +
                ", isFavorited=" + isFavorited +
                ", body='" + body + '\'' +
                ", user=" + user +
                ", retweet=" + retweet +
                ", favorited=" + favorited +
                ", isRetweeted=" + isRetweeted +
                ", entities=" + entities +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createDate);
        dest.writeByte((byte) (isFavorited ? 1 : 0));
        dest.writeString(body);
        dest.writeParcelable(user, flags);
        dest.writeInt(retweet);
        dest.writeInt(favorited);
        dest.writeByte((byte) (isRetweeted ? 1 : 0));
        dest.writeParcelable(entities, flags);
    }
}
