package com.codepath.apps.restclienttemplate.presenter;

import android.content.Intent;
import android.os.Bundle;
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
    int pageCount = 1;
    MainActivity activity;

    public MainTweetPresenter(final MainActivity activity) {
        this.activity = activity;
        adapter = new ItemTweetAdapter();
        adapter.setListener(new ItemTweetAdapter.Listener() {
            @Override
            public void onLoadMore() {
                activity.loadMore.setVisibility(View.VISIBLE);
                Log.e(TAG, "onLoadMore: before" + pageCount );
                pageCount  = pageCount + 1;
                Log.e(TAG, "onLoadMore: after" + pageCount );
                fetchData(pageCount);
            }

            @Override
            public void onClick(Tweet tweet) {
                Bundle bundle =new Bundle();
                bundle.putParcelable("tweet", tweet);
                Intent i = new Intent(activity, DetailTweetActivity.class);
                i.putExtra("data",bundle);
                activity.startActivity(i);
            }

            @Override
            public void onClickFavorite(String id, ItemTweetAdapter.Result result) {
                onClickFav(id, result);
            }

            @Override
            public void onDestroyFavorite(String id, ItemTweetAdapter.Result result) {
                onDestroyFav(id, result);
            }
        });
    }

    public void addPostTweet(Tweet tweet){
        adapter.tweets.add(0, tweet);
        adapter.notifyItemInserted(0);
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
                    if (page == 1)
                        adapter.getData(tweets);
                    else
                        adapter.addData(tweets);
                }

                activity.loadMore.setVisibility(View.GONE);
                activity.refresh.setRefreshing(false);
                activity.loading.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + throwable );
                activity.loading.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                activity.loading.dismiss();
            }
        });
    }

    private void onClickFav(String id, final ItemTweetAdapter.Result result){
        RestApplication.getRestClient().favoriteTweet(id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                result.onSuccess();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void onDestroyFav(String id, final ItemTweetAdapter.Result result){
        RestApplication.getRestClient().destroyFavoriteTweet(id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                result.onSuccess();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                result.onFailure(throwable);
            }
        });
    }

}
