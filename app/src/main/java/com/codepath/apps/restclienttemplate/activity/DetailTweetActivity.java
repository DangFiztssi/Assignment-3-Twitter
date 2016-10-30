package com.codepath.apps.restclienttemplate.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.utils.AppUtils;
import com.codepath.apps.restclienttemplate.utils.PatternEditableBuilder;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTweetActivity extends AppCompatActivity {

    @BindView(R.id.imgAvatarDetail)
    ImageView imgAvatar;
    @BindView(R.id.tvUserNameTwitter)
    TextView tvUserName;
    @BindView(R.id.tvScreenNameTwitter)
    TextView tvScreenName;
    @BindView(R.id.tvBodyTweet)
    TextView tvBody;
    @BindView(R.id.imgPhotoTweetDetail)
    ImageView imgPhoto;
    @BindView(R.id.tvCreateDate)
    TextView tvcreatDate;
    @BindView(R.id.tvCountRetweetDetail)
    TextView tvRetweet;
    @BindView(R.id.tvCountFavouriteDetail)
    TextView tvFav;
    @BindView(R.id.icRetweetDetail)
    ImageView icRetweet;
    @BindView(R.id.icFavDetail)
    ImageView icFav;
    @BindView(R.id.icShareDetail)
    ImageView icShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tweet);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#65BBF2"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        Tweet tweet = getIntent().getExtras().getBundle("data").getParcelable("tweet");

        final Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        tvUserName.setTypeface(font, Typeface.BOLD);
        tvScreenName.setTypeface(font);
        tvBody.setTypeface(font);
        tvcreatDate.setTypeface(font);
        tvFav.setTypeface(font,Typeface.BOLD);
        tvRetweet.setTypeface(font,Typeface.BOLD);

        setupData(tweet);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            super.onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    private void setupData(Tweet tweet) {
        Picasso.with(this)
                .load(tweet.getUser().getAvatar())
                .into(imgAvatar);

        tvUserName.setText(tweet.getUser().getUsername());
        tvScreenName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        new PatternEditableBuilder()
                .addPattern(Pattern.compile("\\@(\\w+)"), Color.parseColor("#ff33b5e5"),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                            }
                        })
                .addPattern(Pattern.compile("\\#(\\w+)"), Color.parseColor("#ff33b5e5"))
                .into(tvBody);

        if (!tweet.getDisplayUrl().equalsIgnoreCase(""))
            Picasso.with(this)
                    .load(tweet.getDisplayUrl())
                    .into(imgPhoto);
        else
            imgPhoto.setVisibility(View.GONE);

        tvcreatDate.setText(AppUtils.getRelativeTimeAgo(tweet.getCreateDate()));

        tvFav.setText(tweet.getFavorited() + "");
        tvRetweet.setText(tweet.getRetweet() + "");

        if(tweet.isFavorited())
            icFav.setImageResource(R.drawable.ic_favorite_checked);

        if(tweet.isRetweeted())
            icRetweet.setImageResource(R.drawable.ic_retweet_checked);
    }


}
