package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DangF on 10/29/16.
 */

public class Entities implements Parcelable{
    @SerializedName("media")
    List<MediaTweet> objMedia;

    protected Entities(Parcel in) {
        objMedia = in.createTypedArrayList(MediaTweet.CREATOR);
    }

    public static final Creator<Entities> CREATOR = new Creator<Entities>() {
        @Override
        public Entities createFromParcel(Parcel in) {
            return new Entities(in);
        }

        @Override
        public Entities[] newArray(int size) {
            return new Entities[size];
        }
    };

    //    public String getDisplauUrl(){
//        String url = "";
//        try{
//            JSONObject obj = objMedia.getJSONObject(0);
//            url = obj.getString("display_url");
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return url;
//    }
    public String getUrl(){
        if (objMedia != null && objMedia.size() > 0) {
            Log.e("url", objMedia.get(0).getUrl() + "");
            return objMedia.get(0).getUrl();
        }
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(objMedia);
    }

    public static class MediaTweet implements Parcelable {
        @SerializedName("display_url")
        String url;

        @SerializedName("media_url")
        String mediaUrl;

        @SerializedName("expanded_url")
        String expandedUrl;

        protected MediaTweet(Parcel in) {
            url = in.readString();
            mediaUrl = in.readString();
            expandedUrl = in.readString();
        }

        public static final Creator<MediaTweet> CREATOR = new Creator<MediaTweet>() {
            @Override
            public MediaTweet createFromParcel(Parcel in) {
                return new MediaTweet(in);
            }

            @Override
            public MediaTweet[] newArray(int size) {
                return new MediaTweet[size];
            }
        };

        public String getUrl(){
            return mediaUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeString(mediaUrl);
            dest.writeString(expandedUrl);
        }
    }
}
