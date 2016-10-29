package com.codepath.apps.restclienttemplate.presenter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.activity.DetailTweetActivity;
import com.codepath.apps.restclienttemplate.activity.MainActivity;
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

    public static final String TAG = MainTweetPresenter.class.getSimpleName().toUpperCase();

    ItemTweetAdapter adapter;
    int pageCount = 0;
    MainActivity activity;

    public MainTweetPresenter(final MainActivity activity) {
        this.activity = activity;
        adapter = new ItemTweetAdapter();
        adapter.setListener(new ItemTweetAdapter.Listener() {
            @Override
            public void onLoadMore() {
                activity.loadMore.setVisibility(View.VISIBLE);
                pageCount += 1;
                fetchData(pageCount);
            }

            @Override
            public void onClick(Tweet tweet) {
                activity.startActivity(new Intent(activity, DetailTweetActivity.class));
            }
        });
    }

    public ItemTweetAdapter getAdapter(){
        return this.adapter;
    }

    public void fetchData(final int page){
        pageCount = page;
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
                    if (page == 0)
                        adapter.getData(tweets);
                    else
                        adapter.addData(tweets);
                }

                activity.loadMore.setVisibility(View.GONE);
                activity.refresh.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + throwable );
            }
        });
    }

}
