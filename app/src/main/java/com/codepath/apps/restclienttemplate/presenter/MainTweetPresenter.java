package com.codepath.apps.restclienttemplate.presenter;

import android.util.Log;

import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.adapter.ItemTweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DangF on 10/28/16.
 */

public class MainTweetPresenter {

    ItemTweetAdapter adapter;

    public MainTweetPresenter() {
        adapter = new ItemTweetAdapter();
    }

    public ItemTweetAdapter getAdapter(){
        return this.adapter;
    }

    public void fetchData(final int page){
        RestApplication.getRestClient().getHomeTimeline(page, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                List<Tweet> tweets = new ArrayList<Tweet>();
                Gson GSON = new Gson();
                for(int i = 0; i< response.length();i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Tweet tweet = GSON.fromJson(obj.toString(), Tweet.class);
                        tweets.add(tweet);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(!tweets.isEmpty()) {
                    Log.e("onSuccess: ", tweets.size() + "");
                    if (page == 0)
                        adapter.getData(tweets);
                    else
                        adapter.addData(tweets);
                }

            }
        });
    }

}
