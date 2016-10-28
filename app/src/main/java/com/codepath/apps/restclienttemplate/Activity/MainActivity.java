package com.codepath.apps.restclienttemplate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.presenter.MainTweetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;

    MainTweetPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainTweetPresenter();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rvTweets.setLayoutManager(manager);
        rvTweets.setAdapter(new SlideInBottomAnimationAdapter(presenter.getAdapter()));

        presenter.fetchData(0);
    }
}
