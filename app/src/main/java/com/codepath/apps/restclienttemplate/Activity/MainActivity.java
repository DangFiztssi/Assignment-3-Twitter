package com.codepath.apps.restclienttemplate.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.presenter.MainTweetPresenter;
import com.codepath.apps.restclienttemplate.utils.AppUtils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class MainActivity extends AppCompatActivity implements ComposeTweetDialogFragment.tweetListener {

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;

    @BindView(R.id.progressLoadMore)
    public ProgressBar loadMore;

    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout refresh;

    @BindView(R.id.fab)
    public FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    MainTweetPresenter presenter;
    public Dialog loading;

    FragmentManager manager1 = getSupportFragmentManager();
    ComposeTweetDialogFragment df = ComposeTweetDialogFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loading = AppUtils.getWaitingDialog(this);
        loading.show();

        setSupportActionBar(toolbar);

        presenter = new MainTweetPresenter(this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTweets.setLayoutManager(manager);
        rvTweets.setAdapter(new SlideInBottomAnimationAdapter(presenter.getAdapter()));

        presenter.fetchData(1);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading.show();
                presenter.fetchData(1);
            }
        });

        df = ComposeTweetDialogFragment.newInstance();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.show(manager1, "fragment_compose");
            }
        });
    }

    @Override
    public void postTweet(final String tweet) {

        loading.show();
        RestApplication.getRestClient().postTweet(tweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson GSON = new Gson();
                Tweet newTweet = GSON.fromJson(response.toString(), Tweet.class);
                presenter.addPostTweet(newTweet);
                rvTweets.scrollToPosition(0);
                df.dismiss();
                loading.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Snackbar snackbar = AppUtils.showSnackBar(getCurrentFocus(), "Ops, your tweet failed, try again!");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    snackbar.setAction("retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            postTweet(tweet);
                            loading.dismiss();
                        }
                    })
                            .setActionTextColor(getColor(android.R.color.holo_blue_bright));
                    snackbar.show();
                }
            }
        });

    }
}
