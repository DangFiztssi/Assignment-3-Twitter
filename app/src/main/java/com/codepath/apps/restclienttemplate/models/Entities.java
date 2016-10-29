package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DangF on 10/29/16.
 */

public class Entities {
    @SerializedName("media")
    List<MediaTweet> objMedia;

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

    public class MediaTweet{
        @SerializedName("display_url")
        String url;

        public String getUrl(){
            return url;
        }
    }
}
